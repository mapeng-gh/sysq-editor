package com.huasheng.sysq.editor.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.SysqResultDao;

@Repository
public class SysqResultDaoImpl extends BaseDao implements SysqResultDao{
	
	private static String NAMESPACE = "mapper.SysqResultMapper";

	@Override
	public List<String> findQuestionaireListByInterviewId(int interviewId) {
		return super.getSqlSession().selectList(NAMESPACE + ".findQuestionaireListByInterviewId", interviewId);
	}

}
