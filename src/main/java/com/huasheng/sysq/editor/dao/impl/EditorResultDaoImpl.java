package com.huasheng.sysq.editor.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.EditorResultDao;
import com.huasheng.sysq.editor.model.SysqResult;

@Repository
public class EditorResultDaoImpl extends BaseDao implements EditorResultDao{
	
	private static String NAMESPACE = "mapper.EditorResultMapper";

	@Override
	public void insert(SysqResult editorSysqResult) {
		super.getSqlSession().insert(NAMESPACE + ".insert", editorSysqResult);
	}
	
	@Override
	public void batchInsert(List<SysqResult> editorResultList) {
		super.getSqlSession().insert(NAMESPACE + ".batchInsert", editorResultList);
	}

	@Override
	public SysqResult selectByAnswerCode(int interviewId, String questionaireCode, String questionCode, String answerCode) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("interviewId", interviewId);
		paramMap.put("questionaireCode", questionaireCode);
		paramMap.put("questionCode", questionCode);
		paramMap.put("answerCode", answerCode);
		
		return super.getSqlSession().selectOne(NAMESPACE + ".selectByAnswerCode", paramMap);
	}

	@Override
	public void deleteByQuestion(int interviewId, String questionaireCode, String questionCode) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("interviewId", interviewId);
		paramMap.put("questionaireCode", questionaireCode);
		paramMap.put("questionCode", questionCode);

		super.getSqlSession().delete(NAMESPACE + ".deleteByQuestion", paramMap);
	}

	@Override
	public List<String> getAnswerCodeList(int interviewId, String questionaireCode, String questionCode) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("interviewId", interviewId);
		paramMap.put("questionaireCode", questionaireCode);
		paramMap.put("questionCode", questionCode);
		
		return super.getSqlSession().selectList(NAMESPACE + ".getAnswerCodeList", paramMap);
	}

	@Override
	public List<SysqResult> getAnswerList(int interviewId, String questionaireCode, String questionCode) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("interviewId", interviewId);
		paramMap.put("questionaireCode", questionaireCode);
		paramMap.put("questionCode", questionCode);
		
		return super.getSqlSession().selectList(NAMESPACE + ".getAnswerList", paramMap);
	}
}
