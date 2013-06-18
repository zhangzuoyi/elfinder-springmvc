package com.zzy.elfinder.web.vo;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL) 
public class AddedResponse {
	private List<ElFile> added;
	private List<String> removed;
	private List<String> warning;

	public List<ElFile> getAdded() {
		if(added==null)
			added=new ArrayList<ElFile>();
		return added;
	}

	public void setAdded(List<ElFile> added) {
		this.added = added;
	}

	public List<String> getRemoved() {
		return removed;
	}

	public void setRemoved(List<String> removed) {
		this.removed = removed;
	}
	public void addRemoved(String name){
		if(removed==null){
			removed=new ArrayList<String>();
		}
		removed.add(name);
	}

	public List<String> getWarning() {
		return warning;
	}

	public void setWarning(List<String> warning) {
		this.warning = warning;
	}

	public void addWarning(String wa){
		if(warning==null){
			warning=new ArrayList<String>();
		}
		warning.add(wa);
	}
//	public static AddedResponse getInstance(){
//		AddedResponse rsp=new AddedResponse();
//		ElFile rspf=new ElFile();
//
//		rspf.setDate("Today 17:00");
//		rspf.setHash("l1_YWY");
//		rspf.setMime("directory");
//		rspf.setName("af");
//		rspf.setPhash("l1_Lw");
//		rspf.setRead(1);
//		rspf.setSize(0L);
//		rspf.setTs(1369299642L);
//		rspf.setWrite(1);
//		rsp.setAdded(rspf);
//		return rsp;
//	}
}
