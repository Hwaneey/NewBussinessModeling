package com.naru.uclair.analyzer.action;

import java.awt.event.ActionEvent;

import com.naru.uclair.analyzer.Analyzer;
import com.naru.uclair.analyzer.views.projectinfo.ProjectInfoView;
import com.naru.uclair.project.Project;


public class ShowProjectInfoEditorAction extends AbstractCommonAction {

	private String editorKey;
	
	public ShowProjectInfoEditorAction(Analyzer analyzer, String key) {
		super(analyzer);
		editorKey = key;
	}
	
	@Override
	public void setProject(Project p) {
		super.setProject(p);
		
		setEnabled(null != p);
	}
	
	/*
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ProjectInfoView viewer = (ProjectInfoView)getAnalyzer().getEditor(editorKey);
		viewer.setProject(this.getAnalyzer().getProject());
		// 에디터를 활성화 한다.
		getAnalyzer().showWorkspace(editorKey);
	}
}
