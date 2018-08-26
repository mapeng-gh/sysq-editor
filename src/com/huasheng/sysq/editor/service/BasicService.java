package com.huasheng.sysq.editor.service;

import java.util.List;

import com.huasheng.sysq.editor.model.Questionaire;
import com.huasheng.sysq.editor.util.CallResult;

public interface BasicService {

	/**
	 * 查找最新问卷列表
	 * @param type
	 * @return
	 */
	public CallResult<List<Questionaire>> findCurrentQuestionaireList(int type);
}
