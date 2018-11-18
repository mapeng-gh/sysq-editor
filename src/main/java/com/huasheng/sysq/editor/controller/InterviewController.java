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
import com.huasheng.sysq.editor.params.QuestionResponse;
import com.huasheng.sysq.editor.service.InterviewService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;
import com.huasheng.sysq.editor.util.SessionCache;

@Controller
@RequestMapping(value="/interview")
public class InterviewController {
	
	@Autowired
	private InterviewService interviewService;
	
	/**
	 * 访谈列表
	 * @param token
	 * @param searchParams
	 * @return
	 */
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
		CallResult<Page<InterviewResponse>> result = interviewService.findUserInterviewPage(loginName, handledParams, currentPage, pageSize);
		LogUtils.info(this.getClass(), "interviewList result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	/**
	 * 问卷列表
	 * @param interviewId
	 * @return
	 */
	@RequestMapping(value="/questionaireList.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<? extends List<Questionaire>> questionaireList(@RequestParam(value="interviewId") int interviewId) {
		LogUtils.info(this.getClass(), "questionaireList params : interviewId = {}",interviewId);
		
		//查询问卷
		CallResult<? extends List<Questionaire>> result = interviewService.findQuestionaireListByInterviewId(interviewId);
		LogUtils.info(this.getClass(), "questionaireList result : {}", JsonUtils.toJson(result));
		
		return result;
	}
	
	/**
	 * 问题列表
	 * @param interviewId
	 * @return
	 */
	@RequestMapping(value="/questionList.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<? extends List<QuestionResponse>> questionList(@RequestParam(value="interviewId") int interviewId , @RequestParam(value="questionaireCode") String questionaireCode) {
		LogUtils.info(this.getClass(), "questionList params : interviewId = {} , questionaireCode",interviewId,questionaireCode);
		
		//查询问题
		CallResult<? extends List<QuestionResponse>> result = interviewService.findQuestionList(interviewId, questionaireCode);
		LogUtils.info(this.getClass(), "questionList result : {}", JsonUtils.toJson(result));
		
		return result;
	}
}
