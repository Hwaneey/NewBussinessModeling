package analyzer.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.naru.common.ui.NormalProgressWindow;
import com.naru.uclair.analyzer.Analyzer;
import com.naru.uclair.analyzer.constants.AnalyzerConstants;
import com.naru.uclair.analyzer.testcase.window.WindowTestSheetGenerator;
import com.naru.uclair.draw.common.HMIDrawingFilenameFilter;

/**
 *
 * <pre>
 * NAME   : 
 * DESC   : 
 *
 * references : 설계서 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 7. 5.
 * @version 1.0
 *
 */
public class WindowTestSheetGenerateAction extends AbstractProjectRelatedAction implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFileChooser fileChooser;
	private NormalProgressWindow progressWindow;
	
	public WindowTestSheetGenerateAction(Analyzer analyzer) {
		super(analyzer);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Analyzer analyzer = getAnalyzer();
		if(null == progressWindow ) {
			progressWindow = new NormalProgressWindow(analyzer);
			progressWindow.setModal(true);
		}
		progressWindow.setProgress(0);
		progressWindow.setLocationRelativeTo(analyzer);
		
		// 1. 화면 목록 생성.
		File windowResource = new File(getProject().getWindowResourcePath());
		String[] windowFileNames = windowResource.list(new HMIDrawingFilenameFilter());
		
		// 2. 화면명 선택창 표시.
		// 2-1. 파일 목록이 없는 경우 없다고 표시.
		if(null != windowFileNames
				&& windowFileNames.length > 0) {
			final String selectWindowName = (String) JOptionPane.showInputDialog(analyzer, 
					AnalyzerConstants.getString("WindowTestSheetGenerate.InputDialog.Msg"),  //$NON-NLS-1$
					AnalyzerConstants.getString("WindowTestSheetGenerate.InputDialog.Title"),  //$NON-NLS-1$
					JOptionPane.PLAIN_MESSAGE, 
					null, 
					windowFileNames, windowFileNames[0]);
			
			if(null != selectWindowName) {
				if(null == fileChooser) {
					fileChooser = new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				}
				int state = fileChooser.showOpenDialog(analyzer);
				if(JFileChooser.APPROVE_OPTION == state) {
					final WindowTestSheetGenerator generator = WindowTestSheetGenerator.getInstance(getProject());
					generator.addPropertyChangeListeners(this);
					
					SwingWorker<String, String> worker = new SwingWorker<String, String>(){
						
						@Override
						protected String doInBackground() throws Exception {
							File saveDir = fileChooser.getSelectedFile();
							try {
								generator.generate(selectWindowName, saveDir);
								JOptionPane.showMessageDialog(getAnalyzer(), 
										AnalyzerConstants.getString("WindowTestSheetGenerate.CompleteMsg")); //$NON-NLS-1$
							} catch (Exception e) {
								JOptionPane.showMessageDialog(getAnalyzer(), 
										e.getMessage(), 
										AnalyzerConstants.getString("WindowTestSheetGenerate.Error.Title"),  //$NON-NLS-1$
										JOptionPane.ERROR_MESSAGE);
							}
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
			}
			
		}
		else {
			JOptionPane.showMessageDialog(analyzer, 
					AnalyzerConstants.getString("WindowTestSheetGenerate.Warning.Msg"),  //$NON-NLS-1$
					AnalyzerConstants.getString("WindowTestSheetGenerate.Warning.Title"),  //$NON-NLS-1$
					JOptionPane.WARNING_MESSAGE);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(WindowTestSheetGenerator.PROPERTY_NAME.equals(evt.getPropertyName())) {
			int progressValue = (Integer) evt.getNewValue();
			progressWindow.setProgress(progressValue);
			progressWindow.setText((String) evt.getOldValue());
		}
	}

}
