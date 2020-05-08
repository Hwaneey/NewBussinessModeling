package com.naru.uclair.analyzer.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import com.naru.uclair.analyzer.Analyzer;
import com.naru.uclair.analyzer.AnalyzerEditorFactory;
import com.naru.uclair.analyzer.analysis.tag.PhysicalDepend;
import com.naru.uclair.analyzer.constants.AnalyzerConstants;
import com.naru.uclair.analyzer.ui.physical.PhysicalTagDependAnalyzeResultView;

public class PhysicalTagDependAction extends AbstractProjectRelatedAction {

	private static final long serialVersionUID = 1L;

	// private PhysicalTagDependAnalyzeDialog analyzeDialog;

	public PhysicalTagDependAction(Analyzer analyzer) {
		super(analyzer);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Analyzer analyzer = getAnalyzer();
		// if(null == analyzeDialog) {
		// analyzeDialog = new PhysicalTagDependAnalyzeDialog(analyzer);
		// }
		// analyzeDialog.setLocationRelativeTo(analyzer);
		// analyzeDialog.setVisible(true);

		int returnType = JOptionPane.showConfirmDialog(analyzer,
				AnalyzerConstants
						.getString("PhysicalTagDependAction.confirm.message"),
				AnalyzerConstants
						.getString("PhysicalTagDependAction.confirm.title"),
				JOptionPane.YES_NO_OPTION);
		if (returnType == JOptionPane.YES_OPTION) {
			if (null != getProject()) {
				PhysicalDepend depend = new PhysicalDepend();
				depend.analyzer(getProject().getTagDictionary());

				PhysicalTagDependAnalyzeResultView editor = (PhysicalTagDependAnalyzeResultView) AnalyzerEditorFactory
						.getFactory()
						.getEditor(
								AnalyzerEditorFactory.PHYSICAL_ADRESS_DEPENDENCY_RESULT_VIEW_KEY);
				if (null != editor) {
					((PhysicalTagDependAnalyzeResultView) editor)
							.setPhysicalDepend(depend);
				}
			}
			// TODO 분석/취소 결과 확인하여 표시.
			analyzer.showWorkspace(AnalyzerEditorFactory.PHYSICAL_ADRESS_DEPENDENCY_RESULT_VIEW_KEY);
		}

	}

}
