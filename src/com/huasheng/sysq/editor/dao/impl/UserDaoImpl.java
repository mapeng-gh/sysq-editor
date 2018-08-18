package com.huasheng.sysq.editor.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.UserDao;
import com.huasheng.sysq.editor.model.User;

@Repository
public class UserDaoImpl extends BaseDao implements UserDao{

        private static String NAMESPACE = "mapper.UserMapper";
        
        @Override
        public int count(Map<String,String> searchParams) {
                return super.getSqlSession().selectOne(NAMESPACE + ".count",searchParams);
        }
	
        @Override
        public List<User> find(Map<String,String> searchParams) {
        	Map<String,Object> handledParams = super.handleParams(searchParams);
        	return super.getSqlSession().selectList(NAMESPACE + ".find",handledParams);
	}

	@Override
	public void insert(User newUser) {
		
	}

	@Override
	public User selectByLoginName(String loginName) {
		return super.getSqlSession().selectOne(NAMESPACE + ".selectByLoginName",loginName);
	}
}
