package com.huasheng.sysq.editor.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.huasheng.sysq.editor.dao.DoctorDao;
import com.huasheng.sysq.editor.dao.EditorLoginLogDao;
import com.huasheng.sysq.editor.dao.UserDao;
import com.huasheng.sysq.editor.model.Doctor;
import com.huasheng.sysq.editor.model.EditorLoginLog;
import com.huasheng.sysq.editor.model.User;
import com.huasheng.sysq.editor.params.LoginResponse;
import com.huasheng.sysq.editor.params.UserResponse;
import com.huasheng.sysq.editor.service.UserService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.Constants;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;
import com.huasheng.sysq.editor.util.SessionCache;
import com.huasheng.sysq.editor.util.ThreadLocalUtils;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private UserDao userDao;	
	
	@Autowired
	private DoctorDao doctorDao;
	
	@Autowired
	private EditorLoginLogDao editorLoginLogDao;
	
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
			LogUtils.error(this.getClass(), "findUserList error", e);
			return CallResult.failure("查找用户失败");
		}
	}
	
	@Override
	public CallResult<Boolean> registerUser(final User registerUser) {
		LogUtils.info(this.getClass(), "registerUser params : {}", JsonUtils.toJson(registerUser));
		
		//账号重复校验
		List<User> userList = userDao.findAll();
		if(userList != null && userList.size() > 0) {
			for(User user : userList) {
				if(user.getLoginName().equals(registerUser.getLoginName())) {
					return CallResult.failure("登录账号已存在");
				}
			}
		}
		
		//浏览人员手机号关联
		if(registerUser.getUserType() == Constants.USER_TYPE_VIEWER) {
			List<Doctor> doctorList = doctorDao.findByMobile(registerUser.getLoginName());
			if(doctorList == null || doctorList.size() == 0) {
				return CallResult.failure("浏览人员登录账号须为App关联的联系电话");
			}
		}
		
		try {
			this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					registerUser.setAuditStatus(Constants.AUDIT_STATUS_ING);
					Date curDate = new Date();
					registerUser.setCreateTime(curDate);
					registerUser.setUpdateTime(curDate);
					userDao.insert(registerUser);
				}
			});
			
			return CallResult.success(true);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "registerUser error", e);
			return CallResult.failure("注册用户失败");
		}
	}

	@Override
	public CallResult<LoginResponse> login(String loginName, String loginPwd) {
		LogUtils.info(this.getClass(), "login params : loginName = {}",loginName);
		
		try {
			//参数校验
			if(StringUtils.isBlank(loginName) || StringUtils.isBlank(loginPwd)) {
				return CallResult.failure("登录名和登录密码不能为空");
			}
			
			//业务校验
			User loginUser = userDao.selectByLoginName(loginName);
			if(loginUser == null) {
				return CallResult.failure("登录名不存在");
			}
			if(loginUser.getAuditStatus() == Constants.AUDIT_STATUS_ING) {
				return CallResult.failure("用户正在审核");
			}else if(loginUser.getAuditStatus() == Constants.AUDIT_STATUS_REJECT) {
				return CallResult.failure("用户审核未通过：" + loginUser.getRemark());
			}
			if(!loginPwd.equals(loginUser.getLoginPwd())) {
				return CallResult.failure("登录密码不正确");
			}
			
			//保存会话
			String token = SessionCache.USER_LOGIN_KEY + UUID.randomUUID().toString();
			SessionCache.add(token, loginUser);
			
			//登录日志
			try {
				EditorLoginLog editorLoginLog = new EditorLoginLog();
				editorLoginLog.setLoginName(loginUser.getLoginName());
				Date curTime = new Date();
				editorLoginLog.setLoginTime(curTime);
				editorLoginLog.setLoginIp(ThreadLocalUtils.getLoginIp());
				editorLoginLog.setCreateTime(curTime);
				editorLoginLogDao.insert(editorLoginLog);
			}catch(Exception e) {
				LogUtils.error(this.getClass(), "login : log error", e);
			}
			
			LoginResponse userLoginResponse = new LoginResponse();
			userLoginResponse.setName(loginUser.getName());
			userLoginResponse.setUserType(loginUser.getUserType());
			userLoginResponse.setToken(token);
			
			return CallResult.success(userLoginResponse);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "login", e);
			return CallResult.failure("登录失败");
		}
	}

	@Override
	public CallResult<UserResponse> viewUser(int userId) {
		try {
			//查询用户
			User user = userDao.selectById(userId);
			
			//删除敏感信息
			UserResponse userResponse = new UserResponse();
			BeanUtils.copyProperties(userResponse, user);
			
			return CallResult.success(userResponse);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "viewUser error", e);
			return CallResult.failure("获取用户失败"); 
		}
		
	}

	@Override
	public CallResult<Boolean> auditUser(final int userId, final int auditStatus, final String remark) {
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult(){
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					User user = userDao.selectById(userId);
					user.setAuditStatus(auditStatus);
					user.setRemark(remark);
					user.setUpdateTime(new Date());
					userDao.update(user);
				}
			});
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "auditUser error", e);
			return CallResult.failure("用户审核失败"); 
		}
		return CallResult.success(true);
	}

	@Override
	public CallResult<Boolean> modifyProfile(final int userId, final User user) {
		LogUtils.info(this.getClass(), "modifyProfile params : userId = {},user = {}", userId,JsonUtils.toJson(user));
		try {
			this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					User modifyUser = userDao.selectById(userId);
					modifyUser.setName(user.getName());
					modifyUser.setMobile(user.getMobile());
					modifyUser.setEmail(user.getEmail());
					modifyUser.setWorkingPlace(user.getWorkingPlace());
					userDao.update(modifyUser);
				}
			});
			return CallResult.success(true);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "modifyProfile error", e);
			return CallResult.failure("修改用户信息失败");
		}
	}

	@Override
	public CallResult<Boolean> modifyPwd(final int userId, final String oldPwd, final String newPwd) {
		LogUtils.info(this.getClass(), "modifyPwd params : userId = {}", userId);
		
		//参数校验
		final User user = userDao.selectById(userId);
		if(!user.getLoginPwd().equals(oldPwd)) {
			return CallResult.failure("原密码不正确");
		}
		
		try {
			this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					user.setLoginPwd(newPwd);
					userDao.update(user);
				}
			});
			
			return CallResult.success(true);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "modifyPwd error", e);
			return CallResult.failure("修改密码失败");
		}
	}

	@Override
	public CallResult<Boolean> resetPwd(int userId, final String pwd) {
		LogUtils.info(this.getClass(), "resetPwd params : userId = {} , pwd = {}", userId,pwd);
		
		//数据校验
		final User user = userDao.selectById(userId);
		if(user == null) {
			return CallResult.failure("用户不存在");
		}
		if(StringUtils.isBlank(pwd)) {
			return CallResult.failure("重置密码不能为空");
		}
		
		try {
			this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					user.setLoginPwd(pwd);
					user.setUpdateTime(new Date());
					userDao.update(user);
				}
			});
			
			return CallResult.success(true);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "resetPwd error", e);
			return CallResult.failure("重置密码失败");
		}
	}

	@Override
	public CallResult<Boolean> changeType(int userId, final int userType) {
		LogUtils.info(this.getClass(), "changeType params : userId = {} , userType = {}", userId,userType);
		
		//数据校验
		final User user = userDao.selectById(userId);
		if(user == null) {
			return CallResult.failure("用户不存在");
		}
		
		try {
			this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					user.setUserType(userType);
					user.setUpdateTime(new Date());
					userDao.update(user);
				}
			});
			
			return CallResult.success(true);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "changeType error", e);
			return CallResult.failure("类型修改失败");
		}
	}
}
