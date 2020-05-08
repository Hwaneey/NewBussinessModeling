/**
 * 
 */
package com.naru.uclair.analyzer.views.project;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.naru.uclair.analyzer.AnalyzerActionFactory;
import com.naru.uclair.analyzer.constants.AnalyzerConstants;
import com.naru.uclair.database.Database;

/**
 * @author yongwoo Park
 * 
 */
public class DatabaseTreeNodePopup extends JPopupMenu {

	private static final long serialVersionUID = 1L;

	private static final String ADD = AnalyzerConstants
			.getString("DatabaseTreeNodePopup.AddDatabase"); //$NON-NLS-1$
	private static final String DELETE = AnalyzerConstants
			.getString("DatabaseTreeNodePopup.DeleteDatabase"); //$NON-NLS-1$
	private static final String PROPERTIES = AnalyzerConstants
			.getString("HMINodeTreeNodePopup.Properties"); //$NON-NLS-1$

	private Database targetNode;

	private ProjectView projectView;

	/**
	 * 필요에 의해 파라미터는 변경할 수 있음
	 * 
	 * @param node
	 */
	public DatabaseTreeNodePopup(ProjectView projectView, Database database) {
		super(AnalyzerConstants.getString("HMINodeTreeNodePopup.NodePopupMenu")); //$NON-NLS-1$
		this.projectView = projectView;
		this.targetNode = database;

		initialize();
	}

	private void initialize() {
		JMenuItem item;
		Action action;

		item = new JMenuItem();
		action = projectView.getAction(AnalyzerActionFactory.NEW_DATABASE);
		item.setAction(action);
		add(item);
		addSeparator();

		item = new JMenuItem();
		action = projectView.getAction(AnalyzerActionFactory.EDIT_DATABASE);
		action.setEnabled(null != targetNode);
		item.setAction(action);
		item.putClientProperty("edit database", targetNode);
		add(item);
		addSeparator();

		item = new JMenuItem();
		action = projectView.getAction(AnalyzerActionFactory.DELETE_DATABASE);
		action.setEnabled(null != targetNode);
		item.setAction(action);
		item.putClientProperty("delete database", targetNode);
		add(item);

	}

}
