package com.huasheng.sysq.editor.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huasheng.sysq.editor.params.TaskResponse;
import com.huasheng.sysq.editor.service.TaskService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;
import com.huasheng.sysq.editor.util.SessionCache;

@Controller
@RequestMapping(value="/myTask")
public class MyTaskController {

	@Autowired
	private TaskService taskService;

	@RequestMapping(value="/taskList.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Page<TaskResponse>> taskList(@RequestHeader(value="Authorization") String token,@RequestParam Map<String,String> searchParams) {
		LogUtils.info(this.getClass(), "taskList params : token = {},searchParams = {}",token,JsonUtils.toJson(searchParams));
		
		//参数处理
		int userId = 0;
		int currentPage = 0;
		int pageSize = 0;
		Map<String,Object> handledParams = new HashMap<String,Object>();
		try {
			userId = SessionCache.get(token).getId();
			handledParams.put("patientName",searchParams.get("patientName"));
			String taskStatus = searchParams.get("taskStatus");
			if(!StringUtils.isBlank(taskStatus)) {
				handledParams.put("taskStatus", Integer.parseInt(taskStatus));
			}
			currentPage = Integer.parseInt(searchParams.get("currentPage"));
			pageSize = Integer.parseInt(searchParams.get("pageSize"));
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "taskList error", e);
			return CallResult.failure("获取我的任务失败");
		}
		
		CallResult<Page<TaskResponse>> result = taskService.findUserTaskPage(userId, handledParams, currentPage, pageSize);
		LogUtils.info(this.getClass(), "taskList result : {}", JsonUtils.toJson(result));
		return result;
	}
}
