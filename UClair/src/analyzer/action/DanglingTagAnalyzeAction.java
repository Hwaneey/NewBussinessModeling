package analyzer.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.naru.common.ui.NormalProgressWindow;

import analyzer.analysis.dangling.DanglingTagAnalyzer;
import analyzer.analysis.dangling.DanglingTagResult;
import analyzer.Analyzer;
import analyzer.AnalyzerEditorFactory;
import analyzer.constants.AnalyzerConstants;
import analyzer.ui.dangling.DanglingTagAnalyzeResultView;


/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 장성현
 * @설명  	: 존재하지않는 객체
 * @변경이력 	: 
 ************************************************/

public class DanglingTagAnalyzeAction extends AbstractProjectRelatedAction implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	private NormalProgressWindow progressWindow;


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
