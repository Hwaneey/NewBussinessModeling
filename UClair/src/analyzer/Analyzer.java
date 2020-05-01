package analyzer;
/*
 * @(#)DockingFrameworkDemo.java
 *
 * Copyright 2002 - 2003 JIDE Software. All rights reserved.
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jidesoft.action.CommandBar;
import com.jidesoft.action.DefaultDockableBarDockableHolder;
import com.jidesoft.action.DockableBarContext;
import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.docking.DockableHolder;
import com.jidesoft.docking.DockingManager;
import com.jidesoft.document.DocumentComponent;
import com.jidesoft.document.DocumentComponentAdapter;
import com.jidesoft.document.DocumentComponentEvent;
import com.jidesoft.document.DocumentPane;
import com.jidesoft.document.IDocumentGroup;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.JideMenu;
import com.jidesoft.swing.JideScrollPane;
import com.jidesoft.swing.JideTabbedPane;
import com.naru.uclair.common.SystemResourceManager;
import com.naru.uclair.draw.util.WindowSelectDialog;
import com.naru.uclair.project.Project;
import com.naru.uclair.script.EngineManager;
import com.naru.uclair.util.TagListWindowDialog;
import com.naru.uclair.workspace.HMIWorkspace;

import analyzer.constants.AnalyzerConstants;
import analyzer.icon.AnalyzerIconFactory;
import analyzer.listener.AnalyzerEventListener;
import analyzer.util.DefaultTree;
import excel.ExcelConnector;


public class Analyzer extends DefaultDockableBarDockableHolder  {

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
	
	private static final String TITLE = AnalyzerConstants.getString(AnalyzerConstants.ANALYZER_TITLE);
	private static final String PROFILE_NAME = "UClair Analyzer";

	private static boolean _autohideAll = false;			// �ڵ� �����
	private static WindowAdapter _windowListener;			// â ����� Ŭ��� ���� ������
	
	private static DocumentPane _workspacePane;				// ��ũ�����̽� �г�
	
	private static JMenu fileMenu;								// �޴��� - ���� 
	private static JMenu analyzeMenu;						// �޴��� - �м� 
	private static JMenu testCaseMenu;						// �޴��� - �׽�Ʈ ���̽�
	private static JMenu helpMenu;							// �޴��� - ����
	private static JMenu viewMenu;							// �޴��� - ��

	public static JMenuItem _redoMenuItem;
	public static JMenuItem _undoMenuItem;

	private static JTree analysorTree;						// �м��޴� Ʈ��
	private static byte[] _fullScreenLayout;				// ��üȭ�� ���̾ƿ� ����

	public Analyzer(String title) throws HeadlessException {
		super(title);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//dockMap = new HashMap<String, JTable>();
				LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
				LookAndFeelFactory.installJideExtension(LookAndFeelFactory.EXTENSION_STYLE_XERTO);
				new Analyzer(new JFrame());
				showDemo(true);
			}
		});
	}


	public static DefaultDockableBarDockableHolder showDemo(final boolean exit) {
		_frame = new Analyzer(TITLE);
		_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		_frame.setIconImage(new ImageIcon(AnalyzerIconFactory.ANALYZE).getImage());
		_frame.getDockingManager().setXmlFormat(true);

		// add a window listener to do clear up when windows closing.
		// â�� ���� �� â�� Ŭ�����ϱ� ���� ������ �߰�
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
		// ������ Ű ����
		_frame.getDockingManager().setProfileKey(PROFILE_NAME);

		// Uses light-weight outline. There are several options here.
		// ���� �ƿ����� ���.
		_frame.getDockingManager().setOutlineMode(DockingManager.FULL_OUTLINE_MODE);
		
		// add tool bar
		// ���� �߰�
		_frame.getDockableBarManager().setProfileKey(PROFILE_NAME);
		//_frame.getDockableBarManager().addDockableBar(createToolBar());

		// add menu bar
		// �޴� �� �߰�
		_frame.setJMenuBar(createMenuBar());

		// Sets the number of steps you allow user to undo.
		// �ǵ����� ��� Ƚ�� ����
		_frame.getDockingManager().setUndoLimit(10);
		_frame.getDockingManager().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				//refreshUndoRedoMenuItems();
			}
		});

		_frame.getDockingManager().beginLoadLayoutData();
		_frame.getDockingManager().setInitSplitPriority(DockingManager.SPLIT_EAST_WEST_SOUTH_NORTH);
        
		// add all dockable frames
		// �⺻ ��ŷ ������ �߰� 
		// �޽��� ������, �м��� ������
		_frame.getDockingManager().addFrame(createMessageFrame());
		_frame.getDockingManager().addFrame(createAnalyzerFrame());
		
		// add workspace frames
		// ��ũ�����̽� ������ �߰�, ����
		_workspacePane = createDocumentTabs();
		_workspacePane.setTabbedPaneCustomizer(new DocumentPane.TabbedPaneCustomizer() {
            public void customize(final JideTabbedPane tabbedPane) {
                tabbedPane.setShowCloseButton(true);							
                tabbedPane.setUseDefaultShowCloseButtonOnTab(false);			 
                tabbedPane.setShowCloseButtonOnTab(true);
            }
        });
		
		_frame.getDockingManager().getWorkspace().setLayout(new BorderLayout());
		_frame.getDockingManager().getWorkspace().add((Component) _workspacePane, BorderLayout.CENTER);
		
		// disallow drop dockable frame to workspace area
		// ��ũ�����̽� ������ ��ŷ ������� �ʱ�
		_frame.getDockingManager().getWorkspace().setAcceptDockableFrame(false);
		_frame.getDockingManager().getWorkspace().setAdjustOpacityOnFly(true);

		// load layout information from previous session. This indicates the end of beginLoadLayoutData() method above.
		// ���� ������ ���̾ƿ� ���� �ε� 
		_frame.getLayoutPersistence().loadLayoutData();
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
	

	/*******************************
	 * @date	: 2020. 4. 28.
	 * @���� 		: ��ũ�����̽� ������
	 * @�����̷� 	:
	 *******************************/
	private static DocumentPane createDocumentTabs() {	
		DocumentPane documentPane = new DocumentPane() {
			@Override
			 protected IDocumentGroup createDocumentGroup() {
                IDocumentGroup group = super.createDocumentGroup();
                if (group instanceof JideTabbedPane) {
                    ((JideTabbedPane) group).addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {		// ��ũ�����̽� ������ ����Ŭ�� �� ��üȭ��
                            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                                if (!_autohideAll) {
                                    _fullScreenLayout = _frame.getDockingManager().getLayoutRawData();
                                    _frame.getDockingManager().autohideAll();
                                    _autohideAll = true;
                                }
                                else {
                                    if (_fullScreenLayout != null) {
                                        _frame.getDockingManager().setLayoutRawData(_fullScreenLayout);
                                    }
                                    _autohideAll = false;
                                }
                                Component lastFocusedComponent = _workspacePane.getActiveDocument().getLastFocusedComponent();
                                if (lastFocusedComponent != null) {
                                    lastFocusedComponent.requestFocusInWindow();
                                }
                            }
                        }
                    });
                }
                return group;
            }
		};
		documentPane.setTabPlacement(javax.swing.JTabbedPane.TOP);
		return documentPane;
	}

	/*******************************
	 * @date	: 2020. 4. 28.
	 * @���� 		: ��ŷ ������ ����
	 * @�����̷� 	:
	 *******************************/
	protected static DockableFrame createDockableFrame(String key, Icon icon) {
		DockableFrame frame = new DockableFrame(key, icon);
		//frame.setPreferredSize(new Dimension(200, 200));
		return frame;
	}

	/*******************************
	 * @date	: 2020. 4. 28.
	 * @���� 		: �м��� �г�
	 * @�����̷� 	:
	 *******************************/
	protected static DockableFrame createAnalyzerFrame() {
		analysorTree = new DefaultTree();
		analysorTree.setVisible(false);
		
		DockableFrame frame = createDockableFrame("�м���", new ImageIcon(AnalyzerIconFactory.ANALYZE));
		frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		frame.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
		frame.getContext().setInitIndex(0);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.add(createScrollPane(analysorTree));

		return frame;
	}

	/*******************************
	 * @date	: 2020. 4. 28.
	 * @���� 		: �޽��� �г�
	 * @�����̷� 	:
	 *******************************/
	protected static DockableFrame createMessageFrame() {
		DockableFrame frame = createDockableFrame("�޽���", new ImageIcon(AnalyzerIconFactory.MESSAGE));
		frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		frame.getContext().setInitSide(DockContext.DOCK_SIDE_SOUTH);

		JTabbedPane jtab = new JTabbedPane();
		jtab.addTab("System out", new JTextArea("System out ����"));
		jtab.addTab("System err", new JTextArea("System err ����"));

		frame.add(jtab);
		return frame;
	}

	/*******************************
	 * @date	: 2020. 4. 28.
	 * @���� 		: Ŭ���� �����Ǵ� ���̺� �г� �� ��ũ�� ���
	 * @�����̷� 	:
	 *******************************/
	protected static DockableFrame createOutputFrame(String key, String path) {
		//dockMap.put(path, ExcelConnector.readTableFromExcel(path));
		DockableFrame frame = createDockableFrame(key, new ImageIcon("../UClair/img/excel.png"));
		frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		frame.getContext().setInitSide(DockContext.DOCK_SIDE_CENTER);
		//frame.add(createScrollPane(dockMap.get(path)));
		return frame;
	}

	private static JScrollPane createScrollPane(Component component) {
		JScrollPane pane = new JideScrollPane(component);
		pane.setVerticalScrollBarPolicy(JideScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return pane;
	}

	/*******************************
	 * @date	: 2020. 4. 28.
	 * @���� 		: ����
	 * @�����̷� 	:
	 *******************************/
	protected static CommandBar createToolBar() {
		CommandBar commandBar = new CommandBar("ToolBar");
		commandBar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
		commandBar.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
		commandBar.setInitIndex(0);
		
		commandBar.add(new JideButton(new ImageIcon(AnalyzerIconFactory.UNDO)));
		commandBar.add(new JideButton(new ImageIcon(AnalyzerIconFactory.REDO)));
		return commandBar;
	}

	/*******************************
	 * @date	: 2020. 4. 28.
	 * @���� 		: �޴���
	 * @�����̷� 	:
	 *******************************/
	protected static JMenuBar createMenuBar() {

		JMenuBar menu = new JMenuBar();

		fileMenu = createFileMenu();
		analyzeMenu = createAnalyzeMenu();
		testCaseMenu = createTestCaseMenu();
		helpMenu = createHelpMenu();
		viewMenu = createViewMenu(_frame);

		analyzeMenu.setEnabled(false);
		testCaseMenu.setEnabled(false);

		menu.add(fileMenu);
		menu.add(analyzeMenu);
		menu.add(testCaseMenu);
		menu.add(helpMenu);
		menu.add(viewMenu);

		return menu;
	}

	/*******************************
	 * @date	: 2020. 4. 28.
	 * @���� 		: �� �޴�
	 * @�����̷� 	:
	 *******************************/
	public static JMenu createViewMenu(final Container container) {
		JMenuItem item;
		JMenu viewMenu = new JideMenu("��");

		item = new JMenuItem("���� ��");
		item.setMnemonic('N');
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
		item.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (container instanceof DockableHolder) {
					DockingManager dockingManager = ((DockableHolder) container).getDockingManager();
					String frameKey = dockingManager.getNextFrame(dockingManager.getActiveFrameKey());
					if (frameKey != null) {
						dockingManager.showFrame(frameKey);
					}
				}
			}
		});
		viewMenu.add(item);

		item = new JMenuItem("���� ��");
		item.setMnemonic('P');
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, InputEvent.SHIFT_MASK));
		item.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (container instanceof DockableHolder) {
					DockingManager dockingManager = ((DockableHolder) container).getDockingManager();
					String frameKey = dockingManager.getPreviousFrame(dockingManager.getActiveFrameKey());
					if (frameKey != null) {
						dockingManager.showFrame(frameKey);
					}
				}
			}
		});
		viewMenu.add(item);

		viewMenu.addSeparator();

		item = new JMenuItem("�м���", new ImageIcon(AnalyzerIconFactory.ANALYZE));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		item.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (container instanceof DockableHolder) {
					((DockableHolder) container).getDockingManager().showFrame("�м���");
				}
			}
		});
		viewMenu.add(item);

		item = new JMenuItem("�޽���", new ImageIcon(AnalyzerIconFactory.MESSAGE));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		item.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (container instanceof DockableHolder) {
					((DockableHolder) container).getDockingManager().showFrame("�޽���");
				}
			}
		});
		viewMenu.add(item);

		return viewMenu;
	}
	/*******************************
	 * @date	: 2020. 4. 28.
	 * @���� 		: ���� �޴�
	 * @�����̷� 	:
	 *******************************/
	private static JMenu createFileMenu() {
		AnalyzerActionFactory actionFactory = AnalyzerActionFactory.getFactory();	

		JMenu menu = new JideMenu("����");
		menu.setMnemonic(KeyEvent.VK_F);

		JMenuItem fileMenuItem1 = new JMenuItem("�׽�Ʈ ���̺� ����");
		fileMenuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openExcel();
			}
		});
		menu.add(fileMenuItem1);

		//�м��� �޴� - ������Ʈ ����
		menu.add(actionFactory.getAction(AnalyzerActionFactory.OPEN_PROJECT));

		//�м��� �޴� - ������Ʈ ����
		JMenuItem fileMenuItem2 = new JMenuItem("������Ʈ ����");
		fileMenuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.ALT_MASK));
		fileMenuItem2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				//for (String key : dockMap.keySet()) {
				//	ExcelConnector.writeExcelFromTable(key, dockMap.get(key));
				//}
			}
		});
		menu.add(fileMenuItem2);
		
		//�м��� �޴� - ������Ʈ �ݱ�
		menu.add(actionFactory.getAction(AnalyzerActionFactory.CLOSE_PROJECT));

		menu.addSeparator();

		//�м��� �޴� - ������Ʈ ����
		JMenuItem fileMenuItem4 = new JMenuItem("����");
		fileMenuItem4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		fileMenuItem4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);
			}
		});
		menu.add(fileMenuItem4);
		
		return menu;
	}
	/*******************************
	 * @date	: 2020. 4. 28.
	 * @���� 		: �м� �޴�
	 * @�����̷� 	: 
	 *******************************/
	private static JMenu createAnalyzeMenu() {
		AnalyzerActionFactory actionFactory = AnalyzerActionFactory.getFactory();

		JMenu menu = new JideMenu("�м�");

		menu.add(new JMenuItem(actionFactory.getAction(500))); 					//�������� �ʴ� �±� �м�
		menu.add(new JMenuItem(actionFactory.getAction(501))); 					//�����±� ���Ӽ� �м�
		menu.add(new JMenuItem(actionFactory.getAction(502)));				 	//�����ּ� ���Ӽ� �м�
		menu.add(new JMenuItem(actionFactory.getAction(503))); 					//��ü�±� �������� �м�
		menu.add(new JMenuItem(actionFactory.getAction(504))); 					//�̺�Ʈ ���Ӽ� �м�
		menu.addSeparator();
		menu.add(new JMenuItem(actionFactory.getAction(510))); 					//��� ��ũ��Ʈ ����
		menu.add(new JMenuItem(actionFactory.getAction(505))); 					//��üȿ�� �縳�� �м�

		return menu;
	}
	/*******************************
	 * @date	: 2020. 4. 28.
	 * @���� 		: �׽�Ʈ ���̽� �޴�
	 * @�����̷� 	:  
	 *******************************/
	private static JMenu createTestCaseMenu() {
		AnalyzerActionFactory actionFactory = AnalyzerActionFactory.getFactory();
		JMenu menu = new JideMenu("�׽�Ʈ ���̽�");

		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.IO_TEST_CASE_GENERATOR))); 				//IO �׽�Ʈ ���̽� ������
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.WINDOW_TEST_CASE_GENERATOR))); 			//ȭ�� �׽�Ʈ ���̽� ������
		menu.addSeparator();		
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.PROJECT_INFO))); 							//������Ʈ ����
		return menu;
	}

	/*******************************
	 * @date	: 2020. 4. 28.
	 * @���� 		: ���� �޴�
	 * @�����̷� 	:
	 *******************************/
	private static JMenu createHelpMenu() {
		JMenu menu = new JideMenu("����");

		menu.add(new JMenuItem("���� ����(����)"));
		menu.addSeparator();
		menu.add(new JMenuItem("���� ������ ����"));
		menu.add(new JMenuItem("�ý��� ������ ����"));
		return menu;
	}

	/*******************************
	 * @date	: 2020. 4. 28.
	 * @���� 		: ���� ���� �ε� �� �г� ����
	 * @�����̷� 	:
	 *******************************/
	private static void openExcel() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("���� ���� ����(*.xlsx)","xlsx"));
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(false);
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			String path = fc.getSelectedFile().getPath();
			String name = fc.getSelectedFile().getName();
			
			SortableTable table = ExcelConnector.readTableFromExcel(path);
			if (_workspacePane.getDocument(path) != null) {	// �̹� ������ �����ִ� ���
				_workspacePane.setActiveDocument(path);
			} else {										// ���� ���� ���
				final DocumentComponent document = new DocumentComponent(new JScrollPane(table), path, name, new ImageIcon(AnalyzerIconFactory.EXCEL));
				document.setDefaultFocusComponent(table);
				document.addDocumentComponentListener(new DocumentComponentAdapter() {
					@Override
					public void documentComponentClosing(DocumentComponentEvent e) {
						int ret = JOptionPane.showConfirmDialog(_frame, "�����Ͻðڽ��ϱ�?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
						if (ret == JOptionPane.YES_OPTION) {
							// ����
							document.setAllowClosing(true);
						}
						else if (ret == JOptionPane.NO_OPTION) {
							// �������
							document.setAllowClosing(true);
						}
						else if (ret == JOptionPane.CANCEL_OPTION) {
							// ���
							document.setAllowClosing(false);
						}
					}
				});
				_workspacePane.openDocument(document);
			}
		}
	}

	//========================================================
	// ���ο� �۾� ���� 
	//========================================================
	/************************************************
	 * @date	: 2020. 4. 27.
	 * @����  	: �⺻���� �ʱ�ȭ�� �����ϴ� �κ�   
	 * @�����̷� 	: 
	 ************************************************/

	private void initialize() {
		AnalyzerActionFactory.createInstance(this);		
	}

	protected Project currentProject;					// ���ο� ���̺귯�� �߰� �ʿ�!!
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
		// �м��޴��� �׽�Ʈ ���̽� �޴� Ȱ��ȭ
		boolean menuEnabled = (newProject != null);
		analyzeMenu.setEnabled(menuEnabled);
		testCaseMenu.setEnabled(menuEnabled);
		// �м��г� Ʈ�� Ȱ��ȭ
		analysorTree.setVisible(menuEnabled);

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
			_frame.setTitle(TITLE + " �� "+ currentProject.getProjectPath());
		} else {
			_frame.setTitle(TITLE);
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
