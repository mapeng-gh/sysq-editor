package com.huasheng.sysq.editor.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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

@Controller
@RequestMapping(value="/usercenter")
public class UsercenterController extends BaseController{
	
	@Autowired
	private UserService userService;

	/**
	 * 用户信息
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/profile.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<UserResponse> profile(HttpServletRequest request) {
		int userId = super.getLoginUser(request).getId();
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
	public CallResult<Boolean> modifyProfile(HttpServletRequest request , @RequestBody String requestJsonStr) {
		User loginUser = super.getLoginUser(request);
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
	
	@RequestMapping(value="/modifyPwd.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Boolean> modifyPwd(HttpServletRequest request , @RequestBody String requestJsonStr) {
		User loginUser = super.getLoginUser(request);
		LogUtils.info(this.getClass(), "modifyPwd params : loginName = {},content = {}",loginUser.getLoginName(),requestJsonStr);
		
		//参数处理
		String oldPwd = "";
		String newPwd = "";
		try {
			JSONObject requestJson = JSON.parseObject(requestJsonStr);
			oldPwd = requestJson.getString("oldPwd");
			newPwd = requestJson.getString("newPwd");
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "modifyPwd error", e);
			return CallResult.failure("修改密码失败");
		}
		
		//修改信息
		CallResult<Boolean> result = userService.modifyPwd(loginUser.getId(), oldPwd, newPwd);
		LogUtils.info(this.getClass(), "modifyPwd result : {}", JsonUtils.toJson(result));
		
		return result;
	}
}
