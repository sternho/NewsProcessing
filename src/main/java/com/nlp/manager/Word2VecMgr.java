package com.nlp.manager;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nlp.db.DatabaseFactory;
import com.nlp.db.dao.Word2VecDao;
import com.nlp.db.vo.Word2Vec;
import com.nlp.util.word2vec.W2VTraining;

public class Word2VecMgr {
	private static final Logger logger = LoggerFactory.getLogger(Word2VecMgr.class);
	
	private static volatile Word2VecMgr instance;
	public static Word2VecMgr getInstance() {
		if(instance==null) {
			instance = new Word2VecMgr();
		}
		return instance;
	}
	
	public void train(InputStream[] input) {
		W2VTraining w2v = new W2VTraining(input);
		w2v.start();
		
		try {
			ArrayList<Word2Vec> allData = getW2vDao().selectAll();
			
			for(Word2Vec data : allData) {
				String[] relatedValues = w2v.get(data.getKey());
				data.setValue(String.join(",", relatedValues));
				getW2vDao().update(data);
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		} catch (ClassNotFoundException e) {
			logger.error(e.toString());
		}
		
	}

	public Word2VecDao getW2vDao() throws ClassNotFoundException {
		return (Word2VecDao) DatabaseFactory.getInstance().getDAO("Word2VecDao");
	}
	
}
