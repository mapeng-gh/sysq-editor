package com.huasheng.sysq.editor.service;

import java.util.List;
import java.util.Map;

import com.huasheng.sysq.editor.model.Questionaire;
import com.huasheng.sysq.editor.params.InterviewResponse;
import com.huasheng.sysq.editor.params.QuestionResponse;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.Page;

public interface InterviewService {

	/**
	 * 查找访谈列表
	 * @param searchParams
	 * @return
	 */
	public CallResult<Page<InterviewResponse>> findUserInterviewPage(String loginName,Map<String,Object> searchParams,int currentPage,int pageSize);
	
	/**
	 * 查找问卷列表
	 * @param interviewId
	 * @return
	 */
	public CallResult<List<Questionaire>> findQuestionaireListByInterviewId(int interviewId);
	
	/**
	 * 查找问题列表
	 * @param interviewId
	 * @param questionaireCode
	 * @return
	 */
	public CallResult<List<QuestionResponse>> findQuestionListByInterviewIdAndQuestionaireCode(int interviewId,String questionaireCode);
}
