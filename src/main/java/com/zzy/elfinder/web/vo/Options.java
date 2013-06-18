package com.zzy.elfinder.web.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.zzy.elfinder.web.Config;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL) 
public class Options {
	private String path;
	private String url;
	private String tmbUrl;
	private String separator;
	private String[] disabled;
	private Integer copyOverwrite;
	private Archivers archivers;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTmbUrl() {
		return tmbUrl;
	}
	public void setTmbUrl(String tmbUrl) {
		this.tmbUrl = tmbUrl;
	}
	public String getSeparator() {
		return separator;
	}
	public void setSeparator(String separator) {
		this.separator = separator;
	}
	public String[] getDisabled() {
		return disabled;
	}
	public void setDisabled(String[] disabled) {
		this.disabled = disabled;
	}
	public Integer getCopyOverwrite() {
		return copyOverwrite;
	}
	public void setCopyOverwrite(Integer copyOverwrite) {
		this.copyOverwrite = copyOverwrite;
	}
	public Archivers getArchivers() {
		return archivers;
	}
	public void setArchivers(Archivers archivers) {
		this.archivers = archivers;
	}
	public static Options getInstance(){
		Options op=new Options();
		op.setArchivers(Archivers.getInstance());
		op.setCopyOverwrite(1);
		op.setDisabled(new String[]{});
		op.setSeparator(Config.SEPARATOR);
		op.setTmbUrl(Config.TMB_URL);
		op.setPath("files");
		op.setUrl(Config.URL);
		return op;
	}
}
