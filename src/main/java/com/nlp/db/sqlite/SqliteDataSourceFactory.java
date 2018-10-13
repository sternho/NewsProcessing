package com.nlp.db.sqlite;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nlp.db.DatabaseFactory;
import com.nlp.db.dao.DAO;

public class SqliteDataSourceFactory extends DatabaseFactory {
	private static final Logger logger = LoggerFactory.getLogger(SqliteDataSourceFactory.class);

	private static volatile SqliteDataSourceFactory instance;

	public static SqliteDataSourceFactory getInstance() {
		if (instance == null) {
			instance = new SqliteDataSourceFactory();
		}
		return instance;
	}

	private Connection connection = null;

	private SqliteDataSourceFactory() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/test.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Statement getStatement() throws SQLException {
		Statement stmt = connection.createStatement();
		stmt.setQueryTimeout(5); // set timeout to 5 sec.
		return stmt;
	}

	public PreparedStatement getStatement(String sql) throws SQLException {
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setQueryTimeout(30); // set timeout to 30 sec.
		return pstmt;
	}

	@Override
	public DAO getDAO(String classname) throws ClassNotFoundException {
		String classpath = "com.nlp.db.sqlite.dao." + classname + "Impl";
		try {
			Class<?> clazz = Class.forName(classpath);
			Method method = clazz.getMethod("getInstance");
			return (DAO) method.invoke(null);
		} catch (Exception e) {
			logger.error("SQLite Datasource getDao failure: " + classpath);
			logger.error(e.toString());
			e.printStackTrace();
		}
		throw new ClassNotFoundException(classpath + " cannot be found.");
	}

	// formatter

	private static DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static String datetimeFormatter(Date value) {
		if (value != null)
			return DATETIME_FORMAT.format(value);
		else
			return null;
	}
	public static Date datetimeFormatter(String value) {
		if (value != null) {
			try {
				return DATETIME_FORMAT.parse(value);
			} catch (ParseException e) {
				logger.error(e.toString());
			}
		}
		return null;
	}

	public static String dateFormatter(Date value) {
		if (value != null)
			return DATE_FORMAT.format(value);
		else
			return null;
	}
	public static Date dateFormatter(String value) {
		if (value != null) {
			try {
				return DATE_FORMAT.parse(value);
			} catch (ParseException e) {
				logger.error(e.toString());
			}
		}
		return null;
	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static String booleanFormatter(Boolean value) {
		if (value == null)
			return null;
		else
			return value ? "Y" : "N";
	}

	public static Boolean booleanFormatter(String value) {
		if ("Y".equals(value))
			return true;
		else if ("N".equals(value))
			return false;
		else
			return null;
	}

}
