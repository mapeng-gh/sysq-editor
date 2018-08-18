package com.huasheng.sysq.editor.dao;

import java.util.List;
import java.util.Map;

import com.huasheng.sysq.editor.model.User;

public interface UserDao {

	/**
	 * 搜索
	 * @param searchParams
	 * @return
	 */
	List<User> find(Map<String,String> searchParams);
	
	/**
	 * 统计
	 * @param searchUser
	 * @return
	 */
	int count(Map<String,String> searchParams);
	
	/**
	 * 插入
	 * @param newUser
	 */
	void insert(User newUser);
	
	/**
	 * 登录账号查询
	 * @param loginName
	 * @return
	 */
	User selectByLoginName(String loginName);
}
