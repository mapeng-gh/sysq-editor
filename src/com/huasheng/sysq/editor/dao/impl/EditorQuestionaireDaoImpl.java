package com.huasheng.sysq.editor.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Override
	public void batchInsert(List<EditorQuestionaire> editorQuestionaireList) {
		super.getSqlSession().insert(NAMESPACE + ".batchInsert", editorQuestionaireList);
	}

	@Override
	public EditorQuestionaire selectByInterviewAndQuestionaire(int interviewId, String questionaireCode) {
		Map<String ,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("interviewId", interviewId);
		paramsMap.put("questionaireCode", questionaireCode);
		
		return super.getSqlSession().selectOne(NAMESPACE + ".selectByInterviewAndQuestionaire", paramsMap);
	}

	@Override
	public void update(EditorQuestionaire editorQuestionaire) {
		super.getSqlSession().update(NAMESPACE + ".update", editorQuestionaire);
	}

	

}
