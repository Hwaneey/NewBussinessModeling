package analyzer.util;

import javax.swing.JComponent;

import com.jidesoft.document.DocumentComponent;
import com.jidesoft.document.DocumentPane;

import analyzer.frame.AnalyzerMainFrame;

public class OpenView {
	private static DocumentPane _workspacePane = AnalyzerMainFrame._workspacePane;
	JComponent initializeUi;
	String viewkey;

	public void ResultView(JComponent initializeUi, String viewkey) {
		final DocumentComponent document = new DocumentComponent(initializeUi, viewkey);

		if (_workspacePane.getDocument(viewkey) != null) {
			_workspacePane.setActiveDocument(viewkey);
		} else {
			_workspacePane.openDocument(document);
			_workspacePane.setActiveDocument(viewkey);
		}

	}
}