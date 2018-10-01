package com.nlp.db;

import com.nlp.db.dao.DAO;
import com.nlp.db.sqlite.SqliteDataSourceFactory;

public abstract class DatabaseFactory {
	
	public abstract DAO getDAO(String classname) throws ClassNotFoundException;
	
	public static DatabaseFactory getInstance() {
		return SqliteDataSourceFactory.getInstance();
	}
	
}
