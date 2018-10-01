package com.nlp.manager.retriever;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nlp.db.DatabaseFactory;
import com.nlp.db.dao.NLPNewsDao;
import com.nlp.db.vo.NLPNews;
import com.nlp.util.parser.html.HTMLParser;

@Component
public class MarketWatchRetrieverMgr {
	
	private static final Logger logger = LoggerFactory.getLogger(MarketWatchRetrieverMgr.class);
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	private enum DATA_SOURCE {
		Market_Watch ("MAIN", "https://www.marketwatch.com/");

		private String category;
		private String link;
		DATA_SOURCE(String category, String link) {
			this.category = category;
			this.link = link;
		}
		
		public String getLink() {
			return this.link;
		}
		public String getCategory() {
			return this.category;
		}
	};
	
	@Scheduled(fixedRate = 30*1000)
	public void reportCurrentTime() {
		logger.info("MarketWatchRetrieverMgr start process time: {}", dateFormat.format(new Date()));
		
		DATA_SOURCE[] links = DATA_SOURCE.values();
		for(DATA_SOURCE link : links) {
			try {
				HTMLParser html = new HTMLParser(link.getLink());
				Elements details = html.getDetailsByClass("a[class^=link]");
				
				for(Element detail : details) {
					String id = detail.html();
					
					NLPNews news = new NLPNews();
					news.setSource("Markat Watch");
					news.setCategory(link.getCategory());
					news.setId(id);
					news.setContent(detail.html());
					news.setProcessed(false);
					
					NLPNews exist = getNLPNewsDao().selectById(id);
					if(exist==null) {
						getNLPNewsDao().insertOrUpdate(news);
					}
				}
			} catch (IOException e) {
				logger.error("Download HTML failure: " + link.name());
				e.printStackTrace();
			} catch (SQLException e) {
				logger.error("insert database failure: " + link.name());
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("Exception: " + link.name());
				e.printStackTrace();
			}
		}
	}
	
	public NLPNewsDao getNLPNewsDao() throws ClassNotFoundException {
		return (NLPNewsDao) DatabaseFactory.getInstance().getDAO("NLPNewsDao");
	}

}
