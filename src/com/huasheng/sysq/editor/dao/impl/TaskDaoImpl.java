package com.huasheng.sysq.editor.dao.impl;

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

}
