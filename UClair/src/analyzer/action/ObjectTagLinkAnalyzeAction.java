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
import analyzer.analysis.linked.ObjectTagLinkAnalyzer;
import analyzer.analysis.linked.ObjectTagLinkResult;
import analyzer.constants.AnalyzerConstants;
import analyzer.ui.linked.ObjectTagLinkAnalyzeResultView;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 이승환
 * @설명  	: 객체 태그 연결 정보 분석 Action 클래스.
 * @변경이력 	: 
 ************************************************/

public class ObjectTagLinkAnalyzeAction extends AbstractProjectRelatedAction implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NormalProgressWindow progressWindow;
	//private ObjectTagLinkAnalyzeDialog analyzeDialog;

	public ObjectTagLinkAnalyzeAction(Analyzer dev) {
		super(dev);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int confirmState = JOptionPane.showConfirmDialog(getAnalyzer(), 
				AnalyzerConstants.getString("ObjectTagLinkAnalyze.ConfirmDialog.Msg"),  //$NON-NLS-1$
				AnalyzerConstants.getString("ObjectTagLinkAnalyze.ConfirmDialog.Title"),  //$NON-NLS-1$
				JOptionPane.YES_NO_OPTION);
		if(JOptionPane.YES_OPTION != confirmState) {
			return;
		}
		
		if(null == progressWindow ) {
			progressWindow = new NormalProgressWindow(getAnalyzer());
			progressWindow.setModal(true);
		}
		progressWindow.setProgress(0);
		progressWindow.setLocationRelativeTo(getAnalyzer());
		
		Analyzer analyzer = getAnalyzer();

		final ObjectTagLinkAnalyzeResultView resultView = (ObjectTagLinkAnalyzeResultView) AnalyzerEditorFactory
				.getFactory().getEditor(AnalyzerEditorFactory.OBJECT_TAG_LINK_RESULT_VIEW_KEY);
		
		// TEST
		final ObjectTagLinkAnalyzer tagLinkAnalyzer =  ObjectTagLinkAnalyzer.getInstance(getProject());
		tagLinkAnalyzer.addPropertyChangeListeners(this);
		
		SwingWorker<String, String> worker = new SwingWorker<String, String>(){
			
			@Override
			protected String doInBackground() throws Exception {
				List<ObjectTagLinkResult> resultList = tagLinkAnalyzer.analyze();
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
		// TODO Auto-generated method stub
		if(ObjectTagLinkAnalyzer.PROPERTY_NAME.equals(evt.getPropertyName())) {
			int progressValue = (Integer) evt.getNewValue();
			progressWindow.setProgress(progressValue);
			progressWindow.setText((String) evt.getOldValue());
		}
	}

}
