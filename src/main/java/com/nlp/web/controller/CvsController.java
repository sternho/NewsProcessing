package com.nlp.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nlp.db.DatabaseFactory;
import com.nlp.db.dao.NLPNewsDao;
import com.nlp.db.vo.NLPNews;
import com.opencsv.CSVWriter;

@RestController
public class CvsController {
	private static final Logger logger = LoggerFactory.getLogger(CvsController.class);
	
	private static final SimpleDateFormat ftFormatter = new SimpleDateFormat("yyyy-MM-dd");

	@RequestMapping("/ExportCsv")
	public void exportCsv(HttpServletResponse response,
			@RequestParam(value = "date", required = true) String date) throws SQLException, ParseException, ClassNotFoundException {
		
		response.setContentType("application/csv");
		response.setHeader( "Content-Disposition", "attachment; filename=NLP_News_" + date + ".csv");
		
		ArrayList<NLPNews> newsList = getNLPNewsDao().selectByDate(ftFormatter.parse(date));
		
		try(CSVWriter csvWriter = new CSVWriter(response.getWriter(),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) {

            String[] headerRecord = {"source", "category", "content", "direct", "processed", "createDt"};
            
            for(NLPNews news : newsList) {
            	csvWriter.writeNext(new String[] {
        			news.getSource(),
        			news.getCategory(),
        			news.getContent(),
        			news.getDirect(),
        			news.getProcessed().toString(),
        			ftFormatter.format(news.getCreateDt())
    			});
            }
            
            csvWriter.writeNext(headerRecord);
		} catch (IOException e) {
			logger.error(e.toString());
		}
	}
	
	public NLPNewsDao getNLPNewsDao() throws ClassNotFoundException {
		return (NLPNewsDao) DatabaseFactory.getInstance().getDAO("NLPNewsDao");
	}
	
}
