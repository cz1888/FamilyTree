package com.ft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ft.service.UserService;

@Controller
@RequestMapping("/user")
public class AddMember {

	@Autowired
	UserService service;
	
}
