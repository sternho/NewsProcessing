package com.nlp.util.sentiment;

import java.rmi.UnexpectedException;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class SentimentAnalyst {
	
	private static volatile SentimentAnalyst instance;
	public static SentimentAnalyst getInstance() {
		if(instance==null) {
			instance = new SentimentAnalyst();
		}
		return instance;
	}
	
	StanfordCoreNLP pipeline;
	
	private SentimentAnalyst() {
		Properties props = new Properties();
	    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
	    props.setProperty("ner.useSUTime", "false");
	    
	    pipeline = new StanfordCoreNLP(props);
	}
	
	public String calculates(String fullSentence) throws UnexpectedException {
		Annotation annotation = new Annotation(fullSentence);
	    pipeline.annotate(annotation);
	    
	    String result = null;
	    
		List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		if (sentences != null && ! sentences.isEmpty()) {
			CoreMap sentence = sentences.get(0);
			result = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
		}
		
//		if("Negative".equals(result)) {
//			return false;
//		} else if("Positive".equals(result)) {
//			return true;
//		} else if("Neutral".equals(result)) {
//			return true;
//		} else {
//			throw new UnexpectedException("Unhandled result: " + result);
//		}
		return result;
	}

}
