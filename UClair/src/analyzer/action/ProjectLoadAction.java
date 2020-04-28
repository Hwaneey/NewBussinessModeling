package analyzer.action;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import com.jidesoft.docking.DockableFrame;
import com.jidesoft.swing.JideScrollPane;
import com.naru.uclair.device.info.DeviceGroupList;
import com.naru.uclair.exception.ProjectNotLoadedException;
import com.naru.uclair.project.Project;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.math3.analysis.function.Add;

import analyzer.Analyzer;
import analyzer.constants.AnalyzerConstants;
import util.DefaultTree;

public class ProjectLoadAction extends AbstractCommonAction implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	/**
	 * folder chooser�� ���õ� ������Ʈ ����.
	 */
	private File selectedFolder;
	

	public ProjectLoadAction(Analyzer analyzer) {
		super(analyzer);
		setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("ProjectLoadAction ����");
		JFileChooser folderChooser = new JFileChooser();
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// ������ ���õ� ���� ����
		if (null != selectedFolder) {
			// ���õ� ������Ʈ ������ ������.
			if (selectedFolder.exists()) {
				folderChooser.setCurrentDirectory(selectedFolder);
			}
			// ���õ� ������Ʈ ������ ������.
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
							"���� �������� ������Ʈ�� ����Ǿ����ϴ�. �����Ͻðڽ��ϱ�?", "������Ʈ ����",
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
					System.out.println("���� �ε� ����");
					{
		
	
					}
					
					
				} catch (ProjectNotLoadedException e1) {
					JOptionPane.showMessageDialog(getAnalyzer(), 
							e1.getMessage(), AnalyzerConstants
							.getString("ProjectLoadAction.Load.Fail"),
							JOptionPane.ERROR_MESSAGE);					
					System.out.println("���� �ε� ����");

				}
				Analyzer a = getAnalyzer();
				a.setProject(p);
				System.out.println("ProjectLoadAction ���� ����");
			}
		}
	}




	public static JScrollPane createScrollPane(Component component) {
		JScrollPane pane = new JideScrollPane(component);
		pane.setVerticalScrollBarPolicy(JideScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return pane;
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

}
