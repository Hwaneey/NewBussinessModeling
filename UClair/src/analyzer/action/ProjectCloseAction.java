/**
 * 
 */
package analyzer.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import analyzer.Analyzer;
import analyzer.constants.AnalyzerConstants;
import com.naru.uclair.project.Project;

/**
 * Ubi-canvas 2.0 시스템 편집기에서 현재 편집 중인 프로젝트를 닫는데 사용되는 {@link ProjectCloseDialog}
 * 화면을 표시하는 action 클래스.<br>
 * 프로젝트를 닫기 위해 사용되는 {@link Developer} 정보를 설정한다.
 * 
 * @author yongwoo park
 * @version 1.0, 2010. 9. 10.
 * @since ubi-canvas 2.0
 * @see ProjectCloseDialog
 */
public class ProjectCloseAction extends AbstractProjectRelatedAction {

	private static final long serialVersionUID = 1L;

	private String PROJECT_CLOSE_MSG = AnalyzerConstants
			.getString("ProjectClose.Message");
	private String PROJECT_CLOSE_TITLE = AnalyzerConstants
			.getString("ProjectClose.Message.Title");

	/**
	 * {@link ProjectCloseAction} 의 생성자. {@link Developer} 정보를 전달받아 상위로 넘긴다.
	 * 
	 * @param dev
	 *            - ubi-canvas 2.0의 시스템 편집 정보
	 */
	public ProjectCloseAction(Analyzer anlayzer) {
		super(anlayzer);
		setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Analyzer analyzer = getAnalyzer();
		Project project = analyzer.getProject();
		if (project != null) {
			String projectName = project.getProjectConfiguration().getName();
			final int result = JOptionPane.showConfirmDialog(analyzer,
					PROJECT_CLOSE_MSG, PROJECT_CLOSE_TITLE,
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				getAnalyzer().closeProject();
				analyzer.closeProject();
			}
		}

	}
}
