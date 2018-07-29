package com.huasheng.sysq.editor.service;

import com.huasheng.sysq.editor.model.User;
import com.huasheng.sysq.editor.params.UserManageSearchRequest;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.Page;

public interface UserService {
	
	/**
	 * 查找用户
	 * @param searchUser
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public CallResult<Page<User>> findUserList(UserManageSearchRequest searchRequest);
	
	/**
	 * 添加用户
	 * @param newUser
	 */
	public CallResult<Boolean> addUser(User newUser);
}
