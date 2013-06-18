package com.zzy.elfinder.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.zzy.elfinder.web.Config;

public class HashUtil {
	public static String[] decode(String hash) throws IOException{
		String[] result=new String[2];
		int i=hash.indexOf("_");
		result[0]=hash.substring(0, i+1);//volumnid
		result[1]=hash.substring(i+1);
		result[1]=Base64Util.decode(result[1]);//path
		return result;
	}
	public static String phash(String hash) throws IOException{
		String[] strs=decode(hash);
		if(strs[1].equals(Config.SEPARATOR)){//根目录
			return null;
		}
		if(strs[1].indexOf(Config.SEPARATOR)==-1){//第一级目录，上级目录就是根目录
			return strs[0]+Base64Util.encode(Config.SEPARATOR);
		}else{
			int i=strs[1].lastIndexOf(Config.SEPARATOR);
			String ppath=strs[1].substring(0, i);
			return strs[0]+Base64Util.encode(ppath);
		}
	}
	public static String encode(String volumnid,String ppath,File f) throws UnsupportedEncodingException{
		String path=null;
		if(ppath.equals(Config.SEPARATOR))//表示根目录
			path=f.getName();//路径前面不用加斜杆
		else
			path=ppath+Config.SEPARATOR+f.getName();
		return volumnid+Base64Util.encode(path);
	}
	public static String encode(String volumnid,String path) throws UnsupportedEncodingException{
		return volumnid+Base64Util.encode(path);
	}
	
	public static void main(String[] args) throws IOException{
		String[] de=decode("l1_dGVzdA");
		System.out.println(de[0]+":"+de[1]);
		de=decode("l1_cGF0aWVudFNvdXJjZS9zcmMvbWFpbi93ZWJhcHAvaGVhZC5odG1s");
		System.out.println(de[0]+":"+de[1]);
	}
}
