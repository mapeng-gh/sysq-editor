package com.huasheng.sysq.editor.dao.impl;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.EditorQuestionDao;
import com.huasheng.sysq.editor.model.EditorQuestion;

@Repository
public class EditorQuestionDaoImpl extends BaseDao implements EditorQuestionDao{
	
	private static String NAMESPACE = "mapper.EditorQuestionMapper";

	@Override
	public void insert(EditorQuestion editorQuestion) {
		super.getSqlSession().insert(NAMESPACE + ".insert", editorQuestion);
	}

}
