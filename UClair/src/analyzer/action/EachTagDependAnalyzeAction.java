package analyzer.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.jidesoft.document.DocumentPane;
import com.naru.common.ui.NormalProgressWindow;

import analyzer.Analyzer;
import analyzer.AnalyzerEditorFactory;
import analyzer.analysis.each.EachTagDependAnalyzer;
import analyzer.analysis.each.EachTagDependResult;
import analyzer.constants.AnalyzerConstants;
import analyzer.frame.AnalyzerMainFrame;
import analyzer.ui.each.EachTagDependAnalyzeResultView;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 이승환
 * @설명  	: 개별 태그 종속성 분석 Action 클래스.
 * @변경이력 	: 
 ************************************************/

public class EachTagDependAnalyzeAction extends AbstractProjectRelatedAction implements PropertyChangeListener {

	/**
	 * 객체 직렬화 버전 아이디.
	 */
	private static final long serialVersionUID = 1L;
	private NormalProgressWindow progressWindow;
	private String key;
	private Icon icon;
	private static DocumentPane _workspacePane =  AnalyzerMainFrame._workspacePane;		
	public EachTagDependAnalyzeAction(Analyzer analyzer) {
		super(analyzer);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int confirmState = JOptionPane.showConfirmDialog(getAnalyzer(), 
				AnalyzerConstants.getString("EachTagDependAnalyze.ConfirmDialog.Msg"),  //$NON-NLS-1$
				AnalyzerConstants.getString("EachTagDependAnalyze.ConfirmDialog.Title"),  //$NON-NLS-1$
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
		
		final EachTagDependAnalyzer eachTagDependAnalyzer = EachTagDependAnalyzer.getInstance(getProject());
		eachTagDependAnalyzer.addPropertyChangeListeners(this);

		final EachTagDependAnalyzeResultView resultView = (EachTagDependAnalyzeResultView) AnalyzerEditorFactory.getFactory().getEditor(AnalyzerEditorFactory.EACH_TAG_DEPENDENCY_RESULT_VIEW_KEY);
		
		SwingWorker<String, String> worker = new SwingWorker<String, String>(){
			
			@Override
			protected String doInBackground() throws Exception {
				List<EachTagDependResult> resultList = eachTagDependAnalyzer.analyze();
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
		if(EachTagDependAnalyzer.PROPERTY_NAME.equals(evt.getPropertyName())) {
			int progressValue = (Integer) evt.getNewValue();
			progressWindow.setProgress(progressValue);
			progressWindow.setText((String) evt.getOldValue());
		}
	}

}
