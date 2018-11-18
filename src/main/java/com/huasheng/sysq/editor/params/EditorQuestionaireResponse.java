package com.huasheng.sysq.editor.params;

import com.huasheng.sysq.editor.model.EditorQuestionaire;
import com.huasheng.sysq.editor.model.Questionaire;

public class EditorQuestionaireResponse {

	private EditorQuestionaire editorQuestionaire;
	private Questionaire questionaire;
	
	public EditorQuestionaire getEditorQuestionaire() {
		return editorQuestionaire;
	}
	public void setEditorQuestionaire(EditorQuestionaire editorQuestionaire) {
		this.editorQuestionaire = editorQuestionaire;
	}
	public Questionaire getQuestionaire() {
		return questionaire;
	}
	public void setQuestionaire(Questionaire questionaire) {
		this.questionaire = questionaire;
	}
}
