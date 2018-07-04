package com.huasheng.sysq.editor.service;

import com.huasheng.sysq.editor.model.User;

public interface UserService {
	
	public User getUserByName(String name);

	public int addUser(User user);
}
