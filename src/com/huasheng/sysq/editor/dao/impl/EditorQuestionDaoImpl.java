package com.huasheng.sysq.editor.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.EditorQuestionDao;
import com.huasheng.sysq.editor.model.EditorQuestion;

@Repository
public class EditorQuestionDaoImpl extends BaseDao implements EditorQuestionDao{
	
	private static String NAMESPACE = "mapper.EditorQuestionMapper";

	@Override
	public void insert(EditorQuestion editorQuestion) {
		super.getSqlSession().insert(NAMESPACE + ".insert", editorQuestion);
	}

	@Override
	public List<EditorQuestion> selectListByQuestionaire(int interviewId, String questionaireCode) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("interviewId", interviewId);
		paramMap.put("questionaireCode", questionaireCode);
		
		return super.getSqlSession().selectList(NAMESPACE + ".selectListByQuestionaire", paramMap);
	}

	@Override
	public EditorQuestion selectByCode(int interviewId, String questionaireCode, String questionCode) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("interviewId", interviewId);
		paramMap.put("questionaireCode", questionaireCode);
		paramMap.put("questionCode", questionCode);
		
		return super.getSqlSession().selectOne(NAMESPACE + ".selectByCode", paramMap);
	}

	@Override
	public void update(EditorQuestion editorQuestion) {
		super.getSqlSession().update(NAMESPACE + ".update", editorQuestion);
	}

}
