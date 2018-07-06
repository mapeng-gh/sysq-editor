package com.huasheng.sysq.editor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huasheng.sysq.editor.model.User;
import com.huasheng.sysq.editor.service.UserService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;

@Controller
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/getUserByName.do",method=RequestMethod.GET)
	@ResponseBody
	public CallResult<User> getUserByName(@RequestParam("name") String name) {
		LogUtils.info(this.getClass(), "getUserByName params : name = {}",name);
		CallResult<User> result = userService.getUserByName(name);
		LogUtils.info(this.getClass(), "getUserByName result : {}", JsonUtils.toJson(result));
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
