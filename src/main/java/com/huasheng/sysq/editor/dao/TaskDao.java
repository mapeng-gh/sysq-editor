package com.huasheng.sysq.editor.dao;

import java.util.List;
import java.util.Map;

import com.huasheng.sysq.editor.model.Task;

public interface TaskDao {
	
	/**
	 * 主键查找
	 * @param id
	 * @return
	 */
	public Task selectById(int id);

	/**
	 * 插入
	 * @param task
	 */
	public void insert(Task task);
	
	/**
	 * 查询任务
	 * @param searchParams
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<Task> findAllTaskPage(Map<String,Object> searchParams , int currentPage , int pageSize);
	
	/**
	 * 统计所有任务
	 * @param searchParams
	 * @return
	 */
	public int countAllTask(Map<String,Object> searchParams);
	
	/**
	 * 查找用户下任务
	 * @param userId
	 * @param searchParams
	 * @return
	 */
	public List<Task> findUserTaskPage(int userId,Map<String,Object> searchParams,int currentPage,int pageSize);
	
	/**
	 * 统计用户下任务
	 * @param userId
	 * @param searchParams
	 * @return
	 */
	public int countUserTask(int userId,Map<String,Object> searchParams);
	
	/**
	 * 更新任务
	 * @param task
	 */
	public void update(Task task);
	
	/**
	 * 根据访谈查询任务
	 * @param interviewId
	 * @return
	 */
	public Task findByInterviewId(int interviewId);
}
