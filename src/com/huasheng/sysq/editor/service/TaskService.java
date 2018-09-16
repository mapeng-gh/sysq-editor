package com.huasheng.sysq.editor.service;

import java.util.List;
import java.util.Map;

import com.huasheng.sysq.editor.model.Questionaire;
import com.huasheng.sysq.editor.params.InterviewResponse;
import com.huasheng.sysq.editor.params.TaskResponse;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.Page;

public interface TaskService {
	
	/**
	 * 查找未分配访谈
	 * @param searchParams
	 * @return
	 */
 	public CallResult<Page<InterviewResponse>> findUnAssignInterviewPage(Map<String,Object> searchParams,int currentPage,int pageSize);

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
	
	//获取任务问卷列表
	public CallResult<List<Questionaire>> getTaskQuestionaireList(int taskId);
}
