package com.huasheng.sysq.editor.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huasheng.sysq.editor.dao.DoctorDao;
import com.huasheng.sysq.editor.dao.InterviewDao;
import com.huasheng.sysq.editor.dao.PatientDao;
import com.huasheng.sysq.editor.model.Interview;
import com.huasheng.sysq.editor.params.InterviewWrap;
import com.huasheng.sysq.editor.params.UnAssignInterviewResponse;
import com.huasheng.sysq.editor.service.InterviewService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;

@Service
public class InterviewServiceImpl implements InterviewService{
	
	@Autowired
	private InterviewDao interviewDao;
	
	@Autowired
	private DoctorDao doctorDao;
	
	@Autowired
	private PatientDao patientDao;

	@Override
	public CallResult<Page<InterviewWrap>> findDoctorInterviewPage(Map<String, String> searchRequest) {
		LogUtils.info(this.getClass(), "findDoctorInterviewPage params : {}", JsonUtils.toJson(searchRequest));
		
		try {
			int currentPage = Integer.parseInt(searchRequest.get("currentPage"));
			int pageSize = Integer.parseInt(searchRequest.get("pageSize"));
			
			//搜索
			List<Interview> interviewList = interviewDao.findDoctorInterviewList(searchRequest);
			
			//获取关联数据
			List<InterviewWrap> interviewWrapList = new ArrayList<InterviewWrap>(); 
			if(interviewList != null && interviewList.size() > 0) {
				for(Interview interview : interviewList) {
					InterviewWrap interviewWrap = new InterviewWrap();
					interviewWrap.setInterview(interview);
					interviewWrap.setDoctor(doctorDao.selectById(interview.getDoctorId()));
					interviewWrap.setPatient(patientDao.selectById(interview.getPatientId()));
					interviewWrapList.add(interviewWrap);
				}
			}
			
			//统计
			int total = interviewDao.countDoctorInterviewList(searchRequest);
			
			return CallResult.success(new Page<InterviewWrap>(interviewWrapList,currentPage,pageSize,total));
			
		}catch(Exception e) {
			LogUtils.error(UserServiceImpl.class, "findInterviewPage error", e);
			return CallResult.failure("查找医生访谈列表失败");
		}
	}

	@Override
	public CallResult<Page<UnAssignInterviewResponse>> findUnAssignInterviewPage(Map<String, String> searchRequest) {
		return null;
	}
	
	

}
