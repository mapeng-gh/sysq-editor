package com.huasheng.sysq.editor.dao.impl;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.UserDao;
import com.huasheng.sysq.editor.model.User;

@Repository
public class UserDaoImpl extends BaseDao implements UserDao{
	
	private static String NAMESPACE = "mapper.UserMapper";

	@Override
	public User getByName(String name) {
		return super.getSqlSession().selectOne(NAMESPACE + ".selectByName", name);
	}

	@Override
	public void insert(User user) {
		super.getSqlSession().insert(NAMESPACE + ".insert", user);
	}

}
