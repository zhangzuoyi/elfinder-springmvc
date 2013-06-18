package com.zzy.elfinder.utils;

import java.io.File;

public class ImageUtil {
	public static void resize(File f,int width,int height){
		javaxt.io.Image image=new javaxt.io.Image(f);
		image.resize(width, height);
		image.saveAs(f);
	}
	public static void crop(File f,int width,int height,int x,int y){
		javaxt.io.Image image=new javaxt.io.Image(f);
		image.crop(x, y, width, height);
		image.saveAs(f);
	}
	public static void rotate(File f,double degree){
		javaxt.io.Image image=new javaxt.io.Image(f);
		image.rotate(degree);
		image.saveAs(f);
	}
}
