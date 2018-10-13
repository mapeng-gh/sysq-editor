package com.huasheng.sysq.editor.dao;

import java.util.List;

import com.huasheng.sysq.editor.model.Answer;

public interface AnswerDao {

	/**
	 * 根据编码批量查询
	 * @param versionId
	 * @param codeList
	 * @return
	 */
	public List<Answer> batchSelectByCode(int versionId,List<String> codeList);
	
	/**
	 * 根据问题查询
	 * @param versionId
	 * @param questionCode
	 * @return
	 */
	public List<Answer> selectListByQuestion(int versionId,String questionCode);
}
