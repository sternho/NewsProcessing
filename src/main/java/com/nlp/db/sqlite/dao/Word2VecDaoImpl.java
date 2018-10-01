package com.nlp.db.sqlite.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.nlp.db.DatabaseFactory;
import com.nlp.db.dao.Word2VecDao;
import com.nlp.db.sqlite.SqliteDataSourceFactory;
import com.nlp.db.vo.Word2Vec;

public class Word2VecDaoImpl implements Word2VecDao {

	private static volatile Word2VecDaoImpl instance;

	public static Word2VecDaoImpl getInstance() {
		if (instance == null) {
			instance = new Word2VecDaoImpl();
		}
		return instance;
	}

	@Override
	public void insertOrUpdate(Word2Vec word) throws SQLException {
		SqliteDataSourceFactory datasource = (SqliteDataSourceFactory) DatabaseFactory.getInstance();

		PreparedStatement pstmt = datasource
				.getStatement("REPLACE INTO WORD2VEC(KEY, VALUE) VALUES(?, ?)");
		pstmt.setString(1, word.getKey());
		pstmt.setString(2, word.getValue());
		pstmt.executeUpdate();
	}

	@Override
	public void update(Word2Vec word) throws SQLException {
		SqliteDataSourceFactory datasource = (SqliteDataSourceFactory) DatabaseFactory.getInstance();

		PreparedStatement pstmt = datasource
				.getStatement("UPDATE WORD2VEC SET VALUE=? WHERE KEY=?");
		pstmt.setString(1, word.getValue());
		pstmt.setString(2, word.getKey());
		pstmt.executeUpdate();
	}

	@Override
	public ArrayList<Word2Vec> selectAll() throws SQLException {
		SqliteDataSourceFactory datasource = (SqliteDataSourceFactory) DatabaseFactory.getInstance();

		String sql = "SELECT KEY, VALUE FROM WORD2VEC";
		ArrayList<Word2Vec> returns = new ArrayList<Word2Vec>();

		Statement stmt = datasource.getStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			String key = rs.getString("KEY");
			String value = rs.getString("VALUE");

			Word2Vec word = new Word2Vec();
			word.setKey(key);
			word.setValue(value);

			returns.add(word);
		}

		return returns;
	}

	@Override
	public Word2Vec selectById(String id) throws SQLException {
		SqliteDataSourceFactory datasource = (SqliteDataSourceFactory) DatabaseFactory.getInstance();

		String sql = "SELECT KEY, VALUE FROM WORD2VEC WHERE KEY=?";
		PreparedStatement pstmt = datasource.getStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			String key = rs.getString("KEY");
			String value = rs.getString("VALUE");

			Word2Vec word = new Word2Vec();
			word.setKey(key);
			word.setValue(value);

			return word;
		}

		return null;
	}

}
