package com.huasheng.sysq.editor.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huasheng.sysq.editor.dao.AnswerDao;
import com.huasheng.sysq.editor.dao.DoctorDao;
import com.huasheng.sysq.editor.dao.EditorQuestionDao;
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
	
	@Autowired
	private EditorQuestionDao editorQuestionDao;
	
	@Autowired
	private EditorResultDao editorResultDao;

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

	/**
	 * 获取原始问题列表
	 * @param interviewId
	 * @param questionaireCode
	 * @return
	 */
	private CallResult<List<QuestionResponse>> findOriginQuestionList(int interviewId,String questionaireCode) {
		LogUtils.info(this.getClass(), "findOriginQuestionList params : interviewId = {},questionaireCode = {}", interviewId,questionaireCode);
		
		//获取所有问题编码
		List<String> questionCodeList = sysqResultDao.getQuestionList(interviewId,questionaireCode);
		
		if(questionCodeList == null || questionCodeList.size() == 0) {
			return CallResult.success(new ArrayList<QuestionResponse>());
		}
		
		//获取问题详细信息
		Interview interview = interviewDao.selectById(interviewId);
		List<Question> questionList = questionDao.batchSelectByCode(interview.getVersionId(),questionCodeList);
		
		List<QuestionResponse> questionResponseList = new ArrayList<QuestionResponse>();
		for(Question question : questionList) {
			
			//构造响应数据
			QuestionResponse questionResponse = new QuestionResponse();
			questionResponse.setQuestion(question);
			questionResponseList.add(questionResponse);
			
			//获取所有答案编码
			List<String> answerCodeList = sysqResultDao.getAnswerList(interviewId, questionaireCode, question.getCode());
			
			if(answerCodeList == null || answerCodeList.size() <= 0) {
				questionResponse.setAnswerList(new ArrayList<AnswerResponse>());
				continue;
			}

			//获取答案详细信息
			List<Answer> answerList = answerDao.batchSelectByCode(interview.getVersionId(),answerCodeList);
			
			//构造答案数据
			List<AnswerResponse> answerResponseList = new ArrayList<AnswerResponse>();
			questionResponse.setAnswerList(answerResponseList);
			for(Answer answer : answerList) {
				AnswerResponse answerResponse = new AnswerResponse();
				answerResponse.setAnswer(answer);
				SysqResult result = sysqResultDao.getAnswerResult(interviewId, questionaireCode, question.getCode(), answer.getCode());
				answerResponse.setResult(result);
				answerResponseList.add(answerResponse);
			}
		}
		
		return CallResult.success(questionResponseList);
	}

	/**
	 * 获取编辑问题列表
	 * @param interviewId
	 * @param questionaireCode
	 * @return
	 */
	private CallResult<List<QuestionResponse>> findEditorQuestionList(int interviewId, String questionaireCode) {
		LogUtils.info(this.getClass(), "findEditorQuestionList params : interviewId = {},questionaireCode = {}", interviewId,questionaireCode);
		
		//获取有效编辑问题
		List<String> questionCodeList = new ArrayList<String>();
		List<EditorQuestion> editorQuestionList = editorQuestionDao.selectListByQuestionaire(interviewId, questionaireCode);
		if(editorQuestionList == null || editorQuestionList.size() <= 0) {
			return CallResult.success(new ArrayList<QuestionResponse>());
		}
		for(EditorQuestion editorQuestion : editorQuestionList) {
			if(editorQuestion.getStatus() == Constants.EDIT_QUESTION_STATUS_VALID) {
				questionCodeList.add(editorQuestion.getQuestionCode());
			}
		}
		
		//获取问题详细信息
		Interview interview = interviewDao.selectById(interviewId);
		List<Question> questionList = questionDao.batchSelectByCode(interview.getVersionId(),questionCodeList);
		
		List<QuestionResponse> questionResponseList = new ArrayList<QuestionResponse>();
		for(Question question : questionList) {
			
			//构造响应数据
			QuestionResponse questionResponse = new QuestionResponse();
			questionResponse.setQuestion(question);
			questionResponseList.add(questionResponse);
			
			//获取所有答案编码
			List<String> answerCodeList = editorResultDao.getAnswerList(interviewId, questionaireCode, question.getCode());
			
			if(answerCodeList == null || answerCodeList.size() <= 0) {
				questionResponse.setAnswerList(new ArrayList<AnswerResponse>());
				continue;
			}

			//获取答案详细信息
			List<Answer> answerList = answerDao.batchSelectByCode(interview.getVersionId(),answerCodeList);
			
			//构造答案数据
			List<AnswerResponse> answerResponseList = new ArrayList<AnswerResponse>();
			questionResponse.setAnswerList(answerResponseList);
			for(Answer answer : answerList) {
				AnswerResponse answerResponse = new AnswerResponse();
				answerResponse.setAnswer(answer);
				SysqResult result = editorResultDao.selectByAnswerCode(interviewId, questionaireCode, question.getCode(), answer.getCode());
				answerResponse.setResult(result);
				answerResponseList.add(answerResponse);
			}
		}
		
		return CallResult.success(questionResponseList);
	}

	@Override
	public CallResult<List<QuestionResponse>> findQuestionList(int interviewId, String questionaireCode) {
		LogUtils.info(this.getClass(), "getQuestionList params : interviewId = {} , questionaireCode = {}", interviewId , questionaireCode);
		
		try {
			//查询访谈编辑状态
			Task task = taskDao.findByInterviewId(interviewId);
			
			if(task == null) {//未开始编辑
				return this.findOriginQuestionList(interviewId, questionaireCode);
			}
			
			//已开始编辑
			return this.findEditorQuestionList(interviewId, questionaireCode);
			
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "getQuestionList error", e);
			return CallResult.failure("获取问题列表失败");
		}
	}
}
