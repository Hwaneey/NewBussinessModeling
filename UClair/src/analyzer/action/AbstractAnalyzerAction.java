package analyzer.action;

import javax.swing.AbstractAction;

import com.naru.uclair.project.Project;

import analyzer.Analyzer;
import analyzer.listener.AnalyzerEventListener;

public abstract class AbstractAnalyzerAction extends AbstractAction implements
AnalyzerEventListener {

	
	private Analyzer analyzer;
	
	public AbstractAnalyzerAction(Analyzer analyzer) {
		
		this.analyzer = analyzer;

		setProject(this.analyzer.getProject());

		setEnabled(false);
		this.analyzer.addAnalyzerEventListener(this);
	}
	
	public Analyzer getAnalyzer() {
		return this.analyzer;
	}

	public void setProject(Project p) {
	}
	
	@Override
	public void projectChanged(Project newProject) {
		setProject(newProject);
	}
}
