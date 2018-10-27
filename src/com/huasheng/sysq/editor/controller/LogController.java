package com.huasheng.sysq.editor.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
