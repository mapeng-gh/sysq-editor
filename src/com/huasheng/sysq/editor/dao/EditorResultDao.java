package com.huasheng.sysq.editor.dao;

import com.huasheng.sysq.editor.model.SysqResult;

public interface EditorResultDao {
	
	/**
	 * 插入
	 * @param editSysqResult
	 */
	public void insert(SysqResult editorSysqResult);
	
	/**
	 * 根据编码查询
	 * @param interviewId
	 * @param questionaireCode
	 * @param questionCode
	 * @param answerCode
	 * @return
	 */
	public SysqResult selectByAnswerCode(int interviewId,String questionaireCode,String questionCode,String answerCode);
	
	/**
	 * 根据问题删除
	 * @param interviewId
	 * @param questionaireCode
	 * @param questionCode
	 */
	public void deleteByQuestion(int interviewId,String questionaireCode,String questionCode);

}
