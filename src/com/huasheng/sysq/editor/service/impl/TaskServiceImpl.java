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

import com.huasheng.sysq.editor.dao.AnswerDao;
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
import com.huasheng.sysq.editor.model.Answer;
import com.huasheng.sysq.editor.model.EditorQuestion;
import com.huasheng.sysq.editor.model.EditorQuestionaire;
import com.huasheng.sysq.editor.model.Interview;
import com.huasheng.sysq.editor.model.Question;
import com.huasheng.sysq.editor.model.Questionaire;
import com.huasheng.sysq.editor.model.SysqResult;
import com.huasheng.sysq.editor.model.Task;
import com.huasheng.sysq.editor.params.AnswerResponse;
import com.huasheng.sysq.editor.params.EditorQuestionResponse;
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
	
	@Autowired
	private AnswerDao answerDao;
	
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
				Questionaire questionaire = questionaireDao.selectByCode(interview.getVersionId(), editorQuestionaire.getQuestionaireCode());
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
		List<Questionaire> questionaireList = questionaireDao.selectListByType(interview.getVersionId(), interview.getType());
		
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
						List<Question> questionList = questionDao.selectListByQuestionaire(interview.getVersionId(), questionaire.getCode());
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

	@Override
	public CallResult<Boolean> enableQuestionaire(int taskId, String questionaireCode) {
		LogUtils.info(this.getClass(), "enableQuestionaire params : taskId = {},questionaireCode = {}", taskId,questionaireCode);
		try {
			this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					Task task = taskDao.selectById(taskId);
					EditorQuestionaire editorQuestionaire = editorQuestionaireDao.selectByInterviewAndQuestionaire(task.getInterviewId(), questionaireCode);
					editorQuestionaire.setStatus(Constants.EDIT_QUESTIONAIRE_STATUS_VALID);
					Date curTime = new Date();
					editorQuestionaire.setUpdateTime(curTime);
					editorQuestionaireDao.update(editorQuestionaire);
					
					//更新任务
					task.setUpdateTime(curTime);
					taskDao.update(task);
				}
			});
			return CallResult.success(true);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "enableQuestionaire error", e);
			return CallResult.failure("启用问卷失败");
		}
	}

	@Override
	public CallResult<Boolean> disableQuestionaire(int taskId, String questionaireCode) {
		LogUtils.info(this.getClass(), "disableQuestionaire params : taskId = {},questionaireCode = {}", taskId,questionaireCode);
		try {
			this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					Task task = taskDao.selectById(taskId);
					EditorQuestionaire editorQuestionaire = editorQuestionaireDao.selectByInterviewAndQuestionaire(task.getInterviewId(), questionaireCode);
					editorQuestionaire.setStatus(Constants.EDIT_QUESTIONAIRE_STATUS_INVALID);
					Date curTime = new Date();
					editorQuestionaire.setUpdateTime(curTime);
					editorQuestionaireDao.update(editorQuestionaire);
					
					//更新任务
					task.setUpdateTime(curTime);
					taskDao.update(task);
				}
			});
			return CallResult.success(true);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "disableQuestionaire error", e);
			return CallResult.failure("禁用问卷失败");
		}
	}

	@Override
	public CallResult<List<EditorQuestionResponse>> getTaskQuestionList(int taskId, String questionaireCode) {
		LogUtils.info(this.getClass(), "getTaskQuestionList params : taskId = {},questionaireCode = {}", taskId,questionaireCode);
		
		try {
			//获取任务问题
			Task task = taskDao.selectById(taskId);
			Interview interview = interviewDao.selectById(task.getInterviewId());
			List<EditorQuestion> editorQuestionList = editorQuestionDao.selectListByQuestionaire(task.getInterviewId(), questionaireCode);
			
			List<EditorQuestionResponse> editorQuestionResponseList = new ArrayList<EditorQuestionResponse>();
			
			//数据封装
			if(editorQuestionList != null && editorQuestionList.size() > 0) {
				for(EditorQuestion editorQuestion : editorQuestionList) {
					EditorQuestionResponse editorQuestionResponse = new EditorQuestionResponse();
					editorQuestionResponse.setEditorQuestion(editorQuestion);
					
					//设置问题
					Question question = questionDao.selectByCode(interview.getVersionId(),editorQuestion.getQuestionCode());
					editorQuestionResponse.setQuestion(question);
					
					//设置答案
					List<AnswerResponse> answerResponseList = new ArrayList<AnswerResponse>();
					List<Answer> answerList = answerDao.selectListByQuestion(interview.getVersionId(),editorQuestion.getQuestionCode());
					if(answerList != null && answerList.size() > 0) {
						for(Answer answer : answerList) {
							AnswerResponse answerResponse = new AnswerResponse();
							answerResponse.setAnswer(answer);
							SysqResult editorResult = editorResultDao.selectByAnswerCode(interview.getId(), questionaireCode, editorQuestion.getQuestionCode(), answer.getCode());
							answerResponse.setResult(editorResult);
							answerResponseList.add(answerResponse);
						}
					}
					
					editorQuestionResponse.setAnswerResponseList(answerResponseList);
					
					editorQuestionResponseList.add(editorQuestionResponse);
				}
			}
			
			return CallResult.success(editorQuestionResponseList);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "getTaskQuestionList error", e);
			return CallResult.failure("获取问题列表失败");
		}
	}

	@Override
	public CallResult<Boolean> enableQuestion(int taskId, String questionaireCode, String questionCode) {
		LogUtils.info(this.getClass(), "enableQuestion params : taskId = {} , questionaireCode = {} , questionCode = {}", taskId,questionaireCode,questionCode);
		try {
			this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					Task task = taskDao.selectById(taskId);
					EditorQuestion editorQuestion = editorQuestionDao.selectByCode(task.getInterviewId(), questionaireCode, questionCode);
					editorQuestion.setStatus(Constants.EDIT_QUESTION_STATUS_VALID);
					Date curTime = new Date();
					editorQuestion.setUpdateTime(curTime);
					editorQuestionDao.update(editorQuestion);
					
					//更新任务
					task.setUpdateTime(curTime);
					taskDao.update(task);
				}
			});
			return CallResult.success(true);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "enableQuestion error", e);
			return CallResult.failure("启动问题失败");
		}
	}

	@Override
	public CallResult<Boolean> disableQuestion(int taskId, String questionaireCode, String questionCode) {
		LogUtils.info(this.getClass(), "disableQuestion params : taskId = {} , questionaireCode = {} , questionCode = {}", taskId,questionaireCode,questionCode);
		try {
			this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					Task task = taskDao.selectById(taskId);
					EditorQuestion editorQuestion = editorQuestionDao.selectByCode(task.getInterviewId(), questionaireCode, questionCode);
					editorQuestion.setStatus(Constants.EDIT_QUESTION_STATUS_INVALID);
					Date curTime = new Date();
					editorQuestion.setUpdateTime(curTime);
					editorQuestionDao.update(editorQuestion);
					
					//更新任务
					task.setUpdateTime(curTime);
					taskDao.update(task);
				}
			});
			return CallResult.success(true);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "disableQuestion error", e);
			return CallResult.failure("禁用问题失败");
		}
	}

}
