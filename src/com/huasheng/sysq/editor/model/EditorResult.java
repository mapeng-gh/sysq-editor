package com.huasheng.sysq.editor.model;

public class EditorResult {

	private int id;
	private int interviewId;
	private String questionaireCode;
	private String questionCode;
	private String answerCode;
	private String answerValue;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInterviewId() {
		return interviewId;
	}
	public void setInterviewId(int interviewId) {
		this.interviewId = interviewId;
	}
	public String getQuestionaireCode() {
		return questionaireCode;
	}
	public void setQuestionaireCode(String questionaireCode) {
		this.questionaireCode = questionaireCode;
	}
	public String getQuestionCode() {
		return questionCode;
	}
	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}
	public String getAnswerCode() {
		return answerCode;
	}
	public void setAnswerCode(String answerCode) {
		this.answerCode = answerCode;
	}
	public String getAnswerValue() {
		return answerValue;
	}
	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}
	
}
