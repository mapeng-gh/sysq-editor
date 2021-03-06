package com.huasheng.sysq.editor.service;

import java.util.List;
import java.util.Map;

import com.huasheng.sysq.editor.model.User;
import com.huasheng.sysq.editor.params.LoginResponse;
import com.huasheng.sysq.editor.params.UserResponse;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.Page;

public interface UserService {
	
	/**
	 * 查找用户
	 * @param searchUser
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public CallResult<Page<User>> findUserPage(Map<String,String> searchParams);
	
	/**
	 * 查看用户
	 * @param userId
	 * @return
	 */
	public CallResult<UserResponse> viewUser(int userId);
	
	/**
	 * 注册用户
	 * @param newUser
	 */
	public CallResult<Boolean> registerUser(User registerUser);
	
	/**
	 * 用户登录
	 * @param loginName
	 * @param password
	 * @return
	 */
	public CallResult<LoginResponse> login(String loginName,String password);
	
	/**
	 * 用户审核
	 * @param loginName
	 * @param auditStatus
	 * @param remark
	 * @return
	 */
	public CallResult<Boolean> auditUser(int userId , int auditStatus , String remark);
	
	/**
	 * 修改个人信息
	 * @param userId
	 * @param user
	 * @return
	 */
	public CallResult<Boolean> modifyProfile(int userId , User user);
	
	/**
	 * 修改密码
	 * @param userId
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	public CallResult<Boolean> modifyPwd(int userId,String oldPwd,String newPwd);
	
	/**
	 * 重置密码
	 * @param userId
	 * @param pwd
	 * @return
	 */
	public CallResult<Boolean> resetPwd(int userId,String pwd);
	
	/**
	 * 类型修改
	 * @param userId
	 * @param userType
	 * @return
	 */
	public CallResult<Boolean> changeType(int userId,int userType);
	
	/**
	 * 获取编辑员列表
	 * @return
	 */
	public CallResult<List<User>> getEditorList();
}
