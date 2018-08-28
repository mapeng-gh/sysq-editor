package com.huasheng.sysq.editor.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.TaskDao;
import com.huasheng.sysq.editor.model.Task;

@Repository
public class TaskDaoImpl extends BaseDao implements TaskDao{
	
	private static String NAMESPACE = "mapper.TaskMapper";

	@Override
	public void insert(Task task) {
		super.getSqlSession().insert(NAMESPACE + ".insert", task);
	}

	@Override
	public List<Task> findAllTaskPage(Map<String, Object> searchParams) {
		return this.getSqlSession().selectList(NAMESPACE + ".findAllTaskPage", searchParams);
	}

	@Override
	public int countAllTask(Map<String, Object> searchParams) {
		return this.getSqlSession().selectOne(NAMESPACE + ".countAllTask", searchParams);
	}

	@Override
	public List<Task> findUserTaskPage(int userId, Map<String, Object> searchParams,int currentPage,int pageSize) {
		if(searchParams == null) {
			searchParams = new HashMap<String,Object>();
		}
		
		searchParams.put("userId", userId);
		
		searchParams.put("offset", (currentPage - 1) * pageSize);
		searchParams.put("limit", pageSize);
		
		return this.getSqlSession().selectList(NAMESPACE + ".findUserTaskPage", searchParams);
	}

	@Override
	public int countUserTask(int userId, Map<String, Object> searchParams) {
		if(searchParams == null) {
			searchParams = new HashMap<String,Object>();
		}
		
		searchParams.put("userId", userId);
		
		return this.getSqlSession().selectOne(NAMESPACE + ".countUserTask", searchParams);
	}

	@Override
	public List<Task> findByInterviewId(int interviewId) {
		return super.getSqlSession().selectOne(NAMESPACE + ".findByInterviewId",interviewId);
	}
}
