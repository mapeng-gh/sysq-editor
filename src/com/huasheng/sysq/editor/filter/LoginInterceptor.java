package com.huasheng.sysq.editor.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.SessionCache;
import com.huasheng.sysq.editor.util.ThreadLocalUtils;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	private String[] excludePaths;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		
		//直接过滤
		String requestPath = request.getRequestURI();
		if(ArrayUtils.contains(this.getExcludePaths(), requestPath)) {
			return true;
		}
		
		//token校验（请求头或者请求参数）
		String token = request.getHeader("Authorization");
		if(StringUtils.isBlank(token)) {
			token = request.getParameter("Authorization");
		}
		if(StringUtils.isBlank(token) || SessionCache.get(token) == null) {
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			response.getWriter().write(JsonUtils.toJson(CallResult.failure(-2,"用户未登录")));
			return false;
		}
		
		//当前用户
		ThreadLocalUtils.setLoginUser(SessionCache.get(token));
		return true;
	}
	
	public String[] getExcludePaths() {
		return excludePaths;
	}

	public void setExcludePaths(String[] excludePaths) {
		this.excludePaths = excludePaths;
	}
}
