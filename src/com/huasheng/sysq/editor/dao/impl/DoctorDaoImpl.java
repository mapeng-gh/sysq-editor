package com.huasheng.sysq.editor.dao.impl;

import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.DoctorDao;
import com.huasheng.sysq.editor.model.Doctor;

@Repository
public class DoctorDaoImpl extends BaseDao implements DoctorDao{
	
	private static String NAMESPACE = "mapper.DoctorMapper";

	@Override
	public Doctor selectById(int id) {
		return super.getSqlSession().selectOne(NAMESPACE + ".selectById",id);
	}

}
