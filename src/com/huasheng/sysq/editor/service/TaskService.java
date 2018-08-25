package com.huasheng.sysq.editor.service;

import com.huasheng.sysq.editor.util.CallResult;

public interface TaskService {

	/**
	 * 任务分配
	 * @param userId
	 * @param taskIds
	 * @return
	 */
	public CallResult<Boolean> assignTask(int userId , String taskIds);
}
