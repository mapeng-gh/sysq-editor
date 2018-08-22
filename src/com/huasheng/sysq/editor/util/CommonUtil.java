package com.huasheng.sysq.editor.util;

import java.util.Map;

public class CommonUtil {

	public static String getParamValue(Map request , String name) {
		return request.get(name) == null ? "" : request.get(name).toString();
	}
}
