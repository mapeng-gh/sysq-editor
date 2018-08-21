package com.huasheng.sysq.editor.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.UserDao;
import com.huasheng.sysq.editor.model.User;

@Repository
public class UserDaoImpl extends BaseDao implements UserDao{

        private static String NAMESPACE = "mapper.UserMapper";
        
	private Map<String,Object> handleFindParams(Map<String,String> searchParams){
		if(searchParams != null) {
			String name = searchParams.get("name");
			String userType = searchParams.get("userType");
			String auditStatus = searchParams.get("auditStatus");
			String currentPage = searchParams.get("currentPage");
			String pageSize = searchParams.get("pageSize");
			
			Map<String,Object> handledParams = new HashMap<String,Object>();
			if(!StringUtils.isBlank(name)) {
				handledParams.put("name",name);
			}
			if(!StringUtils.isBlank(userType)) {
				handledParams.put("userType", Integer.parseInt(userType));
			}
			if(!StringUtils.isBlank(auditStatus)) {
				handledParams.put("auditStatus", Integer.parseInt(auditStatus));
			}
			
        		if(!StringUtils.isBlank(currentPage) && !StringUtils.isBlank(pageSize)) {
        			handledParams.put("offset",(Integer.parseInt(currentPage) -1) * Integer.parseInt(pageSize));
        			handledParams.put("limit", Integer.parseInt(pageSize));
        		}
        		
        		return handledParams;
        	}else {
        		return null;
        	}
	}
        
        @Override
        public int count(Map<String,String> searchParams) {
        	Map<String,Object> handledParams = this.handleFindParams(searchParams);
                return super.getSqlSession().selectOne(NAMESPACE + ".count",handledParams);
        }
	
        @Override
        public List<User> find(Map<String,String> searchParams) {
        	Map<String,Object> handledParams = this.handleFindParams(searchParams);
        	return super.getSqlSession().selectList(NAMESPACE + ".find",handledParams);
	}

	@Override
	public void insert(User newUser) {
		
	}

	@Override
	public User selectByLoginName(String loginName) {
		return super.getSqlSession().selectOne(NAMESPACE + ".selectByLoginName",loginName);
	}

	@Override
	public User selectById(int id) {
		return super.getSqlSession().selectOne(NAMESPACE + ".selectById",id);
	}

	@Override
	public void update(User user) {
		super.getSqlSession().update(NAMESPACE + ".update",user);
	}
}
