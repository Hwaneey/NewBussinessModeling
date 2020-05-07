package analyzer.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import analyzer.Analyzer;
import analyzer.AnalyzerEditorFactory;
import analyzer.analysis.tag.PhysicalDepend;
import analyzer.constants.AnalyzerConstants;
import analyzer.ui.physical.PhysicalTagDependAnalyzeResultView;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 지이삭
 * @설명  	: 존재하지않는 객체
 * @변경이력 	: 
 ************************************************/
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
	
		}

	}

}
