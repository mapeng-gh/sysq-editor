package com.huasheng.sysq.editor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.huasheng.sysq.editor.dao.UserDao;
import com.huasheng.sysq.editor.model.User;
import com.huasheng.sysq.editor.params.UserManageSearchRequest;
import com.huasheng.sysq.editor.service.UserService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private UserDao userDao;	
	
	@Override
	public CallResult<Page<User>> findUserList(UserManageSearchRequest request) {
		try {
			
			//参数校验
			if(!request.validate()) {
				return CallResult.failure("参数格式不正确");
			}
			
			//参数转换
			User searchUser = request.toUser();
			int currentPage = Integer.parseInt(request.getCurrentPage());
			int pageSize = Integer.parseInt(request.getPageSize());
			
			//搜索
			List<User> userList = userDao.selectList(searchUser,currentPage,pageSize);
			
			//统计
			int total = userDao.count(searchUser);
			
			//返回
			return CallResult.success(new Page(userList,currentPage,pageSize,total));
			
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
}
