package com.nlp.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nlp.db.DatabaseFactory;
import com.nlp.db.dao.NLPNewsDao;
import com.nlp.db.vo.NLPNews;

@RestController
public class NLPController {
	
	@RequestMapping("/view_data")
	public String FinancialTimes() {
		
		ArrayList<NLPNews> dataSet = null;
		try {
			dataSet = getNLPNewsDao().selectAll();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			dataSet = new ArrayList<NLPNews>();
		}

		String pageContent = 
				"Total number of records: " + dataSet.size() +
				"<BR/> " +
				"<table id=\"NLP result\" border=\"1\" >" + 
				"   <thead>" + 
				"      <tr>" + 
				"         <th>source</th>" + 
				"         <th>category</th>" + 
				"         <th>id</th>" + 
				"         <th>content</th>" + 
				"         <th>direct</th>" + 
				"         <th>processed</th>" + 
				"         <th>create date</th>" + 
				"      </tr>" + 
				"   </thead>" + 
				"   <tbody>";
		for(NLPNews record : dataSet) {
			pageContent += "" +
					"<tr>" + 
					"	<td>" + record.getSource() + "</td>" + 
					"	<td>" + record.getCategory() + "</td>" + 
					"	<td>" + record.getId() + "</td>" + 
					"	<td>" + record.getContent() + "</td>" + 
					"	<td>" + record.getDirect() + "</td>" + 
					"	<td>" + record.getProcessed() + "</td>" +
					"	<td>" + record.getCreateDt() + "</td>" +  
					"</tr>";
		}

		pageContent += 
				"   </tbody>" + 
				"</table>";
		
		return pageContent;
	}
	
	public NLPNewsDao getNLPNewsDao() throws ClassNotFoundException {
		return (NLPNewsDao) DatabaseFactory.getInstance().getDAO("NLPNewsDao");
	}
	
}
