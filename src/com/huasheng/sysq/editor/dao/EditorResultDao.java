package com.huasheng.sysq.editor.dao;

import java.util.List;

import com.huasheng.sysq.editor.model.SysqResult;

public interface EditorResultDao {
	
	/**
	 * 插入
	 * @param editSysqResult
	 */
	public void insert(SysqResult editorSysqResult);
	
	/**
	 * 批量插入
	 * @param editorResultList
	 */
	public void batchInsert(List<SysqResult> editorResultList);
	
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
	
	/**
	 * 查找答案集合
	 * @param interviewId
	 * @param questionaireCode
	 * @param questionCode
	 * @return
	 */
	public List<String> getAnswerList(int interviewId,String questionaireCode,String questionCode);

}
