package com.huasheng.sysq.editor.model;

import java.util.Date;

public class Task {

	private int id;
	private int userId;
	private int interviewId;
	private Date createTime;
	private Date updateTime;
	
	public Task() {
	}
	
	public Task(int userId,int interviewId,Date createTime,Date updateTime) {
		this.userId = userId;
		this.interviewId = interviewId;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getInterviewId() {
		return interviewId;
	}
	public void setInterviewId(int interviewId) {
		this.interviewId = interviewId;
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
}
