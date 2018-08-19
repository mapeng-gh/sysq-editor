package com.huasheng.sysq.editor.dao;

import com.huasheng.sysq.editor.model.Doctor;

public interface DoctorDao {

	/**
	 * 主键查找
	 * @param id
	 * @return
	 */
	public Doctor selectById(int id);
}
