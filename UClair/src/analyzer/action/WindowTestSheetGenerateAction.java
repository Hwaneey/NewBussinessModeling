package analyzer.action;

import java.awt.event.ActionEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import analyzer.Analyzer;
import com.naru.common.ui.NormalProgressWindow;
import analyzer.constants.AnalyzerConstants;
import analyzer.testcase.window.WindowTestSheetGenerator;
import com.naru.uclair.draw.common.HMIDrawingFilenameFilter;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 박보미
 * @설명  	: 화면 테스트 시트 생성(화면 테스트 케이스) Action 클래스
 * @변경이력 	: 
 ************************************************/

public class WindowTestSheetGenerateAction extends AbstractProjectRelatedAction implements PropertyChangeListener {
	

	private static final long serialVersionUID = 1L;
	private JFileChooser fileChooser;
	private NormalProgressWindow progressWindow;
	
	public WindowTestSheetGenerateAction(Analyzer alayzer) {
		super(alayzer);
		setEnabled(true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("WindowTestSheetGenerateAction");
		Analyzer analyzer = getAnalyzer();
		if(null == progressWindow ) {
			progressWindow = new NormalProgressWindow(analyzer);
			progressWindow.setModal(true);
		}
		progressWindow.setProgress(0);
		progressWindow.setLocationRelativeTo(analyzer);
		
		// 1. ȭ�� ��� ����.
		File windowResource = new File(getProject().getWindowResourcePath());
		String[] windowFileNames = windowResource.list(new HMIDrawingFilenameFilter());
		
		// 2. ȭ��� ����â ǥ��.
		// 2-1. ���� ����� ���� ��� ���ٰ� ǥ��.
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
		// TODO Auto-generated method stub
		
	}

}
