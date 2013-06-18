package com.zzy.elfinder.web.vo;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL) 
public class Response {
	private ElFile cwd;
	private List<ElFile> files;
	private Options options;
	private String uplMaxSize;
	private Double api;
	public ElFile getCwd() {
		return cwd;
	}
	public void setCwd(ElFile cwd) {
		this.cwd = cwd;
	}
	public List<ElFile> getFiles() {
		return files;
	}
	public void setFiles(List<ElFile> files) {
		this.files = files;
	}
	public Options getOptions() {
		return options;
	}
	public void setOptions(Options options) {
		this.options = options;
	}
	public String getUplMaxSize() {
		return uplMaxSize;
	}
	public void setUplMaxSize(String uplMaxSize) {
		this.uplMaxSize = uplMaxSize;
	}
	public Double getApi() {
		return api;
	}
	public void setApi(Double api) {
		this.api = api;
	}
	
}
