package com.ft.junit;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.ft.entity.User;
import com.ft.utils.HibernateUtils;
import com.ft.dao.*;
public class HibernateDemo {

	/**
	 * 
	 */
	@Test
	public void testAdd() {
		
//		第一步 加载hibernate核心配置文件
		// 到src下面找到名称是hibernate.cfg.xml
		//在hibernate里面封装对象
//		Configuration cfg = new Configuration();
//		cfg.configure();
		
//		第二步 创建SessionFactory对象
		//读取hibernate核心配置文件内容，创建sessionFactory
		//在过程中，根据映射关系，在配置数据库里面把表创建
//		SessionFactory sessionFactory = cfg.buildSessionFactory();
		
	
		//由于使用了工具类，所以第一步和第二步可以使用下边一句代替
//		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		
//		第三步 使用SessionFactory创建session对象
		// 类似于连接
//		Session session = sessionFactory.openSession();
		
//		第四步 开启事务
//		Transaction tx = session.beginTransaction();

//		第五步 写具体逻辑 crud操作
	
		UserDao dao=new UserDao();
        User test= new User();
        test.setUsername("哆啦c梦");
       // test.setAddress("日本东京");;
		dao.save(test);
		
//		第六步 提交事务
//		tx.commit();

//		第七步 关闭资源
//		session.close();
//		sessionFactory.close();
//		

	}
}
