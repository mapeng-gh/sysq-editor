package com.huasheng.sysq.editor.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.VersionDao;
import com.huasheng.sysq.editor.model.Version;

@Repository
public class VersionDaoImpl extends BaseDao implements VersionDao{
	
	private static String NAMESPACE = "mapper.VersionMapper";

	@Override
	public List<Version> findAll() {
		return super.getSqlSession().selectList(NAMESPACE + ".findAll");
	}

}
