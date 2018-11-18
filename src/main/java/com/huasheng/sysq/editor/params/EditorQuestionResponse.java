package com.huasheng.sysq.editor.params;

import java.util.List;

import com.huasheng.sysq.editor.model.EditorQuestion;
import com.huasheng.sysq.editor.model.Question;

public class EditorQuestionResponse {

	private EditorQuestion editorQuestion;
	private Question question;
	private List<AnswerResponse> answerResponseList;
	
	public EditorQuestion getEditorQuestion() {
		return editorQuestion;
	}
	public void setEditorQuestion(EditorQuestion editorQuestion) {
		this.editorQuestion = editorQuestion;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public List<AnswerResponse> getAnswerResponseList() {
		return answerResponseList;
	}
	public void setAnswerResponseList(List<AnswerResponse> answerResponseList) {
		this.answerResponseList = answerResponseList;
	}
}
