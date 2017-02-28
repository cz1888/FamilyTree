package com.ssh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssh.common.BaseService;
import com.ssh.dao.UserDao;
import com.ssh.entity.SysUser;

@Service
public class UserService extends BaseService<SysUser> {
 @Autowired 
  UserDao dao;
 /*  
  @Transactional
  public void save(SysUser u)
  {
	  dao.save(u);
	  System.out.println("============");
  }*/
}
