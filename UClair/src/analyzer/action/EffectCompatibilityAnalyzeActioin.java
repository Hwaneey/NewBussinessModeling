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
import analyzer.analysis.effect.EffectCompatibilityAnalyzer;
import analyzer.analysis.effect.EffectCompatibilityResult;
import analyzer.constants.AnalyzerConstants;
import analyzer.ui.effect.EffectCompatibilityAnalyzeResultView;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.action.EffectCompatibilityAnalyzeActioin.java
 * DESC   : 객체 효과 양립성 분석 Action 클래스.
 *
 * references : 설계서 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 20.
 * @version 1.0
 *
 */
public class EffectCompatibilityAnalyzeActioin extends
		AbstractProjectRelatedAction implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NormalProgressWindow progressWindow;

	public EffectCompatibilityAnalyzeActioin(Analyzer dev) {
		super(dev);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int confirmState = JOptionPane.showConfirmDialog(getAnalyzer(), 
				AnalyzerConstants.getString("EffectCompatibilityAnalyze.ConfirmDialog.Msg"),  //$NON-NLS-1$
				AnalyzerConstants.getString("EffectCompatibilityAnalyze.ConfirmDialog.Title"),  //$NON-NLS-1$
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
		
		final EffectCompatibilityAnalyzeResultView resultView = (EffectCompatibilityAnalyzeResultView) AnalyzerEditorFactory
		.getFactory().getEditor(AnalyzerEditorFactory.EFFECT_COMPATIBILITY_RESULT_VIEW_KEY);
		
		final EffectCompatibilityAnalyzer compatibilityAnalyzer = EffectCompatibilityAnalyzer.getInstance(getProject());
		compatibilityAnalyzer.addPropertyChangeListeners(this);
		
		SwingWorker<String, String> worker = new SwingWorker<String, String>(){
			
			@Override
			protected String doInBackground() throws Exception {
				List<EffectCompatibilityResult> resultList = compatibilityAnalyzer.analyze();
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
		
		analyzer.showWorkspace(AnalyzerEditorFactory.EFFECT_COMPATIBILITY_RESULT_VIEW_KEY);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(EffectCompatibilityAnalyzer.PROPERTY_NAME.equals(evt.getPropertyName())) {
			int progressValue = (Integer) evt.getNewValue();
			progressWindow.setProgress(progressValue);
			progressWindow.setText((String) evt.getOldValue());
		}
	}

}
