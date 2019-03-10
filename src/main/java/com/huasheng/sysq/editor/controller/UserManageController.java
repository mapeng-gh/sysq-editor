package com.huasheng.sysq.editor.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.huasheng.sysq.editor.params.InterviewResponse;
import com.huasheng.sysq.editor.params.UserResponse;
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
	private TaskService taskService;
	
	/**
	 * 用户搜索
	 * @param searchRequest
	 * @return
	 */
	@RequestMapping(value="/userList.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Page<User>> userList(@RequestParam Map<String,String> searchRequest) {
		LogUtils.info(this.getClass(), "userList params : {}",JsonUtils.toJson(searchRequest));
		CallResult<Page<User>> result = userService.findUserPage(searchRequest);
		LogUtils.info(this.getClass(), "userList result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	/**
	 * 用户详情
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/userDetail.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<UserResponse> userDetail(@RequestParam("userId") int userId) {
		LogUtils.info(this.getClass(), "userDetail params : userId = {}",userId);
		
		CallResult<UserResponse> result = userService.viewUser(userId);
		LogUtils.info(this.getClass(), "userDetail result : {}", JsonUtils.toJson(result));
		
		return result;
	}
	
	/**
	 * 用户审核
	 * @param requestJsonStr
	 * @return
	 */
	@RequestMapping(value="/auditUser.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
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
	@RequestMapping(value="/interviewList.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Page<InterviewResponse>> interviewList(@RequestParam Map<String,String> searchParams) {
		LogUtils.info(this.getClass(), "interviewList params : searchParams = {}",JsonUtils.toJson(searchParams));
		
		//参数处理
		int currentPage = 0;
		int pageSize = 0;
		Map<String,String> orderParams = new HashMap<String,String>();
		try {
			currentPage = Integer.parseInt(searchParams.get("currentPage"));
			pageSize = Integer.parseInt(searchParams.get("pageSize"));
			orderParams.put("interviewType", searchParams.get("interviewType"));
			orderParams.put("doctorName", searchParams.get("doctorName"));
			orderParams.put("interviewEndTime", searchParams.get("interviewEndTime"));
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "interviewList error", e);
			return CallResult.failure("获取未分配访谈列表失败");
		}
		
		CallResult<Page<InterviewResponse>> result = taskService.findUnAssignInterviewPage(null,orderParams,currentPage,pageSize);
		LogUtils.info(this.getClass(), "interviewList result : {}", JsonUtils.toJson(result));
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
		
		//参数处理
		int userId = 0;
		String interviewIds = "";
		try {
			JSONObject requestJson = JSON.parseObject(requestJsonStr);
			userId = requestJson.getIntValue("userId");
			interviewIds = requestJson.getString("interviewIds");
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "assignTask error", e);
			return CallResult.failure("分配任务失败");
		}
		
		//分配任务
		CallResult<Boolean> result = taskService.assignTask(userId, interviewIds);
		LogUtils.info(this.getClass(), "assignTask result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	/**
	 * 密码重置
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/resetPwd.do",method=RequestMethod.POST)
	@ResponseBody
	public CallResult<Boolean> resetPwd(@RequestParam(value="userId") int userId) {
		LogUtils.info(this.getClass(), "resetPwd params : userId = {}",userId);
		
		CallResult<Boolean> result = userService.resetPwd(userId, "sysq123");
		LogUtils.info(this.getClass(), "resetPwd result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	/**
	 * 类型修改
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/changeType.do",method=RequestMethod.POST)
	@ResponseBody
	public CallResult<Boolean> changeType(@RequestParam(value="userId") int userId , @RequestParam(value="userType") int userType) {
		LogUtils.info(this.getClass(), "resetPwd params : userId = {} , userType = {}",userId,userType);
		
		CallResult<Boolean> result = userService.changeType(userId, userType);
		LogUtils.info(this.getClass(), "changeType result : {}", JsonUtils.toJson(result));
		return result;
	}
}
