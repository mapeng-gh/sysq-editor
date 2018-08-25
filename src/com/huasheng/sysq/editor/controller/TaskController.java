package com.huasheng.sysq.editor.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

@Controller
@RequestMapping(value="/taskManage")
public class TaskController {
	
	@Autowired
	private TaskService taskService;

	@RequestMapping(value="/list.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Page<TaskResponse>> list(@RequestParam Map<String,String> searchParams) {
		LogUtils.info(this.getClass(), "list params : {}",JsonUtils.toJson(searchParams));
		CallResult<Page<TaskResponse>> result = taskService.findTaskPage(searchParams);
		LogUtils.info(this.getClass(), "list result : {}", JsonUtils.toJson(result));
		return result;
	}
}
