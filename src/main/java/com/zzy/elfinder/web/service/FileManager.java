package com.zzy.elfinder.web.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zzy.elfinder.utils.Base64Util;
import com.zzy.elfinder.utils.FileUtil;
import com.zzy.elfinder.utils.HashUtil;
import com.zzy.elfinder.utils.ImageUtil;
import com.zzy.elfinder.web.Config;
import com.zzy.elfinder.web.vo.AddedResponse;
import com.zzy.elfinder.web.vo.ElFile;
import com.zzy.elfinder.web.vo.Options;
import com.zzy.elfinder.web.vo.Response;
import com.zzy.elfinder.web.vo.TreeResponse;
@Service
public class FileManager implements ElfinderService{
	public Response open(String target,Boolean init,Boolean tree) throws IOException{
		Response rsp=new Response();
		if(init!=null&&init){
			rsp.setApi(Config.API);
		}
		rsp.setOptions(Options.getInstance());
		rsp.setUplMaxSize(Config.UPL_MAX_SIZE);
		if(StringUtils.isBlank(target)){//target为空，设置为根目录
			target=Config.DEFAULT_VOLUMNID+Base64Util.encode(Config.SEPARATOR);
		}
		ElFile cwd=ElFile.getInstance(target);
		rsp.setCwd(cwd);
		String[] strs=HashUtil.decode(target);
		File pf=new File(Config.ROOT_PATH,strs[1]);//当前目录
		List<ElFile> files=new ArrayList<ElFile>();
		if(tree!=null&&tree){
			//tree为true, 要加入根目录及其子文件
			addRootAndChildren(strs[0],files);
		}
		boolean isRoot=StringUtils.isBlank(cwd.getPhash());
		//是根目录且tree为true，说明根目录下的子文件已经加入到files里了，不用再重复添加子文件了
		if((tree!=null&&tree)&&isRoot){
			//do nothing
		}else{
			for(File f:pf.listFiles()){
				String hash=HashUtil.encode(strs[0], strs[1], f);
				files.add(ElFile.getInstance(hash));
			}
		}
		rsp.setFiles(files);
		return rsp;
	}
	private void addRootAndChildren(String volumnid,List<ElFile> files) throws IOException{
		String rootHash=volumnid+Base64Util.encode(Config.SEPARATOR);//根目录hash
		files.add(ElFile.getInstance(rootHash));
		File rootFile=new File(Config.ROOT_PATH);
		for(File f:rootFile.listFiles()){
			String hash=HashUtil.encode(volumnid, Config.SEPARATOR, f);
			files.add(ElFile.getInstance(hash));
		}
	}

	@Override
	public TreeResponse parents(String target) throws IOException {
		//获得volumnid和当前路径
		String[] strs=HashUtil.decode(target);
		//获得父目录路径列表，顺序从高到低
		List<String> paths=FileUtil.getParentPaths(strs[1]);
		//获得父目录信息
		List<ElFile> tree=new ArrayList<ElFile>();
		for(String p:paths){
			String hash=HashUtil.encode(strs[0], p);
			tree.add(ElFile.getInstance(hash));
		}
		//获得直接父目录的子文件信息
		String directParent=paths.get(paths.size()-1);
		File dpf=new File(Config.ROOT_PATH,directParent);
		for(File f:dpf.listFiles()){
			String hash=HashUtil.encode(strs[0], directParent, f);
			tree.add(ElFile.getInstance(hash));
		}
		TreeResponse rsp=new TreeResponse();
		rsp.setTree(tree);
		return rsp;
	}
	@Override
	public AddedResponse mkfile(String target, String name,boolean isdir) throws IOException {
		//取得目标文件夹路径
		String[] strs=HashUtil.decode(target);
		//在目标文件夹下创建文件
		String filePath=strs[1]+Config.SEPARATOR+name;
		File f=new File(Config.ROOT_PATH,filePath);
		if(isdir)
			f.mkdir();
		else
			f.createNewFile();
		//返回结果
		AddedResponse rsp=new AddedResponse();
		rsp.getAdded().add(ElFile.getInstance(strs[0],filePath));
		return rsp;
	}
	@Override
	public AddedResponse rename(String target, String name) throws IOException {
		//取得目标文件路径
		String[] strs=HashUtil.decode(target);
		//修改名称
		File f=new File(Config.ROOT_PATH,strs[1]);
		String newPath=FileUtil.getParentPath(strs[1])+Config.SEPARATOR+name;
		f.renameTo(new File(Config.ROOT_PATH,newPath));
		//返回结果
		AddedResponse rsp=new AddedResponse();
		rsp.getAdded().add(ElFile.getInstance(strs[0],newPath));
		rsp.addRemoved(target);
		return rsp;
	}
	@Override
	public AddedResponse upload(String target, MultipartFile[] files) throws IOException {
		AddedResponse rsp=new AddedResponse();
		//获得目标文件夹路径
		String[] strs=HashUtil.decode(target);
		//保存文件
		for(MultipartFile file:files){
			String path=strs[1]+Config.SEPARATOR+file.getOriginalFilename();
			IOUtils.copy(file.getInputStream(), new FileOutputStream(new File(Config.ROOT_PATH,path)));
			rsp.getAdded().add(ElFile.getInstance(strs[0],path));
		}
		//返回结果
		return rsp;
	}
	@Override
	public void download(String target, Boolean download,
			HttpServletResponse response) throws IOException {
		//获得目标文件路径
		String[] strs=HashUtil.decode(target);
		//下载文件
		File f=new File(Config.ROOT_PATH,strs[1]);
		response.setCharacterEncoding("utf-8");
		String finalFileName = URLEncoder.encode(f.getName(),"UTF-8");
		if(download!=null&&download){
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition", "attachment; filename=\""+finalFileName+"\"");
			response.setHeader("Content-Location", finalFileName);
		}
		IOUtils.copy(new FileInputStream(f),response.getOutputStream());
	}
	@Override
	public AddedResponse remove(String[] targets) throws IOException {
		AddedResponse rsp=new AddedResponse();
		for(String target:targets){
			//获得目录文件路径
			String[] strs=HashUtil.decode(target);
			//删除文件，如果是文件夹也要删除子文件
			delFile(rsp,strs[0],strs[1]);
		}
		return rsp;
	}
	private void delFile(AddedResponse rsp,String volumnid,String path) throws UnsupportedEncodingException{
		File f=new File(Config.ROOT_PATH,path);
		if(f.isDirectory()){//文件夹
			for(File cf:f.listFiles()){
				String cfpath=path+Config.SEPARATOR+cf.getName();
				delFile(rsp,volumnid,cfpath);
			}
		}
		f.delete();
		rsp.addRemoved(HashUtil.encode(volumnid, path));
	}
	@Override
	public AddedResponse paste(String dst, String[] targets, Boolean cut)
			throws IOException {
		AddedResponse rsp=new AddedResponse();
		//得到目的地目录路径
		String[] dstStrs=HashUtil.decode(dst);
//		File dstFile=new File(ROOT_PATH,dstStrs[1]);
		for(String target:targets){
			//得到要粘贴的文件路径
			String[] strs=HashUtil.decode(target);
			File src=new File(Config.ROOT_PATH,strs[1]);
			//目的地目录是否存在同名文件，若存在则先删除。但文件只能替换文件，文件夹只能替换文件夹
			String newPath=dstStrs[1]+Config.SEPARATOR+src.getName();
			File newFile=new File(Config.ROOT_PATH,newPath);
			if(newFile.exists()){
				if(src.isDirectory()&&newFile.isDirectory()){
					delFile(rsp,strs[0],newPath);
				}else if(src.isFile()&&newFile.isFile()){
					delFile(rsp,strs[0],newPath);
				}else{
					rsp.addWarning("errNotReplace");
					rsp.addWarning(Config.ROOT_NAME+Config.SEPARATOR+newPath);
					continue;
				}
			}
			//在目的地目录粘贴文件，若是文件夹则子文件也要粘贴过来
			pasteFile(rsp,strs[0],dstStrs[1],src);
			//if cut is true, delete the source file
			if(cut!=null&&cut){
				delFile(rsp,strs[0],strs[1]);
			}
		}
		//return result
		return rsp;
	}
	private void pasteFile(AddedResponse rsp,String volumnid,String dstPath,File src) throws IOException{
		String newPath=dstPath+Config.SEPARATOR+src.getName();
		File newFile=new File(Config.ROOT_PATH,newPath);
		if(src.isDirectory()){
			newFile.mkdir();
			rsp.getAdded().add(ElFile.getInstance(volumnid,newPath));
			for(File f:src.listFiles()){
				pasteFile(rsp,volumnid,newPath,f);
			}
		}else{
			newFile.createNewFile();
			IOUtils.copy(new FileInputStream(src), new FileOutputStream(newFile));
			rsp.getAdded().add(ElFile.getInstance(volumnid,newPath));
		}
	}
	@Override
	public Map<String, Map<String, String>> tmb(String[] targets) {
		Map<String,Map<String,String>> result=new HashMap<String,Map<String,String>>();
		Map<String,String> images=new HashMap<String,String>();
		String suffix="_"+System.currentTimeMillis();//加上后缀，告诉页面tmb已经发生更新
		for(String target:targets){
			images.put(target, target+suffix);
		}
		result.put("images", images);
		return result;
	}
	@Override
	public Map<String, Long> size(String[] targets) throws IOException {
		long result=0;
		for(String target:targets){
			//get file's path
			String[] strs=HashUtil.decode(target);
			//get file's size
			File f=new File(Config.ROOT_PATH,strs[1]);
			result+=fileSize(f);
		}
		//return result
		Map<String,Long> map=new HashMap<String,Long>();
		map.put("size", result);
		return map;
	}
	private long fileSize(File f){
		long size=0;
		if(f.isDirectory()){
			for(File cf:f.listFiles()){
				size+=fileSize(cf);
			}
		}else{
			size+=f.length();
		}
		return size;
	}
	@Override
	public Map<String, String> getContent(String target) throws IOException {
		//get file path
		String[] strs=HashUtil.decode(target);
		//read file content
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		IOUtils.copy(new FileInputStream(new File(Config.ROOT_PATH,strs[1])), out);
		//return result
		Map<String,String> result=new HashMap<String,String>();
		result.put("content", new String(out.toByteArray(),"utf-8"));
		return result;
	}
	@Override
	public Map<String, String> dim(String target) throws IOException {
		//获取图像路径
		String[] strs=HashUtil.decode(target);
		File f=new File(Config.ROOT_PATH,strs[1]);
		//获得图片尺寸
		BufferedImage image = ImageIO.read(f);
		String dim=image.getWidth()+"x"+image.getHeight();
		Map<String,String> result=new HashMap<String,String>();
		result.put("dim", dim);
		return result;
	}
	@Override
	public AddedResponse duplicate(String[] targets) throws IOException {
		AddedResponse rsp=new AddedResponse();
		for(String target:targets){
			//得到目标文件路径
			String[] strs=HashUtil.decode(target);
			File tf=new File(Config.ROOT_PATH,strs[1]);
			//生成新的文件名
			String nn=FileUtil.duplicateFileName(tf);
			File nf=new File(tf.getParentFile(),nn);
			//复制文件，文件夹还要复制子文件
			copyFile(tf,nf);
			String newPath=FileUtil.getParentPath(strs[1])+Config.SEPARATOR+nn;
			rsp.getAdded().add(ElFile.getInstance(strs[0], newPath));
		}
		//返回结果
		return rsp;
	}
	private void copyFile(File of,File nf) throws FileNotFoundException, IOException{
		if(of.isFile()){
			IOUtils.copy(new FileInputStream(of), new FileOutputStream(nf));
		}else{
			nf.mkdir();
			for(File f:of.listFiles()){
				copyFile(f,new File(nf,f.getName()));
			}
		}
	}
	@Override
	public Map<String, ElFile[]> editContent(String target,
			String content) throws IOException {
		String[] strs=HashUtil.decode(target);
		File tf=new File(Config.ROOT_PATH,strs[1]);
		IOUtils.copy(new ByteArrayInputStream(content.getBytes("utf-8")), new FileOutputStream(tf));
		Map<String, ElFile[]> map=new HashMap<String, ElFile[]>();
		map.put("changed", new ElFile[]{ElFile.getInstance(target)});
		return map;
	}
	@Override
	public Map<String, List<ElFile>> search(String condition) throws IOException {
		List<ElFile> list=new ArrayList<ElFile>();
		File root=new File(Config.ROOT_PATH);
		for(File file:root.listFiles())
			query(file,null,condition,list);
		Map<String, List<ElFile>> map=new HashMap<String, List<ElFile>>();
		map.put("files", list);
		return map;
	}
	private void query(File file,String parentPath,String condition,List<ElFile> list) throws IOException{
		String path=null;
		if(parentPath==null){//root
			path=file.getName();
		}else{
			path=parentPath+Config.SEPARATOR+file.getName();
		}
		
		if(file.isDirectory()){
			for(File f:file.listFiles()){
				query(f,path,condition,list);
			}
		}else{
			if(file.getName().startsWith(condition)){
				list.add(ElFile.getSearchInstance(Config.DEFAULT_VOLUMNID, path));
			}
		}
	}
	@Override
	public TreeResponse subfolders(String target) throws IOException {
		List<ElFile> list=new ArrayList<ElFile>();
		String[] strs=HashUtil.decode(target);
		File pf=new File(Config.ROOT_PATH,strs[1]);
		for(File f:pf.listFiles()){
			if(f.isDirectory()){
				list.add(ElFile.getInstance(strs[0], strs[1]+Config.SEPARATOR+f.getName()));
			}
		}
		TreeResponse rsp=new TreeResponse();
		rsp.setTree(list);
		return rsp;
	}
	@Override
	public Map<String, List<String>> ls(String target) throws IOException {
		List<String> list=new ArrayList<String>();
		String[] strs=HashUtil.decode(target);
		File pf=new File(Config.ROOT_PATH,strs[1]);
		for(File f:pf.listFiles()){
			if(f.isFile()){
				list.add(f.getName());
			}
		}
		Map<String, List<String>> map=new HashMap<String, List<String>>();
		map.put("list", list);
		return map;
	}
	@Override
	public Map<String, ElFile[]> changeImage(String target, String mode,
			int width, int height, int x, int y, double degree)
			throws IOException {
		String[] strs=HashUtil.decode(target);
		File f=new File(Config.ROOT_PATH,strs[1]);
		if("resize".equals(mode)){
			ImageUtil.resize(f, width, height);
		}else if("crop".equals(mode)){
			ImageUtil.crop(f, width, height, x, y);
		}else if("rotate".equals(mode)){
			System.out.println("rotate:"+degree);
			ImageUtil.rotate(f, degree);
		}
		Map<String, ElFile[]> map=new HashMap<String, ElFile[]>();
		map.put("changed", new ElFile[]{ElFile.getNewTmbInstance(target)});
		return map;
	}
}
