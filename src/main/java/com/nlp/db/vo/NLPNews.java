package com.nlp.db.vo;

import java.util.Date;

import com.opencsv.bean.CsvBindByName;

public class NLPNews {
	
//	public static enum NLP_SOURCE {
//		FT //Financial Times
//	}
	
	@CsvBindByName
	private String source;
	@CsvBindByName
	private String category;
	@CsvBindByName
	private String id;
	@CsvBindByName
	private String content;
	@CsvBindByName
	private String direct;
	@CsvBindByName
	private Boolean processed;
	@CsvBindByName
	private Date createDt;
	
	public void setSource(String source) {
		this.source = source;
	}
	public String getSource() {
		return this.source;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategory() {
		return this.category;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return this.content;
	}
	
	public void setDirect(String direct) {
		this.direct = direct;
	}
	public String getDirect() {
		return this.direct;
	}
	
	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}
	public Boolean getProcessed() {
		return this.processed;
	}
	
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	public Date getCreateDt() {
		return this.createDt;
	}
	
}
