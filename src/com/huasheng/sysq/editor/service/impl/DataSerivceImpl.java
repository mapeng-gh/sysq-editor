package com.huasheng.sysq.editor.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huasheng.sysq.editor.dao.AnswerDao;
import com.huasheng.sysq.editor.dao.QuestionDao;
import com.huasheng.sysq.editor.dao.QuestionaireDao;
import com.huasheng.sysq.editor.dao.VersionDao;
import com.huasheng.sysq.editor.model.Answer;
import com.huasheng.sysq.editor.model.Question;
import com.huasheng.sysq.editor.model.Questionaire;
import com.huasheng.sysq.editor.model.Version;
import com.huasheng.sysq.editor.params.AnswerResponse;
import com.huasheng.sysq.editor.params.QuestionResponse;
import com.huasheng.sysq.editor.service.DataService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;

@Service
public class DataSerivceImpl implements DataService{
	
	@Autowired
	private VersionDao versionDao;
	
	@Autowired
	private QuestionaireDao questionaireDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private AnswerDao answerDao;

	@Override
	public CallResult<Page<Version>> findVersionPage(Map<String, Object> searchParams, int currentPage,int pageSize) {
		LogUtils.info(this.getClass(), "findVersionPage params : searchParams = {},currentPage = {},pageSize = {}", JsonUtils.toJson(searchParams),currentPage,pageSize);
		
		try {
			List<Version> versionList = versionDao.findVersionPage(searchParams, currentPage, pageSize);
			
			int total = versionDao.countVersion(searchParams);
			
			return CallResult.success(new Page<Version>(versionList,currentPage,pageSize,total));
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "findVersionPage error", e);
			return CallResult.failure("获取版本列表失败");
		}
	}

	@Override
	public CallResult<List<Questionaire>> getQuestionaireList(int versionId,int type) {
		LogUtils.info(this.getClass(), "getQuestionaireList params : versionId = {}", versionId);
		
		try {
			List<Questionaire> questionaireList = questionaireDao.selectListByType(versionId, type);
			return CallResult.success(questionaireList);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "getQuestionaireList error", e);
			return CallResult.failure("获取问卷列表失败");
		}
	}

	@Override
	public CallResult<List<QuestionResponse>> getQuestionList(int versionId, String questionaireCode) {
		LogUtils.info(this.getClass(), "getQuestionList params : versionId = {},questionaireCode = {}", versionId,questionaireCode);
		
		try {
			//查询问题
			List<Question> questionList = questionDao.selectListByQuestionaire(versionId, questionaireCode);
			if(questionList == null || questionList.size() == 0) {
				return CallResult.success(new ArrayList<QuestionResponse>());
			}
			
			List<QuestionResponse> questionResponseList = new ArrayList<QuestionResponse>();
			for(Question question : questionList) {
				QuestionResponse questionResponse = new QuestionResponse();
				questionResponse.setQuestion(question);
				
				//获取答案
				List<Answer> answerList = answerDao.selectListByQuestion(versionId,question.getCode());
				if(answerList == null || answerList.size() == 0) {
					questionResponse.setAnswerList(new ArrayList<AnswerResponse>());
				}else {
					List<AnswerResponse> answerResponseList = new ArrayList<AnswerResponse>();
					for(Answer answer : answerList) {
						AnswerResponse answerResponse = new AnswerResponse();
						answerResponse.setAnswer(answer);
						answerResponseList.add(answerResponse);
					}
					questionResponse.setAnswerList(answerResponseList);
				}
				
				questionResponseList.add(questionResponse);
			}
			
			return CallResult.success(questionResponseList);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "getQuestionList error", e);
			return CallResult.failure("获取问题列表失败");
		}
	}
	
}
