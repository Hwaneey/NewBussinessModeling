package com.naru.uclair.analyzer.views.project;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.jidesoft.docking.DockableFrame;
import com.naru.uclair.analyzer.AnalyzerActionFactory;
import com.naru.uclair.analyzer.AnalyzerIconFactory;
import analyzer.listener.AnalyzerEventListener;
import com.naru.uclair.database.Database;
import com.naru.uclair.node.HMINode;
import com.naru.uclair.project.Project;


/**
 * 프로젝트의 정보를 설정하는 화면으로 이동하기 위한 메뉴 트리 화면. 이 트리의 각 노드들을 더블 클릭하므로써 편집창으로 이동하거나, 새로운
 * 프로젝트를 생성할 수 있다.
 * 
 * @author haemoon Jung
 * 
 */
public class ProjectView extends DockableFrame implements
                AnalyzerEventListener, PropertyChangeListener {

    private static final long serialVersionUID = 1L;

	private JTree projectTree = null;

	private ProjectTreeModel model = null;

//	private WindowEditor windowEditor = null;

	private WindowNodePopup windowNodePopup = null;

	private void initialize() {
		model = new ProjectTreeModel();
		projectTree = new JTree(model);
		projectTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		projectTree.setCellRenderer(new ProjectTreeCellRenderer());
		projectTree.addMouseListener(new TreeMouseListener(this));

		// tree = new ProjectTree();

		getContentPane().add(new JScrollPane(projectTree));
//		if (null != windowEditor) {
//			windowEditor.removePropertyChangeListener(this);
//			windowEditor = null;
//		}
//		windowEditor = (WindowEditor) DeveloperEditorFactory.getFactory()
//		                .getEditor(DeveloperEditorFactory.WINDOW_EDITOR_KEY);
//		windowEditor.addPropertyChangeListener(this);
		initAction();
	}

	private void initAction() {

		Action action;
		ActionMap viewActions = getActionMap();

		AnalyzerActionFactory actionFactory = AnalyzerActionFactory
		                .getFactory();
		// project
//		action = actionFactory.getAction(AnalyzerActionFactory.NEW_PROJECT);
//		viewActions.put(AnalyzerActionFactory.NEW_PROJECT, action);

		action = actionFactory.getAction(AnalyzerActionFactory.PROJECT_INFO);
		viewActions.put(AnalyzerActionFactory.PROJECT_INFO, action);

		// Node
		action = actionFactory.getAction(AnalyzerActionFactory.NEW_NODE);
		viewActions.put(AnalyzerActionFactory.NEW_NODE, action);

		action = actionFactory.getAction(AnalyzerActionFactory.EDIT_NODE);
		viewActions.put(AnalyzerActionFactory.EDIT_NODE, action);

		action = actionFactory.getAction(AnalyzerActionFactory.DELETE_NODE);
		viewActions.put(AnalyzerActionFactory.DELETE_NODE, action);
		
		// Alarm
		action = actionFactory.getAction(AnalyzerActionFactory.ALARM_GROUP_CONFIG);
		viewActions.put(AnalyzerActionFactory.ALARM_GROUP_CONFIG, action);
		
		action = actionFactory.getAction(AnalyzerActionFactory.ALARM_UMS_CONFIG);
		viewActions.put(AnalyzerActionFactory.ALARM_UMS_CONFIG, action);
		
		// DateBase
		action = actionFactory.getAction(AnalyzerActionFactory.NEW_DATABASE);
		viewActions.put(AnalyzerActionFactory.NEW_DATABASE, action);

		action = actionFactory.getAction(AnalyzerActionFactory.EDIT_DATABASE);
		viewActions.put(AnalyzerActionFactory.EDIT_DATABASE, action);

		action = actionFactory
		                .getAction(AnalyzerActionFactory.DELETE_DATABASE);
		viewActions.put(AnalyzerActionFactory.DELETE_DATABASE, action);

		// WindowEditor

		// user
		action = actionFactory.getAction(AnalyzerActionFactory.USER_CONFIG);
		viewActions.put(AnalyzerActionFactory.USER_CONFIG, action);

		// Event
		action = actionFactory.getAction(AnalyzerActionFactory.EVENT_CONFIG);
		viewActions.put(AnalyzerActionFactory.EVENT_CONFIG, action);

		// Script
		action = actionFactory.getAction(AnalyzerActionFactory.SCRIPT_CONFIG);
		viewActions.put(AnalyzerActionFactory.SCRIPT_CONFIG, action);

		// Tag
		action = actionFactory.getAction(AnalyzerActionFactory.TAG_CONFIG);
		viewActions.put(AnalyzerActionFactory.TAG_CONFIG, action);

		// Data Collection
		action = actionFactory
		                .getAction(AnalyzerActionFactory.DATA_COLLECT_CONFIG);
		viewActions.put(AnalyzerActionFactory.DATA_COLLECT_CONFIG, action);

		// Device
		action = actionFactory.getAction(AnalyzerActionFactory.DEVICE_CONFIG);
		viewActions.put(AnalyzerActionFactory.DEVICE_CONFIG, action);

		// Protocol
//		action = actionFactory.getAction(AnalyzerActionFactory.PROTOCOL_CONFIG);
//		viewActions.put(AnalyzerActionFactory.PROTOCOL_CONFIG, action);
		
		// GIS
//		action = actionFactory.getAction(AnalyzerActionFactory.GIS_CONFIG);
//		viewActions.put(AnalyzerActionFactory.GIS_CONFIG, action);
		
		// F5 update windowNode
		KeyStroke refreshKey = KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0, false);
	    this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(refreshKey, "REFRESH"); //$NON-NLS-1$
	    this.getRootPane().getActionMap().put("REFRESH", new AbstractAction(){ //$NON-NLS-1$
            private static final long serialVersionUID = 1L;
			@Override
	    	public void actionPerformed(ActionEvent e) {
//				model.comfirmWindowFile();
	    	} 
	    });
	}

	public Action getAction(int key) {
		ActionMap viewActions = getActionMap();
		Action action = viewActions.get(key);
		if (null != action) {
			return action;
		}
		return null;
	}

	private void createWindowNodePopup() {
//		windowNodePopup = new WindowNodePopup(windowEditor);
	}

	public ProjectView(String key) {
		this(key, null);
	}

	public ProjectView(String key, ImageIcon icon) {
		super(key, icon);
		initialize();
	}

	class TreeMouseListener extends MouseAdapter {

		private ProjectView projectView;

		public TreeMouseListener(ProjectView projectView) {
			this.projectView = projectView;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			TreePath path = projectTree.getPathForLocation(e.getX(), e.getY());
			projectTree.setSelectionPath(path);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			TreePath selectedPath = ((JTree) e.getSource()).getSelectionPath();

			int modifier = e.getModifiers();
			// 오른쪽 마우스 버튼 동작 반응
			if (modifier == InputEvent.BUTTON3_MASK) {
				// 팝업 메뉴.
				JPopupMenu popupMenu = null;
				// 각 선택된 노드에 맞게 팝업 메뉴를 보여준다.
				if (null == selectedPath) {
//					popupMenu = new JPopupMenu();
//					JMenu menu = new JMenu("New"); //$NON-NLS-1$
//					menu.add(getAction(AnalyzerActionFactory.NEW_PROJECT));
//					popupMenu.add(menu);
				}
				else {
					TreeNode selectedNode = (TreeNode) selectedPath
					                .getLastPathComponent();
					String nodeName = selectedNode.toString();
					// 네트워크 환경 노드
					if (ProjectTreeModel.NETWORK_CONFIG.equals(nodeName)) {
						popupMenu = new HMINodeTreeNodePopup(projectView, null);
					}
					// 네트워크 환경의 child 노드(노드)
					if (selectedNode instanceof HMINodeTreeNode) {
						popupMenu = new HMINodeTreeNodePopup(projectView,
						                ((HMINodeTreeNode) selectedNode)
						                                .getNode());
					}

					if (ProjectTreeModel.DATABASE_CONFIG.equals(nodeName)) {
						popupMenu = new DatabaseTreeNodePopup(projectView, null);
					}

					if (selectedNode instanceof DatabaseTreeNode) {
						popupMenu = new DatabaseTreeNodePopup(projectView,
						                ((DatabaseTreeNode) selectedNode)
						                                .getDatabase());
					}

					if (selectedNode instanceof WindowTreeNode) {
						if (null == windowNodePopup) {
							createWindowNodePopup();
						}
						windowNodePopup.setTarget(null);
						popupMenu = windowNodePopup;
					}
					if (selectedNode instanceof WindowNameNode) {
						if (null == windowNodePopup) {
							createWindowNodePopup();
						}
						windowNodePopup
						                .setTarget(((WindowNameNode) selectedNode)
						                                .getUserObject());
						popupMenu = windowNodePopup;
					}
				}

				if (null != popupMenu) {
					popupMenu.show(projectTree, e.getX(), e.getY());
				}
			}
			else if (modifier == InputEvent.BUTTON1_MASK) {
				if ((null != selectedPath) && (e.getClickCount() == 2)) {
					TreeNode selectedNode = (TreeNode) selectedPath
					                .getLastPathComponent();

					if (selectedNode instanceof HMINodeTreeNode) {
						HMINode node = ((HMINodeTreeNode) selectedNode)
						                .getNode();
						Action action = getAction(AnalyzerActionFactory.EDIT_NODE);
						action.putValue("edit node", node);
						action.actionPerformed(null);
					}
					else if (selectedNode instanceof DatabaseTreeNode) {
						Database database = ((DatabaseTreeNode) selectedNode)
						                .getDatabase();
						Action action = getAction(AnalyzerActionFactory.EDIT_DATABASE);
						action.putValue("edit database", database);
						action.actionPerformed(null);
					}
					else if (selectedNode instanceof WindowNameNode) {
						File f = ((WindowNameNode) selectedNode)
						                .getUserObject();
						// 화면 파일이 존재하는지 검사한다.
//						if(f.exists()){
//							Action action = windowEditor.getAction(AnalyzerActionFactory.OPEN);
//							action.putValue(com.naru.ubicanvas.editors.window.Messages.getString("open.action.file.param"), f);
//							action.actionPerformed(null);
//							action.putValue(com.naru.ubicanvas.editors.window.Messages.getString("open.action.file.param"), null);
//						}
//						else {
//							JOptionPane.showMessageDialog(windowEditor,
//											ConstantsResource.getString("ProjectView.Message.WindowComfirm"),
//											ConstantsResource.getString("ValidateDialog.Title.Warning"),
//											JOptionPane.WARNING_MESSAGE);
//							model.updateWindowListNode();
//						}
					}
					else {
						String nodeName = selectedNode.toString();
						if (ProjectTreeModel.PROJECT_CONFIG.equals(nodeName)) {
							getAction(AnalyzerActionFactory.PROJECT_INFO).actionPerformed(null);
						}
						else if (ProjectTreeModel.USER_CONFIG.equals(nodeName)) {
							Action action = getAction(AnalyzerActionFactory.USER_CONFIG);
							action.actionPerformed(null);
						}
						else if (ProjectTreeModel.EVENT_CONFIG.equals(nodeName)) {
							Action action = getAction(AnalyzerActionFactory.EVENT_CONFIG);
							action.actionPerformed(null);
						}
						else if (ProjectTreeModel.SCRIPT_CONFIG.equals(nodeName)) {
							Action action = getAction(AnalyzerActionFactory.SCRIPT_CONFIG);
							action.actionPerformed(null);
						}
						else if (ProjectTreeModel.TAG_CONFIG.equals(nodeName)) {
							Action action = getAction(AnalyzerActionFactory.TAG_CONFIG);
							action.actionPerformed(null);
						}
						else if (ProjectTreeModel.DATA_COLLECT_CONFIG.equals(nodeName)) {
							Action action = getAction(AnalyzerActionFactory.DATA_COLLECT_CONFIG);
							action.actionPerformed(null);
						}
						else if (ProjectTreeModel.DEVICE_CONFIG.equals(nodeName)) {
							Action action = getAction(AnalyzerActionFactory.DEVICE_CONFIG);
							action.actionPerformed(null);
						}
//						else if (ProjectTreeModel.PROTOCOL_CONFIG.equals(nodeName)) {
//							Action action = getAction(AnalyzerActionFactory.PROTOCOL_CONFIG);
//							action.actionPerformed(null);
//						}
						else if (ProjectTreeModel.ALARM_GROUP.equals(nodeName)) {
							Action action = getAction(AnalyzerActionFactory.ALARM_GROUP_CONFIG);
							action.actionPerformed(null);
						}// 경보 환경의 경보 그룹 더블 클릭

						else if (ProjectTreeModel.UMS.equals(nodeName)) {
							Action action = getAction(AnalyzerActionFactory.ALARM_UMS_CONFIG);
							action.actionPerformed(null);
						}// 경보 환경의 UMS 더블 클릭
					}
				}
			}
		}
	}

	@Override
	public void projectChanged(Project newProject) {
		model.setProject(newProject);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();

//		if (WindowEditor.ACTIVE_VIEW_PROPERTY.equals(propertyName)) {
//			TreePath selectedPath = projectTree.getSelectionPath();
//
//			MutableTreeNode windowNode = model
//			                .getConfigurationNode(ProjectTreeModel.WINDOW_CONFIG);
//			model.reload(windowNode);
//			if (null != selectedPath) {
//				projectTree.setSelectionPath(selectedPath);
//			}
//		}
	}

	class ProjectTreeCellRenderer extends DefaultTreeCellRenderer {

        private static final long serialVersionUID = 1L;

		private Hashtable<String, Icon> configNodeIconTable;

		private Icon rootIcon;
		private Icon nodeIcon;
		private Icon windowOpenIcon;
		private Icon windowCloseIcon;

		public ProjectTreeCellRenderer() {

			configNodeIconTable = new Hashtable<String, Icon>();

			rootIcon = AnalyzerIconFactory
			                .getImageIcon(AnalyzerIconFactory.System.UBICANVAS_ICON);

			nodeIcon = AnalyzerIconFactory
			                .getImageIcon(AnalyzerIconFactory.System.NODE);
			// windowIcon =
			// DeveloperIconFactory.getImageIcon(DeveloperIconFactory
			// .System.WINDOW);
			
			/**
			 * 2009년 9월28일 성권이가 추가함
			 * 화면 윈도우 활성화/비활성화
			 */
			windowOpenIcon = AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory.System.WINDOW_OPENVIEW);
			windowCloseIcon = AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory.System.WINDOW_CLOSEVIEW);

			Icon icon;

			icon = AnalyzerIconFactory
			                .getImageIcon(AnalyzerIconFactory.System.PROJECT);
			configNodeIconTable.put(ProjectTreeModel.PROJECT_CONFIG, icon);

			icon = AnalyzerIconFactory
			                .getImageIcon(AnalyzerIconFactory.System.NETWORK_CFG);
			configNodeIconTable.put(ProjectTreeModel.NETWORK_CONFIG, icon);

			icon = AnalyzerIconFactory
			                .getImageIcon(AnalyzerIconFactory.System.ALARM);
			configNodeIconTable.put(ProjectTreeModel.ALARM_CONFIG, icon);

			icon = AnalyzerIconFactory
			                .getImageIcon(AnalyzerIconFactory.System.TAG_DIC);
			configNodeIconTable.put(ProjectTreeModel.TAG_CONFIG, icon);

			icon = AnalyzerIconFactory
			                .getImageIcon(AnalyzerIconFactory.System.DEVICE_CFG);
			configNodeIconTable.put(ProjectTreeModel.DEVICE_CONFIG, icon);
			
			icon = AnalyzerIconFactory
            				.getImageIcon(AnalyzerIconFactory.System.PROTOCOL_CFG);
			configNodeIconTable.put(ProjectTreeModel.PROTOCOL_CONFIG, icon);

			icon = AnalyzerIconFactory
			                .getImageIcon(AnalyzerIconFactory.System.DATA_COLLECT_CFG);
			configNodeIconTable.put(ProjectTreeModel.DATA_COLLECT_CONFIG, icon);

			icon = AnalyzerIconFactory
			                .getImageIcon(AnalyzerIconFactory.System.EVENT_DIC);
			configNodeIconTable.put(ProjectTreeModel.EVENT_CONFIG, icon);

			icon = AnalyzerIconFactory
			                .getImageIcon(AnalyzerIconFactory.System.DATABASE_CFG);
			configNodeIconTable.put(ProjectTreeModel.DATABASE_CONFIG, icon);

			icon = AnalyzerIconFactory
			                .getImageIcon(AnalyzerIconFactory.System.SECURITY_CFG);
			configNodeIconTable.put(ProjectTreeModel.USER_CONFIG, icon);

			icon = AnalyzerIconFactory
			                .getImageIcon(AnalyzerIconFactory.System.SCRIPT_DIC);
			configNodeIconTable.put(ProjectTreeModel.SCRIPT_CONFIG, icon);
			
			icon = AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory.System.GIS_CONFIG);
			configNodeIconTable.put(ProjectTreeModel.GIS_CONFIG, icon);
		}

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
		                boolean sel, boolean expanded, boolean leaf, int row,
		                boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, sel, expanded,
			                leaf, row, hasFocus);

			if (value == model.getRoot()) {
//				setIcon(rootIcon);
				setFont(null);
			}
			else {
				setFont(null);
				String configKey = model.getConfigurationNodeKey(value);
				if (null != configKey) {
					setIcon(configNodeIconTable.get(configKey));
				}
				else {
					if (value instanceof HMINodeTreeNode) {
						setIcon(nodeIcon);
					}
					else if (value instanceof DatabaseTreeNode) {
						setIcon(AnalyzerIconFactory
						                .getImageIcon(AnalyzerIconFactory.System.DATABASE));
					}
					else if (ProjectTreeModel.ALARM_GROUP.equals(value
					                .toString())) {
						setIcon(null);
					}
					else if (ProjectTreeModel.UMS.equals(value.toString())) {
						setIcon(null);
					}
					else if (value instanceof WindowNameNode) {
						WindowNameNode windowNameNode = (WindowNameNode) value;
//						setIcon(null); // 아이콘
//						if (windowEditor.isOpened(windowNameNode
//						                .getUserObject())) {
//							setFont(new Font(null, Font.BOLD, 12));
//							HMIDesignView activeView = windowEditor.getActiveView();
//							if (null != activeView && value.toString().equals(activeView.getFile().getName())) {
//								setForeground(Color.ORANGE); // 열린화면
//							}
//							setIcon(windowOpenIcon);
//						}
//						else {
//							setForeground(Color.LIGHT_GRAY); // 안열린 화면 
//							setIcon(windowCloseIcon);
//						}

					}
					else {
						if (leaf) {
							setIcon(leafIcon);
						}
						else {
							if (expanded) {
								setIcon(openIcon);
							}
							else {
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
