package com.huasheng.sysq.editor.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.QuestionaireDao;
import com.huasheng.sysq.editor.model.Questionaire;

@Repository
public class QuestionaireDaoImpl extends BaseDao implements QuestionaireDao{
	
	private static String NAMESPACE = "mapper.QuestionaireMapper";

	@Override
	public List<Questionaire> selectListByType(int versionId, int type) {
		Map<String,Object> searchParams = new HashMap<String,Object>();
		searchParams.put("versionId", versionId);
		searchParams.put("type", type);
		return super.getSqlSession().selectList(NAMESPACE + ".selectListByType", searchParams);
	}

	@Override
	public List<Questionaire> batchSelectByCode(int versionId, List<String> codeList) {
		Map<String,Object> sqlParams = new HashMap<String,Object>();
		sqlParams.put("versionId", versionId);
		sqlParams.put("codeList", codeList);
		return super.getSqlSession().selectList(NAMESPACE + ".batchSelectByCode", sqlParams);
	}

	@Override
	public Questionaire selectByCode(int versionId, String code) {
		Map<String,Object> sqlParams = new HashMap<String,Object>();
		sqlParams.put("versionId", versionId);
		sqlParams.put("code", code);
		
		return super.getSqlSession().selectOne(NAMESPACE + ".selectByCode", sqlParams);
	}

}
