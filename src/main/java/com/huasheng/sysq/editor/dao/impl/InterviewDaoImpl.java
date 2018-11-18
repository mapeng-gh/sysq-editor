package com.huasheng.sysq.editor.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.InterviewDao;
import com.huasheng.sysq.editor.model.Interview;

@Repository
public class InterviewDaoImpl extends BaseDao implements InterviewDao{
	
	private static String NAMESPACE = "mapper.InterviewMapper";
	
	@Override
	public List<Interview> findUserInterviewPage(String loginName,Map<String, Object> searchParams,int currentPage,int pageSize) {
		if(searchParams == null) {
			searchParams = new HashMap<String,Object>();
		}
		
		searchParams.put("loginName", loginName);
		searchParams.put("offset", (currentPage - 1) * pageSize);
		searchParams.put("limit", pageSize);
		
		return this.getSqlSession().selectList(NAMESPACE + ".findUserInterviewPage", searchParams);
	}

	@Override
	public int countUserInterview(String loginName,Map<String, Object> searchParams) {
		if(searchParams == null) {
			searchParams = new HashMap<String,Object>();
		}
		searchParams.put("loginName", loginName);
		
		return this.getSqlSession().selectOne(NAMESPACE + ".countUserInterview", searchParams);
	}
	
	@Override
	public List<Interview> findUnAssignInterviewPage(Map<String, Object> searchParams,Map<String,String> orderParams,int currentPage,int pageSize) {
		if(searchParams == null) {
			searchParams = new HashMap<String,Object>();
		}
		
		searchParams.put("offset",(currentPage -1) * pageSize);
		searchParams.put("limit", pageSize);
		
		if(orderParams != null && orderParams.size() > 0) {
			for(String key : orderParams.keySet()) {
				searchParams.put(key, orderParams.get(key));
			}
		}
		
        	return super.getSqlSession().selectList(NAMESPACE + ".findUnAssignInterviewPage",searchParams);
	}

	@Override
	public int countUnAssignInterview(Map<String, Object> searchParams) {
		if(searchParams == null) {
			searchParams = new HashMap<String,Object>();
		}
		
        	return super.getSqlSession().selectOne(NAMESPACE + ".countUnAssignInterview",searchParams);
	}

	@Override
	public Interview selectById(int id) {
		return super.getSqlSession().selectOne(NAMESPACE + ".selectById", id);
	}
}
