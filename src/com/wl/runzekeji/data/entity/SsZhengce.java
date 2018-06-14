package com.wl.runzekeji.data.entity;

import java.util.Date;

public class SsZhengce {
	private String title;
	private String refNumber;
	private Date publishTime;
	private String content;
	private String sourceHtml;
	private String url;
	private String parentUrl;
	private Date inputTime;
	public SsZhengce(String title, String refNumber, Date publishTime,
			String content, String sourceHtml, String url, String parentUrl,
			Date inputTime) {
		super();
		this.title = title;
		this.refNumber = refNumber;
		this.publishTime = publishTime;
		this.content = content;
		this.sourceHtml = sourceHtml;
		this.url = url;
		this.parentUrl = parentUrl;
		this.inputTime = inputTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSourceHtml() {
		return sourceHtml;
	}
	public void setSourceHtml(String sourceHtml) {
		this.sourceHtml = sourceHtml;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParentUrl() {
		return parentUrl;
	}
	public void setParentUrl(String parentUrl) {
		this.parentUrl = parentUrl;
	}
	public Date getInputTime() {
		return inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	
	
	
}
