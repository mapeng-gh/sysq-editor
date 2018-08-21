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
	public List<User> find(Map<String,String> searchParams);
	
	/**
	 * 统计
	 * @param searchUser
	 * @return
	 */
	public int count(Map<String,String> searchParams);
	
	/**
	 * 插入
	 * @param newUser
	 */
	public void insert(User newUser);
	
	/**
	 * 登录账号查询
	 * @param loginName
	 * @return
	 */
	public User selectByLoginName(String loginName);
	
	/**
	 * 主键查找
	 * @param id
	 * @return
	 */
	public User selectById(int id);
	
	/**
	 * 更新
	 * @param user
	 */
	public void update(User user);
}
