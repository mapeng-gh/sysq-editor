package com.huasheng.sysq.editor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huasheng.sysq.editor.model.User;
import com.huasheng.sysq.editor.params.UserManageSearchRequest;
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
	
	@RequestMapping(value="/list.do",method=RequestMethod.GET)
	@ResponseBody
	public CallResult<Page<User>> list(UserManageSearchRequest searchRequest) {
		LogUtils.info(this.getClass(), "list params : {}",JsonUtils.toJson(searchRequest));
		CallResult<Page<User>> result = userService.findUserList(searchRequest);
		LogUtils.info(this.getClass(), "list result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	@RequestMapping(value="/addUser.do",method=RequestMethod.POST)
	@ResponseBody
	public CallResult<Boolean> addUser(@RequestBody String userJson) {
		LogUtils.info(this.getClass(), "addUser params : {}", userJson);
		User user = JsonUtils.toBean(userJson, User.class);
		CallResult<Boolean> result = userService.addUser(user);
		LogUtils.info(this.getClass(), "addUser result : {}", result);
		return result;
	}
}
