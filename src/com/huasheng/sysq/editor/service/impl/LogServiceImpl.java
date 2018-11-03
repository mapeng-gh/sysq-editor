package com.huasheng.sysq.editor.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huasheng.sysq.editor.dao.EditorEditLogDao;
import com.huasheng.sysq.editor.dao.EditorLoginLogDao;
import com.huasheng.sysq.editor.dao.UserDao;
import com.huasheng.sysq.editor.model.EditorEditLog;
import com.huasheng.sysq.editor.model.EditorLoginLog;
import com.huasheng.sysq.editor.model.User;
import com.huasheng.sysq.editor.params.EditorLoginLogResponse;
import com.huasheng.sysq.editor.service.LogService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;

@Service
public class LogServiceImpl implements LogService{
	
	@Autowired
	private EditorLoginLogDao editorLoginLogDao;
	
	@Autowired
	private EditorEditLogDao editorEditLogDao;
	
	@Autowired
	private UserDao userDao;

	@Override
	public CallResult<Page<EditorLoginLogResponse>> findLoginLogPage(Map<String, Object> searchParams, int currentPage,int pageSize) {
		LogUtils.info(this.getClass(), "findLoginLogPage params : searchParams = {} , currentPage = {} , pageSize = {}", JsonUtils.toJson(searchParams),currentPage,pageSize);
		
		try {
			//查询登录日志
			List<EditorLoginLog> editorLoginLogList = editorLoginLogDao.findPage(searchParams, currentPage, pageSize);
			
			//关联数据
			List<EditorLoginLogResponse> editorLoginLogResponseList = new ArrayList<EditorLoginLogResponse>(); 
			if(editorLoginLogList != null && editorLoginLogList.size() > 0) {
				for(EditorLoginLog editorLoginLog : editorLoginLogList) {
					EditorLoginLogResponse editorLoginLogResponse = new EditorLoginLogResponse();
					editorLoginLogResponse.setId(editorLoginLog.getId());
					editorLoginLogResponse.setLoginName(editorLoginLog.getLoginName());
					editorLoginLogResponse.setLoginIp(editorLoginLog.getLoginIp());
					editorLoginLogResponse.setLoginTime(editorLoginLog.getLoginTime());
					
					//获取用户详情
					User loginUser = userDao.selectByLoginName(editorLoginLog.getLoginName());
					if(loginUser != null) {
						editorLoginLogResponse.setName(loginUser.getName());
						editorLoginLogResponse.setUserType(loginUser.getUserType());
						editorLoginLogResponse.setWorkingPlace(loginUser.getWorkingPlace());
					}
					
					editorLoginLogResponseList.add(editorLoginLogResponse);
				}
			}
			
			//统计
			int total = editorLoginLogDao.count(searchParams);
			
			return CallResult.success(new Page<EditorLoginLogResponse>(editorLoginLogResponseList,currentPage,pageSize,total));
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "findLoginLogPage error", e);
			return CallResult.failure("获取登录日志失败");
		}
	}

	@Override
	public CallResult<Page<EditorEditLog>> findEditLogPage(Map<String, Object> searchParams, int currentPage,int pageSize) {
		LogUtils.info(this.getClass(), "findEditLogPage params : searchParams = {} , currentPage = {} , pageSize = {}", JsonUtils.toJson(searchParams),currentPage,pageSize);
		
		try {
			//查询编辑日志
			List<EditorEditLog> editorEditLogList = editorEditLogDao.findPage(searchParams, currentPage, pageSize);
			
			//统计
			int total = editorEditLogDao.count(searchParams);
			
			return CallResult.success(new Page<EditorEditLog>(editorEditLogList,currentPage,pageSize,total));
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "findEditLogPage error", e);
			return CallResult.failure("查询编辑日志失败");
		}
	}

}
