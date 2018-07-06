package com.huasheng.sysq.editor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.huasheng.sysq.editor.dao.UserDao;
import com.huasheng.sysq.editor.model.User;
import com.huasheng.sysq.editor.service.UserService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.LogUtils;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public CallResult<User> getUserByName(final String name) {
		User u = userDao.getByName(name);
		return CallResult.success(u);
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
