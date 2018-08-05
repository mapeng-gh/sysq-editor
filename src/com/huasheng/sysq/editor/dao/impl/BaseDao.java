package com.huasheng.sysq.editor.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDao extends SqlSessionDaoSupport{

	@Autowired
	@Override
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	/**
	 * 参数处理
	 * @param searchParams
	 * @return
	 */
	public Map<String,Object> handleParams(Map<String,String> searchParams){
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
}
