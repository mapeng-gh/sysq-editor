package com.huasheng.sysq.editor.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.SysqResultDao;
import com.huasheng.sysq.editor.model.SysqResult;

@Repository
public class SysqResultDaoImpl extends BaseDao implements SysqResultDao{
	
	private static String NAMESPACE = "mapper.SysqResultMapper";

	@Override
	public List<String> findQuestionaireList(int interviewId) {
		return super.getSqlSession().selectList(NAMESPACE + ".findQuestionaireList", interviewId);
	}

	@Override
	public List<String> findQuestionList(int interviewId, String questionaireCode) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("interviewId", interviewId);
		paramMap.put("questionaireCode", questionaireCode);
		
		return super.getSqlSession().selectList(NAMESPACE + ".findQuestionList", paramMap);
	}

	@Override
	public List<String> findAnswerList(int interviewId, String questionaireCode, String questionCode) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("interviewId", interviewId);
		paramMap.put("questionaireCode", questionaireCode);
		paramMap.put("questionCode", questionCode);
		
		return super.getSqlSession().selectList(NAMESPACE + ".findAnswerList", paramMap);
	}

	@Override
	public SysqResult findAnswerResult(int interviewId, String questionaireCode, String questionCode,String answerCode) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("interviewId", interviewId);
		paramMap.put("questionaireCode", questionaireCode);
		paramMap.put("questionCode", questionCode);
		paramMap.put("answerCode", answerCode);
		
		return super.getSqlSession().selectOne(NAMESPACE + ".findAnswerResult", paramMap);
	}

}
