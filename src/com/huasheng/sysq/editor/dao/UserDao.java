package com.huasheng.sysq.editor.dao;

import com.huasheng.sysq.editor.model.User;

public interface UserDao {

	public User getByName(String name);
	
	public void insert(User user);
}
