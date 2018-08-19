package com.huasheng.sysq.editor.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huasheng.sysq.editor.model.User;
import com.huasheng.sysq.editor.params.InterviewWrap;
import com.huasheng.sysq.editor.service.InterviewService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;
import com.huasheng.sysq.editor.util.SessionCache;

@Controller
@RequestMapping(value="/browse")
public class BrowseController {
	
	@Autowired
	private InterviewService interviewService;

	@RequestMapping(value="/doctorInterviewList.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Page<InterviewWrap>> doctorInterviewList(@RequestHeader("Authorization") String token,@RequestParam Map<String,String> searchParams) {
		LogUtils.info(this.getClass(), "doctorInterviewList params : {}",JsonUtils.toJson(searchParams));
		
		//绑定医生账号
		User loginUser = SessionCache.get(token);
		if(searchParams == null) {
			searchParams = new HashMap<String,String>();
		}
		searchParams.put("doctorMobile", loginUser.getLoginName());
		
		CallResult<Page<InterviewWrap>> result = interviewService.findDoctorInterviewPage(searchParams);
		LogUtils.info(this.getClass(), "doctorInterviewList result : {}", JsonUtils.toJson(result));
		return result;
	}
}
