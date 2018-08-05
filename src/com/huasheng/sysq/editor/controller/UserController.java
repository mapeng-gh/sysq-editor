package com.huasheng.sysq.editor.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huasheng.sysq.editor.model.User;
import com.huasheng.sysq.editor.params.UserCreateRequest;
import com.huasheng.sysq.editor.service.UserService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;

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
	
	@RequestMapping(value="/createUser.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Boolean> createUser(@RequestBody UserCreateRequest createRequest) {
		LogUtils.info(this.getClass(), "createUser params : {}", createRequest);
//		LogUtils.info(this.getClass(), "createUser result : {}", result);
		return null;
	}
}
