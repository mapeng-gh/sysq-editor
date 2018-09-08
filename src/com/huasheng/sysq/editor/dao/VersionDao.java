package com.huasheng.sysq.editor.dao;

import java.util.List;
import java.util.Map;

import com.huasheng.sysq.editor.model.Version;

public interface VersionDao {

	/**
	 * 查找版本
	 * @param searchParams
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<Version> findVersionPage(Map<String,Object> searchParams,int currentPage,int pageSize);
	
	/**
	 * 统计版本
	 * @param searchParams
	 * @return
	 */
	public int countVersion(Map<String,Object> searchParams);
}
