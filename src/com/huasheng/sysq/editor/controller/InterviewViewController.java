package com.huasheng.sysq.editor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huasheng.sysq.editor.model.Questionaire;
import com.huasheng.sysq.editor.params.InterviewResponse;
import com.huasheng.sysq.editor.service.InterviewViewService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;
import com.huasheng.sysq.editor.util.SessionCache;

@Controller
@RequestMapping(value="/interviewView")
public class InterviewViewController {
	
	@Autowired
	private InterviewViewService interviewViewService;
	
	@RequestMapping(value="/interviewList.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Page<InterviewResponse>> interviewList(@RequestHeader("Authorization") String token,@RequestParam Map<String,String> searchParams) {
		LogUtils.info(this.getClass(), "interviewList params : {}",JsonUtils.toJson(searchParams));
		
		//参数处理
		String loginName = "";
		int currentPage = 0;
		int pageSize = 0;
		Map<String,Object> handledParams = new HashMap<String,Object>();
		try {
			loginName = SessionCache.get(token).getLoginName();
			currentPage = Integer.parseInt(searchParams.get("currentPage"));
			pageSize = Integer.parseInt(searchParams.get("pageSize"));
			handledParams.put("name",searchParams.get("name"));
			if(!StringUtils.isBlank(searchParams.get("type"))) {
				handledParams.put("type", Integer.valueOf(searchParams.get("type")));
			}
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "interviewList error", e);
			return CallResult.failure("获取访谈失败");
		}
		
		//查询访谈
		CallResult<Page<InterviewResponse>> result = interviewViewService.findDoctorInterviewPage(loginName, handledParams, currentPage, pageSize);
		LogUtils.info(this.getClass(), "interviewList result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	@RequestMapping(value="/questionaireList.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<List<Questionaire>> questionaireList(@RequestParam(value="interviewId") int interviewId) {
		LogUtils.info(this.getClass(), "questionaireList params : interviewId = {}",interviewId);
		
		//查询问卷
		CallResult<List<Questionaire>> result = interviewViewService.findQuestionaireListByInterviewId(interviewId);
		LogUtils.info(this.getClass(), "questionaireList result : {}", JsonUtils.toJson(result));
		
		return result;
	}
}
