package com.zzy.elfinder.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/connector2")
public class TestConnectorController {
	Logger logger = LoggerFactory.getLogger(TestConnectorController.class);
	@RequestMapping(method = RequestMethod.GET)
	public void get(@RequestParam("cmd")String cmd,@RequestParam(value="target",required=false)String target,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		logger.info("cmd:"+cmd);
		response.setContentType("application/json");
		String str=null;
		if(cmd.equals("open")){
			str=getOpen();
		}else if(cmd.equals("mkdir")){
			str=getAdded();
		}
		response.getWriter().write(str);
	}
	private String getOpen(){
		return "{\"cwd\":{\"mime\":\"directory\",\"ts\":1369636697,\"read\":1,\"write\":1,\"size\":0,\"hash\":\"l1_Lw\",\"volumeid\":\"l1_\",\"name\":\"files\",\"date\":\"Today 14:38\",\"locked\":1,\"dirs\":1},\"options\":{\"path\":\"files\",\"url\":\"/elfinder/php/../files/\",\"tmbUrl\":\"/elfinder/php/../files/.tmb/\",\"disabled\":[],\"separator\":\"/\",\"copyOverwrite\":1,\"archivers\":{\"create\":[\"application/x-tar\",\"application/x-gzip\",\"application/x-bzip2\",\"application/zip\",\"application/x-7z-compressed\"],\"extract\":[\"application/x-tar\",\"application/x-gzip\",\"application/x-bzip2\",\"application/zip\",\"application/x-rar\",\"application/x-7z-compressed\"]}},\"files\":[{\"mime\":\"directory\",\"ts\":1369636697,\"read\":1,\"write\":1,\"size\":0,\"hash\":\"l1_Lw\",\"volumeid\":\"l1_\",\"name\":\"files\",\"date\":\"Today 14:38\",\"locked\":1,\"dirs\":1},{\"mime\":\"text/plain\",\"ts\":1369299538,\"read\":1,\"write\":1,\"size\":0,\"hash\":\"l1_YWEudHh0\",\"name\":\"aa.txt\",\"phash\":\"l1_Lw\",\"date\":\"23 May 2013 16:58\"},{\"mime\":\"directory\",\"ts\":1369299642,\"read\":1,\"write\":1,\"size\":0,\"hash\":\"l1_YWZpbGU\",\"name\":\"afile\",\"phash\":\"l1_Lw\",\"date\":\"23 May 2013 17:00\"},{\"mime\":\"text/plain\",\"ts\":1369297073,\"read\":1,\"write\":1,\"size\":0,\"hash\":\"l1_YmIudHh0\",\"name\":\"bb.txt\",\"phash\":\"l1_Lw\",\"date\":\"23 May 2013 16:17\"},{\"mime\":\"directory\",\"ts\":1369636994,\"read\":1,\"write\":1,\"size\":0,\"hash\":\"l1_Y2M\",\"name\":\"cc\",\"phash\":\"l1_Lw\",\"date\":\"Today 14:43\"},{\"mime\":\"directory\",\"ts\":1369297516,\"read\":1,\"write\":1,\"size\":0,\"hash\":\"l1_dGVzdA\",\"name\":\"test\",\"phash\":\"l1_Lw\",\"date\":\"23 May 2013 16:25\"},{\"mime\":\"image/jpeg\",\"ts\":1369299957,\"read\":1,\"write\":1,\"size\":63325,\"hash\":\"l1_5pWw5a2XNC5qcGc\",\"name\":\"u6570u5b574.jpg\",\"phash\":\"l1_Lw\",\"date\":\"23 May 2013 17:05\",\"tmb\":\"l1_5pWw5a2XNC5qcGc1369299957.png\"}],\"api\":\"2.0\",\"uplMaxSize\":\"2M\"}";
	}
	private String getAdded(){
		return "{\"added\":[{\"mime\":\"directory\",\"ts\":1369299642,\"read\":1,\"write\":1,\"size\":0,\"hash\":\"l1_YWY\",\"name\":\"af\",\"phash\":\"l1_Lw\",\"date\":\"Today 17:00\"}]}";
	}
}
