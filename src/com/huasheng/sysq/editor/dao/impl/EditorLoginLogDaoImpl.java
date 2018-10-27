package com.huasheng.sysq.editor.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.EditorLoginLogDao;
import com.huasheng.sysq.editor.model.EditorLoginLog;

@Repository
public class EditorLoginLogDaoImpl extends BaseDao implements EditorLoginLogDao{
	
	private static String NAMESPACE = "mapper.EditorLoginLogMapper";

	@Override
	public void insert(EditorLoginLog editorLoginLog) {
		super.getSqlSession().insert(NAMESPACE + ".insert", editorLoginLog);
	}

	@Override
	public List<EditorLoginLog> findPage(Map<String, Object> searchParams, int currentPage, int pageSize) {
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
