package com.ssh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssh.dao.ArticleDao;
import com.ssh.dao.UserDao;
import com.ssh.entity.Article;
import com.ssh.entity.SysUser;

@Service
public class ArticleService {
  @Autowired 
  ArticleDao dao;
  
  @Transactional
  public void save(Article e)
  {
	  dao.save(e);
	  System.out.println("============");
  }

public List<Article> findAll() {
	List<Article> list= dao.findAll();
	return list;
	
}
}
