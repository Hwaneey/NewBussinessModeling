package analyzer.util;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.jidesoft.document.DocumentComponent;
import com.jidesoft.document.DocumentPane;

import analyzer.AnalyzerEditorFactory;
import analyzer.frame.AnalyzerMainFrame;
import analyzer.frame.MessageFrame;

public class OpenView {
	private static DocumentPane _workspacePane = AnalyzerMainFrame._workspacePane;
	JComponent initializeUi;
	String viewkey;

	public void ResultView(JComponent initializeUi, String viewkey) {
		final Icon icon = AnalyzerEditorFactory.getFactory().getIcon(viewkey);
		final DocumentComponent document = new DocumentComponent(initializeUi, viewkey, viewkey, icon);

		if (_workspacePane.getDocument(viewkey) != null) {
			_workspacePane.setActiveDocument(viewkey);
		} else {
			_workspacePane.openDocument(document);
			_workspacePane.setActiveDocument(viewkey);
			MessageFrame.appendMessage(viewkey + " 창이 열렸습니다.");
		}
	}
}