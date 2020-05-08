package analyzer.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.jidesoft.swing.FolderChooser;
import com.naru.common.ui.NormalProgressWindow;
import analyzer.Analyzer;
import com.naru.uclair.analyzer.constants.AnalyzerConstants;
import com.naru.uclair.exception.ProjectNotLoadedException;
import com.naru.uclair.project.Project;
import com.naru.uclair.project.ProjectLoader;

public class ProjectLoadAction extends AbstractCommonAction implements
		PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	private NormalProgressWindow progressWindow = null;

	private String FILE_LOAD_MSG = AnalyzerConstants
			.getString("ProjectLoadAction.Load.File");
	private String DATABASE_LOAD_MSG = AnalyzerConstants
			.getString("ProjectLoadAction.Load.DB");
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
				folderChooser.setCurrentDirectory(selectedFolder
						.getParentFile());
			}
		}
		folderChooser.setFileHidingEnabled(true);

		int result = folderChooser.showOpenDialog(getAnalyzer());
		if (result == FolderChooser.APPROVE_OPTION) {
			selectedFolder = folderChooser.getSelectedFile();
			folderChooser.setVisible(false);

			// 2010. 3. 19 추가
			// 기존 프로젝트가 편집 중이고, 변경된 내용이 있을 경우에 저장/저장않음/취소를 선택하도록 한다.
			boolean openAccept = true;
			if (null != getAnalyzer().getProject()) {
				if (getAnalyzer().getProject().isChanged()) {
					int close = JOptionPane.showConfirmDialog(getAnalyzer(),
							"현재 편집중인 프로젝트가 변경되었습니다. 저장하시겠습니까?", "프로젝트 저장",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);

					switch (close) {
					case JOptionPane.YES_OPTION:
						getAnalyzer().saveProject();
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

				if (null == progressWindow) {
					progressWindow = new NormalProgressWindow(getAnalyzer());
					progressWindow.setModal(true);
					progressWindow.setText(AnalyzerConstants
							.getString("ProjectLoadAction.Load.Ready"));
				} else {
					progressWindow.setProgress(0);
					progressWindow.setText(AnalyzerConstants
							.getString("ProjectLoadAction.Load.Ready"));
				}
				progressWindow.setLocationRelativeTo(getAnalyzer());

				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						Project p = null;
						try {
							p = new Project(selectedFolder.toURI(),
									ProjectLoadAction.this);
						} catch (ProjectNotLoadedException e) {
							JOptionPane.showMessageDialog(getAnalyzer(), e
									.getMessage(), AnalyzerConstants
									.getString("ProjectLoadAction.Load.Fail"),
									JOptionPane.ERROR_MESSAGE);
							progressWindow.dispose();
						}
						getAnalyzer().setProject(p);
						return null;
					}
				};
				worker.execute();
				;
				progressWindow.setVisible(true);
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();

		if (ProjectLoader.PROJECT_LOAD_FILE.equals(propertyName)) {
			String msg = (String) evt.getOldValue();
			int progress = (((Integer) evt.getNewValue()) * 100)
					/ ProjectLoader.PROJECT_INFO_COUNT;
			progressWindow.setText(String.format(FILE_LOAD_MSG, msg, progress)
					+ "%");
			progressWindow.setProgress(progress);

			if (ProjectLoader.SAVE_LOAD_COMPLETE.equals(msg)) {
				progressWindow.setText(AnalyzerConstants
						.getString("ProjectLoadAction.Load.Complete"));
				// JOptionPane.showMessageDialog(getAnalyzer(),
				// ConstantsResource
				// .getString("ProjectLoadAction.Load.Complete"));
				progressWindow.dispose();
			}
		} else if (ProjectLoader.PROJECT_LOAD_DB.equals(propertyName)) {
			String msg = (String) evt.getOldValue();
			int progress = (((Integer) evt.getNewValue()) * 100)
					/ ProjectLoader.PROJECT_INFO_COUNT;
			progressWindow.setText(String.format(DATABASE_LOAD_MSG, msg,
					progress)
					+ "%");
			progressWindow.setProgress(progress);

			if (ProjectLoader.SAVE_LOAD_COMPLETE.equals(msg)) {
				progressWindow.setText(AnalyzerConstants
						.getString("ProjectLoadAction.Load.Complete"));
				// JOptionPane.showMessageDialog(getAnalyzer(),
				// ConstantsResource
				// .getString("ProjectLoadAction.Load.Complete"));
				progressWindow.dispose();
			}
		}
	}

}
