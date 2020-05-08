package analyzer.action;

import java.awt.event.ActionEvent;

import com.naru.uclair.analyzer.Analyzer;
import com.naru.uclair.analyzer.AnalyzerEditorFactory;
import com.naru.uclair.analyzer.ui.script.ScriptSyntaxAnalyzeDialog;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.action.ScriptSyntaxAnalyzeAction.java
 * DESC   : ��ũ��Ʈ ���� �м� Action Ŭ����.
 *
 * references : ���輭 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 20.
 * @version 1.0
 *
 */
public class ScriptSyntaxAnalyzeAction extends AbstractProjectRelatedAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ScriptSyntaxAnalyzeDialog analyzeDialog;

	public ScriptSyntaxAnalyzeAction(Analyzer dev) {
		super(dev);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Analyzer analyzer = getAnalyzer();
		if(null == analyzeDialog) {
			analyzeDialog = new ScriptSyntaxAnalyzeDialog(analyzer);
		}
		analyzeDialog.setLocationRelativeTo(analyzer);
		analyzeDialog.setVisible(true);
		
		// TODO �м�/��� Ȯ�� �� ǥ��
		analyzer.showWorkspace(AnalyzerEditorFactory.SCRIPT_SYNTAX_RESULT_VIEW_KEY);
	}

}
