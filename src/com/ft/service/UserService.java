package com.ft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ft.common.BaseService;
import com.ft.dao.UserDao;
import com.ft.entity.User;



@Service
public class UserService  extends BaseService<User>{
@Autowired
UserDao dao;


}
