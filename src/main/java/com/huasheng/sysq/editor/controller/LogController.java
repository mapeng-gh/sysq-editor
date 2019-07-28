package com.huasheng.sysq.editor.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huasheng.sysq.editor.model.EditorEditLog;
import com.huasheng.sysq.editor.params.EditorLoginLogResponse;
import com.huasheng.sysq.editor.service.LogService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;

@Controller
@RequestMapping(value="/log")
public class LogController {
	
	@Autowired
	private LogService logService;

	/**
	 * 查询登录日志
	 * @param searchParams
	 * @return
	 */
	@RequestMapping(value="/loginLogList.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Page<EditorLoginLogResponse>> loginLogList(@RequestParam Map<String,String> searchParams) {
		LogUtils.info(this.getClass(), "loginLogList params : searchParams = {}",JsonUtils.toJson(searchParams));
		
		//参数处理
		Map<String,Object> handledParams = new HashMap<String,Object>();
		int currentPage = 0;
		int pageSize = 0;
		try {
			handledParams.put("startTime",searchParams.get("startTime"));
			handledParams.put("endTime",searchParams.get("endTime"));
			handledParams.put("loginName", searchParams.get("loginName"));
			currentPage = Integer.parseInt(searchParams.get("currentPage"));
			pageSize = Integer.parseInt(searchParams.get("pageSize"));
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "loginLogList error", e);
			return CallResult.failure("获取登录日志失败");
		}
		
		CallResult<Page<EditorLoginLogResponse>> result = logService.findLoginLogPage(handledParams, currentPage, pageSize);
		LogUtils.info(this.getClass(), "loginLogList result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	/**
	 * 查询编辑日志
	 * @param searchParams
	 * @return
	 */
	@RequestMapping(value="/editLogList.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Page<EditorEditLog>> editLogList(@RequestParam Map<String,String> searchParams) {
		LogUtils.info(this.getClass(), "editLogList params : searchParams = {}",JsonUtils.toJson(searchParams));
		
		//参数处理
		Map<String,Object> handledParams = new HashMap<String,Object>();
		int currentPage = 0;
		int pageSize = 0;
		try {
			if(!StringUtils.isBlank(searchParams.get("interviewId"))) {
				handledParams.put("interviewId", Integer.parseInt(searchParams.get("interviewId")));
			}
			if(!StringUtils.isBlank(searchParams.get("questionaireCode"))) {
				handledParams.put("questionaireCode",searchParams.get("questionaireCode"));
			}
			if(!StringUtils.isBlank(searchParams.get("questionCode"))) {
				handledParams.put("questionCode", searchParams.get("questionCode"));
			}
			if(!StringUtils.isBlank(searchParams.get("loginName"))) {
				handledParams.put("loginName", searchParams.get("loginName"));
			}
			if(!StringUtils.isBlank(searchParams.get("startTime"))) {
				handledParams.put("startTime", searchParams.get("startTime"));
			}
			if(!StringUtils.isBlank(searchParams.get("endTime"))) {
				handledParams.put("endTime", searchParams.get("endTime"));
			}
			
			currentPage = Integer.parseInt(searchParams.get("currentPage"));
			pageSize = Integer.parseInt(searchParams.get("pageSize"));
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "editLogList error", e);
			return CallResult.failure("查询编辑日志失败");
		}
		
		CallResult<Page<EditorEditLog>> result = logService.findEditLogPage(handledParams, currentPage, pageSize);
		LogUtils.info(this.getClass(), "editLogList result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	
}
