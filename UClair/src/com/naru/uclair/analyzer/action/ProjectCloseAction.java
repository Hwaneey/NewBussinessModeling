/**
 * 
 */
package com.naru.uclair.analyzer.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import com.naru.uclair.analyzer.Analyzer;
import com.naru.uclair.analyzer.constants.AnalyzerConstants;
import com.naru.uclair.project.Project;

/**
 * Ubi-canvas 2.0 �ý��� �����⿡�� ���� ���� ���� ������Ʈ�� �ݴµ� ���Ǵ� {@link ProjectCloseDialog}
 * ȭ���� ǥ���ϴ� action Ŭ����.<br>
 * ������Ʈ�� �ݱ� ���� ���Ǵ� {@link Developer} ������ �����Ѵ�.
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
	 * {@link ProjectCloseAction} �� ������. {@link Developer} ������ ���޹޾� ������ �ѱ��.
	 * 
	 * @param dev
	 *            - ubi-canvas 2.0�� �ý��� ���� ����
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
				analyzer.closeProject();
			}
		}

	}
}
