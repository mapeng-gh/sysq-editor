package com.huasheng.sysq.editor.dao;

import java.util.List;

import com.huasheng.sysq.editor.model.Question;

public interface QuestionDao {

	/**
	 * 根据编码批量查询问题
	 * @param versionId
	 * @param codeList
	 * @return
	 */
	public List<Question> batchFindByVersionAndCode(int versionId,List<String> codeList);
}
