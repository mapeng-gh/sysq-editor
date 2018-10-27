package com.huasheng.sysq.editor.service;

import java.util.Map;

import com.huasheng.sysq.editor.params.EditorLoginLogResponse;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.Page;

public interface LogService {

	/**
	 * 搜索登录日志
	 * @param searchParams
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public CallResult<Page<EditorLoginLogResponse>> findLoginLogPage(Map<String,Object> searchParams , int currentPage , int pageSize);
}
