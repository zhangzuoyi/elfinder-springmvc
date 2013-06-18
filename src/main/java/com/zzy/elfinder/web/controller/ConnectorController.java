package com.zzy.elfinder.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zzy.elfinder.web.service.ElfinderService;
import com.zzy.elfinder.web.vo.AddedResponse;
import com.zzy.elfinder.web.vo.Archivers;
import com.zzy.elfinder.web.vo.ElFile;
import com.zzy.elfinder.web.vo.Options;
import com.zzy.elfinder.web.vo.Response;

@Controller
@RequestMapping(value = "/connector")
public class ConnectorController {
	Logger logger = LoggerFactory.getLogger(ConnectorController.class);
	private ElfinderService service;
	@Autowired
	public void setService(ElfinderService service) {
		this.service = service;
	}
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Object get(@RequestParam("cmd")String cmd,
			@RequestParam(value="target",required=false)String target,
			@RequestParam(value="init",required=false)Boolean init,
			@RequestParam(value="tree",required=false)Boolean tree,
			@RequestParam(value="download",required=false)Boolean download,
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="current",required=false)String current,
			@RequestParam(value="src",required=false)String src,
			@RequestParam(value="dst",required=false)String dst,
			@RequestParam(value="targets[]",required=false)String[] targets,
			@RequestParam(value="cut",required=false)Boolean cut,
			@RequestParam(value="content",required=false)String content,
			@RequestParam(value="type",required=false)String type,
			@RequestParam(value="q",required=false)String queryCondition,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		logger.info("cmd:"+cmd);
		if(cmd.equals("open")){
			return service.open(target, init, tree);
		}else if(cmd.equals("mkdir")){
			return service.mkfile(target,name,true);
		}else if(cmd.equals("parents")){
			return service.parents(target);
		}else if(cmd.equals("mkfile")){
			return service.mkfile(target,name,false);
		}else if(cmd.equals("rename")){
			return service.rename(target,name);
		}else if(cmd.equals("file")){
			service.download(target,download,response);
		}else if(cmd.equals("rm")){
			return service.remove(targets);
		}else if(cmd.equals("paste")){
			return service.paste(dst,targets,cut);
		}else if(cmd.equals("tmb")){
			return service.tmb(targets);
		}else if(cmd.equals("size")){
			return service.size(targets);
		}else if(cmd.equals("get")){
			return service.getContent(target);
		}else if(cmd.equals("dim")){
			return service.dim(target);
		}else if(cmd.equals("duplicate")){
			return service.duplicate(targets);
		}else if(cmd.equals("search")){
			return service.search(queryCondition);
		}else if(cmd.equals("tree")){
			return service.subfolders(target);
		}else if(cmd.equals("ls")){
			return service.ls(target);
		}else if(cmd.equals("resize")){
			String mode=request.getParameter("mode");
			System.out.println(mode);
			int degree=convert2Int(request.getParameter("degree"));
			int width=convert2Int(request.getParameter("width"));
			int height=convert2Int(request.getParameter("height"));
			int x=convert2Int(request.getParameter("x"));
			int y=convert2Int(request.getParameter("y"));
			return service.changeImage(target, mode, width, height, x, y, degree);
		}
		
		return getUnknownCmdResponse();
	}
	private int convert2Int(String str){
		if(str==null){
			return 0;
		}else{
			return Integer.valueOf(str);
		}
	}
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Object post(@RequestParam("cmd")String cmd,
			@RequestParam(value="target",required=false)String target,
			@RequestParam(value="upload[]",required=false) MultipartFile[] uploads,
			@RequestParam(value="content",required=false)String content) throws IOException{
		if(cmd.equals("upload")){
			return service.upload(target, uploads);
		}else if(cmd.equals("put")){
			return service.editContent(target, content);
		}
		return getUnknownCmdResponse();
	}
	private Map<String,String> getUnknownCmdResponse(){
		//{"error" : "errUnknownCmd"}
		Map<String,String> map=new HashMap<String,String>();
		map.put("error", "errUnknownCmd");
		return map;
	}
}
