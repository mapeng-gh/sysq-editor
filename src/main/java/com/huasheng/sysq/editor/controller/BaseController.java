package com.huasheng.sysq.editor.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import com.huasheng.sysq.editor.model.User;
import com.huasheng.sysq.editor.util.SessionCache;

@Controller
public class BaseController {
	
	/**
	 * 获取登录Token
	 * @param request
	 * @return
	 */
	protected String getLoginToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		return token;
	}
	
	/**
	 * 获取当前用户
	 * @param request
	 * @return
	 */
	protected User getLoginUser(HttpServletRequest request) {
		String token = this.getLoginToken(request);
		return SessionCache.getSession(token);
	}
	
	/**
	 * 删除当前用户
	 * @param request
	 */
	protected void remLoginUser(HttpServletRequest request) {
		String token = this.getLoginToken(request);
		SessionCache.remSession(token);
	}
}
