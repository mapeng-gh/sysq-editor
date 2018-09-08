package com.huasheng.sysq.editor.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huasheng.sysq.editor.model.Version;
import com.huasheng.sysq.editor.service.DataService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;

@Controller
@RequestMapping(value="/dataManage")
public class DataManageController {
	
	@Autowired
	private DataService dataService;

	@RequestMapping(value="/versionList.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Page<Version>> versionList(@RequestParam Map<String,String> searchParams) {
		LogUtils.info(this.getClass(), "versionList params : {}",JsonUtils.toJson(searchParams));
		
		//参数处理
		int currentPage = 0;
		int pageSize = 0;
		Map<String,Object> handledParams = new HashMap<String,Object>();
		try {
			currentPage = Integer.parseInt(searchParams.get("currentPage"));
			pageSize = Integer.parseInt(searchParams.get("pageSize"));
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "versionList error", e);
			return CallResult.failure("获取版本列表失败");
		}
		
		//查询版本
		CallResult<Page<Version>> result = dataService.findVersionPage(handledParams, currentPage, pageSize);
		LogUtils.info(this.getClass(), "versionList result : {}", JsonUtils.toJson(result));
		return result;
	}
}
