package com.huasheng.sysq.editor.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.huasheng.sysq.editor.dao.DoctorDao;
import com.huasheng.sysq.editor.dao.InterviewDao;
import com.huasheng.sysq.editor.dao.PatientDao;
import com.huasheng.sysq.editor.dao.TaskDao;
import com.huasheng.sysq.editor.dao.UserDao;
import com.huasheng.sysq.editor.model.Interview;
import com.huasheng.sysq.editor.model.Task;
import com.huasheng.sysq.editor.params.TaskResponse;
import com.huasheng.sysq.editor.service.TaskService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.Constants;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Autowired
	private TaskDao taskDao;
	
	@Autowired
	private InterviewDao interviewDao;
	
	@Autowired
	private PatientDao patientDao;
	
	@Autowired
	private DoctorDao doctorDao;
	
	@Autowired
	private UserDao userDao;

	@Override
	public CallResult<Boolean> assignTask(int userId, String interviewIds) {
		LogUtils.info(this.getClass(), "assignTask params : userId = {},interviewIds = {}", userId,interviewIds);

		try {
			this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					
					String interviewIdArray[] = interviewIds.split(",");
					if(interviewIdArray != null && interviewIdArray.length > 0) {
						for(String interviewId : interviewIdArray) {
							Date currentTime = new Date();
							Task task = new Task(userId,Integer.parseInt(interviewId),Constants.TASK_STATUS_ASSIGNED,currentTime,currentTime);
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

	@Override
	public CallResult<Page<TaskResponse>> findTaskPage(Map<String, Object> searchParams , int currentPage , int pageSize) {
		LogUtils.info(this.getClass(), "findTaskPage params : {}", JsonUtils.toJson(searchParams));
		
		try {
			//查询任务
			List<Task> taskList = taskDao.findAllTaskPage(searchParams,currentPage,pageSize);
			
			//关联数据
			List<TaskResponse> taskResponseList = new ArrayList<TaskResponse>();
			if(taskList != null && taskList.size() > 0) {
				for(Task task : taskList) {
					TaskResponse taskResponse = new TaskResponse();
					taskResponse.setTask(task);
					taskResponse.setUser(userDao.selectById(task.getUserId()));
					Interview interview = interviewDao.selectById(task.getInterviewId());
					taskResponse.setInterview(interview);
					taskResponse.setPatient(patientDao.selectById(interview.getPatientId()));
					taskResponse.setDoctor(doctorDao.selectById(interview.getDoctorId()));
					taskResponseList.add(taskResponse);
				}
			}
			
			//统计
			int total = taskDao.countAllTask(searchParams);
			
			return CallResult.success(new Page<TaskResponse>(taskResponseList,currentPage,pageSize,total));
			
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "findTaskPage error", e);
			return CallResult.failure("查找任务失败");
		}
	}

	@Override
	public CallResult<Page<TaskResponse>> findUserTaskPage(int userId, Map<String, Object> searchParams,int currentPage,int pageSize) {
		LogUtils.info(this.getClass(), "findUserTaskPage params : userId = {},searchParams = {},currentPage = {},pageSize = {}", userId,JsonUtils.toJson(searchParams),currentPage,pageSize);
		
		try {
			//查询任务
			List<Task> taskList = taskDao.findUserTaskPage(userId, searchParams, currentPage, pageSize);
			
			//关联数据
			List<TaskResponse> taskResponseList = new ArrayList<TaskResponse>();
			if(taskList != null && taskList.size() > 0) {
				for(Task task : taskList) {
					TaskResponse taskResponse = new TaskResponse();
					taskResponse.setTask(task);
					taskResponse.setUser(userDao.selectById(task.getUserId()));
					Interview interview = interviewDao.selectById(task.getInterviewId());
					taskResponse.setInterview(interview);
					taskResponse.setPatient(patientDao.selectById(interview.getPatientId()));
					taskResponse.setDoctor(doctorDao.selectById(interview.getDoctorId()));
					taskResponseList.add(taskResponse);
				}
			}
			
			//统计
			int total = taskDao.countUserTask(userId, searchParams);
			
			return CallResult.success(new Page<TaskResponse>(taskResponseList,currentPage,pageSize,total));
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "findUserTaskPage errror", e);
			return CallResult.failure("查找我的任务失败");
		}
	}

}
