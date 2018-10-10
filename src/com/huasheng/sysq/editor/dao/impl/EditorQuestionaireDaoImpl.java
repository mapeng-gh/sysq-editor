package com.huasheng.sysq.editor.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.EditorQuestionaireDao;
import com.huasheng.sysq.editor.model.EditorQuestionaire;

@Repository
public class EditorQuestionaireDaoImpl extends BaseDao implements EditorQuestionaireDao{
	
	private static String NAMESPACE = "mapper.EditorQuestionaireMapper";

	@Override
	public List<EditorQuestionaire> getListByInterviewId(int interviewId) {
		return super.getSqlSession().selectList(NAMESPACE + ".getListByInterviewId", interviewId);
	}

	@Override
	public void insert(EditorQuestionaire editorQuestionaire) {
		super.getSqlSession().insert(NAMESPACE + ".insert", editorQuestionaire);
	}

}
