package com.huasheng.sysq.editor.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.EditorEditLogDao;
import com.huasheng.sysq.editor.model.EditorEditLog;

@Repository
public class EditorEditLogDaoImpl extends BaseDao implements EditorEditLogDao{
	
	private static String NAMESPACE = "mapper.EditorEditLogMapper";


	@Override
	public void insert(EditorEditLog editorEditLog) {
		super.getSqlSession().insert(NAMESPACE + ".insert",editorEditLog);
	}

	@Override
	public List<EditorEditLog> findPage(Map<String, Object> searchParams, int currentPage, int pageSize) {
		if(searchParams == null) {
			searchParams = new HashMap<String,Object>();
		}
		searchParams.put("offset", (currentPage - 1) * pageSize);
		searchParams.put("limit", pageSize);
		
		return this.getSqlSession().selectList(NAMESPACE + ".findPage", searchParams);
	}

	@Override
	public int count(Map<String, Object> searchParams) {
		if(searchParams == null) {
			searchParams = new HashMap<String,Object>();
		}
		
		return this.getSqlSession().selectOne(NAMESPACE + ".count", searchParams);
	}

}
