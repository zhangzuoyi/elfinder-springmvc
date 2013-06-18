package com.zzy.elfinder.web.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.zzy.elfinder.web.vo.AddedResponse;
import com.zzy.elfinder.web.vo.ElFile;
import com.zzy.elfinder.web.vo.Response;
import com.zzy.elfinder.web.vo.TreeResponse;

public interface ElfinderService {
	/**
	 * 返回当前文件夹及其子文件信息,cwd代表当前文件夹，files代表子文件信息<br />
	 * init为true的话，要返回api和options信息<br />
	 * tree为true的话，要在files里放入根目录及其子文件信息
	 * @param target 目标文件夹
	 * @param init
	 * @param tree
	 * @return
	 * @throws IOException
	 */
	Response open(String target,Boolean init,Boolean tree) throws IOException;
	/**
	 * 返回父文件夹信息，一般到追溯到根目录<br />
	 * 直接父文件夹下的子文件信息也要返回
	 * @param target
	 * @return
	 * @throws IOException 
	 */
	TreeResponse parents(String target) throws IOException;
	/**
	 * 新建文件或者文件夹
	 * @param target 目标文件夹
	 * @param name 文件名称
	 * @param isdir 是否创建文件夹
	 * @return
	 * @throws IOException 
	 */
	AddedResponse mkfile(String target,String name,boolean isdir) throws IOException;
	/**
	 * 修改名称<br />
	 * 应该还要考虑目标文件不存在的情况
	 * @param target 目标文件
	 * @param name 修改后的名称
	 * @return
	 * @throws IOException 
	 */
	AddedResponse rename(String target,String name) throws IOException;
	AddedResponse upload(String target,MultipartFile[] files) throws IOException;
	void download(String target,Boolean download,HttpServletResponse response) throws IOException;
	AddedResponse remove(String[] targets) throws IOException;
	AddedResponse paste(String dst,String[] targets,Boolean cut) throws IOException;
	Map<String,Map<String,String>> tmb(String[] targets);
	/**
	 * 获取目标文件的大小<br />
	 * 如果是文件夹的话是统计里面的所有文件
	 * @param targets
	 * @return
	 * @throws IOException 
	 */
	Map<String,Long> size(String[] targets) throws IOException;
	Map<String,String> getContent(String target) throws IOException;
	/**
	 * 获得图片尺寸信息
	 * @param target
	 * @return
	 */
	Map<String,String> dim(String target) throws IOException;
	/**
	 * 在原文件夹中复制<br />
	 * 复制后的文件名规则：原文件名+ copy+ 数字+后缀名
	 * @param targets
	 * @return
	 * @throws IOException
	 */
	AddedResponse duplicate(String[] targets) throws IOException;
	Map<String,ElFile[]> editContent(String target,String content) throws IOException;
	Map<String,List<ElFile>> search(String condition) throws IOException;
	/**
	 * 返回子文件夹信息
	 * @param target
	 * @return
	 * @throws IOException
	 */
	TreeResponse subfolders(String target) throws IOException;
	/**
	 * 列出目标文件夹下的文件名
	 * @param target
	 * @return
	 * @throws IOException
	 */
	Map<String,List<String>> ls(String target) throws IOException;
	Map<String,ElFile[]> changeImage(String target,String mode,int width,int height,int x,int y,double degree) throws IOException;
}
