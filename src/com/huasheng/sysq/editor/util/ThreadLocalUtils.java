package com.huasheng.sysq.editor.util;

public class ThreadLocalUtils {

	private static ThreadLocal<String> loginIpThreadLocal = new ThreadLocal<String>();
	
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
}
