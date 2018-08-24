package com.huasheng.sysq.editor.dao;

import java.util.List;
import java.util.Map;

import com.huasheng.sysq.editor.model.Interview;

public interface InterviewDao {

	/**
	 * 搜索医生访谈列表
	 * @param searchRequest
	 * @return
	 */
	List<Interview> findDoctorInterviewList(Map<String,String> searchRequest);
	
	/**
	 * 统计医生访谈列表
	 * @param searchRequest
	 * @return
	 */
	int countDoctorInterviewList(Map<String,String> searchRequest);
}
