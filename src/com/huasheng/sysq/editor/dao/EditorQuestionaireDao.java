package com.huasheng.sysq.editor.dao;

import java.util.List;

import com.huasheng.sysq.editor.model.EditorQuestionaire;

public interface EditorQuestionaireDao {

	/**
	 * 获取访谈下问卷列表
	 * @param interviewId
	 * @return
	 */
	public List<EditorQuestionaire> getListByInterviewId(int interviewId);
	
	/**
	 * 插入
	 * @param editorQuestionaire
	 */
	public void insert(EditorQuestionaire editorQuestionaire);
}