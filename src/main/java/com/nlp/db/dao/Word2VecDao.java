package com.nlp.db.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.nlp.db.vo.Word2Vec;

public interface Word2VecDao extends DAO {

	public void insertOrUpdate(Word2Vec news) throws SQLException;
	public void update(Word2Vec news) throws SQLException;
	public ArrayList<Word2Vec> selectAll() throws SQLException;
	public Word2Vec selectById(String id) throws SQLException;
	
	
}
