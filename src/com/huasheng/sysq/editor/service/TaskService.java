package com.huasheng.sysq.editor.service;

import java.util.Map;

import com.huasheng.sysq.editor.params.TaskResponse;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.Page;

public interface TaskService {

	/**
	 * 任务分配
	 * @param userId
	 * @param interviewIds
	 * @return
	 */
	public CallResult<Boolean> assignTask(int userId , String interviewIds);
	
	/**
	 * 查询任务
	 * @param searchParams
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public CallResult<Page<TaskResponse>> findTaskPage(Map<String,Object> searchParams , int currentPage , int pageSize);
	
	/**
	 * 查询用户下任务
	 * @param userId
	 * @param searchParams
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public CallResult<Page<TaskResponse>> findUserTaskPage(int userId,Map<String,Object> searchParams,int currentPage,int pageSize);
}
