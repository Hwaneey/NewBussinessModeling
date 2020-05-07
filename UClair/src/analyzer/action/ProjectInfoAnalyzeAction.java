package analyzer.action;

import java.awt.event.ActionEvent;

import analyzer.Analyzer;
import analyzer.AnalyzerEditorFactory;
import analyzer.views.projectinfo.ProjectInfoView;
import com.naru.uclair.project.Project;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.action.ProjectInfoAction.java
 * DESC   : 프로젝트 정보 분석 Action 클래스.
 *
 * references : 설계서 NARU-XXX-XXX-XXX
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
			
		}
	}

}
