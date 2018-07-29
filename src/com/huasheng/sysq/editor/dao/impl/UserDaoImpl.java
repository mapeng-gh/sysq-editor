package com.huasheng.sysq.editor.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.UserDao;
import com.huasheng.sysq.editor.model.User;

@Repository
public class UserDaoImpl extends BaseDao implements UserDao{

        private static String NAMESPACE = "mapper.UserMapper";
        
        @Override
        public int count(User searchUser) {
                return super.getSqlSession().selectOne(NAMESPACE + ".count",searchUser);
        }
	
        @Override
        public List<User> selectList(User searchUser, int pageSize, int currentPage) {
//                return super.getSqlSession().selectList(NAMESPACE + ".find",searchUser,new RowBounds((currentPage-1)*pageSize, pageSize));
                return super.getSqlSession().selectList(NAMESPACE + ".find",searchUser);
			
	}

	@Override
	public void insert(User newUser) {
		
	}
}
