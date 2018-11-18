package com.huasheng.sysq.editor.dao;

import java.util.List;

import com.huasheng.sysq.editor.model.Questionaire;

public interface QuestionaireDao {

	/**
	 * 根据类型查询
	 * @param versionId
	 * @param type
	 * @return
	 */
	public List<Questionaire> selectListByType(int versionId,int type);
	
	/**
	 * 根据编码批量查询
	 * @param versionId
	 * @param codeList
	 * @return
	 */
	public List<Questionaire> batchSelectByCode(int versionId,List<String> codeList);
	
	/**
	 * 根据编码查询
	 * @param versionId
	 * @param code
	 * @return
	 */
	public Questionaire selectByCode(int versionId,String code);
}
