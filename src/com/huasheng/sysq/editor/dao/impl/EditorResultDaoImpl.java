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
	public List<String> getQuestionaireList(int interviewId) {
		return super.getSqlSession().selectList(NAMESPACE + ".getQuestionaireList", interviewId);
	}

	@Override
	public List<String> getQuestionList(int interviewId, String questionaireCode) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("interviewId", interviewId);
		paramMap.put("questionaireCode", questionaireCode);
		
		return super.getSqlSession().selectList(NAMESPACE + ".getQuestionList", paramMap);
	}

	@Override
	public List<String> getAnswerList(int interviewId, String questionaireCode, String questionCode) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("interviewId", interviewId);
		paramMap.put("questionaireCode", questionaireCode);
		paramMap.put("questionCode", questionCode);
		
		return super.getSqlSession().selectList(NAMESPACE + ".getAnswerList", paramMap);
	}

	@Override
	public SysqResult getAnswerResult(int interviewId, String questionaireCode, String questionCode,String answerCode) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("interviewId", interviewId);
		paramMap.put("questionaireCode", questionaireCode);
		paramMap.put("questionCode", questionCode);
		paramMap.put("answerCode", answerCode);
		
		return super.getSqlSession().selectOne(NAMESPACE + ".getAnswerResult", paramMap);
	}

	@Override
	public List<SysqResult> getAllAnswerResult(int interviewId) {
		return super.getSqlSession().selectList(NAMESPACE + ".getAllAnswerResult", interviewId);
	}

	@Override
	public void batchInsert(List<SysqResult> resultList) {
		super.getSqlSession().insert(NAMESPACE + ".batchInsert", resultList);
	}

}
