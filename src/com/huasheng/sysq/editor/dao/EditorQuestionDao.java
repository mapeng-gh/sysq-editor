package com.huasheng.sysq.editor.dao;

import java.util.List;

import com.huasheng.sysq.editor.model.EditorQuestion;

public interface EditorQuestionDao {

	/**
	 * 插入
	 * @param editorQuestion
	 */
	public void insert(EditorQuestion editorQuestion);
	
	/**
	 * 批量插入
	 * @param editorQuestionList
	 */
	public void batchInsert(List<EditorQuestion> editorQuestionList);
	
	/**
	 * 根据访谈和问卷获取问题列表
	 * @param interviewId
	 * @param questionaireCode
	 * @return
	 */
	public List<EditorQuestion> selectListByQuestionaire(int interviewId,String questionaireCode);
	
	/**
	 * 根据编码查询
	 * @param interviewId
	 * @param questionaireCode
	 * @param questionCode
	 * @return
	 */
	public EditorQuestion selectByCode(int interviewId,String questionaireCode,String questionCode);
	
	/**
	 * 更新
	 * @param editorQuestion
	 */
	public void update(EditorQuestion editorQuestion);
}
