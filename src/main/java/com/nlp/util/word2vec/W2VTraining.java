package com.nlp.util.word2vec;

import java.io.InputStream;
import java.util.Collection;

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.AggregatingSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class W2VTraining {
	private static final Logger logger = LoggerFactory.getLogger(W2VTraining.class);
	
	InputStream[] source;
	Word2Vec vec;
	
	public W2VTraining(InputStream[] stream) {
		source = stream;
	}
	
	public void start() {
		logger.info("Load & Vectorize Sentences....");
		// Strip white space before and after for each line
		
		AggregatingSentenceIterator.Builder builder = new AggregatingSentenceIterator.Builder();
		for(InputStream input : source) {
			builder.addSentenceIterator(new BasicLineIterator(input));
		}
		SentenceIterator iter = builder.build();
		
		// Split on white spaces in the line to get words
		TokenizerFactory t = new DefaultTokenizerFactory();

		/*
		 * CommonPreprocessor will apply the following regex to each token:
		 * [\d\.:,"'\(\)\[\]|/?!;]+ So, effectively all numbers, punctuation symbols and
		 * some special symbols are stripped off. Additionally it forces lower case for
		 * all tokens.
		 */
		t.setTokenPreProcessor(new CommonPreprocessor());
		logger.info("Building model....");
		vec = new Word2Vec.Builder().minWordFrequency(5).iterations(1).layerSize(100).seed(42).windowSize(5)
				.iterate(iter).tokenizerFactory(t).build();

		logger.info("Fitting Word2Vec model....");
		vec.fit();

		logger.info("Writing word vectors to text file....");
		
		// Word Vectors.
//		logger.info("Closest Words:");
//		Collection<String> lst = vec.wordsNearestSum("day", 10);
//		logger.info("10 Words closest to 'day': {}", lst);
	}
	
	public String[] get(String key) {
		Collection<String> lst = vec.wordsNearestSum(key, 10);
		return lst.toArray(new String[0]);
	}

}
