package com.huasheng.sysq.editor.dao;

import java.util.List;

import com.huasheng.sysq.editor.model.Doctor;

public interface DoctorDao {

	/**
	 * 主键查找
	 * @param id
	 * @return
	 */
	public Doctor selectById(int id);
	
	/**
	 * 根据联系电话查找
	 * @param mobile
	 * @return
	 */
	public List<Doctor> findByMobile(String mobile);
}
