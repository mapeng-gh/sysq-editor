package com.huasheng.sysq.editor.service;

import java.util.List;
import java.util.Map;

import com.huasheng.sysq.editor.model.Questionaire;
import com.huasheng.sysq.editor.model.Version;
import com.huasheng.sysq.editor.params.QuestionResponse;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.Page;

public interface DataService {

	/**
	 * 查询版本
	 * @param searchParams
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public CallResult<Page<Version>> findVersionPage(Map<String,Object> searchParams,int currentPage,int pageSize);
	
	/**
	 * 查询问卷
	 * @param versionId
	 * @return
	 */
	public CallResult<List<Questionaire>> getQuestionaireList(int versionId,int type);
	
	/**
	 * 问题列表
	 * @param versionId
	 * @param questionaireCode
	 * @return
	 */
	public CallResult<? extends List<QuestionResponse>> getQuestionList(int versionId,String questionaireCode);
	
	/**
	 * 查看问题
	 * @param versionId
	 * @param questionCode
	 * @return
	 */
	public CallResult<QuestionResponse> getQuestion(int versionId,String questionCode);
}

