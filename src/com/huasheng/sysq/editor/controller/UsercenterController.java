package com.huasheng.sysq.editor.controller;

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
import com.huasheng.sysq.editor.params.UserResponse;
import com.huasheng.sysq.editor.service.UserService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.SessionCache;

@Controller
@RequestMapping(value="/usercenter")
public class UsercenterController {
	
	@Autowired
	private UserService userService;

	/**
	 * 用户信息
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/profile.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<UserResponse> profile(@RequestHeader(value="Authorization") String token) {
		int userId = SessionCache.get(token).getId();
		LogUtils.info(this.getClass(), "list params : userId = {}",userId);
		
		CallResult<UserResponse> result = userService.viewUser(userId);
		LogUtils.info(this.getClass(), "profile result : {}", JsonUtils.toJson(result));
		
		return result;
	}
	
	/**
	 * 修改信息
	 * @param token
	 * @param requestJsonStr
	 * @return
	 */
	@RequestMapping(value="/modifyProfile.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Boolean> modifyProfile(@RequestHeader(value="Authorization") String token,@RequestBody String requestJsonStr) {
		User loginUser = SessionCache.get(token);
		LogUtils.info(this.getClass(), "modifyProfile params : loginName = {},content = {}",loginUser.getLoginName(),requestJsonStr);
		
		//参数处理
		User user = new User();
		try {
			JSONObject requestJson = JSON.parseObject(requestJsonStr);
			user.setName(requestJson.getString("name"));
			user.setMobile(requestJson.getString("mobile"));
			user.setEmail(requestJson.getString("email"));
			user.setWorkingPlace(requestJson.getString("workingPlace"));
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "modifyProfile error", e);
			return CallResult.failure("修改信息失败");
		}
		
		//修改信息
		CallResult<Boolean> result = userService.modifyProfile(loginUser.getId(), user);
		LogUtils.info(this.getClass(), "profile result : {}", JsonUtils.toJson(result));
		
		return result;
	}
}
