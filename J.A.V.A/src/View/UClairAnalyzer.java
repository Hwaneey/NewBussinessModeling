package View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;

import com.javadocking.DockingManager;
import com.javadocking.dock.BorderDock;
import com.javadocking.dock.FloatDock;
import com.javadocking.dock.Position;
import com.javadocking.dock.SplitDock;
import com.javadocking.dock.TabDock;
import com.javadocking.dock.docker.BorderDocker;
import com.javadocking.dock.factory.LeafDockFactory;
import com.javadocking.dock.factory.ToolBarDockFactory;
import com.javadocking.dockable.DefaultDockable;
import com.javadocking.dockable.Dockable;
import com.javadocking.dockable.DockableState;
import com.javadocking.dockable.StateActionDockable;
import com.javadocking.dockable.action.DefaultDockableStateAction;
import com.javadocking.dockable.action.DefaultDockableStateActionFactory;
import com.javadocking.event.DockingEvent;
import com.javadocking.event.DockingListener;
import com.javadocking.model.DefaultDockingPath;
import com.javadocking.model.DockingPath;
import com.javadocking.model.FloatDockModel;
import com.javadocking.util.LookAndFeelUtil;
import com.javadocking.visualizer.DockingMinimizer;
import com.javadocking.visualizer.FloatExternalizer;
import com.javadocking.visualizer.SingleMaximizer;

public class UClairAnalyzer extends JPanel implements TreeSelectionListener {

	// Static fields.

	public static final int FRAME_WIDTH = 1400;
	public static final int FRAME_HEIGHT = 800;

	// Fields.

	private Dockable[] dockables;
	private DockingPath dockingPath;
	private JMenu windowMenu;

	// Constructors.

	public UClairAnalyzer(JFrame frame) {

		super(new BorderLayout());

		// Create the dock model for the docks, minimizer and maximizer.
		FloatDockModel dockModel = new FloatDockModel("workspace.dck");
		String frameId = "frame";
		dockModel.addOwner(frameId, frame);

		// Give the dock model to the docking manager.
		DockingManager.setDockModel(dockModel);

		// Create the content components.

		// The dockables.
		dockables = new Dockable[5];
		JPanel buttonPanel = createButtonPanel();
		JPanel helloPanel = new JPanel();
		JPanel chartPanel1 = new JPanel();
		JPanel chartPanel2 = new JPanel();
		JPanel chartPanel3 = new JPanel();

		// Create the dockables around the content components.
		dockables[0] = createDockable("buttonPanel", buttonPanel, "분석기", null, "Create new charts");
		dockables[1] = createDockable("helloPanel", helloPanel, "Hello", null, "Hello, I am another dockable");
		dockables[2] = createDockable("chartPanel1", chartPanel1, "Window", null, "Chart number 1");
		dockables[3] = createDockable("chartPanel2", chartPanel2, "System out", null, "Chart number 2");
		dockables[4] = createDockable("chartPanel3", chartPanel3, "System err", null, "Chart number 3");

		// Give the float dock a different child dock factory.
		// We don't want the floating docks to be splittable.
		FloatDock floatDock = dockModel.getFloatDock(frame);
		floatDock.setChildDockFactory(new LeafDockFactory(false));

		// Create the tab docks.
		TabDock centerTabbedDock = new TabDock();
		TabDock leftTabbedDock = new TabDock();
		TabDock bottomTabbedDock = new TabDock();

		// Add the dockables to these tab docks.
		centerTabbedDock.addDockable(dockables[2], new Position(0));
//		centerTabbedDock.addDockable(dockables[3], new Position(1));
//		centerTabbedDock.addDockable(dockables[4], new Position(2));

		centerTabbedDock.setSelectedDockable(dockables[2]);
		leftTabbedDock.addDockable(dockables[0], new Position(0));
		// bottomTabbedDock.addDockable(dockables[1], new Position(0));
		bottomTabbedDock.addDockable(dockables[3], new Position(0));
		bottomTabbedDock.addDockable(dockables[4], new Position(1));

		SplitDock rightSplitDock = new SplitDock();
		rightSplitDock.addChildDock(bottomTabbedDock, new Position(Position.BOTTOM));
		rightSplitDock.addChildDock(centerTabbedDock, new Position(Position.TOP));
		rightSplitDock.setDividerLocation(500);

		SplitDock leftSplitDock = new SplitDock();
		leftSplitDock.addChildDock(leftTabbedDock, new Position(Position.CENTER));

		SplitDock totalSplitDock = new SplitDock();
		totalSplitDock.addChildDock(leftSplitDock, new Position(Position.LEFT));
		totalSplitDock.addChildDock(rightSplitDock, new Position(Position.RIGHT));
		totalSplitDock.setDividerLocation(180);

		// Add the root dock to the dock model.
		dockModel.addRootDock("totalDock", totalSplitDock, frame);

		// Create a maximizer and add it to the dock model.
		SingleMaximizer maximizePanel = new SingleMaximizer(totalSplitDock);
		dockModel.addVisualizer("maximizer", maximizePanel, frame);

		// Create a docking minimizer and add it to the dock model.
		BorderDock minimizerBorderDock = new BorderDock(new ToolBarDockFactory());
		minimizerBorderDock.setMode(BorderDock.MODE_MINIMIZE_BAR);
		minimizerBorderDock.setCenterComponent(maximizePanel);
		BorderDocker borderDocker = new BorderDocker();
		borderDocker.setBorderDock(minimizerBorderDock);
		DockingMinimizer minimizer = new DockingMinimizer(borderDocker);
		dockModel.addVisualizer("minimizer", minimizer, frame);

		// Add an externalizer to the dock model.
		dockModel.addVisualizer("externalizer", new FloatExternalizer(frame), frame);

		// Add this dock also as root dock to the dock model.
		dockModel.addRootDock("minimizerBorderDock", minimizerBorderDock, frame);

		// Add the border dock to this panel.
		this.add(minimizerBorderDock, BorderLayout.CENTER);

		// Add the paths of the docked dockables to the model with the docking paths.
		addDockingPath(dockables[0]);
		addDockingPath(dockables[1]);
		addDockingPath(dockables[2]);
		addDockingPath(dockables[3]);
		addDockingPath(dockables[4]);

		dockingPath = DockingManager.getDockingPathModel().getDockingPath(dockables[2].getID());

		// Create the menubar.
		JMenuBar menuBar = createMenu(dockables);
		frame.setJMenuBar(menuBar);

	}

	private JPanel createButtonPanel() {

		JPanel panel = new JPanel(new BorderLayout());

		new JFrame();
		DefaultMutableTreeNode af = new DefaultMutableTreeNode("AFactory");
		DefaultMutableTreeNode a = new DefaultMutableTreeNode("프로젝트 정보 분석");
		DefaultMutableTreeNode b = new DefaultMutableTreeNode("존재하지 않는 태그 분석");
		DefaultMutableTreeNode c = new DefaultMutableTreeNode("개별태그 종속성 분석");
		DefaultMutableTreeNode d = new DefaultMutableTreeNode("가상태그 종속성 분석");
		DefaultMutableTreeNode e = new DefaultMutableTreeNode("물리주소 종속성 분석");
		DefaultMutableTreeNode f = new DefaultMutableTreeNode("객체태그 연결정보 분석");
		DefaultMutableTreeNode g = new DefaultMutableTreeNode("이벤트 종속성 분석");
		DefaultMutableTreeNode h = new DefaultMutableTreeNode("이벤트 종속성 분석");
		DefaultMutableTreeNode i = new DefaultMutableTreeNode("계산스크립트 검증");
		DefaultMutableTreeNode j = new DefaultMutableTreeNode("객체효과 양립성 분석");

		af.add(a);
		af.add(b);
		af.add(c);
		af.add(d);
		af.add(e);
		af.add(f);
		af.add(g);
		// af.add(h);
		af.add(i);
		af.add(j);
		JTree jt = new JTree(af);
		jt.setToggleClickCount(2);
		panel.add(jt);
		// return panel;
		// JTextField currentSelectionField = new JTextField("Current Selection: NONE");
		// jt.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		jt.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) jt.getLastSelectedPathComponent();

				if (node == null)
					// Nothing is selected.
					return;

				// 트리 노드 이름 얻어오는거
				Object nodeInfo = node.getUserObject();

				if (node.isLeaf()) {

					// JOptionPane.showConfirmDialog(null,nodeInfo+ " 실행하시겠습니까?", "확인",
					// JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
				} else {

				}

//				jt.expandPath(new TreePath(a.getPath()));
//			    currentSelectionField.setText(e.getPath().toString());
			}
		});

		jt.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getClickCount() == 2) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) jt.getLastSelectedPathComponent();

					Object nodeInfo = node.getUserObject(); // 트리 안에 노드 이름 얻어오는거

					if (e.getButton() == 1) {
					} // 클릭
					if (e.getClickCount() == 2) {
						if (nodeInfo == "AFactory")
							return;
						int res = JOptionPane.showConfirmDialog(null, nodeInfo + " 실행하시겠습니까?", "확인",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
						if (res == 0) { // OK=0 , Cancel=2 리턴
							String[] columnType = { "스크립트명", "계산스크립트 검증" };
							Object[][] data = { { "", "" }, { "", "" },

							};

							JTable table = new JTable(data, columnType);
							JPanel project = new JPanel(new BorderLayout());

							project.add(new JScrollPane(table)); // table 추가 장소

							// 도킹 패널 생성
							Dockable dockable = createDockable("" + nodeInfo, project, "" + nodeInfo, null, "JTABLE ");

							// 도킹 패널이 어느부분에 생성되는가?
							DockingPath newDockingPath = DefaultDockingPath.copyDockingPath(dockable, dockingPath);
							DockingManager.getDockingPathModel().add(newDockingPath);

							// 도키패널에 기능 및 경로 부여
							DockingManager.getDockingExecutor().changeDocking(dockable, dockingPath);

						}
					}

				}
			}
		});
		return panel;
	}

	private Dockable addAllActions(Dockable dockable) {

		Dockable wrapper = new StateActionDockable(dockable, new DefaultDockableStateActionFactory(),
				DockableState.statesClosed());
		wrapper = new StateActionDockable(wrapper, new DefaultDockableStateActionFactory(),
				DockableState.statesAllExceptClosed());
		return wrapper;

	}

	private Dockable createDockable(String id, Component content, String title, Icon icon, String description) {

		// Create the dockable.
		DefaultDockable dockable = new DefaultDockable(id, content, title, icon);

		// Add a description to the dockable. It will be displayed in the tool tip.
		dockable.setDescription(description);

		Dockable dockableWithActions = addAllActions(dockable);

		return dockableWithActions;

	}

	private JMenuBar createMenu(Dockable[] dockables) {
		// Create the menu bar.
		JMenuBar menuBar = new JMenuBar();

		// Build the File menu.
		JMenu fileMenu = new JMenu("파일");
		JMenu fileanalysis = new JMenu("분석");
		JMenu filetest = new JMenu("테스트 케이스");
		JMenu filehelp = new JMenu("도움말");

		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.getAccessibleContext().setAccessibleDescription("The File Menu");
		menuBar.add(fileMenu);
		menuBar.add(fileanalysis);
		menuBar.add(filetest);
		menuBar.add(filehelp);

		fileanalysis.add("존재하지 않는 태그 분석");
		fileanalysis.add("가상태그 종속성 분석");
		fileanalysis.add("물리주소 종속성 분석");
		fileanalysis.add("객체태그 연결정보 분석");
		fileanalysis.add("이벤트 종속성 분석");
		fileanalysis.add("계산스크립트 검증");
		fileanalysis.add("객체효과 양립성 분석");

		// fileanalysis.setEnabled(false);

		filetest.add("IO 테스트 케이스 생성기");
		filetest.add("화면 테스트 케이스 생성기");

		filehelp.add("도움말");
		// Build the Window menu.
		windowMenu = new JMenu("Window");
		windowMenu.setMnemonic(KeyEvent.VK_W);
		windowMenu.getAccessibleContext().setAccessibleDescription("The Window Menu");
		// menuBar.add(windowMenu);

		// The JMenuItem for File
		JMenuItem p_open = new JMenuItem("프로젝트 열기");
		p_open.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_O, java.awt.Event.CTRL_MASK | java.awt.Event.SHIFT_MASK));
		p_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Choose a directory to save your file: ");
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				int returnValue = jfc.showSaveDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					if (jfc.getSelectedFile().isDirectory()) {
						System.out.println("You selected the directory: " + jfc.getSelectedFile());
					}
				}
			}
		});

		JMenuItem p_close = new JMenuItem("프로젝트 닫기");
		p_close.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_C, java.awt.Event.CTRL_MASK | java.awt.Event.SHIFT_MASK));

		JMenuItem menuItem = new JMenuItem("Exit", KeyEvent.VK_F4);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Exit te application");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		fileMenu.add(p_open);
		fileMenu.add(p_close);
		fileMenu.addSeparator();
		fileMenu.add(menuItem);

		// The JMenuItems for the dockables.
		for (int index = 0; index < dockables.length; index++) {
			// Create the check box menu for the dockable.
			JCheckBoxMenuItem cbMenuItem = new DockableMenuItem(dockables[index]);
			windowMenu.add(cbMenuItem);
		}

		return menuBar;

	}

	private DockingPath addDockingPath(Dockable dockable) {

		if (dockable.getDock() != null) {
			// Create the docking path of the dockable.
			DockingPath dockingPath = DefaultDockingPath.createDockingPath(dockable);
			DockingManager.getDockingPathModel().add(dockingPath);
			return dockingPath;
		}

		return null;

	}

	// Private classes.

	private class DockableMenuItem extends JCheckBoxMenuItem {
		public DockableMenuItem(Dockable dockable) {
			super(dockable.getTitle(), dockable.getIcon());

			setSelected(dockable.getDock() != null);

			DockableMediator dockableMediator = new DockableMediator(dockable, this);
			dockable.addDockingListener(dockableMediator);
			addItemListener(dockableMediator);
		}
	}

	private class DockableMediator implements ItemListener, DockingListener {

		private Dockable dockable;
		private Action closeAction;
		private Action restoreAction;
		private JMenuItem dockableMenuItem;

		public DockableMediator(Dockable dockable, JMenuItem dockableMenuItem) {

			this.dockable = dockable;
			this.dockableMenuItem = dockableMenuItem;
			closeAction = new DefaultDockableStateAction(dockable, DockableState.CLOSED);
			restoreAction = new DefaultDockableStateAction(dockable, DockableState.NORMAL);

		}

		public void itemStateChanged(ItemEvent itemEvent) {

			dockable.removeDockingListener(this);
			if (itemEvent.getStateChange() == ItemEvent.DESELECTED) {
				// Close the dockable.
				closeAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Close"));
			} else {
				// Restore the dockable.
				restoreAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Restore"));
			}
			dockable.addDockingListener(this);

		}

		public void dockingChanged(DockingEvent dockingEvent) {
			if (dockingEvent.getDestinationDock() != null) {
				dockableMenuItem.removeItemListener(this);
				dockableMenuItem.setSelected(true);
				dockableMenuItem.addItemListener(this);
			} else {
				dockableMenuItem.removeItemListener(this);
				dockableMenuItem.setSelected(false);
				dockableMenuItem.addItemListener(this);
			}
		}

		public void dockingWillChange(DockingEvent dockingEvent) {
		}

	}

	// Main method.

	public static void createAndShowGUI() {

		// Remove the borders from the split panes and the split pane dividers.
		LookAndFeelUtil.removeAllSplitPaneBorders();

		// Create the frame.
		JFrame frame = new JFrame("UClairAnalyzer");

		// Set the default location and size.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

		// Create the panel and add it to the frame.
		UClairAnalyzer panel = new UClairAnalyzer(frame);
		frame.getContentPane().add(panel);

		// Set the frame properties and show it.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	public static void main(String args[]) {
		Runnable doCreateAndShowGUI = new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		};
		SwingUtilities.invokeLater(doCreateAndShowGUI);
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
