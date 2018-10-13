package com.nlp.db.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nlp.db.vo.NLPNews;

public interface NLPNewsDao extends DAO {

	public void insertOrUpdate(NLPNews news) throws SQLException;
	public void update(NLPNews news) throws SQLException;
	public ArrayList<NLPNews> selectAll() throws SQLException;
	public ArrayList<NLPNews> selectNonProcessed() throws SQLException;
	public NLPNews selectById(String id) throws SQLException;
	public ArrayList<NLPNews> selectByDate(Date date) throws SQLException;
	public ArrayList<NLPNews> selectContentBykeyWords(String[] id) throws SQLException;
	public Map<Date, String[]> selectContentBykeyWordsGrpByDate(String[] ids) throws SQLException;
	
}
