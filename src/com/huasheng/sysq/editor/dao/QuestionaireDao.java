package com.huasheng.sysq.editor.dao;

import java.util.List;

import com.huasheng.sysq.editor.model.Questionaire;

public interface QuestionaireDao {

	/**
	 * 查找问卷列表
	 * @param versionId
	 * @param type
	 * @return
	 */
	public List<Questionaire> findQuestionaireList(int versionId,int type);
	
	/**
	 * 根据编码批量查询问卷
	 * @param versionId
	 * @param codeList
	 * @return
	 */
	public List<Questionaire> batchFindByVersionAndCode(int versionId,List<String> codeList);
	
	/**
	 * 根据编码查询问卷
	 * @param versionId
	 * @param code
	 * @return
	 */
	public Questionaire findByVersionAndCode(int versionId,String code);
}
