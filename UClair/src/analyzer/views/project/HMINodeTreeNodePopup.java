/**
 * 
 */
package analyzer.views.project;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import analyzer.AnalyzerActionFactory;
import analyzer.constants.AnalyzerConstants;
import com.naru.uclair.node.HMINode;

/**
 * @author yongwoo Park
 * 
 */
public class HMINodeTreeNodePopup extends JPopupMenu {// implements

	// ActionListener{

	private static final long serialVersionUID = 1L;

	private static final String ADD = AnalyzerConstants
			.getString("HMINodeTreeNodePopup.AddNode"); //$NON-NLS-1$
	private static final String DELETE = AnalyzerConstants
			.getString("HMINodeTreeNodePopup.DeleteNode"); //$NON-NLS-1$
	private static final String PROPERTIES = AnalyzerConstants
			.getString("HMINodeTreeNodePopup.Properties"); //$NON-NLS-1$

	// private JMenuItem addMenuItem = new JMenuItem(ADD);
	// private JMenuItem deleteMenuItem = new JMenuItem(DELETE);
	// private JMenuItem propertieMenuItem = new JMenuItem(PROPERTIES);
	//	
	// private JTree projcetTree;
	// private TreeNode node;

	private HMINode targetNode;

	private ProjectView projectView;

	/**
	 * �ʿ信 ���� �Ķ���ʹ� ������ �� ����
	 * 
	 * @param node
	 */
	public HMINodeTreeNodePopup(ProjectView projectView, HMINode node) {
		super(AnalyzerConstants.getString("HMINodeTreeNodePopup.NodePopupMenu")); //$NON-NLS-1$
		this.projectView = projectView;
		this.targetNode = node;

		initialize();
	}

	private void initialize() {
		JMenuItem item;
		Action action;

		item = new JMenuItem();
		action = projectView.getAction(AnalyzerActionFactory.NEW_NODE);
		item.setAction(action);
		add(item);
		addSeparator();

		item = new JMenuItem();
		action = projectView.getAction(AnalyzerActionFactory.EDIT_NODE);
		action.setEnabled(null != targetNode);
		item.setAction(action);
		item.putClientProperty("edit node", targetNode);
		add(item);
		addSeparator();

		item = new JMenuItem();
		action = projectView.getAction(AnalyzerActionFactory.DELETE_NODE);
		action.setEnabled(null != targetNode);
		item.setAction(action);
		item.putClientProperty("delete node", targetNode);
		add(item);

	}

}
