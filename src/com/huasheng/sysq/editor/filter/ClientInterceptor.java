package com.huasheng.sysq.editor.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.huasheng.sysq.editor.util.ThreadLocalUtils;
import com.huasheng.sysq.editor.util.WebUtils;

public class ClientInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		
		//访问IP
		ThreadLocalUtils.setLoginIp(WebUtils.getClientIp(request));
		return true;
	}
}
