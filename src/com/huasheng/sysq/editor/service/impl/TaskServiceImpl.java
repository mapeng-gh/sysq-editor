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
import com.huasheng.sysq.editor.dao.EditorResultDao;
import com.huasheng.sysq.editor.dao.InterviewDao;
import com.huasheng.sysq.editor.dao.PatientDao;
import com.huasheng.sysq.editor.dao.QuestionaireDao;
import com.huasheng.sysq.editor.dao.SysqResultDao;
import com.huasheng.sysq.editor.dao.TaskDao;
import com.huasheng.sysq.editor.dao.UserDao;
import com.huasheng.sysq.editor.model.Interview;
import com.huasheng.sysq.editor.model.Questionaire;
import com.huasheng.sysq.editor.model.SysqResult;
import com.huasheng.sysq.editor.model.Task;
import com.huasheng.sysq.editor.params.InterviewResponse;
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
	
	@Autowired
	private EditorResultDao editorResultDao;
	
	@Autowired
	private QuestionaireDao questionaireDao;
	
	@Autowired
	private SysqResultDao sysqResultDao;
	
	@Override
	public CallResult<Page<InterviewResponse>> findUnAssignInterviewPage(Map<String, Object> searchParams,int currentPage,int pageSize) {
		LogUtils.info(this.getClass(), "findUnAssignInterviewPage params : searchParams = {},currentPage = {},pageSize = {}", JsonUtils.toJson(searchParams),currentPage,pageSize);
		
		try {
			//查询访谈
			List<Interview> interviewList = interviewDao.findUnAssignInterviewPage(searchParams,currentPage,pageSize);
			
			//关联数据
			List<InterviewResponse> interviewResponseList = new ArrayList<InterviewResponse>(); 
			if(interviewList != null && interviewList.size() > 0) {
				for(Interview interview : interviewList) {
					InterviewResponse interviewResponse = new InterviewResponse();
					interviewResponse.setInterview(interview);
					interviewResponse.setDoctor(doctorDao.selectById(interview.getDoctorId()));
					interviewResponse.setPatient(patientDao.selectById(interview.getPatientId()));
					interviewResponseList.add(interviewResponse);
				}
			}
			
			//统计
			int total = interviewDao.countUnAssignInterview(searchParams);
			
			return CallResult.success(new Page<InterviewResponse>(interviewResponseList,currentPage,pageSize,total));
		}catch(Exception e) {
			LogUtils.error(UserServiceImpl.class, "findUnAssignInterviewPage error", e);
			return CallResult.failure("查找未分配访谈列表失败");
		}
	}

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
							
							//创建任务
							Date currentTime = new Date();
							Task task = new Task(userId,Integer.parseInt(interviewId),Constants.TASK_STATUS_ASSIGNED,currentTime,currentTime);
							taskDao.insert(task);
							
							//初始化答案
							List<SysqResult> resultList = sysqResultDao.getAllAnswerResult(Integer.parseInt(interviewId));
							if(resultList != null && resultList.size() > 0) {
								editorResultDao.batchInsert(resultList);
							}
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
					Interview interview = interviewDao.selectById(task.getInterviewId());
					taskResponse.setInterview(interview);
					taskResponse.setPatient(patientDao.selectById(interview.getPatientId()));
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

	@Override
	public CallResult<List<Questionaire>> getTaskQuestionaireList(int taskId) {
		LogUtils.info(this.getClass(), "getTaskQuestionaireList params : taskId = {}", taskId);
		try {
			//获取访谈
			Task task = taskDao.selectById(taskId);
			Interview interview = interviewDao.selectById(task.getInterviewId());
			
			//获取问卷编码
			List<String> questionaireCodeList = editorResultDao.getQuestionaireList(task.getInterviewId());
			
			//获取问卷
			List<Questionaire> questionaireList = questionaireDao.batchFindByVersionAndCode(interview.getVersionId(), questionaireCodeList);
			
			return CallResult.success(questionaireList);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "getTaskQuestionaireList error", e);
			return CallResult.failure("获取问卷列表失败");
		}
	}

	@Override
	public CallResult<TaskResponse> getTaskDetail(int taskId) {
		LogUtils.info(this.getClass(), "getTaskDetail params : taskId = {}", taskId);
		try {
			TaskResponse taskResponse = new TaskResponse();
			
			//获取任务
			Task task = taskDao.selectById(taskId);
			taskResponse.setTask(task);
			
			//关联数据
			Interview interview = interviewDao.selectById(task.getInterviewId());
			taskResponse.setInterview(interview);
			taskResponse.setPatient(patientDao.selectById(interview.getPatientId()));
			taskResponse.setDoctor(doctorDao.selectById(interview.getDoctorId()));
			
			return CallResult.success(taskResponse);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "getTaskDetail error", e);
			return CallResult.failure("获取任务详情失败");
		}
	}

}
