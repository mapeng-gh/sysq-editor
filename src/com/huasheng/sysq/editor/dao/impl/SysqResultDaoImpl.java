package com.huasheng.sysq.editor.dao.impl;

import java.util.List;

import com.huasheng.sysq.editor.dao.SysqResultDao;

public class SysqResultDaoImpl extends BaseDao implements SysqResultDao{
	
	private static String NAMESPACE = "mapper.SysqResultMapper";

	@Override
	public List<String> findQuestionaireListByInterviewId(int interviewId) {
		return super.getSqlSession().selectList(NAMESPACE + ".findQuestionaireListByInterviewId", interviewId);
	}

}
