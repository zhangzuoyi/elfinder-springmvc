package com.zzy.elfinder.web.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL) 
public class Archivers {
	private String[] create;
	private String[] extract;
	private static Archivers instance;
	public String[] getCreate() {
		return create;
	}
	public void setCreate(String[] create) {
		this.create = create;
	}
	public String[] getExtract() {
		return extract;
	}
	public void setExtract(String[] extract) {
		this.extract = extract;
	}
	public static Archivers getInstance(){
		if(instance==null){
			instance=new Archivers();
			String[] cr=new String[]{"application/x-tar", 
			        "application/x-gzip", 
			        "application/x-bzip2", 
			        "application/zip", 
			        "application/x-rar", 
			        "application/x-7z-compressed"};
			String[] ex=new String[]{"application/x-tar", 
			        "application/x-gzip", 
			        "application/x-bzip2", 
			        "application/zip", 
			        "application/x-7z-compressed"};
			instance.setCreate(cr);
			instance.setExtract(ex);
		}
		return instance;
	}
}
