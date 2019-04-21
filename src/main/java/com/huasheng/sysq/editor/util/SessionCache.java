package com.huasheng.sysq.editor.util;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.huasheng.sysq.editor.model.User;

public class SessionCache {
	
	private static Map<String,User> cache = new ConcurrentHashMap<String,User>();
	
	/**
	 * 随机生成Key
	 * @return
	 */
	public static String genRandomKey() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 添加会话
	 * @param key
	 * @param value
	 */
	public static void addSession(String key , User value) {
		cache.put(key, value);
	}
	
	/**
	 * 移除会话
	 * @param key
	 */
	public static void remSession(String key) {
		cache.remove(key);
	}
	
	/**
	 * 获取会话
	 * @param key
	 * @return
	 */
	public static User getSession(String key) {
		return cache.get(key);
	}
}
