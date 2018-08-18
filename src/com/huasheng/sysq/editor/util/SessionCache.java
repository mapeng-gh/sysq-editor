package com.huasheng.sysq.editor.util;

import java.util.HashMap;
import java.util.Map;

import com.huasheng.sysq.editor.model.User;

public class SessionCache {
	
	public static String USER_LOGIN_KEY = "USER_LOGIN_";

	private static Map<String,User> cache = new HashMap<String,User>();
	
	public static void add(String key , User value) {
		cache.put(key, value);
	}
	
	public static void remove(String key) {
		cache.remove(key);
	}
	
	public static User get(String key) {
		return cache.get(key);
	}
}
