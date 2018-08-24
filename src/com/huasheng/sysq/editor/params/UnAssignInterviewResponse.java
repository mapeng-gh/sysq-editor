package com.huasheng.sysq.editor.params;

import com.huasheng.sysq.editor.model.Doctor;
import com.huasheng.sysq.editor.model.Interview;
import com.huasheng.sysq.editor.model.Patient;

public class UnAssignInterviewResponse {

	private Interview interview;
	private Patient patient;
	private Doctor doctor;
	
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
}
