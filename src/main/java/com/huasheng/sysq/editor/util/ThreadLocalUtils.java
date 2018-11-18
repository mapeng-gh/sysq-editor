package com.huasheng.sysq.editor.util;

import com.huasheng.sysq.editor.model.User;

public class ThreadLocalUtils {

	private static ThreadLocal<String> loginIpThreadLocal = new ThreadLocal<String>();
	private static ThreadLocal<User> loginUserThreadLocal = new ThreadLocal<User>();
	
	/**
	 * 访问IP
	 * @param ip
	 */
	public static void setLoginIp(String ip) {
		loginIpThreadLocal.set(ip);
	}
	
	public static String getLoginIp() {
		return loginIpThreadLocal.get();
	}

	/**
	 * 当前用户
	 * @param loginUser
	 */
	public static void setLoginUser(User loginUser) {
		loginUserThreadLocal.set(loginUser);
	}
	
	public static User getLoginUser() {
		return loginUserThreadLocal.get();
	}
}
