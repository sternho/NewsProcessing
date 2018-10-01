package com.nlp.db.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.nlp.db.vo.NLPNews;

public interface NLPNewsDao extends DAO {

	public void insertOrUpdate(NLPNews news) throws SQLException;
	public void update(NLPNews news) throws SQLException;
	public ArrayList<NLPNews> selectAll() throws SQLException;
	public ArrayList<NLPNews> selectNonProcessed() throws SQLException;
	public NLPNews selectById(String id) throws SQLException;
	public ArrayList<NLPNews> selectByDate(Date date) throws SQLException;
	
}
