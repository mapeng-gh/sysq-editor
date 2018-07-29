package com.huasheng.sysq.editor.dao;

import java.util.List;

import com.huasheng.sysq.editor.model.User;

public interface UserDao {

	/**
	 * 搜索
	 * @param searchUser
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	List<User> selectList(User searchUser,int pageSize,int currentPage);
	
	/**
	 * 统计
	 * @param searchUser
	 * @return
	 */
	int count(User searchUser);
	
	/**
	 * 插入
	 * @param newUser
	 */
	void insert(User newUser);
}
