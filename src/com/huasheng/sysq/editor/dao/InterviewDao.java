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
	 * @param mobile
	 * @param searchParams
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<Interview> findDoctorInterviewPage(String mobile,Map<String,Object> searchParams,int currentPage,int pageSize);
	
	/**
	 * 统计医生访谈
	 * @param mobile
	 * @param searchParams
	 * @return
	 */
	public int countDoctorInterview(String mobile,Map<String,Object> searchParams);
	
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
	
	/**
	 * 查找编辑访谈列表
	 * @param userId
	 * @param searchParams
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<Interview> findEditorInterviewPage(int userId,Map<String,Object> searchParams,int currentPage,int pageSize);
	
	/**
	 * 统计编辑访谈
	 * @param userId
	 * @param searchParams
	 * @return
	 */
	public int countEditorInterview(int userId,Map<String,Object> searchParams);
}
