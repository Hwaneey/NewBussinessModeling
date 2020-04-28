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

public class ProjectCloseAction extends AbstractCommonAction implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	public ProjectCloseAction(Analyzer analyzer) {
		super(analyzer);
		setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("ProjectCloseAction 수행");
		getAnalyzer().closeProject();
		System.out.println("ProjectCloseAction 오류 없음");
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

}
