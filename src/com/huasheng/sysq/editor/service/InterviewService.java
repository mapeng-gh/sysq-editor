package com.huasheng.sysq.editor.service;

import java.util.Map;

import com.huasheng.sysq.editor.params.InterviewWrap;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.Page;

public interface InterviewService {

	/**
	 * 查找医生访谈列表
	 * @param searchParams
	 * @return
	 */
	public CallResult<Page<InterviewWrap>> findDoctorInterviewPage(Map<String,String> searchParams);
}
