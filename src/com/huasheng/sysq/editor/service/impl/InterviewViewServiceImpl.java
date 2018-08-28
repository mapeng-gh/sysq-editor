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
import com.huasheng.sysq.editor.model.Questionaire;
import com.huasheng.sysq.editor.params.InterviewResponse;
import com.huasheng.sysq.editor.service.InterviewViewService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;

@Service
public class InterviewViewServiceImpl implements InterviewViewService{
	
	@Autowired
	private InterviewDao interviewDao;
	
	@Autowired
	private DoctorDao doctorDao;
	
	@Autowired
	private PatientDao patientDao;

	@Override
	public CallResult<Page<InterviewResponse>> findDoctorInterviewPage(String mobile,Map<String,Object> searchParams,int currentPage,int pageSize) {
		LogUtils.info(this.getClass(), "findDoctorInterviewPage params : mobile = {},searchParams = {},currentPage = {},pageSize = {}",mobile,JsonUtils.toJson(searchParams),currentPage,pageSize);
		
		try {
			//查询访谈
			List<Interview> interviewList = interviewDao.findDoctorInterviewPage(mobile, searchParams, currentPage, pageSize);
			
			//关联数据
			List<InterviewResponse> interviewResponseList = new ArrayList<InterviewResponse>(); 
			if(interviewList != null && interviewList.size() > 0) {
				for(Interview interview : interviewList) {
					InterviewResponse interviewResponse = new InterviewResponse();
					interviewResponse.setInterview(interview);
					interviewResponse.setDoctor(doctorDao.selectById(interview.getDoctorId()));
					interviewResponse.setPatient(patientDao.selectById(interview.getPatientId()));
					interviewResponseList.add(interviewResponse);
				}
			}
			
			//统计
			int total = interviewDao.countDoctorInterview(mobile, searchParams);
			
			return CallResult.success(new Page<InterviewResponse>(interviewResponseList,currentPage,pageSize,total));
			
		}catch(Exception e) {
			LogUtils.error(UserServiceImpl.class, "findDoctorInterviewPage error", e);
			return CallResult.failure("查找访谈失败");
		}
	}

	@Override
	public CallResult<List<Questionaire>> findQuestionaireListByInterviewId(int interviewId) {
		return null;
	}

}
