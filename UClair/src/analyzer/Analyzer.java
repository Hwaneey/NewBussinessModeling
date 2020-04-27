package analyzer;
/*
 * @(#)DockingFrameworkDemo.java
 *
 * Copyright 2002 - 2003 JIDE Software. All rights reserved.
 */

import com.jidesoft.docking.*;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideMenu;
import com.jidesoft.swing.JideScrollPane;
import com.naru.uclair.common.SystemResourceManager;
import com.naru.uclair.draw.util.WindowSelectDialog;
import com.naru.uclair.project.Project;
import com.naru.uclair.script.EngineManager;
import com.naru.uclair.util.TagListWindowDialog;
import com.naru.uclair.workspace.HMIWorkspace;


import analyzer.constants.AnalyzerConstants;
import analyzer.listener.AnalyzerEventListener;


import excel.ExcelConnector;
import util.DefaultTree;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * This is a sample program for JIDE Docking Framework. It will create a JFrame with about 20 dockable frames to show
 * the features of JIDE Docking Framework. <br> Required jar files: jide-common.jar, jide-dock.jar <br> Required L&F:
 * Jide L&F extension required
 */


public class Analyzer extends DefaultDockableHolder  {
	
	public Analyzer(JFrame frame) {
		super();
		listeners = Collections.synchronizedList(new ArrayList<AnalyzerEventListener>());
		initialize();
		Analyzer(frame, 1);
	}
	
	public enum Analysis_Selector {
		NonExistTag, VirtualTag, PhysicalAddress, ObjConnTag, Event, CalcScript, ObjEffectCompatibility, None
	}
	
	private void Analyzer(JFrame frame, int j) {
		// TODO Auto-generated method stub
		
	}
	
	private static Analyzer _frame;
	public static String _lastDirectory = ".";

	private static final String PROFILE_NAME = "UClair Analyzer";

	private static boolean _autohideAll = false;
	private static WindowAdapter _windowListener;
	public static JMenuItem _redoMenuItem;
	public static JMenuItem _undoMenuItem;
	

	private static JTree analysorTree;
	private static String projectPath;
	private static HashMap<String, JTable> dockMap;

	
	
	public Analyzer(String title) throws HeadlessException {
		super(title);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
				LookAndFeelFactory.installJideExtension(LookAndFeelFactory.EXTENSION_STYLE_XERTO);
				new Analyzer(new JFrame());
				showDemo(true);
			}
		});

	}

	
	public static DefaultDockableHolder showDemo(final boolean exit) {
		dockMap = new HashMap<String, JTable>();
		_frame = new Analyzer("UClair Analyzer");
		_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		_frame.getDockingManager().setXmlFormat(true);
		//_frame.setUndecorated(true);
		// add a window listener to do clear up when windows closing.
		_windowListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				clearUp();
				if (exit) {
					System.exit(0);
				}
			}
		};
		_frame.addWindowListener(_windowListener);

		// Set the profile key
		_frame.getDockingManager().setProfileKey(PROFILE_NAME);

		// Uses light-weight outline. There are several options here.
		_frame.getDockingManager().setOutlineMode(DockingManager.FULL_OUTLINE_MODE);

		// add menu bar
		_frame.setJMenuBar(createMenuBar());

		// Sets the number of steps you allow user to undo.
		_frame.getDockingManager().setUndoLimit(10);
		_frame.getDockingManager().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				//refreshUndoRedoMenuItems();
			}
		});

		// Now let's start to addFrame()
		_frame.getDockingManager().beginLoadLayoutData();

		_frame.getDockingManager().setInitSplitPriority(DockingManager.SPLIT_EAST_WEST_SOUTH_NORTH);

		// add all dockable frames
		_frame.getDockingManager().addFrame(createMessageFrame());
		_frame.getDockingManager().addFrame(createAnalyzerFrame());

		// just use default size. If you want to overwrite, you can call this method
		//        _frame.getDockingManager().setInitBounds(new Rectangle(0, 0, 960, 800));

		// disallow drop dockable frame to workspace area
		_frame.getDockingManager().getWorkspace().setAcceptDockableFrame(true);
		_frame.getDockingManager().getWorkspace().setAdjustOpacityOnFly(false);

		// load layout information from previous session. This indicates the end of beginLoadLayoutData() method above.
		_frame.getDockingManager().loadLayoutData();

		_frame.toFront();
		return _frame;
	}

	private static void clearUp() {
		_frame.removeWindowListener(_windowListener);
		_windowListener = null;

		if (_frame.getDockingManager() != null) {
			_frame.getDockingManager().saveLayoutData();
		}
//		_frame.setUndecorated(true);

		_frame.dispose();
		_frame = null;
	}

	protected static DockableFrame createDockableFrame(String key, Icon icon) {
		DockableFrame frame = new DockableFrame(key, icon);
		frame.setPreferredSize(new Dimension(200, 200));
		return frame;
	}

	protected static DockableFrame createAnalyzerFrame() {
		DockableFrame frame = createDockableFrame("�м���", new ImageIcon("../UClair/img/magnifying-glass.png"));
		frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		frame.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
		frame.getContext().setInitIndex(0);
		frame.getContentPane().setLayout(new BorderLayout());
		analysorTree = new DefaultTree();

		frame.add(createScrollPane(analysorTree));
		analysorTree.setVisible(false);
		
		return frame;
	}
	
	protected static DockableFrame createOutputFrame(String key, String path) {
		dockMap.put(path, ExcelConnector.readTableFromExcel(path));
		DockableFrame frame = createDockableFrame(key, new ImageIcon("../UClair/img/excel.png"));
		frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		frame.getContext().setInitSide(DockContext.DOCK_SIDE_CENTER);
		frame.add(createScrollPane(dockMap.get(path)));
		return frame;
	}
	
	protected static DockableFrame createMessageFrame() {
		DockableFrame frame = createDockableFrame("�޽���", new ImageIcon("../UClair/img/command-line.png"));
		frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		frame.getContext().setInitSide(DockContext.DOCK_SIDE_SOUTH);

		JTabbedPane jtab = new JTabbedPane();
		jtab.addTab("System out", new JPanel());
		jtab.addTab("System err", new JPanel());

		frame.add(jtab);
		return frame;
	}

	
	protected static JMenuBar createMenuBar() {

		JMenuBar menu = new JMenuBar();
		

		JMenu fileMenu = createFileMenu();
		JMenu analyzeMenu = createAnalyzeMenu();
		JMenu testCaseMenu = createTestCaseMenu();
		JMenu helpMenu = createHelpMenu();

		menu.add(fileMenu);
		menu.add(analyzeMenu);
		menu.add(testCaseMenu);
		menu.add(helpMenu);

		return menu;
	}

	private static JScrollPane createScrollPane(Component component) {
		JScrollPane pane = new JideScrollPane(component);
		pane.setVerticalScrollBarPolicy(JideScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return pane;
	}

	private static JMenu createHelpMenu() {
		JMenu menu = new JideMenu("����");

		menu.add(new JMenuItem("���� ����(����)"));
		menu.addSeparator();
		menu.add(new JMenuItem("���� ������ ����"));
		menu.add(new JMenuItem("�ý��� ������ ����"));
		return menu;
	}

	private static JMenu createAnalyzeMenu() {
		AnalyzerActionFactory actionFactory = AnalyzerActionFactory.getFactory();

		JMenu menu = new JideMenu("�м�");
		
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.DANGLING_TAG_ANALYSIS))); //�������� �ʴ� �±� �м�
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.VIRTUAL_TAG_DEPENDENCY_ANALYSIS))); //�����±� ���Ӽ� �м�
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.PHYSICAL_ADRESS_DEPENDENCY_ANALYSIS))); //�����ּ� ���Ӽ� �м�
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.EACH_TAG_DEPENDENCY_ANALYSIS))); //��ü�±� �������� �м�
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.EVENT_TAG_DEPENDENCY_ANALYSIS))); //�̺�Ʈ ���Ӽ� �м�
		menu.addSeparator();
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.SCRIPT_ANALYSIS))); //��� ��ũ��Ʈ ����
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.OBJECT_EFFECT_COMPATIBILITY_ANALYSIS))); //��üȿ�� �縳�� �м�
		
		return menu;
	}

	private static JMenu createTestCaseMenu() {
		AnalyzerActionFactory actionFactory = AnalyzerActionFactory.getFactory();
		JMenu menu = new JideMenu("�׽�Ʈ ���̽�");

		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.IO_TEST_CASE_GENERATOR))); //IO �׽�Ʈ ���̽� ������
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.WINDOW_TEST_CASE_GENERATOR))); //ȭ�� �׽�Ʈ ���̽� ������
		
		return menu;
	}
	
	private static JMenu createFileMenu() {
		AnalyzerActionFactory actionFactory = AnalyzerActionFactory.getFactory();

		
		// Build the File menu.
		JMenu menu = new JMenu("����");
		menu.setMnemonic(KeyEvent.VK_F);

//		 The JMenuItem for File
		JMenuItem fileMenuItem1 = new JMenuItem("������Ʈ ����");
		fileMenuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK | ActionEvent.ALT_MASK));		
		menu.add(actionFactory.getAction(AnalyzerActionFactory.OPEN_PROJECT));
		
		JMenuItem fileMenuItem4 = new JMenuItem("������Ʈ ����");
		fileMenuItem4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.ALT_MASK));
		fileMenuItem4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				for (String key : dockMap.keySet()) {
					ExcelConnector.writeExcelFromTable(key, dockMap.get(key));
				}
			}
		});
		menu.add(fileMenuItem4);

		JMenuItem fileMenuItem2 = new JMenuItem("������Ʈ �ݱ�");
		fileMenuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK | ActionEvent.ALT_MASK));
		fileMenuItem2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				projectPath = "";
				analysorTree.setModel(DefaultTree.getDefaultTreeModel());
				for (String key : dockMap.keySet()) {
					_frame.getDockingManager().removeFrame(key.substring(key.lastIndexOf('\\') + 1));
				}
				analysorTree.setVisible(false);
			}
		});
		menu.add(fileMenuItem2);
		
		menu.addSeparator();
		JMenuItem fileMenuItem3 = new JMenuItem("����");
		fileMenuItem3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		fileMenuItem3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);
			}
		});
		menu.add(fileMenuItem3);

		return menu;
	}
	
//	private static void openProject() {
//		JFileChooser fc = new JFileChooser();
//		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//		fc.setMultiSelectionEnabled(false);
//		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
//			// ���� ���� .xlsx
//			final String pattern = ".xlsx";
//			
//			// �����ִ� �ƿ�ǲ ������ ��� ����
//			for (String key : dockMap.keySet()) {
//				_frame.getDockingManager().removeFrame(key.substring(key.lastIndexOf('\\') + 1));
//			}
//			dockMap.clear();
//			projectPath = fc.getSelectedFile().getPath();
//			String name = fc.getSelectedFile().getName();
//			DefaultTreeModel model = (DefaultTreeModel)analysorTree.getModel();
//			DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
//			DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode(name);
//			root.removeAllChildren();
//			
//			File file = new File(projectPath);
//			String fileList[] = file.list(new FilenameFilter() {
//				@Override
//				public boolean accept(File dir, String name) {
//					return name.endsWith(pattern); 
//				}
//			});
//
//			if(fileList.length > 0){
//				for(int i=0; i < fileList.length; i++){
//					newRoot.add(new DefaultMutableTreeNode(fileList[i]));
//				}
//			}
//			model.setRoot(newRoot);
//			analysorTree.setVisible(true);
//			analysorTree.addMouseListener(new MouseAdapter() {
//				public void mouseClicked(MouseEvent e) {
//					if (e.getClickCount() == 2) {
//						DefaultMutableTreeNode node = (DefaultMutableTreeNode) analysorTree.getLastSelectedPathComponent();
//						if (node == null || node.getChildCount() > 0 || node.isRoot() || projectPath.isEmpty()) return;
//
//						// filename			: ���ϸ�
//						// key 				: ���� ���
//						// extension		: ���� Ȯ����
//						String filename = node.toString();
//						String key = projectPath + '\\' + filename;
//						String extension = filename.substring(filename.lastIndexOf('.'));
//						
//						/*
//						if (!extension.equals(".xlsx")) {
//							JOptionPane.showMessageDialog(null, "�ùٸ��� ���� Ȯ�����Դϴ�.", "���� ����", JOptionPane.WARNING_MESSAGE);
//							return;
//						}
//						*/
//					
//						if (!dockMap.containsKey(key)) {
//							_frame.getDockingManager().addFrame(createOutputFrame(filename, key));
//						} 
//						_frame.getDockingManager().activateFrame(filename);
//					}
//				}
//			});
//			model.reload();
//		}
//	}
	
	//========================================================
	// ���ο� �۾� ���� 
	//========================================================
	/************************************************
	 * @date	: 2020. 4. 22.
	 * ����  	: �⺻���� �ʱ�ȭ�� �����ϴ� �κ�   
	 ************************************************/
	
	private void initialize() {
		AnalyzerActionFactory.createInstance(this);		
	}
	
	protected Project currentProject;	// ���ο� ���̺��� �߰� �ʿ�!!
	private List<AnalyzerEventListener> listeners;
	
	public Project getProject() {		
		return currentProject;
	}
	
	public void closeProject() {
		if (null != currentProject) {
			//workspacePane.removeAll();
			setProject(null);
		}
	}
	public void setProject(final Project newProject) {
		if (null != currentProject) {
			// singleton �ν��Ͻ��� diposeó��
			EngineManager.disposeEngines();
			SystemResourceManager.disposeResources();

			// WindowListDialog.setWindowPath(null);
			// ȭ�� ����â ����.
			WindowSelectDialog.setWindowPath(null);
			TagListWindowDialog.setCurrentTagDictionary(null);
		}
		if (currentProject == newProject) {
			return;
		}

		currentProject = newProject;
		if (null != currentProject) {
			// �ý��� ���ҽ� ������ �ʱ�ȭ
			final SystemResourceManager resourceManager = SystemResourceManager
					.createInstance(false);
			resourceManager.addTopUI(this);
			resourceManager.setProject(currentProject);
			
			// �⺻ ����(�׷��) ����
			final EngineManager scriptEngineManager = EngineManager.createInstance(false);

			// ���� ���� ��ũ�� �Ӽ����� �ϰ��� �ְ� �������� �ֱ� ���� ��ü.
			newProject.enableLinker();

			// TODO: �� �κ� Ȯ�� �ʿ�
			// ȭ�� ���� â ����.
			//WindowSelectDialog.setWindowPath(currentProject
			//		.getWindowResourcePath());
			//TagListWindowDialog.setCurrentTagDictionary(currentProject
			//		.getTagDictionary());

			// Workspace�� ������ ������Ʈ ���� ����.
			final HMIWorkspace workspace = HMIWorkspace.getInstance();
			if (!workspace.isExist(currentProject)) {
				workspace.addProject(currentProject);
			}
			HMIWorkspace.getInstance().setStartProject(
					currentProject.getProjectPath());
			HMIWorkspace.getInstance().save();
		}

		fireProjectChangeEvent(currentProject);
	}
	protected void fireProjectChangeEvent(final Project newProject) {
		for (final AnalyzerEventListener l : listeners) {
			try {
				l.projectChanged(newProject);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Analyzer �̺�Ʈ ������ ���(������Ʈ ���� �̺�Ʈ)
	 * 
	 * @param l
	 */
	public void addAnalyzerEventListener(final AnalyzerEventListener l) {
		listeners.add(l);
	}

	
	public void exitWithConfirm() {
		// TODO Auto-generated method stub
		String exitMessage = AnalyzerConstants
				.getString(AnalyzerConstants.ANALYZER_EXIT_DIALOG_MSG);
		String exitTitle = AnalyzerConstants
				.getString(AnalyzerConstants.ANALYZER_EXIT_DIALOG_TITLE);
		final int result = JOptionPane.showConfirmDialog(this, exitMessage,
				exitTitle, JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			System.exit(0);	// ����
		} else {
			return;
		}
	}
}
