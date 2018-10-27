package com.huasheng.sysq.editor.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class WebUtils {

	/**
	 * 获取访问IP
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {
		String remoteAddr = request.getHeader("X-FORWARDED-FOR");
		if(StringUtils.isBlank(remoteAddr)) {
			remoteAddr = request.getRemoteAddr();
		}
		return remoteAddr;
	}
}
