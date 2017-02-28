package com.ssh.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssh.entity.Article;
import com.ssh.entity.SysUser;

@Repository
public class ArticleDao {
   @Autowired
   SessionFactory sf;
   
   public void save(Article e)
   {
	   sf.getCurrentSession().save(e);
   }

public List<Article> findAll() {
	String queryString = "from Article";
	Query queryObject = sf.getCurrentSession().createQuery(queryString);
	return queryObject.list();
}
}
