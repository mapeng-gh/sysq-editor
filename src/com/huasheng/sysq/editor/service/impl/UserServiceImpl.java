package com.huasheng.sysq.editor.service.impl;

import org.springframework.stereotype.Service;

import com.huasheng.sysq.editor.model.User;
import com.huasheng.sysq.editor.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Override
	public User getUserByName(String name) {
		return new User(1,"zhangsan",20);
	}
	
	@Override
	public int addUser(User user) {
		return 1;
	}
}
