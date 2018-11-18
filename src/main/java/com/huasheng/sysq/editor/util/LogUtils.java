package com.huasheng.sysq.editor.util;

import org.slf4j.LoggerFactory;

public class LogUtils {

	public static void info(Class<?> clz,String msg,Object... obj) {
		LoggerFactory.getLogger(clz).info(msg, obj);
	}
	
	public static void warn(Class<?> clz,String msg,Object... obj) {
		LoggerFactory.getLogger(clz).warn(msg, obj);
	}
	
	public static void error(Class<?> clz,String msg,Throwable ex) {
		LoggerFactory.getLogger(clz).error(msg, ex);
	}
}
