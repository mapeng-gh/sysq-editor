package com.huasheng.sysq.editor.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.SessionCache;

public class SessionInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestPath = request.getRequestURI();
		if(ArrayUtils.contains(new String[] {"/login.do","/logout.do"}, requestPath)) {
			return true;
		}
		
		String token = request.getHeader("Authorization");
		if(StringUtils.isBlank(token) || SessionCache.get(token) == null) {
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			response.getWriter().write(JsonUtils.toJson(CallResult.failure(-2,"用户未登录")));
			return false;
		}
		
		return true;
	}
}
