package com.huasheng.sysq.editor.dao;

import java.util.List;
import java.util.Map;

import com.huasheng.sysq.editor.model.User;

public interface UserDao {
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public User selectById(int id);
	
	/**
	 * 根据登录账号查询
	 * @param loginName
	 * @return
	 */
	public User selectByLoginName(String loginName);
	
	/**
	 * 根据用户类型查询
	 * @param userType
	 * @return
	 */
	public List<User> findByUserType(int userType);

	/**
	 * 根据条件分页查询
	 * @param searchParams
	 * @return
	 */
	public List<User> find(Map<String,String> searchParams);
	
	/**
	 * 根据条件统计
	 * @param searchUser
	 * @return
	 */
	public int count(Map<String,String> searchParams);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<User> findAll();
	
	/**
	 * 插入
	 * @param newUser
	 */
	public void insert(User newUser);
	
	/**
	 * 更新
	 * @param user
	 */
	public void update(User user);
}
