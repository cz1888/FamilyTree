package com.ssh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.google.gson.Gson;
import com.ssh.dao.UserDao;
import com.ssh.entity.Article;
import com.ssh.entity.SysUser;
import com.ssh.service.ArticleService;
import com.ssh.service.UserService;
import com.test.User;

@Controller
@RequestMapping("/cms")
public class ArticleController {
	@Autowired
	ArticleService service;
	
	@RequestMapping("/toPublish")
	public String toPublish()
	{
		return "front/publish";
	}
	 
	@RequestMapping("/list")
	@ResponseBody
	public String findAll(Model m)
	{
	    String json="";
	    Gson gson=new Gson();
	    json=gson.toJson(service.findAll());
		return json;
	}

	@RequestMapping("/publish")
	public String publish(Model m,Article e)
	{
		service.save(e);
		m.addAttribute("e",e);
		return "front/viewArticle";
	}
}
