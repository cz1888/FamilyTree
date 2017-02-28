package com.ft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ft.dao.UserDao;
import com.ft.entity.User;
import com.ft.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {	
		
	    @Autowired
		UserService service;
		
				
		@RequestMapping("/toregisit")
		public String tologin()
		{
			return "regisit";
		}
		
		@RequestMapping("/tosuc")
		public String login(Model m,User u)
		{
			service.save(u);
			m.addAttribute("u", u);
			return "suc";
		}
	}

