package analyzer.action;

import analyzer.Analyzer;
import com.naru.uclair.project.Project;

public abstract class AbstractProjectRelatedAction extends
		AbstractAnalyzerAction {

	private Project project;

	public AbstractProjectRelatedAction(Analyzer dev) {
		super(dev);
	}

	public Project getProject() {
		return project;
	}

	@Override
	public void setProject(Project p) {
		project = p;

		setEnabled(null != p);
	}
}
