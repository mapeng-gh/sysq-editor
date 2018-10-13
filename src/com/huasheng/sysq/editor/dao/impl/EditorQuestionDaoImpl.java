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
	public List<EditorQuestion> selectListByInterviewAndQuestionaire(int interviewId, String questionaireCode) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("interviewId", interviewId);
		paramsMap.put("questionaireCode", questionaireCode);
		
		return super.getSqlSession().selectList(NAMESPACE + ".selectListByInterviewAndQuestionaire", paramsMap);
	}

}
