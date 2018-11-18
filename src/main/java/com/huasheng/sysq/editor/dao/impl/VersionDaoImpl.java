package com.huasheng.sysq.editor.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.VersionDao;
import com.huasheng.sysq.editor.model.Version;

@Repository
public class VersionDaoImpl extends BaseDao implements VersionDao{
	
	private static String NAMESPACE = "mapper.VersionMapper";

	@Override
	public List<Version> findVersionPage(Map<String, Object> searchParams, int currentPage, int pageSize) {
		if(searchParams == null) {
			searchParams = new HashMap<String,Object>();
		}
		
		searchParams.put("offset", (currentPage - 1) * pageSize);
		searchParams.put("limit", pageSize);
		
		return super.getSqlSession().selectList(NAMESPACE + ".findVersionPage", searchParams);
	}

	@Override
	public int countVersion(Map<String, Object> searchParams) {
		return super.getSqlSession().selectOne(NAMESPACE + ".countVersion", searchParams);
	}

	

}
