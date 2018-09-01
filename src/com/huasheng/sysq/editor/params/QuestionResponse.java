package com.huasheng.sysq.editor.params;

import java.util.List;

import com.huasheng.sysq.editor.model.Question;

public class QuestionResponse {

	private Question question;
	private List<AnswerResponse> answerList;
	
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public List<AnswerResponse> getAnswerList() {
		return answerList;
	}
	public void setAnswerList(List<AnswerResponse> answerList) {
		this.answerList = answerList;
	}
}
