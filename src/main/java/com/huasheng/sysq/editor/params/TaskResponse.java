package com.huasheng.sysq.editor.params;

import com.huasheng.sysq.editor.model.Doctor;
import com.huasheng.sysq.editor.model.Interview;
import com.huasheng.sysq.editor.model.Patient;
import com.huasheng.sysq.editor.model.Task;
import com.huasheng.sysq.editor.model.User;

public class TaskResponse {

	private Task task;
	private User user;
	private Interview interview;
	private Patient patient;
	private Doctor doctor;
	
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public Interview getInterview() {
		return interview;
	}
	public void setInterview(Interview interview) {
		this.interview = interview;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
