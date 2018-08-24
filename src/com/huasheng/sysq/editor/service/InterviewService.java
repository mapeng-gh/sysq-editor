package com.huasheng.sysq.editor.service;

import java.util.Map;

import com.huasheng.sysq.editor.params.InterviewWrap;
import com.huasheng.sysq.editor.params.UnAssignInterviewResponse;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.Page;

public interface InterviewService {

	/**
	 * 查找医生访谈列表
	 * @param searchParams
	 * @return
	 */
	public CallResult<Page<InterviewWrap>> findDoctorInterviewPage(Map<String,String> searchParams);
	
	/**
	 * 查找未分配访谈列表
	 * @param searchRequest
	 * @return
	 */
 	public CallResult<Page<UnAssignInterviewResponse>> findUnAssignInterviewPage(Map<String,String> searchRequest);
}
