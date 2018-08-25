package com.huasheng.sysq.editor.dao;

import java.util.List;
import java.util.Map;

import com.huasheng.sysq.editor.model.Task;

public interface TaskDao {

	/**
	 * 插入
	 * @param task
	 */
	public void insert(Task task);
	
	/**
	 * 查找
	 * @param searchParams
	 * @return
	 */
	public List<Task> findMainPage(Map<String,Object> searchParams);
	
	/**
	 * 统计
	 * @param searchParams
	 * @return
	 */
	public int countMain(Map<String,Object> searchParams);
}
