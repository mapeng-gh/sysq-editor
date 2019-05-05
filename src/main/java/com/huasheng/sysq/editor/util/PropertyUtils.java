package com.huasheng.sysq.editor.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {
	
	private static Properties props = new Properties();
	
	static {
		InputStream is = null;
		try {
			is = PropertyUtils.class.getClassLoader().getResourceAsStream("application.properties");
			
			if(is == null) {
				LogUtils.error(PropertyUtils.class, "load properties error : application.properties not found");
			}else {
				props.load(is);
				LogUtils.info(PropertyUtils.class, "load properties success");
			}
			
		}catch(Exception loadEx) {
			LogUtils.error(PropertyUtils.class, "load properties error : application.properties load error", loadEx);
		}finally {
			if(is != null) {
				try {
					is.close();
				}catch(Exception IOEx) {
				}
			}
		}
	}
	
	public static String getStringProp(String key) {
		return props.getProperty(key);
	}
	
	public static Integer getIntProp(String key) {
		if(props.containsKey(key)) {
			return Integer.valueOf(props.getProperty(key));
		}
		return null;
	}
	
}
