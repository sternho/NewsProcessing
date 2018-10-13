package com.nlp.db.sqlite.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nlp.db.DatabaseFactory;
import com.nlp.db.dao.NLPNewsDao;
import com.nlp.db.sqlite.SqliteDataSourceFactory;
import com.nlp.db.vo.NLPNews;

public class NLPNewsDaoImpl implements NLPNewsDao {
//	private static final Logger logger = LoggerFactory.getLogger(NLPNewsDaoImpl.class);

	private static volatile NLPNewsDaoImpl instance;

	public static NLPNewsDaoImpl getInstance() {
		if (instance == null) {
			instance = new NLPNewsDaoImpl();
		}
		return instance;
	}
	
	@Override
	public void insertOrUpdate(NLPNews news) throws SQLException {
		SqliteDataSourceFactory datasource = (SqliteDataSourceFactory) DatabaseFactory.getInstance();

		PreparedStatement pstmt = datasource
				.getStatement("REPLACE INTO NLP_NEWS(SOURCE, CATEGORY, ID, CONTENT, DIRECT, PROCESSED, CREATE_DT) VALUES(?, ?, ?, ?, ?, ?, ?)");
		pstmt.setString(1, news.getSource());
		pstmt.setString(2, news.getCategory());
		pstmt.setString(3, news.getId());
		pstmt.setString(4, news.getContent());
//		pstmt.setString(5, SqliteDataSourceFactory.booleanFormatter(news.getDirect()));
		pstmt.setString(5, news.getDirect());
		pstmt.setString(6, SqliteDataSourceFactory.booleanFormatter(news.getProcessed()));
//		pstmt.setString(7, SqliteDataSourceFactory.dateFormatter(news.getCreateDt()));
		pstmt.setString(7, SqliteDataSourceFactory.datetimeFormatter(new Date()));
		pstmt.executeUpdate();
	}

	@Override
	public void update(NLPNews news) throws SQLException {
		SqliteDataSourceFactory datasource = (SqliteDataSourceFactory) DatabaseFactory.getInstance();

		PreparedStatement pstmt = datasource
				.getStatement("UPDATE NLP_NEWS SET CATEGORY=?, CONTENT=?, DIRECT=?, PROCESSED=? WHERE SOURCE=? AND ID=?");
		pstmt.setString(1, news.getCategory());
		pstmt.setString(2, news.getContent());
//		pstmt.setString(3, SqliteDataSourceFactory.booleanFormatter(news.getDirect()));
		pstmt.setString(3, news.getDirect());
		pstmt.setString(4, SqliteDataSourceFactory.booleanFormatter(news.getProcessed()));
		pstmt.setString(5, news.getSource());
		pstmt.setString(6, news.getId());
		pstmt.executeUpdate();
	}

	@Override
	public ArrayList<NLPNews> selectAll() throws SQLException {
		SqliteDataSourceFactory datasource = (SqliteDataSourceFactory) DatabaseFactory.getInstance();

		String sql = "SELECT SOURCE, CATEGORY, ID, CONTENT, DIRECT, PROCESSED, CREATE_DT FROM NLP_NEWS";
		ArrayList<NLPNews> returns = new ArrayList<NLPNews>();

		Statement stmt = datasource.getStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			String source = rs.getString("SOURCE");
			String category = rs.getString("CATEGORY");
			String id = rs.getString("ID");
			String content = rs.getString("CONTENT");
//			Boolean direct = SqliteDataSourceFactory.booleanFormatter(rs.getString("DIRECT"));
			String direct = rs.getString("DIRECT");
			Boolean processed = SqliteDataSourceFactory.booleanFormatter(rs.getString("PROCESSED"));
			Date createDt = SqliteDataSourceFactory.datetimeFormatter(rs.getString("CREATE_DT"));

			NLPNews news = new NLPNews();
			news.setSource(source);
			news.setCategory(category);
			news.setId(id);
			news.setContent(content);
			news.setDirect(direct);
			news.setProcessed(processed);
			news.setCreateDt(createDt);

			returns.add(news);
		}

		return returns;
	}

	@Override
	public NLPNews selectById(String id) throws SQLException {
		SqliteDataSourceFactory datasource = (SqliteDataSourceFactory) DatabaseFactory.getInstance();

		String sql = "SELECT SOURCE, CATEGORY, ID, CONTENT, DIRECT, PROCESSED, CREATE_DT FROM NLP_NEWS WHERE ID=?";
		PreparedStatement pstmt = datasource.getStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			String source = rs.getString("source");
			String category = rs.getString("category");
			String id1 = rs.getString("id");
			String content = rs.getString("content");
//			Boolean direct = SqliteDataSourceFactory.booleanFormatter(rs.getString("direct"));
			String direct = rs.getString("DIRECT");
			Boolean processed = SqliteDataSourceFactory.booleanFormatter(rs.getString("processed"));
			Date createDt = SqliteDataSourceFactory.datetimeFormatter(rs.getString("CREATE_DT"));

			NLPNews news = new NLPNews();
			news.setSource(source);
			news.setCategory(category);
			news.setId(id1);
			news.setContent(content);
			news.setDirect(direct);
			news.setProcessed(processed);
			news.setCreateDt(createDt);

			return news;
		}

		return null;
	}

	@Override
	public ArrayList<NLPNews> selectNonProcessed() throws SQLException {
		SqliteDataSourceFactory datasource = (SqliteDataSourceFactory) DatabaseFactory.getInstance();

		String sql = "SELECT SOURCE, CATEGORY, ID, CONTENT, DIRECT, PROCESSED, CREATE_DT FROM NLP_NEWS WHERE PROCESSED='N'";
		ArrayList<NLPNews> returns = new ArrayList<NLPNews>();

		Statement stmt = datasource.getStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			String source = rs.getString("source");
			String category = rs.getString("category");
			String id = rs.getString("id");
			String content = rs.getString("content");
//			Boolean direct = SqliteDataSourceFactory.booleanFormatter(rs.getString("direct"));
			String direct = rs.getString("DIRECT");
			Boolean processed = SqliteDataSourceFactory.booleanFormatter(rs.getString("processed"));
			Date createDt = SqliteDataSourceFactory.datetimeFormatter(rs.getString("CREATE_DT"));
			
			NLPNews news = new NLPNews();
			news.setSource(source);
			news.setCategory(category);
			news.setId(id);
			news.setContent(content);
			news.setDirect(direct);
			news.setProcessed(processed);
			news.setCreateDt(createDt);
			
			returns.add(news);
		}

		return returns;
	}

	@Override
	public ArrayList<NLPNews> selectByDate(Date date) throws SQLException {
		SqliteDataSourceFactory datasource = (SqliteDataSourceFactory) DatabaseFactory.getInstance();

		String sql = "SELECT SOURCE, CATEGORY, ID, CONTENT, DIRECT, PROCESSED, CREATE_DT FROM NLP_NEWS " + 
				"WHERE CREATE_DT >= Datetime(?) " + 
				"  AND CREATE_DT <= Datetime(?)";
		ArrayList<NLPNews> returns = new ArrayList<NLPNews>();
		
		PreparedStatement pstmt = datasource.getStatement(sql);
		pstmt.setString(1, SqliteDataSourceFactory.datetimeFormatter(date));
		pstmt.setString(2, SqliteDataSourceFactory.datetimeFormatter(SqliteDataSourceFactory.addDays(date, 1)));
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			String source = rs.getString("source");
			String category = rs.getString("category");
			String id1 = rs.getString("id");
			String content = rs.getString("content");
			String direct = rs.getString("DIRECT");
			Boolean processed = SqliteDataSourceFactory.booleanFormatter(rs.getString("processed"));
			Date createDt = SqliteDataSourceFactory.datetimeFormatter(rs.getString("CREATE_DT"));

			NLPNews news = new NLPNews();
			news.setSource(source);
			news.setCategory(category);
			news.setId(id1);
			news.setContent(content);
			news.setDirect(direct);
			news.setProcessed(processed);
			news.setCreateDt(createDt);
			
			returns.add(news);
		}

		return returns;
	}

	@Override
	public ArrayList<NLPNews> selectContentBykeyWords(String[] ids) throws SQLException {
		SqliteDataSourceFactory datasource = (SqliteDataSourceFactory) DatabaseFactory.getInstance();

		String sql = "SELECT SOURCE, CATEGORY, ID, CONTENT, DIRECT, PROCESSED, CREATE_DT FROM NLP_NEWS WHERE CONTENT IN(";
		for(int i=0; i<ids.length; i++) {
			if(i!=0)
				sql += ", ";
			sql += "?";
		}
		sql += ")";
		
		PreparedStatement pstmt = datasource.getStatement(sql);
		for(int i=0; i<ids.length; i++)
			pstmt.setString(i+1, ids[i]);
		
		ArrayList<NLPNews> returns = new ArrayList<NLPNews>();

		Statement stmt = datasource.getStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			String source = rs.getString("SOURCE");
			String category = rs.getString("CATEGORY");
			String id = rs.getString("ID");
			String content = rs.getString("CONTENT");
			String direct = rs.getString("DIRECT");
			Boolean processed = SqliteDataSourceFactory.booleanFormatter(rs.getString("PROCESSED"));
			Date createDt = SqliteDataSourceFactory.datetimeFormatter(rs.getString("CREATE_DT"));

			NLPNews news = new NLPNews();
			news.setSource(source);
			news.setCategory(category);
			news.setId(id);
			news.setContent(content);
			news.setDirect(direct);
			news.setProcessed(processed);
			news.setCreateDt(createDt);

			returns.add(news);
		}

		return returns;
	}

	@Override
	public Map<Date, String[]> selectContentBykeyWordsGrpByDate(String[] ids) throws SQLException {
		SqliteDataSourceFactory datasource = (SqliteDataSourceFactory) DatabaseFactory.getInstance();

		String sql = "SELECT DIRECT, count(*) as COUNT, date(CREATE_DT) as DATE FROM NLP_NEWS WHERE CONTENT IN(";
		for(int i=0; i<ids.length; i++) {
			if(i!=0)
				sql += ", ";
			sql += "?";
		}
		sql += ") AND PROCESSED='Y' group by DATE ";
		
		PreparedStatement pstmt = datasource.getStatement(sql);
		for(int i=0; i<ids.length; i++)
			pstmt.setString(i+1, ids[i]);
		
		Map<Date, String[]> returns = new HashMap<Date, String[]>();

		Statement stmt = datasource.getStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			String direct = rs.getString("DIRECT");
			String count = rs.getString("COUNT");
			Date createDt = SqliteDataSourceFactory.dateFormatter(rs.getString("DATE"));
			
			returns.put(createDt, new String[]{direct, count});
		}
		
		return returns;
	}

}
