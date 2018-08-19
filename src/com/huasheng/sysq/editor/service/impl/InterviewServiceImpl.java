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
import com.huasheng.sysq.editor.service.InterviewService;
import com.huasheng.sysq.editor.util.CallResult;
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
	public CallResult<Page<InterviewWrap>> findDoctorInterviewPage(Map<String, String> searchParams) {
		try {
			//参数校验
			String doctorMobile = searchParams.get("doctorMobile");
			if(StringUtils.isBlank(doctorMobile)) {
				return CallResult.failure("医生账号不能为空");
			}
			
			String interviewType = searchParams.get("interviewType");
			if(!StringUtils.isBlank(interviewType)) {
				if(!StringUtils.isNumeric(interviewType)) {
					return CallResult.failure("访谈类型参数不正确");
				}
			}
			String currentPage = searchParams.get("currentPage");
			if(StringUtils.isBlank(currentPage)) {
				return CallResult.failure("分页参数不能为空");
			}else {
				if(!StringUtils.isNumeric(currentPage) || Integer.parseInt(currentPage) <= 0){
					return CallResult.failure("分页参数不正确");
				}
			}
			String pageSize = searchParams.get("pageSize");
			if(StringUtils.isBlank(pageSize)) {
				return CallResult.failure("分页参数不能为空");
			}else {
				if(!StringUtils.isNumeric(pageSize) || Integer.parseInt(pageSize) <= 0) {
					return CallResult.failure("分页参数不正确");
				}
			}
			
			//搜索
			List<Interview> interviewList = interviewDao.find(searchParams);
			
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
			int total = interviewDao.count(searchParams);
			
			return CallResult.success(new Page<InterviewWrap>(interviewWrapList,Integer.parseInt(currentPage),Integer.parseInt(pageSize),total));
			
		}catch(Exception e) {
			LogUtils.error(UserServiceImpl.class, "findInterviewPage error", e);
			return CallResult.failure("查找医生访谈列表失败");
		}
	}

}
