package com.huasheng.sysq.editor.service;

import java.util.Map;

import com.huasheng.sysq.editor.params.TaskResponse;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.Page;

public interface TaskService {

	/**
	 * 任务分配
	 * @param userId
	 * @param taskIds
	 * @return
	 */
	public CallResult<Boolean> assignTask(int userId , String taskIds);
	
	/**
	 * 查找任务
	 * @param searchParams
	 * @return
	 */
	public CallResult<Page<TaskResponse>> findTaskPage(Map<String,String> searchParams);
	
	/**
	 * 查找用户下任务
	 * @param userId
	 * @param searchParams
	 * @return
	 */
	public CallResult<Page<TaskResponse>> findUserTaskPage(int userId,Map<String,Object> searchParams,int currentPage,int pageSize);
}
