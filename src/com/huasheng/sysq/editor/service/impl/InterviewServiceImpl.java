package com.huasheng.sysq.editor.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
			
			int currentPage = Integer.parseInt(searchRequest.get("currentPage"));
			int pageSize = Integer.parseInt(searchRequest.get("pageSize"));
			return CallResult.success(new Page<InterviewWrap>(interviewWrapList,currentPage,pageSize,total));
			
		}catch(Exception e) {
			LogUtils.error(UserServiceImpl.class, "findInterviewPage error", e);
			return CallResult.failure("查找医生访谈列表失败");
		}
	}

	@Override
	public CallResult<Page<UnAssignInterviewResponse>> findUnAssignInterviewPage(Map<String, Object> searchRequest,int currentPage,int pageSize) {
		LogUtils.info(this.getClass(), "findUnAssignInterviewPage params : {}", JsonUtils.toJson(searchRequest));
		
		try {
			//搜索
			List<Interview> unAssignInterviewList = interviewDao.findUnAssignInterviewList(null,currentPage,pageSize);
			
			//获取关联数据
			List<UnAssignInterviewResponse> unAssignInterviewResponseList = new ArrayList<UnAssignInterviewResponse>(); 
			if(unAssignInterviewList != null && unAssignInterviewList.size() > 0) {
				for(Interview unAssignInterview : unAssignInterviewList) {
					UnAssignInterviewResponse unAssignInterviewResponse = new UnAssignInterviewResponse();
					unAssignInterviewResponse.setInterview(unAssignInterview);
					unAssignInterviewResponse.setDoctor(doctorDao.selectById(unAssignInterview.getDoctorId()));
					unAssignInterviewResponse.setPatient(patientDao.selectById(unAssignInterview.getPatientId()));
					unAssignInterviewResponseList.add(unAssignInterviewResponse);
				}
			}
			
			//统计
			int total = interviewDao.countUnAssignInterviewList(null);
			
			
			return CallResult.success(new Page<UnAssignInterviewResponse>(unAssignInterviewResponseList,currentPage,pageSize,total));
			
		}catch(Exception e) {
			LogUtils.error(UserServiceImpl.class, "findUnAssignInterviewPage error", e);
			return CallResult.failure("查找未分配访谈列表失败");
		}
	}
}
