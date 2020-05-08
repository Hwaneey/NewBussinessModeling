package com.naru.uclair.analyzer.action;

import java.awt.event.ActionEvent;

import com.naru.uclair.analyzer.Analyzer;
import com.naru.uclair.analyzer.AnalyzerEditorFactory;
import com.naru.uclair.analyzer.views.projectinfo.ProjectInfoView;
import com.naru.uclair.project.Project;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.action.ProjectInfoAction.java
 * DESC   : ������Ʈ ���� �м� Action Ŭ����.
 *
 * references : ���輭 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory ywpark
 * @since 2012. 6. 20.
 * @version 1.0
 *
 */
public class ProjectInfoAnalyzeAction extends AbstractProjectRelatedAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProjectInfoAnalyzeAction(Analyzer analyzer) {
		super(analyzer);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		JOptionPane.showMessageDialog(getAnalyzer(), "TEST - ProjectInfoAnalyze Action");
		if(null != getAnalyzer().getProject()) {
			Project project = getAnalyzer().getProject();
			ProjectInfoView projectInfoView = (ProjectInfoView) AnalyzerEditorFactory.getFactory().getEditor(AnalyzerEditorFactory.PROJECT_INFO_EDITOR_KEY);
			if(null != projectInfoView) {
				projectInfoView.setText("");
				projectInfoView.setProject(project);
			}
			
			getAnalyzer().showWorkspace(AnalyzerEditorFactory.PROJECT_INFO_EDITOR_KEY);
		}
	}

}
