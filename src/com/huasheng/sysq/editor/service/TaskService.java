package com.huasheng.sysq.editor.service;

import java.util.List;
import java.util.Map;

import com.huasheng.sysq.editor.params.EditorQuestionResponse;
import com.huasheng.sysq.editor.params.EditorQuestionaireResponse;
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
	
	/**
	 * 获取任务详情
	 * @param taskId
	 * @return
	 */
	public CallResult<TaskResponse> getTaskDetail(int taskId);
	
	/**
	 * 完成任务
	 * @param taskId
	 * @return
	 */
	public CallResult<Boolean> finishTask(int taskId);
	
	/**
	 * 获取任务问卷列表
	 * @param taskId
	 * @return
	 */
	public CallResult<List<EditorQuestionaireResponse>> getTaskQuestionaireList(int taskId);
	
	/**
	 * 启用问卷
	 * @param taskId
	 * @param questionaireCode
	 * @return
	 */
	public CallResult<Boolean> enableQuestionaire(int taskId,String questionaireCode);
	
	/**
	 * 禁用问卷
	 * @param taskId
	 * @param questionaireCode
	 * @return
	 */
	public CallResult<Boolean> disableQuestionaire(int taskId,String questionaireCode);
	
	/**
	 * 获取任务问题列表
	 * @param taskId
	 * @param questionaireCode
	 * @return
	 */
	public CallResult<List<EditorQuestionResponse>> getTaskQuestionList(int taskId,String questionaireCode);
}
