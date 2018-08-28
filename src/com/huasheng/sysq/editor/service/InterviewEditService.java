package com.huasheng.sysq.editor.service;

import java.util.Map;

import com.huasheng.sysq.editor.params.InterviewResponse;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.Page;

public interface InterviewEditService {
	
	/**
	 * 查找未分配访谈列表
	 * @param searchParams
	 * @return
	 */
 	public CallResult<Page<InterviewResponse>> findUnAssignInterviewPage(Map<String,Object> searchParams,int currentPage,int pageSize);
 	
 	/**
 	 * 查找编辑访谈列表
 	 * @param userId
 	 * @param searchParams
 	 * @param currentPage
 	 * @param pageSize
 	 * @return
 	 */
 	public CallResult<Page<InterviewResponse>> findEditorInterviewPage(int userId,Map<String,Object> searchParams,int currentPage,int pageSize);
 	
 	
}

