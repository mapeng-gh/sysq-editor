package com.huasheng.sysq.editor.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.huasheng.sysq.editor.dao.InterviewDao;
import com.huasheng.sysq.editor.model.Interview;

@Repository
public class InterviewDaoImpl extends BaseDao implements InterviewDao{
	
	private static String NAMESPACE = "mapper.InterviewMapper";
	
	/**
	 * 参数处理：搜索
	 * @param searchParams
	 * @return
	 */
	private Map<String,Object> handleFindDoctorInterviewParams(Map<String,String> searchParams){
		if(searchParams != null) {
			String doctorMobile = searchParams.get("doctorMobile");
			String patientName = searchParams.get("patientName");
			String interviewType = searchParams.get("interviewType");
			String currentPage = searchParams.get("currentPage");
			String pageSize = searchParams.get("pageSize");
			
			Map<String,Object> handledParams = new HashMap<String,Object>();
			if(!StringUtils.isBlank(doctorMobile)) {
				handledParams.put("doctorMobile", doctorMobile);
			}
			if(!StringUtils.isBlank(patientName)) {
				handledParams.put("patientName",patientName);
			}
			if(!StringUtils.isBlank(interviewType)) {
				handledParams.put("interviewType", Integer.parseInt(interviewType));
			}
        		if(!StringUtils.isBlank(currentPage) && !StringUtils.isBlank(pageSize)) {
        			handledParams.put("offset",(Integer.parseInt(currentPage) -1) * Integer.parseInt(pageSize));
        			handledParams.put("limit", Integer.parseInt(pageSize));
        		}
        		
        		return handledParams;
        	}else {
        		return null;
        	}
	}

	@Override
	public List<Interview> findDoctorInterviewList(Map<String, String> searchParams) {
		Map<String,Object> handledParams = this.handleFindDoctorInterviewParams(searchParams);
        	return super.getSqlSession().selectList(NAMESPACE + ".findDoctorInterviewList",handledParams);
	}

	@Override
	public int countDoctorInterviewList(Map<String, String> searchParams) {
		return super.getSqlSession().selectOne(NAMESPACE + ".countDoctorInterviewList",searchParams);
	}


}
