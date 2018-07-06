package com.huasheng.sysq.editor.service;

import com.huasheng.sysq.editor.model.User;
import com.huasheng.sysq.editor.util.CallResult;

public interface UserService {
	
	public CallResult<User> getUserByName(String name);

	public CallResult<Boolean> addUser(User user);
}
