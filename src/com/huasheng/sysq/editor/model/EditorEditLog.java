package com.huasheng.sysq.editor.model;

import java.util.Date;

public class EditorEditLog {

	private int id;
	private String loginName;
	private String name;
	private int interviewId;
	private int versionId;
	private String questionaireCode;
	private String questionaireTitle;
	private String questionCode;
	private int operateType;
	private String beforeValue;
	private String afterValue;
	private Date editTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getInterviewId() {
		return interviewId;
	}
	public void setInterviewId(int interviewId) {
		this.interviewId = interviewId;
	}
	public int getVersionId() {
		return versionId;
	}
	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}
	public String getQuestionaireCode() {
		return questionaireCode;
	}
	public void setQuestionaireCode(String questionaireCode) {
		this.questionaireCode = questionaireCode;
	}
	public String getQuestionaireTitle() {
		return questionaireTitle;
	}
	public void setQuestionaireTitle(String questionaireTitle) {
		this.questionaireTitle = questionaireTitle;
	}
	public String getQuestionCode() {
		return questionCode;
	}
	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}
	public int getOperateType() {
		return operateType;
	}
	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}
	public String getBeforeValue() {
		return beforeValue;
	}
	public void setBeforeValue(String beforeValue) {
		this.beforeValue = beforeValue;
	}
	public String getAfterValue() {
		return afterValue;
	}
	public void setAfterValue(String afterValue) {
		this.afterValue = afterValue;
	}
	public Date getEditTime() {
		return editTime;
	}
	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}
}
