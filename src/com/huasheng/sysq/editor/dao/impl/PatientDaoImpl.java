package com.huasheng.sysq.editor.dao.impl;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.PatientDao;
import com.huasheng.sysq.editor.model.Patient;

@Repository
public class PatientDaoImpl extends BaseDao implements PatientDao{
	
	private static String NAMESPACE = "mapper.PatientMapper";

	@Override
	public Patient selectById(int id) {
		return super.getSqlSession().selectOne(NAMESPACE + ".selectById",id);
	}

}
