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
	 * 查找医生访谈列表
	 * @param searchRequest
	 * @return
	 */
	public List<Interview> findDoctorInterviewList(Map<String,String> searchParams);
	
	/**
	 * 统计医生访谈列表
	 * @param searchRequest
	 * @return
	 */
	public int countDoctorInterviewList(Map<String,String> searchParams);
	
	/**
	 * 查找未分配访谈列表
	 * @param searchParams
	 * @return
	 */
	public List<Interview> findUnAssignInterviewList(Map<String,Object> searchParams,int currentPage,int pageSize);
	
	/**
	 * 统计未分配访谈列表
	 * @param searchParams
	 * @return
	 */
	public int countUnAssignInterviewList(Map<String,Object> searchParams);
}
