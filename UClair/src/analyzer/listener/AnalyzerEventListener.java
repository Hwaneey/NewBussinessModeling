package analyzer.listener;

import com.naru.uclair.project.Project;

public interface AnalyzerEventListener {
	/**
	 * Developer에서 편집되는 Project가 New/Open/Close에 의해 변경될 경우 발생되는 이벤트.
	 * 
	 * @param newProject
	 */
	public void projectChanged(Project newProject);
}
