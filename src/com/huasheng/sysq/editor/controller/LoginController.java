package com.huasheng.sysq.editor.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huasheng.sysq.editor.model.User;
import com.huasheng.sysq.editor.params.LoginResponse;
import com.huasheng.sysq.editor.service.UserService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.SessionCache;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;

	/**
	 * 用户登录
	 * @param requestJsonStr
	 * @return
	 */
	@RequestMapping(value="/login.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<LoginResponse> login(@RequestBody String requestJsonStr) {
		LogUtils.info(this.getClass(), "login params : {}",requestJsonStr);
		
		//参数处理
		String loginName,loginPwd;
		try {
			JSONObject requestJson = JSON.parseObject(requestJsonStr);
			loginName = requestJson.getString("loginName");
			loginPwd = requestJson.getString("loginPwd");
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "login error", e);
			return CallResult.failure("参数格式不正确");
		}
		
		CallResult<LoginResponse> result = userService.login(loginName,loginPwd);
		LogUtils.info(this.getClass(), "login result : {}", JsonUtils.toJson(result));
		
		return result;
	}
	
	/**
	 * 用户退出
	 * @param token
	 * @return
	 */
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
