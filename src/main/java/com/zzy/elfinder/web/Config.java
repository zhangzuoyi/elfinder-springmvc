package com.zzy.elfinder.web;

import java.util.ResourceBundle;

public class Config {

	public static final double API=2.0;
	public static final String UPL_MAX_SIZE;
	public static final String SEPARATOR="/";
	public static final String ROOT_PATH;
	public static final String ROOT_NAME="files";
	public static final String DEFAULT_VOLUMNID="l1_";
	public static final String TMB_URL;
	public static final String URL;
	static{
		ResourceBundle rb=ResourceBundle.getBundle("config");
		UPL_MAX_SIZE=rb.getString("UPL_MAX_SIZE");
		ROOT_PATH=rb.getString("ROOT_PATH");
		TMB_URL=rb.getString("CONTEXT_PATH")+"/tmb/";
		URL=rb.getString("CONTEXT_PATH")+"/file/";
	}
}
