package com.ssh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssh.common.Page;
import com.ssh.dao.UserDao;
import com.ssh.entity.SysUser;
import com.ssh.service.UserService;
import com.test.User;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService service;
	
	@Autowired
	UserDao dao;
	
	@RequestMapping("/toLogin")
	public String tologin()
	{
		return "front/login";
	}
	
	@RequestMapping("/login")
	public String login(Model m,SysUser u)
	{
		dao.save(u);
		m.addAttribute("u", u);
		return "front/success";
	}
	
	@RequestMapping("/list")
	public String list(Model m,Page p)
	{
	  Page<SysUser> list=service.getScrollData(p.getIndex(), p.getPageSize());
	  m.addAttribute("u",list);
	  return "front/userList";
	}
}
