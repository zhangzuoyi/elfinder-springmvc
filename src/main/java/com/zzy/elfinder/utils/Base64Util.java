package com.zzy.elfinder.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/** 
 * New 2.x PHP connector uses the following algorithm to create hash from file path:
remove root path from file path
encrypt resulting path so it could be later decrypted (not implemented yet, but stub is present)
encode already encrypted path using base64 with replacement +/= -> -_.
remove trailing dots
add prefix - unique volume id (must start with [a-z])
resulting string must be valid HTML id attribute (that is why base64 is used).
 * @author zhangzuoyi
 *
 */
public class Base64Util {
	@SuppressWarnings("restriction")
	public static String encode(String str) throws UnsupportedEncodingException{
		BASE64Encoder en=new BASE64Encoder();
		String tmp=en.encode(str.getBytes("utf-8"));
//		System.out.println(tmp.length());
//		System.out.println(tmp);
		tmp=tmp.replaceAll("\\+", "-");
		tmp=tmp.replaceAll("/", "_");
		tmp=tmp.replaceAll("=", ".");
		tmp=tmp.replaceAll("\\.*$", "");
		return tmp;
	}
	/**
	 * Base64 后的字符串，长度一定是4的整数倍，末尾有一个，两个或没有'='
	 * @param str
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("restriction")
	public static String decode(String str) throws IOException{
		str=str.replaceAll("-", "+");
		str=str.replaceAll("_", "/");
		str=str.replaceAll("\\.", "=");
		if(str.length()%4==3){
			str=str+"=";
		}else if(str.length()%4==2){
			str=str+"==";
		}
//		System.out.println(str);
		BASE64Decoder de=new BASE64Decoder();
		return new String(de.decodeBuffer(str),"utf-8");
	}
	public static void main(String[] args) throws IOException{
		String str=encode("cc");
		System.out.println(str);
		System.out.println(decode("Y2MvZGQgY29weSAxL2Rk"));
	}
}
