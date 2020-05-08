package analyzer.action;

import javax.swing.AbstractAction;

import com.naru.common.NaruAssert;
import analyzer.Analyzer;
import analyzer.action.*;
import analyzer.listener.AnalyzerEventListener;
import com.naru.uclair.project.Project;

public abstract class AbstractAnalyzerAction extends AbstractAction implements
		AnalyzerEventListener {

	private Analyzer analyzer;

	public AbstractAnalyzerAction(Analyzer analyzer) {
		NaruAssert.isNotNull(analyzer);
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
