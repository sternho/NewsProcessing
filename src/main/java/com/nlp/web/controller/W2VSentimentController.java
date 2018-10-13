package com.nlp.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nlp.db.DatabaseFactory;
import com.nlp.db.dao.NLPNewsDao;
import com.nlp.db.dao.Word2VecDao;
import com.nlp.db.vo.NLPNews;
import com.nlp.db.vo.Word2Vec;

@Controller
public class W2VSentimentController {

	@RequestMapping(value = "/result_nlp_w2v", method = RequestMethod.GET)
	public ModelAndView keyWords() throws ClassNotFoundException, SQLException {
		ArrayList<Word2Vec> w2vRecords = getW2vDao().selectAll();
		List<CountResult> resultList = new ArrayList<CountResult>();
		
		for(int i=0; i<w2vRecords.size(); i++) {
			if(w2vRecords.get(i).getValue()==null)
				continue;
			String[] keys = w2vRecords.get(i).getValue().split(",");
			
			List<NLPNews> news = getNLPNewsDao().selectContentBykeyWords(keys);
			
			CountResult result = new CountResult();
			result.setKey(w2vRecords.get(i).getKey());
			result.setVeryNegative(news.stream().filter(tmp -> NLPNews.RESULT.VERY_NEGATIVE.name().equals(tmp.getDirect())).count());
			result.setNegative(news.stream().filter(tmp -> NLPNews.RESULT.NEGATIVE.name().equals(tmp.getDirect())).count());
			result.setNeutral(news.stream().filter(tmp -> NLPNews.RESULT.NEUTRAL.name().equals(tmp.getDirect())).count());
			result.setPositive(news.stream().filter(tmp -> NLPNews.RESULT.POSITIVE.name().equals(tmp.getDirect())).count());
			result.setVeryPositive(news.stream().filter(tmp -> NLPNews.RESULT.VERY_POSITIVE.name().equals(tmp.getDirect())).count());
			resultList.add(result);
		}
		
		ModelAndView model = new ModelAndView();
    	model.addObject("result", resultList);
    	model.setViewName("w2v_sentiment_result");
    	return model;
	}

	@RequestMapping(value = "/chart_nlp_w2v", method = RequestMethod.GET)
	public ModelAndView keyWords(@RequestParam(value = "product", required = true) String product) throws ClassNotFoundException, SQLException {
		Map<Date, String[]> news = null;
		
		Word2Vec w2vRecords = getW2vDao().selectById(product);
		if(w2vRecords.getValue()!=null) {
			String[] keys = w2vRecords.getValue().split(",");
			news = getNLPNewsDao().selectContentBykeyWordsGrpByDate(keys);
		}
		
		ModelAndView model = new ModelAndView();
    	model.addObject("result", news);
    	model.setViewName("w2v_sentiment_result");
    	return model;
	}

	public Word2VecDao getW2vDao() throws ClassNotFoundException {
		return (Word2VecDao) DatabaseFactory.getInstance().getDAO("Word2VecDao");
	}
	public NLPNewsDao getNLPNewsDao() throws ClassNotFoundException {
		return (NLPNewsDao) DatabaseFactory.getInstance().getDAO("NLPNewsDao");
	}
	
	public static class CountResult {
		String key;
		long veryNegative, negative, neutral, positive, veryPositive;
		
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public long getVeryNegative() {
			return veryNegative;
		}
		public void setVeryNegative(long veryNegative) {
			this.veryNegative = veryNegative;
		}
		public long getNegative() {
			return negative;
		}
		public void setNegative(long negative) {
			this.negative = negative;
		}
		public long getNeutral() {
			return neutral;
		}
		public void setNeutral(long neutral) {
			this.neutral = neutral;
		}
		public long getPositive() {
			return positive;
		}
		public void setPositive(long positive) {
			this.positive = positive;
		}
		public long getVeryPositive() {
			return veryPositive;
		}
		public void setVeryPositive(long veryPositive) {
			this.veryPositive = veryPositive;
		}
	}

}
