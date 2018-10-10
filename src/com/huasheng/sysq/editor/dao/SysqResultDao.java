package com.huasheng.sysq.editor.dao;

import java.util.List;

import com.huasheng.sysq.editor.model.SysqResult;

public interface SysqResultDao {

	/**
	 * 查询问卷集合
	 * @param interviewId
	 * @return
	 */
	public List<String> getQuestionaireList(int interviewId);
	
	/**
	 * 查找问题集合
	 * @param interviewId
	 * @param questionaireCode
	 * @return
	 */
	public List<String> getQuestionList(int interviewId,String questionaireCode);
	
	/**
	 * 查找答案集合
	 * @param interviewId
	 * @param questionaireCode
	 * @param questionCode
	 * @return
	 */
	public List<String> getAnswerList(int interviewId,String questionaireCode,String questionCode);
	
	/**
	 * 获取问题下所有结果
	 * @param interviewId
	 * @param questionaireCode
	 * @param questionCode
	 * @return
	 */
	public List<SysqResult> getAnswerResultByQuestion(int interviewId,String questionaireCode,String questionCode);
	
	/**
	 * 查找结果
	 * @param interviewId
	 * @param questionaireCode
	 * @param questionCode
	 * @param answerCode
	 * @return
	 */
	public SysqResult getAnswerResult(int interviewId,String questionaireCode,String questionCode,String answerCode);
	
	/**
	 * 获取所有答案
	 * @param interviewId
	 * @return
	 */
	public List<SysqResult> getAllAnswerResult(int interviewId);
}
