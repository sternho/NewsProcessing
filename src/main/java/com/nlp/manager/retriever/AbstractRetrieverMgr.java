package com.nlp.manager.retriever;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nlp.db.DatabaseFactory;
import com.nlp.db.dao.NLPNewsDao;
import com.nlp.db.vo.NLPNews;
import com.nlp.util.parser.html.HTMLParser;

@Component
public class AbstractRetrieverMgr {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractRetrieverMgr.class);
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	private enum DATA_SOURCE {
		FinancialTimes ("Financial Times", "https://www.ft.com/", "js-teaser-heading-link"),
		cnbc ("CNBC", "https://www.cnbc.com/world/?region=world", "headline"),
		marketWatch ("Markat Watch", "https://www.marketwatch.com/", "link"),
		wsj ("The Wall Street Journal", "https://www.wsj.com/news/markets", "wsj-headline-link"),
		reuters ("Reuters", "https://www.reuters.com/finance/markets", "story-title"),
		bbc ("BBC", "https://www.bbc.com/news/business", "title-link__title-text"),
		cityAM ("City A.M.", "http://www.cityam.com/markets", "h3");

		private String source;
		private String link;
		private String tags;
		DATA_SOURCE(String source, String link, String tags) {
			this.source = source;
			this.link = link;
			this.tags = tags;
		}
		
		public String getLink() {
			return this.link;
		}
		public String getSource() {
			return this.source;
		}
		
		public String getTags() {
			return this.tags;
		}
	};

//	@Scheduled(fixedRate = 30*1000)
	public void reportCurrentTime() {
		logger.info("FinancialTimesRetrieverMgr start process time: {}", dateFormat.format(new Date()));
		
		DATA_SOURCE[] links = DATA_SOURCE.values();
		for(DATA_SOURCE link : links) {
			System.out.println("Parse: " + link.getSource());
			try {
				HTMLParser html = new HTMLParser(link.getLink());
				Elements details = html.getDetailsByClass("a[class^=" + link.getTags() + "]");
				
				for(Element detail : details) {
					String id = detail.attr("data-content-id");
					
					NLPNews news = new NLPNews();
					news.setSource(link.getSource());
					news.setCategory("MAIN");
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
