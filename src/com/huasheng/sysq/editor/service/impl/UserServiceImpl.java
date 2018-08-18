package com.huasheng.sysq.editor.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.huasheng.sysq.editor.dao.UserDao;
import com.huasheng.sysq.editor.model.User;
import com.huasheng.sysq.editor.params.UserLoginResponse;
import com.huasheng.sysq.editor.service.UserService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.Constants;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;
import com.huasheng.sysq.editor.util.SessionCache;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private UserDao userDao;	
	
	@Override
	public CallResult<Page<User>> findUserPage(Map<String,String> searchParams) {
		try {
			//参数校验
			String userType = searchParams.get("userType");
			if(!StringUtils.isBlank(userType)) {
				if(!StringUtils.isNumeric(userType)) {
					return CallResult.failure("用户类型参数不正确");
				}
			}
			
			String auditStatus = searchParams.get("auditStatus");
			if(!StringUtils.isBlank(auditStatus)) {
				if(!StringUtils.isNumeric(auditStatus)) {
					return CallResult.failure("审核状态参数不正确");
				}
			}
			
			String currentPage = searchParams.get("currentPage");
			if(StringUtils.isBlank(currentPage)) {
				return CallResult.failure("分页参数不正确");
			}else {
				if(!StringUtils.isNumeric(currentPage) || Integer.parseInt(currentPage) <= 0){
					return CallResult.failure("分页参数不正确");
				}
			}
			
			String pageSize = searchParams.get("pageSize");
			if(StringUtils.isBlank(pageSize)) {
				return CallResult.failure("分页参数不正确");
			}else {
				if(!StringUtils.isNumeric(pageSize) || Integer.parseInt(pageSize) <= 0) {
					return CallResult.failure("分页参数不正确");
				}
			}
			
			//搜索
			List<User> userList = userDao.find(searchParams);
			
			//统计
			int total = userDao.count(searchParams);
			
			//返回
			return CallResult.success(new Page<User>(userList,Integer.parseInt(currentPage),Integer.parseInt(pageSize),total));
			
		}catch(Exception e) {
			LogUtils.error(UserServiceImpl.class, "findUserList error", e);
			return CallResult.failure("查找用户失败");
		}
	}
	
	@Override
	public CallResult<Boolean> addUser(final User user) {
		CallResult<Boolean> result = transactionTemplate.execute(new TransactionCallback<CallResult<Boolean>>(){
			@Override
			public CallResult<Boolean> doInTransaction(TransactionStatus transactionStatus) {
				try {
					userDao.insert(user);
					return CallResult.success(true);
				}catch(Exception e) {
					LogUtils.error(UserServiceImpl.class, "addUser error", e);
					return CallResult.failure("添加用户出错");
				}
			}
		});
		return result;
	}

	@Override
	public CallResult<UserLoginResponse> login(String loginName, String loginPwd) {
		
		//参数校验
		if(StringUtils.isBlank(loginName) || StringUtils.isBlank(loginPwd)) {
			return CallResult.failure("登录名和登录密码不能为空");
		}
		
		//业务校验
		User loginUser = userDao.selectByLoginName(loginName);
		if(loginUser == null) {
			return CallResult.failure("登录名不存在");
		}
		if(loginUser.getAuditStatus() == Constants.AUDIT_STATUS_NONE) {
			return CallResult.failure("用户正在审核");
		}else if(loginUser.getAuditStatus() == Constants.AUDIT_STATUS_REJECT) {
			return CallResult.failure("用户审核未通过：" + loginUser.getAuditRemark());
		}
		if(!loginPwd.equals(loginUser.getLoginPwd())) {
			return CallResult.failure("登录密码不正确");
		}
		
		//保存会话
		String token = SessionCache.USER_LOGIN_KEY + UUID.randomUUID().toString();
		SessionCache.add(token, loginUser);
		
		UserLoginResponse userLoginResponse = new UserLoginResponse();
		userLoginResponse.setName(loginUser.getName());
		userLoginResponse.setUserType(loginUser.getUserType());
		userLoginResponse.setToken(token);
		
		return CallResult.success(userLoginResponse);
	}
}
