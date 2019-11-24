package com.huasheng.sysq.editor.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huasheng.sysq.editor.params.LoginResponse;
import com.huasheng.sysq.editor.params.UserResponse;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
@TestPropertySource(locations= {"file:///F:/workspace/sysq-editor/profile/env-dev.properties"})
public class UserServiceTest {
	
	@Autowired
	private UserService userService;

	@Test
	public void testViewUser() {
		CallResult<UserResponse> result = userService.viewUser(1);
		Assert.assertTrue(result.isSuccess());
	}
	
	@Test
	public void testLogin() {
		CallResult<LoginResponse> result = userService.login("admin", "123456");
		System.out.println(JsonUtils.toJson(result));
		Assert.assertTrue(result.isSuccess()); 
	}
}
