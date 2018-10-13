package com.huasheng.sysq.editor.dao;

import java.util.List;

import com.huasheng.sysq.editor.model.Question;

public interface QuestionDao {
	
	/**
	 * 根据编码查询
	 * @param versionId
	 * @param questionaireCode
	 * @param code
	 * @return
	 */
	public Question selectByCode(int versionId,String code);

	/**
	 * 根据编码批量查询
	 * @param versionId
	 * @param codeList
	 * @return
	 */
	public List<Question> batchSelectByCode(int versionId,List<String> codeList);
	
	/**
	 * 根据问卷查询
	 * @param versionId
	 * @param questionaireCode
	 * @return
	 */
	public List<Question> selectListByQuestionaire(int versionId,String questionaireCode);
}
