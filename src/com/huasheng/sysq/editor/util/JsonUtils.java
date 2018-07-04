package com.huasheng.sysq.editor.util;

import com.alibaba.fastjson.JSON;

public class JsonUtils {

	public static String toJson(Object obj) {
		return JSON.toJSONString(obj);
	}
	
	public static <T> T toBean(String jsonStr,Class<T> clz) {
		return JSON.parseObject(jsonStr, clz);
	}
}
