package com.huasheng.sysq.editor.params;

import com.huasheng.sysq.editor.model.Answer;
import com.huasheng.sysq.editor.model.SysqResult;

public class AnswerResponse {

	private Answer answer;
	private SysqResult result;
	
	public Answer getAnswer() {
		return answer;
	}
	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	public SysqResult getResult() {
		return result;
	}
	public void setResult(SysqResult result) {
		this.result = result;
	}
}
