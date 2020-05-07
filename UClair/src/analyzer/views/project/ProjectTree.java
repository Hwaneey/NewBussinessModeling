package analyzer.views.project;

import java.awt.Component;
import java.io.File;
import java.util.Hashtable;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

//import analyzer.AnalyzerIconFactory;
import com.naru.uclair.project.Project;

/**
 * Title: Description: Copyright: Copyright (c) 2001 Company:
 * 
 * @author
 * @version 1.0
 */
public class ProjectTree extends JTree {

	// public static final Font DEFAULT_FONT = new Font("SanSerif", 0, 12);

	// ////////////////////////////////////////////////////////////////////////
	// Ʈ�� ���� ��� ���� ����.
	// ////////////////////////////////////////////////////////////////////////
	private ProjectTreeModel model = null;
	// ////////////////////////////////////////////////////////////////////////
	// Ʈ�� ���� ��� ���� ��.
	// ////////////////////////////////////////////////////////////////////////

	private WindowTreeNode selectedWindowNode = null;

	public ProjectTree() {
		this(null);
	}

	public ProjectTree(Project p) {
		initComponent();
		setCellRenderer(new ProjectTreeRenderer());
		setProject(p);
	}

	private void initComponent() {
		model = new ProjectTreeModel();
		this.setModel(model);
		// this.addMouseListener(new MouseAdapter() {
		// public void mouseClicked(MouseEvent me) {
		// TreePath tp = getSelectionPath();
		// if (tp != null) {
		// int modifier = me.getModifiers();
		// MutableTreeNode treeNode = (MutableTreeNode)
		// tp.getLastPathComponent();
		//
		// if ((treeNode == model.getRoot()) && (me.getClickCount() == 2)) {
		// ProjectConfigurationManager manager = new
		// ProjectConfigurationManager();
		// manager.setVisible(true);
		// return;
		// }
		// ProjectTree.this.setSelectionPath(tp);
		// if (treeNode instanceof WindowTreeNode) {
		// selectedWindowNode = (WindowTreeNode) treeNode;
		// if (modifier == MouseEvent.BUTTON3_MASK) {
		// windowTreeNodePopup = new WindowTreeNodePopup(ProjectTree.this,
		// me.getPoint(), (WindowTreeNode) treeNode);
		// }
		// }
		// else if (treeNode instanceof WindowNameNode) {
		// selectedWindowNode = (WindowTreeNode) treeNode.getParent();
		// if (me.getModifiers() == MouseEvent.BUTTON3_MASK) {
		// windowNameNodePopup = new WindowNameNodePopup(ProjectTree.this,
		// me.getPoint(), (WindowNameNode) treeNode);
		// }
		// else if (me.getClickCount() == 2) {
		// openWindow((File) ((WindowNameNode)treeNode).getUserObject());
		// }
		// }
		// else {
		// selectedWindowNode = null;
		// if (me.getClickCount() == 2) {
		// // if (treeNode instanceof TagDictionaryTreeNode) {
		// // builder.showTagDictionary();
		// // }
		// // else if (treeNode instanceof AlarmTreeNode) {
		// // builder.showEventConfig();
		// // }
		// // else if (treeNode instanceof TrendModelTreeNode) {
		// // builder.showCollectionModel();
		// // }
		// if (treeNode instanceof DatabaseTreeNode) {
		// // builder.showDatabaseConfig();
		// }
		// else if (treeNode instanceof NetworkTreeNode) {
		// // builder.showNetworkConfig();
		// }
		// else if (treeNode instanceof DeviceTreeNode) {
		// // builder.showDeviceConfig();
		// }
		// else if (treeNode instanceof ScriptTreeNode) {
		// // builder.showScriptEditor();
		// }
		// // else if (treeNode instanceof UserTreeNode) {
		// // builder.showUserConfig();
		// // }
		// }
		// }
		// }
		// }
		// });
	}

	public void createWindow(File baseFolder) {
		// builder.create();
	}

	/**
	 * �Ķ���ͷ� ������ ȭ�� ������ ������ Builder���� ��û�Ѵ�.
	 * 
	 * @param windowFile
	 *            File
	 */
	public void openWindow(File windowFile) {
		// builder.open(windowFile);
	}

	/**
	 * �Ķ���ͷ� ������ ȭ���� ���������� �����ϰ�, ������ ��������� WindowTreeNode�� �����Ѵ�.
	 * 
	 * @param windowFile
	 *            File
	 */
	public void deleteWindow(File windowFile) {
		// WindowTreeNode node =
		// model.getWindowTreeNode(windowFile.getParentFile().getName());
		// if (windowFile.exists()) {
		// windowFile.delete();
		// }
		// model.reload(node);
	}

	public void registerWindow(String nodeName) {
		// model.reload(model.getWindowTreeNode(nodeName));
	}

	public File getSelectedWindowNode() {
		if (selectedWindowNode == null)
			return null;
		else
			return selectedWindowNode.getUserObject();
	}

	public void setProject(Project p) {
		model.setProject(p);
		// if (null != p) {
		// TreePath path = new TreePath(model.getRoot());
		// this.setExpandedState(path, true);
		// }

		// DefaultMutableTreeNode node = model.getWindowNode();
		// try {
		// TreeNode selectNode = node.getFirstChild();
		// if (selectNode != null) {
		// TreePath path = new TreePath(model.getPath(selectNode));
		// this.setSelectionPath(path);
		// selectedWindowNode = (WindowTreeNode) selectNode;
		// }
		// } catch (Exception e) {}
	}

	class ProjectTreeRenderer extends DefaultTreeCellRenderer {

		private Hashtable<String, Icon> configNodeIconTable;

		private Icon rootIcon;
		private Icon nodeIcon;
		private Icon windowIcon;

//		public ProjectTreeRenderer() {
//			configNodeIconTable = new Hashtable<String, Icon>();
//
//			rootIcon = AnalyzerIconFactory
//					.getImageIcon(AnalyzerIconFactory.System.UBICANVAS_ICON);
//
//			nodeIcon = AnalyzerIconFactory
//					.getImageIcon(AnalyzerIconFactory.System.NODE);
//			windowIcon = AnalyzerIconFactory
//					.getImageIcon(AnalyzerIconFactory.System.WINDOW);
//
//			Icon icon;
//
//			icon = AnalyzerIconFactory
//					.getImageIcon(AnalyzerIconFactory.System.NETWORK_CFG);
//			configNodeIconTable.put(ProjectTreeModel.NETWORK_CONFIG, icon);
//
//			icon = AnalyzerIconFactory
//					.getImageIcon(AnalyzerIconFactory.System.ALARM_CFG);
//			configNodeIconTable.put(ProjectTreeModel.ALARM_CONFIG, icon);
//
//			icon = AnalyzerIconFactory
//					.getImageIcon(AnalyzerIconFactory.System.TAG_DIC);
//			configNodeIconTable.put(ProjectTreeModel.TAG_CONFIG, icon);
//
//			icon = AnalyzerIconFactory
//					.getImageIcon(AnalyzerIconFactory.System.DEVICE);
//			configNodeIconTable.put(ProjectTreeModel.DEVICE_CONFIG, icon);
//
//			icon = AnalyzerIconFactory
//					.getImageIcon(AnalyzerIconFactory.System.DATA_COLLECT_CFG);
//			configNodeIconTable.put(ProjectTreeModel.DATA_COLLECT_CONFIG, icon);
//
//			icon = AnalyzerIconFactory
//					.getImageIcon(AnalyzerIconFactory.System.EVENT_DIC);
//			configNodeIconTable.put(ProjectTreeModel.EVENT_CONFIG, icon);
//
//			icon = AnalyzerIconFactory
//					.getImageIcon(AnalyzerIconFactory.System.DATABASE_CFG);
//			configNodeIconTable.put(ProjectTreeModel.DATABASE_CONFIG, icon);
//
//			icon = AnalyzerIconFactory
//					.getImageIcon(AnalyzerIconFactory.System.SECURITY_CFG);
//			configNodeIconTable.put(ProjectTreeModel.USER_CONFIG, icon);
//
//			icon = AnalyzerIconFactory
//					.getImageIcon(AnalyzerIconFactory.System.SCRIPT_DIC);
//			configNodeIconTable.put(ProjectTreeModel.SCRIPT_CONFIG, icon);
//			/**
//			 * �������� �߰�
//			 */
//			icon = AnalyzerIconFactory
//					.getImageIcon(AnalyzerIconFactory.System.PROTOCOL_CFG);
//			configNodeIconTable.put(ProjectTreeModel.PROTOCOL_CONFIG, icon);
//		}

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel, expanded,
					leaf, row, hasFocus);

			if (value == model.getRoot()) {
				setIcon(rootIcon);
			} else {
				String configKey = model.getConfigurationNodeKey(value);
				if (null != configKey) {
					setIcon(configNodeIconTable.get(configKey));
				} else {
					if (value instanceof HMINodeTreeNode) {
						setIcon(nodeIcon);
					} else if (value instanceof WindowNameNode) {
						setIcon(windowIcon);
					} else {
						if (leaf) {
							setIcon(leafIcon);
						} else {
							if (expanded) {
								setIcon(openIcon);
							} else {
								setIcon(closedIcon);
							}
						}
					}
				}
			}

			return this;
		}
	}
}
