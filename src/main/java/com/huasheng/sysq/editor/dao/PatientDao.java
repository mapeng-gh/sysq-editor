package com.huasheng.sysq.editor.dao;

import com.huasheng.sysq.editor.model.Patient;

public interface PatientDao {

	/**
	 * 主键查找
	 * @param id
	 * @return
	 */
	public Patient selectById(int id);
}
