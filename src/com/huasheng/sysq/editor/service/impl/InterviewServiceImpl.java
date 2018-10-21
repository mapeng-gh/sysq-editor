package com.huasheng.sysq.editor.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huasheng.sysq.editor.dao.AnswerDao;
import com.huasheng.sysq.editor.dao.DoctorDao;
import com.huasheng.sysq.editor.dao.InterviewDao;
import com.huasheng.sysq.editor.dao.PatientDao;
import com.huasheng.sysq.editor.dao.QuestionDao;
import com.huasheng.sysq.editor.dao.QuestionaireDao;
import com.huasheng.sysq.editor.dao.SysqResultDao;
import com.huasheng.sysq.editor.dao.TaskDao;
import com.huasheng.sysq.editor.dao.UserDao;
import com.huasheng.sysq.editor.model.Answer;
import com.huasheng.sysq.editor.model.Interview;
import com.huasheng.sysq.editor.model.Question;
import com.huasheng.sysq.editor.model.Questionaire;
import com.huasheng.sysq.editor.model.SysqResult;
import com.huasheng.sysq.editor.model.Task;
import com.huasheng.sysq.editor.model.User;
import com.huasheng.sysq.editor.params.AnswerResponse;
import com.huasheng.sysq.editor.params.InterviewResponse;
import com.huasheng.sysq.editor.params.QuestionResponse;
import com.huasheng.sysq.editor.service.InterviewService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.Constants;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;

@Service
public class InterviewServiceImpl implements InterviewService{
	
	@Autowired
	private InterviewDao interviewDao;
	
	@Autowired
	private DoctorDao doctorDao;
	
	@Autowired
	private PatientDao patientDao;
	
	@Autowired
	private TaskDao taskDao;
	
	@Autowired
	private SysqResultDao sysqResultDao;
	
	@Autowired
	private QuestionaireDao questionaireDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private AnswerDao answerDao;
	
	@Autowired
	private UserDao userDao;

	@Override
	public CallResult<Page<InterviewResponse>> findUserInterviewPage(String loginName,Map<String,Object> searchParams,int currentPage,int pageSize) {
		LogUtils.info(this.getClass(), "findUserInterviewPage params : loginName = {},searchParams = {},currentPage = {},pageSize = {}",loginName,JsonUtils.toJson(searchParams),currentPage,pageSize);
		
		try {
			//获取用户类型
			User loginUser = userDao.selectByLoginName(loginName);
			if(loginUser.getUserType() == Constants.USER_TYPE_ADMIN) {
				loginName = "";
			}
			
			//查询访谈
			List<Interview> interviewList = interviewDao.findUserInterviewPage(loginName, searchParams, currentPage, pageSize);
			
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
			int total = interviewDao.countUserInterview(loginName, searchParams);
			
			return CallResult.success(new Page<InterviewResponse>(interviewResponseList,currentPage,pageSize,total));
			
		}catch(Exception e) {
			LogUtils.error(UserServiceImpl.class, "findUserInterviewPage error", e);
			return CallResult.failure("查找访谈失败");
		}
	}

	@Override
	public CallResult<List<Questionaire>> findQuestionaireListByInterviewId(int interviewId) {
		LogUtils.info(this.getClass(), "findQuestionaireListByInterviewId params : interviewId = {}", interviewId);
		
		try {
			//从答案表查询所有问卷编码
			List<String> questionaireCodeList = sysqResultDao.getQuestionaireList(interviewId);
			
			if(questionaireCodeList == null || questionaireCodeList.size() == 0) {
				return CallResult.success(new ArrayList<Questionaire>());
			}
			
			//查询问卷详细信息
			Interview interview = interviewDao.selectById(interviewId);
			List<Questionaire> questionaireList = questionaireDao.batchSelectByCode(interview.getVersionId(), questionaireCodeList);
			return CallResult.success(questionaireList);
			
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "findQuestionaireListByInterviewId error", e);
			return CallResult.failure("获取问卷列表失败");
		}
	}

	@Override
	public CallResult<List<QuestionResponse>> findQuestionListByInterviewIdAndQuestionaireCode(int interviewId,String questionaireCode) {
		LogUtils.info(this.getClass(), "findQuestionListByInterviewIdAndQuestionaireCode params : interviewId = {},questionaireCode = {}", interviewId,questionaireCode);
		
		try {
			Task task = taskDao.findByInterviewId(interviewId);
			if(task == null) {//访谈未编辑
				List<String> questionCodeList = sysqResultDao.getQuestionList(interviewId,questionaireCode);
				if(questionCodeList == null || questionCodeList.size() == 0) {
					return CallResult.success(new ArrayList<QuestionResponse>());
				}else {
					//获取问题列表
					Interview interview = interviewDao.selectById(interviewId);
					List<Question> questionList = questionDao.batchSelectByCode(interview.getVersionId(),questionCodeList);
					
					List<QuestionResponse> questionResponseList = new ArrayList<QuestionResponse>();
					for(Question question : questionList) {
						QuestionResponse questionResponse = new QuestionResponse();
						questionResponse.setQuestion(question);
						
						//获取答案列表
						List<String> answerCodeList = sysqResultDao.getAnswerList(interviewId, questionaireCode, question.getCode());
						List<Answer> answerList = answerDao.batchSelectByCode(interview.getVersionId(),answerCodeList);
						if(answerList == null || answerList.size() == 0) {
							questionResponse.setAnswerList(new ArrayList<AnswerResponse>());
						}else {
							List<AnswerResponse> answerResponseList = new ArrayList<AnswerResponse>();
							for(Answer answer : answerList) {
								AnswerResponse answerResponse = new AnswerResponse();
								answerResponse.setAnswer(answer);
								
								//获取结果
								SysqResult result = sysqResultDao.getAnswerResult(interviewId, questionaireCode, question.getCode(), answer.getCode());
								answerResponse.setResult(result);
								
								answerResponseList.add(answerResponse);
							}
							questionResponse.setAnswerList(answerResponseList);
						}
						
						questionResponseList.add(questionResponse);
					}
					return CallResult.success(questionResponseList);
				}
				
			}else {//访谈已编辑
				return CallResult.success(null);
			}
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "findQuestionListByInterviewIdAndQuestionaireCode error", e);
			return CallResult.failure("获取问题列表失败");
		}
	}
}
