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
	 * 根据访谈和问卷获取问题列表
	 * @param interviewId
	 * @param questionaireCode
	 * @return
	 */
	public List<EditorQuestion> selectListByInterviewAndQuestionaire(int interviewId,String questionaireCode);
}
