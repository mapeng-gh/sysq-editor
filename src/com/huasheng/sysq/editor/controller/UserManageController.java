package com.huasheng.sysq.editor.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huasheng.sysq.editor.model.User;
import com.huasheng.sysq.editor.params.UnAssignInterviewResponse;
import com.huasheng.sysq.editor.service.InterviewService;
import com.huasheng.sysq.editor.service.TaskService;
import com.huasheng.sysq.editor.service.UserService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;

@Controller
@RequestMapping(value="/userManage")
public class UserManageController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InterviewService interviewService;
	
	@Autowired
	private TaskService taskService;
	
	/**
	 * 用户搜索
	 * @param searchRequest
	 * @return
	 */
	@RequestMapping(value="/list.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Page<User>> list(@RequestParam Map<String,String> searchRequest) {
		LogUtils.info(this.getClass(), "list params : {}",JsonUtils.toJson(searchRequest));
		CallResult<Page<User>> result = userService.findUserPage(searchRequest);
		LogUtils.info(this.getClass(), "list result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	/**
	 * 用户详情
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/detail.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<User> detail(@RequestParam("userId") String userId) {
		LogUtils.info(this.getClass(), "detail params : userId = {}",userId);
		
		//参数校验
		if(StringUtils.isBlank(userId)) {
			return CallResult.failure("参数不能为空");
		}
		if(!StringUtils.isNumeric(userId)) {
			return CallResult.failure("参数格式不正确");
		}
		
		CallResult<User> result = userService.viewUser(Integer.valueOf(userId));
		LogUtils.info(this.getClass(), "detail result : {}", JsonUtils.toJson(result));
		
		return result;
	}
	
	/**
	 * 用户审核
	 * @param requestJsonStr
	 * @return
	 */
	@RequestMapping(value="/audit.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Boolean> auditUser(@RequestBody String requestJsonStr) {
		LogUtils.info(this.getClass(), "auditUser params : {}", requestJsonStr);
		
		//参数处理
		int userId,auditStatus;
		String remark;
		try {
			JSONObject requestJson = JSON.parseObject(requestJsonStr);
			userId = requestJson.getIntValue("userId");
			auditStatus = requestJson.getIntValue("auditStatus");
			remark = requestJson.getString("remark");
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "auditUser error", e);
			return CallResult.failure("参数格式不正确");
		}
		
		
		CallResult<Boolean> result = userService.auditUser(userId,auditStatus,remark);
		LogUtils.info(this.getClass(), "auditUser result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	/**
	 * 获取未分配访谈列表
	 * @param searchRequest
	 * @return
	 */
	@RequestMapping(value="/taskList.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Page<UnAssignInterviewResponse>> taskList(@RequestParam(value="currentPage") int currentPage,@RequestParam(value="pageSize") int pageSize) {
		LogUtils.info(this.getClass(), "task params : currentPage = {},pageSize = {}",currentPage,pageSize);
		CallResult<Page<UnAssignInterviewResponse>> result = interviewService.findUnAssignInterviewPage(null,currentPage,pageSize);
		LogUtils.info(this.getClass(), "task result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	/**
	 * 任务分配
	 * @param searchRequest
	 * @return
	 */
	@RequestMapping(value="/assignTask.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Boolean> assignTask(@RequestBody String requestJsonStr) {
		LogUtils.info(this.getClass(), "assignTask params : {}",requestJsonStr);
		JSONObject requestJson = JSON.parseObject(requestJsonStr);
		int userId = requestJson.getIntValue("userId");
		String taskIds = requestJson.getString("taskIds");
		CallResult<Boolean> result = taskService.assignTask(userId, taskIds);
		LogUtils.info(this.getClass(), "assignTask result : {}", JsonUtils.toJson(result));
		return result;
	}
	
}