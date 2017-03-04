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
		
				
		@RequestMapping("/toregist")
		public String tologin()
		{
			return "regist";
		}
		
		/*@RequestMapping("/tosuc")
		public String login(Model m,User u)
		{
			service.save(u);
			//service.delete(3);
			m.addAttribute("u", u);
			return "suc";
		}*/
	    
		//用户注册判断
		@RequestMapping("/toSignUp")
		public String signUp(Model m, User u) {
			int i;
			i = service.countBy("o.username = ?", new Object[] { u.getUsername() });
			if (i == 1)
				return "userExist";
			else {
				service.save(u);
				return "signUpSucceed";
			}
		}
		
		//用户登录判断
		@RequestMapping("/toSignIn")
		public String signIn(Model m, User u){
			int i;
			i = service.countBy("o.username = ?", new Object[] { u.getUsername() });
			if(i==1){
				User a = service.findUniqueBy("username", u.getUsername());
				String b = a.getPassword();
				String c = u.getPassword();
				if(b.equals(c)){
					return "signInSuc";
				}
				else return "regist";
			}
			return "userNotExist";
		}
		
		//添加家庭成员
		@RequestMapping("/add")
		public String addFather(Model m, User u){
			service.save(u);
			u.getId();
			return "";
		}
	}

