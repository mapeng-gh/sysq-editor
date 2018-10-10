package com.huasheng.sysq.editor.dao.impl;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.EditorResultDao;
import com.huasheng.sysq.editor.model.SysqResult;

@Repository
public class EditorResultDaoImpl extends BaseDao implements EditorResultDao{
	
	private static String NAMESPACE = "mapper.EditorResultMapper";

	@Override
	public void insert(SysqResult editorSysqResult) {
		super.getSqlSession().insert(NAMESPACE + ".insert", editorSysqResult);
	}
}
