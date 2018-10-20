package com.huasheng.sysq.editor.dao;

import java.util.List;
import java.util.Map;

import com.huasheng.sysq.editor.model.Interview;

public interface InterviewDao {
	
	/**
	 * 主键查找
	 * @param id
	 * @return
	 */
	public Interview selectById(int id);

	/**
	 * 查找用户访谈
	 * @param mobile
	 * @param searchParams
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<Interview> findUserInterviewPage(String loginName,Map<String,Object> searchParams,int currentPage,int pageSize);
	
	/**
	 * 统计用户访谈
	 * @param mobile
	 * @param searchParams
	 * @return
	 */
	public int countUserInterview(String loginName,Map<String,Object> searchParams);
	
	/**
	 * 查找未分配访谈列表
	 * @param searchParams
	 * @return
	 */
	public List<Interview> findUnAssignInterviewPage(Map<String,Object> searchParams,int currentPage,int pageSize);
	
	/**
	 * 统计未分配访谈列表
	 * @param searchParams
	 * @return
	 */
	public int countUnAssignInterview(Map<String,Object> searchParams);
}
