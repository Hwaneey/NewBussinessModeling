package analyzer.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.naru.uclair.exception.ProjectNotLoadedException;
import com.naru.uclair.project.Project;

import analyzer.Analyzer;
import analyzer.constants.AnalyzerConstants;

public class ProjectLoadAction extends AbstractCommonAction implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	/**
	 * folder chooser로 선택된 프로젝트 폴더.
	 */
	private File selectedFolder;
	

	public ProjectLoadAction(Analyzer analyzer) {
		super(analyzer);
		setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("ProjectLoadAction 수행");
		JFileChooser folderChooser = new JFileChooser();
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// 기존에 선택된 폴더 선택
		if (null != selectedFolder) {
			// 선택된 프로젝트 폴더가 있을때.
			if (selectedFolder.exists()) {
				folderChooser.setCurrentDirectory(selectedFolder);
			}
			// 선택된 프로젝트 폴더가 없을때.
			else if (selectedFolder.getParentFile().exists()) {
				folderChooser.setCurrentDirectory(selectedFolder.getParentFile());
			}
		}
		
		folderChooser.setFileHidingEnabled(true);
		
		int result = folderChooser.showOpenDialog(getAnalyzer());
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFolder = folderChooser.getSelectedFile();
			folderChooser.setVisible(false);
			
			boolean openAccept = true;
			if (null != getAnalyzer().getProject()) {
				if (getAnalyzer().getProject().isChanged()) {
					int close = JOptionPane.showConfirmDialog(getAnalyzer(),
							"현재 편집중인 프로젝트가 변경되었습니다. 저장하시겠습니까?", "프로젝트 저장",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);

					switch (close) {
					case JOptionPane.YES_OPTION:
						//getAnalyzer().saveProject();
						openAccept = true;
						break;
					case JOptionPane.NO_OPTION:
						openAccept = true;
						break;
					case JOptionPane.CANCEL_OPTION:
						openAccept = false;
						break;
					}
				}
			}
			
			if (openAccept) {
				getAnalyzer().closeProject();
				Project p = null;
				try {
					p = new Project(selectedFolder.toURI(),
							ProjectLoadAction.this);
					System.out.println("파일 로드 성공");
					{
						
					}
					
					
				} catch (ProjectNotLoadedException e1) {
					JOptionPane.showMessageDialog(getAnalyzer(), 
							e1.getMessage(), AnalyzerConstants
							.getString("ProjectLoadAction.Load.Fail"),
							JOptionPane.ERROR_MESSAGE);					
					System.out.println("파일 로드 실패");

				}
				Analyzer a = getAnalyzer();
				a.setProject(p);
				System.out.println("ProjectLoadAction 오류 없음");
			}
		}
	}
		
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

}
