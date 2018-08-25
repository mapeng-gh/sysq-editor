package com.huasheng.sysq.editor.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.huasheng.sysq.editor.dao.TaskDao;
import com.huasheng.sysq.editor.model.Task;
import com.huasheng.sysq.editor.service.TaskService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.LogUtils;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Autowired
	private TaskDao taskDao;

	@Override
	public CallResult<Boolean> assignTask(int userId, String taskIds) {
		LogUtils.info(this.getClass(), "assignTask params : userId = {},taskIds = {}", userId,taskIds);

		try {
			this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					
					String taskIdArray[] = taskIds.split(",");
					if(taskIdArray != null && taskIdArray.length > 0) {
						for(String taskId : taskIdArray) {
							Date currentTime = new Date();
							Task task = new Task(userId,Integer.parseInt(taskId),currentTime,currentTime);
							taskDao.insert(task);
						}
					}
				}
			});
			return CallResult.success(true);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "assignTask error", e);
			return CallResult.failure("分配任务失败");
		}
	}

}
