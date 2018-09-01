package com.huasheng.sysq.editor.dao;

import java.util.List;

import com.huasheng.sysq.editor.model.SysqResult;

public interface SysqResultDao {

	/**
	 * 查询问卷集合
	 * @param interviewId
	 * @return
	 */
	public List<String> findQuestionaireList(int interviewId);
	
	/**
	 * 查找问题集合
	 * @param interviewId
	 * @param questionaireCode
	 * @return
	 */
	public List<String> findQuestionList(int interviewId,String questionaireCode);
	
	/**
	 * 查找答案集合
	 * @param interviewId
	 * @param questionaireCode
	 * @param questionCode
	 * @return
	 */
	public List<String> findAnswerList(int interviewId,String questionaireCode,String questionCode);
	
	/**
	 * 查找结果
	 * @param interviewId
	 * @param questionaireCode
	 * @param questionCode
	 * @param answerCode
	 * @return
	 */
	public SysqResult findAnswerResult(int interviewId,String questionaireCode,String questionCode,String answerCode);
}
