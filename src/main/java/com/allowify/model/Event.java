package com.allowify.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author Bharat
 *
 */
@Document
public class Event {

	@Id
	private Long id;

	private String url;
	private String uri;
	private String method;
	private String parameters;
	private String json;
	private Date startTime;
	private Long duration;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", url=" + url + ", method=" + method
				+ ", parameters=" + parameters + ", json=" + json + ", uri="
				+ uri + ", startTime=" + startTime + ", duration=" + duration
				+ "]";
	}
	
}
