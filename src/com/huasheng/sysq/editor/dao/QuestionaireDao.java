package com.huasheng.sysq.editor.dao;

import java.util.List;

import com.huasheng.sysq.editor.model.Questionaire;

public interface QuestionaireDao {

	/**
	 * 查找问卷列表
	 * @param versionId
	 * @param type
	 * @return
	 */
	public List<Questionaire> findQuestionaireList(int versionId,int type);
}
