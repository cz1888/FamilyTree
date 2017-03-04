package com.ft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ft.service.UserService;

@Controller
@RequestMapping("/manager")
public class Manager {

	@Autowired
	UserService service;
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, Model model)
	{
		// service.delete(2);
		service.delete(id);
		//m.addAttribute("u", u);
		model.addAttribute("users",service.findAll());
		return "suc";
	}
	
	@RequestMapping("/get/{name}")
	public String get(@PathVariable("name") String name, Model model)
	{
		// model.addAttribute("u",service.get(id));
		model.addAttribute("cnt",service.countBy("o.username = ?", new Object[]{name}));
		return "suc";
	}
	
	@RequestMapping("/display")
	public String display(Model m){
		
		m.addAttribute("users",service.findAll());
		return "manager";
	}
}
