package analyzer;
/*
 * @(#)DockingFrameworkDemo.java
 *
 * Copyright 2002 - 2003 JIDE Software. All rights reserved.
 */

import com.jidesoft.action.CommandBarFactory;
import com.jidesoft.docking.*;
import com.jidesoft.icons.JideIconsFactory;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideMenu;
import com.jidesoft.swing.JideScrollPane;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.utils.Lm;

import excel.ExcelConnector;
import util.DefaultTree;

import org.hibernate.cfg.annotations.reflection.XMLContext.Default;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * This is a sample program for JIDE Docking Framework. It will create a JFrame with about 20 dockable frames to show
 * the features of JIDE Docking Framework. <br> Required jar files: jide-common.jar, jide-dock.jar <br> Required L&F:
 * Jide L&F extension required
 */
public class UClairAnalyzer extends DefaultDockableHolder {

	private static UClairAnalyzer _frame;
	public static String _lastDirectory = ".";

	private static final String PROFILE_NAME = "UClair Analyzer";

	private static boolean _autohideAll = false;
	private static WindowAdapter _windowListener;
	public static JMenuItem _redoMenuItem;
	public static JMenuItem _undoMenuItem;
	
	/*
	 * 
	 */
	private static JTree analysorTree;
	private static String projectPath;
	private static HashMap<String, JTable> dockMap;
	
	public UClairAnalyzer(String title) throws HeadlessException {
		super(title);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
				LookAndFeelFactory.installJideExtension(LookAndFeelFactory.XERTO_STYLE);
				showDemo(true);
			}
		});

	}

	public static DefaultDockableHolder showDemo(final boolean exit) {
		dockMap = new HashMap<String, JTable>();
		
		_frame = new UClairAnalyzer("UClair Analyzer");
		_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		_frame.getDockingManager().setXmlFormat(true);

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

		// comment this if you don't want to use javax pref
		//        _frame.getDockingManager().setUsePref(false);

		// Uses light-weight outline. There are several options here.
		_frame.getDockingManager().setOutlineMode(DockingManager.FULL_OUTLINE_MODE);

		// uncomment following lines to adjust the sliding speed of autohide frame
		//        _frame.getDockingManager().setInitDelay(100);
		//        _frame.getDockingManager().setSteps(1);
		//        _frame.getDockingManager().setStepDelay(0);

		// uncomment following lines if you want to customize the tabbed pane used in Docking Framework
		//        _frame.getDockingManager().setTabbedPaneCustomizer(new DockingManager.TabbedPaneCustomizer(){
		//            public void customize(JideTabbedPane tabbedPane) {
		//                tabbedPane.setShowCloseButton(true);
		//                tabbedPane.setShowCloseButtonOnTab(true);
		//            }
		//        });

		// uncomment following lines if you want to customize the popup menu of DockableFrame
		//       _frame.getDockingManager().setPopupMenuCustomizer(new com.jidesoft.docking.PopupMenuCustomizer() {
		//           public void customizePopupMenu(JPopupMenu menu, DockingManager dockingManager, DockableFrame dockableFrame, boolean onTab) {
		//              // ... do customization here
		//           }
		//
		//           public boolean isPopupMenuShown(DockingManager dockingManager, DockableFrame dockableFrame, boolean onTab) {
		//               return false;
		//           }
		//       });

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

		// uncomment following line(s) if you want to limit certain feature.
		// If you uncomment all four lines, then the dockable frame will not
		// be moved anywhere.
		//_frame.getDockingManager().setRearrangable(false);
		//_frame.getDockingManager().setAutohidable(false);
		//_frame.getDockingManager().setHidable(false);
		//_frame.getDockingManager().setFloatable(false);

		_frame.toFront();
		return _frame;
	}

	private static void clearUp() {
		_frame.removeWindowListener(_windowListener);
		_windowListener = null;

		if (_frame.getDockingManager() != null) {
			_frame.getDockingManager().saveLayoutData();
		}

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

	static int i = 0;
	
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
		JMenu menu = new JideMenu("�м�");

		menu.add(new JMenuItem("�������� �ʴ� �±� �м�"));
		menu.add(new JMenuItem("�����±� ���Ӽ� �м�"));
		menu.add(new JMenuItem("�����ּ� ���Ӽ� �м�"));
		menu.add(new JMenuItem("��ü�±� �������� �м�"));
		menu.add(new JMenuItem("�̺�Ʈ ���Ӽ� �м�"));
		menu.addSeparator();
		menu.add(new JMenuItem("��� ��ũ��Ʈ ����"));
		menu.add(new JMenuItem("��üȿ�� �縳�� �м�"));
		return menu;
	}

	private static JMenu createTestCaseMenu() {
		JMenu menu = new JideMenu("�׽�Ʈ ���̽�");

		menu.add(new JMenuItem("IO �׽�Ʈ ���̽� ������"));
		menu.add(new JMenuItem("ȭ�� �׽�Ʈ ���̽� ������"));
		
		return menu;
	}
	
	private static JMenu createFileMenu() {
		// Build the File menu.
		JMenu menu = new JideMenu("����");
		menu.setMnemonic(KeyEvent.VK_F);

		// The JMenuItem for File
		JMenuItem fileMenuItem1 = new JMenuItem("������Ʈ ����");
		fileMenuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK | ActionEvent.ALT_MASK));
		fileMenuItem1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				openProject();
			}
		});
		menu.add(fileMenuItem1);
		
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
	
	private static void openProject() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setMultiSelectionEnabled(false);
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			// ���� ���� .xlsx
			final String pattern = ".xlsx";
			
			// �����ִ� �ƿ�ǲ ������ ��� ����
			for (String key : dockMap.keySet()) {
				_frame.getDockingManager().removeFrame(key.substring(key.lastIndexOf('\\') + 1));
			}
			dockMap.clear();
			projectPath = fc.getSelectedFile().getPath();
			String name = fc.getSelectedFile().getName();
			DefaultTreeModel model = (DefaultTreeModel)analysorTree.getModel();
			DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
			DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode(name);
			root.removeAllChildren();
			
			File file = new File(projectPath);
			String fileList[] = file.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(pattern); 
				}
			});

			if(fileList.length > 0){
				for(int i=0; i < fileList.length; i++){
					newRoot.add(new DefaultMutableTreeNode(fileList[i]));
				}
			}
			model.setRoot(newRoot);
			analysorTree.setVisible(true);
			analysorTree.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) analysorTree.getLastSelectedPathComponent();
						if (node == null || node.getChildCount() > 0 || node.isRoot() || projectPath.isEmpty()) return;

						// filename			: ���ϸ�
						// key 				: ���� ���
						// extension		: ���� Ȯ����
						String filename = node.toString();
						String key = projectPath + '\\' + filename;
						String extension = filename.substring(filename.lastIndexOf('.'));
						
						int dialogResult = JOptionPane.showConfirmDialog (null, filename +"��(��) �����Ͻðڽ��ϱ�?","Confirm",JOptionPane.YES_NO_OPTION);
						if(dialogResult == JOptionPane.NO_OPTION){
						  return;
						}
						
						/*
						if (!extension.equals(".xlsx")) {
							JOptionPane.showMessageDialog(null, "�ùٸ��� ���� Ȯ�����Դϴ�.", "���� ����", JOptionPane.WARNING_MESSAGE);
							return;
						}
						*/
					
						if (!dockMap.containsKey(key)) {
							_frame.getDockingManager().addFrame(createOutputFrame(filename, key));
						} 
						_frame.getDockingManager().activateFrame(filename);
					}
				}
			});
			model.reload();
		}
	}
}
