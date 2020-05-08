package analyzer.action;

import java.awt.event.ActionEvent;

import com.naru.uclair.analyzer.Analyzer;
import com.naru.uclair.project.Project;


public class ShowEditorAction extends AbstractCommonAction {

	private String editorKey;
	
	public ShowEditorAction(Analyzer analyzer, String key) {
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
		getAnalyzer().showWorkspace(editorKey);
	}
}
