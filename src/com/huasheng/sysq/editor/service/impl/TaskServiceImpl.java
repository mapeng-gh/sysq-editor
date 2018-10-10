package com.huasheng.sysq.editor.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.huasheng.sysq.editor.dao.DoctorDao;
import com.huasheng.sysq.editor.dao.EditorQuestionDao;
import com.huasheng.sysq.editor.dao.EditorQuestionaireDao;
import com.huasheng.sysq.editor.dao.EditorResultDao;
import com.huasheng.sysq.editor.dao.InterviewDao;
import com.huasheng.sysq.editor.dao.PatientDao;
import com.huasheng.sysq.editor.dao.QuestionDao;
import com.huasheng.sysq.editor.dao.QuestionaireDao;
import com.huasheng.sysq.editor.dao.SysqResultDao;
import com.huasheng.sysq.editor.dao.TaskDao;
import com.huasheng.sysq.editor.dao.UserDao;
import com.huasheng.sysq.editor.model.EditorQuestion;
import com.huasheng.sysq.editor.model.EditorQuestionaire;
import com.huasheng.sysq.editor.model.Interview;
import com.huasheng.sysq.editor.model.Question;
import com.huasheng.sysq.editor.model.Questionaire;
import com.huasheng.sysq.editor.model.SysqResult;
import com.huasheng.sysq.editor.model.Task;
import com.huasheng.sysq.editor.params.EditorQuestionaireResponse;
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
	
	@Autowired
	private EditorQuestionaireDao editorQuestionaireDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private EditorQuestionDao editorQuestionDao;
	
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
	public CallResult<List<EditorQuestionaireResponse>> getTaskQuestionaireList(int taskId) {
		LogUtils.info(this.getClass(), "getTaskQuestionaireList params : taskId = {}", taskId);
		try {
			//获取任务
			Task task = taskDao.selectById(taskId);
			
			//任务初始化
			if(task.getStatus() == Constants.TASK_STATUS_ASSIGNED) {
				try {
					this.initTask(task);
				}catch(Exception e) {
					LogUtils.error(this.getClass(), "getTaskQuestionaireList", e);
					return CallResult.failure("任务初始化失败");
				}
			}
			
			//获取任务问卷
			List<EditorQuestionaireResponse> editorQuestionaireResponseList = new ArrayList<EditorQuestionaireResponse>();
			Interview interview = interviewDao.selectById(task.getInterviewId());
			List<EditorQuestionaire> editorQuestionaireList = editorQuestionaireDao.getListByInterviewId(interview.getId());
			for(EditorQuestionaire editorQuestionaire : editorQuestionaireList) {
				EditorQuestionaireResponse editorQuestionaireResponse = new EditorQuestionaireResponse();
				editorQuestionaireResponse.setEditorQuestionaire(editorQuestionaire);
				Questionaire questionaire = questionaireDao.findByVersionAndCode(interview.getVersionId(), editorQuestionaire.getQuestionaireCode());
				editorQuestionaireResponse.setQuestionaire(questionaire);
				editorQuestionaireResponseList.add(editorQuestionaireResponse);
			}
			
			return CallResult.success(editorQuestionaireResponseList);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "getTaskQuestionaireList error", e);
			return CallResult.failure("获取问卷列表失败");
		}
	}
	
	/**
	 * 任务初始化
	 * @param task
	 */
	private void initTask(Task task) {
		
		//获取所有问卷
		Interview interview = interviewDao.selectById(task.getInterviewId());
		List<Questionaire> questionaireList = questionaireDao.findQuestionaireList(interview.getVersionId(), interview.getType());
		
		if(questionaireList != null && questionaireList.size() > 0) {
			this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					
					//添加问卷
					for(Questionaire questionaire : questionaireList) {
						EditorQuestionaire editorQuestionaire = new EditorQuestionaire();
						editorQuestionaire.setInterviewId(interview.getId());
						editorQuestionaire.setQuestionaireCode(questionaire.getCode());
						editorQuestionaire.setSeqNum(questionaire.getSeqNum());
						Date curQuestionaireTime = new Date();
						editorQuestionaire.setCreateTime(curQuestionaireTime);
						editorQuestionaire.setUpdateTime(curQuestionaireTime);
						
						//设置状态
						editorQuestionaire.setStatus(Constants.EDIT_QUESTIONAIRE_STATUS_INVALID);
						List<String> questionaireCodeList = sysqResultDao.getQuestionaireList(interview.getId());
						if(questionaireCodeList != null && questionaireCodeList.size() > 0) {
							if(questionaireCodeList.contains(questionaire.getCode())){
								editorQuestionaire.setStatus(Constants.EDIT_QUESTIONAIRE_STATUS_VALID);
							}
						}
						
						editorQuestionaireDao.insert(editorQuestionaire);
						
						//添加问题
						List<Question> questionList = questionDao.findByQuestionaireCode(interview.getVersionId(), questionaire.getCode());
						if(questionList != null && questionList.size() > 0) {
							for(Question question : questionList) {
								EditorQuestion editorQuestion = new EditorQuestion();
								editorQuestion.setInterviewId(interview.getId());
								editorQuestion.setQuestionaireCode(questionaire.getCode());
								editorQuestion.setQuestionCode(question.getCode());
								Date curQuestionTime = new Date();
								editorQuestion.setCreateTime(curQuestionTime);
								editorQuestion.setUpdateTime(curQuestionTime);
								editorQuestion.setSeqNum(question.getSeqNum());
								
								//设置状态
								editorQuestion.setStatus(Constants.EDIT_QUESTION_STATUS_INVALID);
								List<String> questionCodeList = sysqResultDao.getQuestionList(interview.getId(),questionaire.getCode());
								if(questionCodeList != null && questionCodeList.size() > 0) {
									if(questionCodeList.contains(question.getCode())){
										editorQuestion.setStatus(Constants.EDIT_QUESTION_STATUS_VALID);
									}
								}
								
								editorQuestionDao.insert(editorQuestion);
								
								//添加答案（有效问题）
								if(editorQuestion.getStatus() == Constants.EDIT_QUESTION_STATUS_VALID) {
									List<SysqResult> sysqResultList = sysqResultDao.getAnswerResultByQuestion(interview.getId(), questionaire.getCode(), question.getCode());
									if(sysqResultList != null && sysqResultList.size() > 0) {
										for(SysqResult sysqResult : sysqResultList) {
											SysqResult editorSysqResult = new SysqResult();
											try {
												BeanUtils.copyProperties(editorSysqResult, sysqResult);
												editorResultDao.insert(editorSysqResult);
											}catch(Exception e) {
												throw new RuntimeException(e);
											}
											
										}
									}
								}
							}
						}
					}
					LogUtils.info(this.getClass(), "initTask : init success");
					
					//修改任务状态
					task.setStatus(Constants.TASK_STATUS_EDITING);
					task.setUpdateTime(new Date());
					taskDao.update(task);
					LogUtils.info(this.getClass(), "initTask : update task status success");
				}
			});
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

	@Override
	public CallResult<Boolean> finishTask(int taskId) {
		LogUtils.info(this.getClass(), "finishTask params : taskId = {}", taskId);
		
		try {
			this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					Task task = taskDao.selectById(taskId);
					task.setStatus(Constants.TASK_STATUS_FINISHED);
					task.setUpdateTime(new Date());
					taskDao.update(task);
				}
			});
			return CallResult.success(true);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "finishTask error", e);
			return CallResult.failure("完成任务失败");
		}
	}

}
