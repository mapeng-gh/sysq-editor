package com.huasheng.sysq.editor.dao;

import java.util.List;
import java.util.Map;

import com.huasheng.sysq.editor.model.EditorEditLog;

public interface EditorEditLogDao {

	/**
	 * 插入
	 * @param editorEditLog
	 */
	public void insert(EditorEditLog editorEditLog);
	
	/**
	 * 搜索
	 * @param searchParams
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<EditorEditLog> findPage(Map<String,Object> searchParams , int currentPage , int pageSize);
	
	/**
	 * 统计
	 * @param searchParams
	 * @return
	 */
	public int count(Map<String,Object> searchParams);
}
