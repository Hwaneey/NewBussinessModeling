package analyzer.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.naru.common.ui.NormalProgressWindow;
import analyzer.Analyzer;
import analyzer.AnalyzerEditorFactory;
import com.naru.uclair.analyzer.analysis.dangling.DanglingTagAnalyzer;
import com.naru.uclair.analyzer.analysis.dangling.DanglingTagResult;
import com.naru.uclair.analyzer.constants.AnalyzerConstants;
import com.naru.uclair.analyzer.ui.dangling.DanglingTagAnalyzeResultView;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.action.DanglingTagAnalyzeAction.java
 * DESC   : ��ü �±� �����Ӽ� �м� Action Ŭ����. 
 *
 * references : ���輭 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory ywpark
 * @since 2012. 6. 20.
 * @version 1.0
 *
 */
public class DanglingTagAnalyzeAction extends AbstractProjectRelatedAction implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NormalProgressWindow progressWindow;
	//private DanglingTagAnalzeDialog analzeDialog;

	public DanglingTagAnalyzeAction(Analyzer analyzer) {
		super(analyzer);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int confirmState = JOptionPane.showConfirmDialog(getAnalyzer(), 
				AnalyzerConstants.getString("DanglingTagAnalyze.ConfirmDialog.Msg"),  //$NON-NLS-1$
				AnalyzerConstants.getString("DanglingTagAnalyze.ConfirmDialog.Title"),  //$NON-NLS-1$
				JOptionPane.YES_NO_OPTION);
		if(JOptionPane.YES_OPTION != confirmState) {
			return;
		}
		
		Analyzer analyzer = getAnalyzer();
		if(null == progressWindow ) {
			progressWindow = new NormalProgressWindow(analyzer);
			progressWindow.setModal(true);
		}
		progressWindow.setProgress(0);
		progressWindow.setLocationRelativeTo(analyzer);
		
		final DanglingTagAnalyzer danglingTagAnalyzer = DanglingTagAnalyzer.getInstance(getProject());
		danglingTagAnalyzer.addPropertyChangeListeners(this);
		
		final DanglingTagAnalyzeResultView resultView = (DanglingTagAnalyzeResultView) AnalyzerEditorFactory
				.getFactory().getEditor(AnalyzerEditorFactory.DANGLING_TAG_RESULT_VIEW_KEY);
		
		SwingWorker<String, String> worker = new SwingWorker<String, String>(){
			
			@Override
			protected String doInBackground() throws Exception {
				List<DanglingTagResult> resultList = danglingTagAnalyzer.analyze();
				resultView.setAnalyzeResult(resultList);
				return null;
			}
			
			@Override
			protected void done() {
				progressWindow.dispose();
			}
		};
		worker.execute();
		progressWindow.setVisible(true);
		
		analyzer.showWorkspace(AnalyzerEditorFactory.DANGLING_TAG_RESULT_VIEW_KEY);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(DanglingTagAnalyzer.PROPERTY_NAME.equals(evt.getPropertyName())) {
			int progressValue = (Integer) evt.getNewValue();
			progressWindow.setProgress(progressValue);
			progressWindow.setText((String) evt.getOldValue());
		}
	}

}
