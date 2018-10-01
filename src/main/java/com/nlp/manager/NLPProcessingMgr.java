package com.nlp.manager;

import java.rmi.UnexpectedException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nlp.db.DatabaseFactory;
import com.nlp.db.dao.NLPNewsDao;
import com.nlp.db.vo.NLPNews;
import com.nlp.util.sentiment.SentimentAnalyst;

@Component
public class NLPProcessingMgr {
	private static final Logger logger = LoggerFactory.getLogger(NLPProcessingMgr.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	private SentimentAnalyst analyst = null;
	
	@PostConstruct
	public void init() {
		analyst = SentimentAnalyst.getInstance();
	}

	@Scheduled(fixedRate = 30 * 1000)
	public void reportCurrentTime() {
		logger.info("NLPProcessingMgr start process time: {}", dateFormat.format(new Date()));
		
		ArrayList<NLPNews> data = selectNonProcessingData();
		logger.info("NLPProcess records count: " + data.size());
		
		for(NLPNews news : data) {
			try {
				String result = analyst.calculates(news.getContent());
				news.setDirect(result);
				news.setProcessed(true);
				
				getNLPNewsDao().update(news);
			} catch (UnexpectedException e) {
				logger.error("NLPProcess error: " + news.getId());
				logger.error(e.toString());
			} catch (SQLException e) {
				logger.error("NLPProcess SQL error: " + news.getId());
				logger.error(e.toString());
			} catch (Exception e) {
				logger.error("NLPProcess error: " + news.getId());
				logger.error(e.toString());
			}
		}
		
	}

	private ArrayList<NLPNews> selectNonProcessingData() {
		try {
			return getNLPNewsDao().selectNonProcessed();
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return new ArrayList<NLPNews>();
	}

	public NLPNewsDao getNLPNewsDao() throws ClassNotFoundException {
		return (NLPNewsDao) DatabaseFactory.getInstance().getDAO("NLPNewsDao");
	}

}
