package com.huasheng.sysq.editor.dao;

import java.util.List;

import com.huasheng.sysq.editor.model.Answer;

public interface AnswerDao {

	/**
	 * 根据编码批量查询答案
	 * @param versionId
	 * @param codeList
	 * @return
	 */
	public List<Answer> batchFindByVersionAndCode(int versionId,List<String> codeList);
}
