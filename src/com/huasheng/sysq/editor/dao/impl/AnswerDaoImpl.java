package com.huasheng.sysq.editor.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.AnswerDao;
import com.huasheng.sysq.editor.model.Answer;

@Repository
public class AnswerDaoImpl extends BaseDao implements AnswerDao{
	
	private static String NAMESPACE = "mapper.AnswerMapper";

	@Override
	public List<Answer> batchFindByVersionAndCode(int versionId, List<String> codeList) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("versionId", versionId);
		paramMap.put("codeList", codeList);
		
		return super.getSqlSession().selectList(NAMESPACE + ".batchFindByVersionAndCode", paramMap);
	}

}
