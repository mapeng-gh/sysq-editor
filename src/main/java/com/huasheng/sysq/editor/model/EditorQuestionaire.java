package com.huasheng.sysq.editor.model;

import java.util.Date;

public class EditorQuestionaire {

	private int id;
	private int interviewId;
	private String questionaireCode;
	private Date createTime;
	private Date updateTime;
	private int seqNum;
	
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}
	
}
