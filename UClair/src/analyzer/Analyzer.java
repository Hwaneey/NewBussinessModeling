package analyzer;

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
	
	private static JMenu fileMenu;
	private static JMenu analyzeMenu;
	private static JMenu testCaseMenu;
	private static JMenu helpMenu;
	private static JMenu viewMenu;
	
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
		_frame.setIconImage(new ImageIcon("../UClair/img/magnifying-glass.png").getImage());

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
		_frame.dispose();
		_frame = null;
	}
	
	/*******************************
	 * @date	: 2020. 4. 28.
	 * @설명 		: 도킹 생성
	 * @변경이력 	:
	 *******************************/
	protected static DockableFrame createDockableFrame(String key, Icon icon) {
		DockableFrame frame = new DockableFrame(key, icon);
		frame.setPreferredSize(new Dimension(200, 200));
		return frame;
	}
	
	/*******************************
	 * @date	: 2020. 4. 28.
	 * @설명 		: 분석기 패널
	 * @변경이력 	:
	 *******************************/
	protected static DockableFrame createAnalyzerFrame() {
		DockableFrame frame = createDockableFrame("분석기", new ImageIcon("../UClair/img/magnifying-glass.png"));
		frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		frame.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
		frame.getContext().setInitIndex(0);
		frame.getContentPane().setLayout(new BorderLayout());
		analysorTree = new DefaultTree();

		frame.add(createScrollPane(analysorTree));
		analysorTree.setVisible(false);
		
		return frame;
	}
	
	/*******************************
	 * @date	: 2020. 4. 28.
	 * @설명 		: 메시지 패널
	 * @변경이력 	:
	 *******************************/
	protected static DockableFrame createMessageFrame() {
		DockableFrame frame = createDockableFrame("메시지", new ImageIcon("../UClair/img/computer.png"));
		frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		frame.getContext().setInitSide(DockContext.DOCK_SIDE_SOUTH);

		JTabbedPane jtab = new JTabbedPane();
		jtab.addTab("System out", new JPanel());
		jtab.addTab("System err", new JPanel());

		frame.add(jtab);
		return frame;
	}
	
	/*******************************
	 * @date	: 2020. 4. 28.
	 * @설명 		: 클릭시 생성되는 테이블 패널 및 스크롤 기능
	 * @변경이력 	:
	 *******************************/
	protected static DockableFrame createOutputFrame(String key, String path) {
		dockMap.put(path, ExcelConnector.readTableFromExcel(path));
		DockableFrame frame = createDockableFrame(key, new ImageIcon("../UClair/img/excel.png"));
		frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		frame.getContext().setInitSide(DockContext.DOCK_SIDE_CENTER);
		frame.add(createScrollPane(dockMap.get(path)));
		return frame;
	}
	private static JScrollPane createScrollPane(Component component) {
		JScrollPane pane = new JideScrollPane(component);
		pane.setVerticalScrollBarPolicy(JideScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return pane;
	}
	
	/*******************************
	 * @date	: 2020. 4. 28.
	 * @설명 		: 메뉴바
	 * @변경이력 	:
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
	 * @설명 		: 뷰 메뉴
	 * @변경이력 	: 2020.04.28
	 *******************************/
	public static JMenu createViewMenu(final Container container) {
        JMenuItem item;
        JMenu viewMenu = new JideMenu("뷰");

        item = new JMenuItem("다음 뷰");
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

        item = new JMenuItem("이전 뷰");
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

        item = new JMenuItem("분석기", new ImageIcon("../UClair/img/magnifying-glass.png"));
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
        item.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (container instanceof DockableHolder) {
                    ((DockableHolder) container).getDockingManager().showFrame("분석기");
                }
            }
        });
        viewMenu.add(item);

        item = new JMenuItem("메시지", new ImageIcon("../UClair/img/computer.png"));
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
        item.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (container instanceof DockableHolder) {
                    ((DockableHolder) container).getDockingManager().showFrame("메시지");
                }
            }
        });
        viewMenu.add(item);

        return viewMenu;
    }
	/*******************************
	 * @date	: 2020. 4. 28.
	 * @설명 		: 파일 메뉴
	 * @변경이력 	:
	 *******************************/
	private static JMenu createFileMenu() {
		AnalyzerActionFactory actionFactory = AnalyzerActionFactory.getFactory();	
		
		JMenu menu = new JideMenu("파일");
		menu.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem fileMenuItem1 = new JMenuItem("테스트 테이블 열기");
		fileMenuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openProject();
			}
		});
		menu.add(fileMenuItem1);
		
		//분석기 프로젝트 로드
		menu.add(actionFactory.getAction(AnalyzerActionFactory.OPEN_PROJECT));
		
		
		JMenuItem fileMenuItem2 = new JMenuItem("프로젝트 저장");
		fileMenuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.ALT_MASK));
		fileMenuItem2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				for (String key : dockMap.keySet()) {
					ExcelConnector.writeExcelFromTable(key, dockMap.get(key));
				}
			}
		});
		menu.add(fileMenuItem2);
		menu.add(actionFactory.getAction(AnalyzerActionFactory.CLOSE_PROJECT));
		
		menu.addSeparator();
		
		
		JMenuItem fileMenuItem4 = new JMenuItem("종료");
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
	 * @설명 		: 분석 메뉴
	 * @변경이력 	:
	 *******************************/
	private static JMenu createAnalyzeMenu() {
		AnalyzerActionFactory actionFactory = AnalyzerActionFactory.getFactory();

		JMenu menu = new JideMenu("분석");
		
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.DANGLING_TAG_ANALYSIS))); //존재하지 않는 태그 분석
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.VIRTUAL_TAG_DEPENDENCY_ANALYSIS))); //가상태그 종속성 분석
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.PHYSICAL_ADRESS_DEPENDENCY_ANALYSIS))); //물리주소 종속성 분석
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.EACH_TAG_DEPENDENCY_ANALYSIS))); //객체태그 연결정보 분석
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.EVENT_TAG_DEPENDENCY_ANALYSIS))); //이벤트 종속성 분석
		menu.addSeparator();
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.SCRIPT_ANALYSIS))); //계산 스크립트 검증
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.OBJECT_EFFECT_COMPATIBILITY_ANALYSIS))); //객체효과 양립성 분석
		
		return menu;
	}
	/*******************************
	 * @date	: 2020. 4. 28.
	 * @설명 		: 테스트 케이스 메뉴
	 * @변경이력 	: 
	 *******************************/
	private static JMenu createTestCaseMenu() {
		AnalyzerActionFactory actionFactory = AnalyzerActionFactory.getFactory();
		JMenu menu = new JideMenu("테스트 케이스");

		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.IO_TEST_CASE_GENERATOR))); //IO 테스트 케이스 생성기
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.WINDOW_TEST_CASE_GENERATOR))); //화면 테스트 케이스 생성기
		menu.addSeparator();		
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.PROJECT_INFO))); //프로젝트 인포
		return menu;
	}
	
	/*******************************
	 * @date	: 2020. 4. 28.
	 * @설명 		: 도움말 메뉴
	 * @변경이력 	:
	 *******************************/
	private static JMenu createHelpMenu() {
		JMenu menu = new JideMenu("도움말");

		menu.add(new JMenuItem("도움말 보기(목차)"));
		menu.addSeparator();
		menu.add(new JMenuItem("정보 편집기 정보"));
		menu.add(new JMenuItem("시스템 편집기 정보"));
		return menu;
	}

	/*******************************
	 * @date	: 2020. 4. 28.
	 * @설명 		: 엑셀 파일 로드 및 패널 생성
	 * @변경이력 	:
	 *******************************/
	private static void openProject() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setMultiSelectionEnabled(false);
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			// 파일 필터 .xlsx
			final String pattern = ".xlsx";
			
			// 열려있는 아웃풋 프레임 모두 제거
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

						// filename			: 파일명
						// key 				: 파일 경로
						// extension		: 파일 확장자
						String filename = node.toString();
						String key = projectPath + '\\' + filename;
						String extension = filename.substring(filename.lastIndexOf('.'));
						
						/*
						if (!extension.equals(".xlsx")) {
							JOptionPane.showMessageDialog(null, "올바르지 않은 확장자입니다.", "열기 오류", JOptionPane.WARNING_MESSAGE);
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
	
	//========================================================
	// 새로운 작업 영역 
	//========================================================
	/************************************************
	 * @date	: 2020. 4. 27.
	 * @설명  	: 기본적인 초기화를 수행하는 부분   
	 * @변경이력 	: 
	 ************************************************/
	
	private void initialize() {
		AnalyzerActionFactory.createInstance(this);		
	}
	
	protected Project currentProject;	// 새로운 라이블러리 추가 필요!!
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
		// 분석메뉴와 테스트 케이스 메뉴 활성화
		boolean menuEnabled = (newProject != null);
		analyzeMenu.setEnabled(menuEnabled);
		testCaseMenu.setEnabled(menuEnabled);
		// 분석패널 트리 활성화
		analysorTree.setVisible(menuEnabled);
		
		if (null != currentProject) {
			// singleton 인스턴스의 dipose처리
			EngineManager.disposeEngines();
			SystemResourceManager.disposeResources();

			// WindowListDialog.setWindowPath(null);
			// 화면 선택창 변경.
			WindowSelectDialog.setWindowPath(null);
			TagListWindowDialog.setCurrentTagDictionary(null);
		}
		if (currentProject == newProject) {
			return;
		}

		currentProject = newProject;
		if (null != currentProject) {
			// 시스템 리소스 관리자 초기화
			final SystemResourceManager resourceManager = SystemResourceManager
					.createInstance(false);
			resourceManager.addTopUI(this);
			resourceManager.setProject(currentProject);
			
			// 기본 엔진(그루비) 생성
			final EngineManager scriptEngineManager = EngineManager.createInstance(false);

			// 정보 간의 링크된 속성들을 일관성 있게 유지시켜 주기 위한 객체.
			newProject.enableLinker();

			// TODO: 이 부분 확인 필요
			// 화면 선택 창 변경.
			//WindowSelectDialog.setWindowPath(currentProject
			//		.getWindowResourcePath());
			//TagListWindowDialog.setCurrentTagDictionary(currentProject
			//		.getTagDictionary());

			// Workspace에 열려진 프로젝트 내용 저장.
			final HMIWorkspace workspace = HMIWorkspace.getInstance();
			if (!workspace.isExist(currentProject)) {
				workspace.addProject(currentProject);
			}
			HMIWorkspace.getInstance().setStartProject(
					currentProject.getProjectPath());
			HMIWorkspace.getInstance().save();
			_frame.setTitle(PROFILE_NAME + " │ "+ currentProject.getProjectPath());
		} else {
			_frame.setTitle(PROFILE_NAME);
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
	 * Analyzer 이벤트 리스너 등록(프로젝트 변경 이벤트)
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
			System.exit(0);	// 종료
		} else {
			return;
		}
	}
}