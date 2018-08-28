package com.huasheng.sysq.editor.service;

import java.util.List;
import java.util.Map;

import com.huasheng.sysq.editor.model.Questionaire;
import com.huasheng.sysq.editor.params.InterviewResponse;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.Page;

public interface InterviewViewService {

	/**
	 * 查找医生访谈列表
	 * @param searchParams
	 * @return
	 */
	public CallResult<Page<InterviewResponse>> findDoctorInterviewPage(String mobile,Map<String,Object> searchParams,int currentPage,int pageSize);
	
	/**
	 * 根据访谈查找问卷列表
	 * @param interviewId
	 * @return
	 */
	public CallResult<List<Questionaire>> findQuestionaireListByInterviewId(int interviewId);
}
