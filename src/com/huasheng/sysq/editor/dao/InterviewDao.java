package com.huasheng.sysq.editor.dao;

import java.util.List;
import java.util.Map;

import com.huasheng.sysq.editor.model.Interview;

public interface InterviewDao {

	/**
	 * 搜索
	 * @param searchParams
	 * @return
	 */
	List<Interview> find(Map<String,String> searchParams);
	
	/**
	 * 统计
	 * @param searchParams
	 * @return
	 */
	int count(Map<String,String> searchParams);
}
