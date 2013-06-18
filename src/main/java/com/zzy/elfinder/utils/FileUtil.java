package com.zzy.elfinder.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zzy.elfinder.web.Config;

public class FileUtil {
	/**
	 * 判断是否有子文件夹
	 * @param f
	 * @return
	 */
	public static boolean hasSubdirs(File f){
		if(f.isDirectory()){
			for(File sf:f.listFiles()){
				if(sf.isDirectory()){
					return true;
				}
			}
			return false;
		}else{
			return false;
		}
	}
	/**
	 * get unix timestamp
	 * @param f
	 * @return
	 */
	public static long getTs(File f){
		return f.lastModified()/1000L;
	}
	public static String getFileName(File f) throws IOException{
		File rootFile=new File(Config.ROOT_PATH);
		if(f.getCanonicalPath().equals(rootFile.getCanonicalPath())){
			return Config.ROOT_NAME;
		}else{
			return f.getName();
		}
	}
	/**
	 * 获得父目录路径
	 * @param path
	 * @return
	 */
	public static String getParentPath(String path){
		int i=path.lastIndexOf(Config.SEPARATOR);
		if(i==-1){
			return Config.SEPARATOR;//根目录
		}else{
			return path.substring(0, i);
		}
	}
	/**
	 * 获得父目录路径列表，顺序从高到低
	 * @return
	 */
	public static List<String> getParentPaths(String path){
		List<String> result=new ArrayList<String>();
		String[] paths=path.split(Config.SEPARATOR);
		result.add(Config.SEPARATOR);
		String str=null;
		for(int i=0;i<paths.length-1;i++){
			String p=paths[i];
			if(str==null){
				str=p;
			}else{
				str=str+Config.SEPARATOR+p;
			}
			result.add(str);
		}
//		for(String p:paths){
//			if(str==null){
//				str=p;
//			}else{
//				str=str+ElfinderService.SEPARATOR+p;
//			}
//			result.add(str);
//		}
		return result;
	}
	public static String duplicateFileName(File f){
		String on=f.getName();
		String name="";
		String suffix="";
		String regex = "(.+)( copy)( \\d+)(\\.?.*)";
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(on);
		if(matcher.matches()){
			name=matcher.group(1);
			suffix=matcher.group(4);
		}else{
			int i=on.lastIndexOf(".");
			if(i!=-1){
				name=on.substring(0, i);
				suffix=on.substring(i);
			}else{
				name=on;
			}
		}
		int i=1;
		String nn=null;
		while(true){
			nn=name+" copy"+" "+i+suffix;
			File nf=new File(f.getParentFile(),nn);
			if(!nf.exists()){
				break;
			}
			i++;
		}
		return nn;
	}
}
