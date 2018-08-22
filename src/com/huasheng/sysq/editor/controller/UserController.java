package com.huasheng.sysq.editor.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huasheng.sysq.editor.model.User;
import com.huasheng.sysq.editor.params.UserCreateRequest;
import com.huasheng.sysq.editor.params.UserLoginResponse;
import com.huasheng.sysq.editor.service.UserService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.CommonUtil;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;
import com.huasheng.sysq.editor.util.SessionCache;

@Controller
@RequestMapping(value="/userManage")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/list.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Page<User>> list(@RequestParam Map<String,String> searchRequest) {
		LogUtils.info(this.getClass(), "list params : {}",JsonUtils.toJson(searchRequest));
		CallResult<Page<User>> result = userService.findUserPage(searchRequest);
		LogUtils.info(this.getClass(), "list result : {}", JsonUtils.toJson(result));
		return result;
	}
	
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
	
	@RequestMapping(value="/audit.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Boolean> auditUser(@RequestBody String requestJsonStr) {
		LogUtils.info(this.getClass(), "auditUser params : {}", requestJsonStr);
		
		//参数校验
		String userId,auditStatus,remark;
		try {
			Map map = JsonUtils.toMap(requestJsonStr);
			userId = CommonUtil.getParamValue(map, "userId");
			auditStatus = CommonUtil.getParamValue(map, "auditStatus");
			remark = CommonUtil.getParamValue(map, "remark");
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "auditUser error", e);
			return CallResult.failure("参数格式不正确");
		}
		
		
		CallResult<Boolean> result = userService.auditUser(Integer.parseInt(userId),Integer.parseInt(auditStatus),remark);
		LogUtils.info(this.getClass(), "auditUser result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	@RequestMapping(value="/createUser.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Boolean> createUser(@RequestBody UserCreateRequest createRequest) {
		LogUtils.info(this.getClass(), "createUser params : {}", createRequest);
//		LogUtils.info(this.getClass(), "createUser result : {}", result);
		return null;
	}
	
	@RequestMapping(value="/login.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<UserLoginResponse> login(@RequestBody String userLoginJsonStr) {
		LogUtils.info(this.getClass(), "login params : {}",userLoginJsonStr);
		
		Map userLoginMap = new HashMap();
		try {
			userLoginMap = JsonUtils.toMap(userLoginJsonStr);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "login error", e);
			return CallResult.failure("参数格式不正确");
		}
		
		CallResult<UserLoginResponse> result = userService.login((String)userLoginMap.get("loginName"), (String)userLoginMap.get("loginPwd"));
		LogUtils.info(this.getClass(), "login result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	@RequestMapping(value="/logout.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Boolean> logout(@RequestHeader(value="Authorization",required=false) String token) {
		LogUtils.info(this.getClass(), "logout params : token = {}",token);
		
		if(StringUtils.isBlank(token)) {
			LogUtils.info(this.getClass(), "logout result : token is empty");
			return CallResult.success(true);
		}
		
		User loginUser = SessionCache.get(token);
		if(loginUser == null) {
			LogUtils.info(this.getClass(), "logout result : token not exists");
			return CallResult.success(true);
		}
		
		SessionCache.remove(token);
		LogUtils.info(this.getClass(), "logout result : success");
		return CallResult.success(true);
	}
}
