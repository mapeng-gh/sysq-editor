package com.huasheng.sysq.editor.dao;

import java.util.List;

public interface SysqResultDao {

	/**
	 * 查询访谈问卷列表
	 * @param interviewId
	 * @return
	 */
	public List<String> findQuestionaireListByInterviewId(int interviewId);
}
