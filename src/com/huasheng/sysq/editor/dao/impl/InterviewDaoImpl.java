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
	public List<Interview> findDoctorInterviewPage(String mobile,Map<String, Object> searchParams,int currentPage,int pageSize) {
		if(searchParams == null) {
			searchParams = new HashMap<String,Object>();
		}
		
		searchParams.put("mobile", mobile);
		
		searchParams.put("offset", (currentPage - 1) * pageSize);
		searchParams.put("limit", pageSize);
		
		return this.getSqlSession().selectList(NAMESPACE + ".findDoctorInterviewPage", searchParams);
	}

	@Override
	public int countDoctorInterview(String mobile,Map<String, Object> searchParams) {
		if(searchParams == null) {
			searchParams = new HashMap<String,Object>();
		}
		
		searchParams.put("mobile", mobile);
		
		return this.getSqlSession().selectOne(NAMESPACE + ".countDoctorInterview", searchParams);
	}
	
	@Override
	public List<Interview> findUnAssignInterviewPage(Map<String, Object> searchParams,int currentPage,int pageSize) {
		if(searchParams == null) {
			searchParams = new HashMap<String,Object>();
		}
		
		searchParams.put("offset",(currentPage -1) * pageSize);
		searchParams.put("limit", pageSize);
		
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
