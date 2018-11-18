package com.huasheng.sysq.editor.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.QuestionDao;
import com.huasheng.sysq.editor.model.Question;

@Repository
public class QuestionDaoImpl extends BaseDao implements QuestionDao{
	
	private static String NAMESPACE = "mapper.QuestionMapper";

	@Override
	public List<Question> batchSelectByCode(int versionId, List<String> codeList) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("versionId", versionId);
		paramMap.put("codeList", codeList);
		
		return super.getSqlSession().selectList(NAMESPACE + ".batchSelectByCode", paramMap);
	}

	@Override
	public List<Question> selectListByQuestionaire(int versionId, String questionaireCode) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("versionId", versionId);
		paramMap.put("questionaireCode", questionaireCode);
		
		return super.getSqlSession().selectList(NAMESPACE + ".selectListByQuestionaire", paramMap);
	}

	@Override
	public Question selectByCode(int versionId,String code) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("versionId", versionId);
		paramMap.put("code", code);
		
		return super.getSqlSession().selectOne(NAMESPACE + ".selectByCode", paramMap);
	}
}
