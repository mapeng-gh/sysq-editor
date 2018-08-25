package com.huasheng.sysq.editor.dao.impl;

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
	public List<Task> findMainPage(Map<String, Object> searchParams) {
		return this.getSqlSession().selectList(NAMESPACE + ".findMainPage", searchParams);
	}

	@Override
	public int countMain(Map<String, Object> searchParams) {
		return this.getSqlSession().selectOne(NAMESPACE + ".countMain", searchParams);
	}

}
