package com.huasheng.sysq.editor.dao;

import java.util.List;
import java.util.Map;

import com.huasheng.sysq.editor.model.EditorLoginLog;

public interface EditorLoginLogDao {

	/**
	 * 插入
	 * @param editorLoginLog
	 */
	public void insert(EditorLoginLog editorLoginLog);
	
	/**
	 * 搜索
	 * @param searchParams
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<EditorLoginLog> findPage(Map<String,Object> searchParams , int currentPage , int pageSize);
	
	/**
	 * 统计
	 * @param searchParams
	 * @return
	 */
	public int count(Map<String,Object> searchParams);
}
