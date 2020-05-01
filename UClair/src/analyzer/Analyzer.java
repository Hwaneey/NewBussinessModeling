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

	private static boolean _autohideAll = false;			// 자동 숨기기
	private static WindowAdapter _windowListener;			// 창 종료시 클리어를 위한 리스너
	
	private static DocumentPane _workspacePane;				// 워크스페이스 패널
	
	private static JMenu fileMenu;								// 메뉴바 - 파일 
	private static JMenu analyzeMenu;						// 메뉴바 - 분석 
	private static JMenu testCaseMenu;						// 메뉴바 - 테스트 케이스
	private static JMenu helpMenu;							// 메뉴바 - 도움말
	private static JMenu viewMenu;							// 메뉴바 - 뷰

	public static JMenuItem _redoMenuItem;
	public static JMenuItem _undoMenuItem;

	private static JTree analysorTree;						// 분석메뉴 트리
	private static byte[] _fullScreenLayout;				// 전체화면 레이아웃 정보

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
		// 창을 닫을 때 창을 클리어하기 위한 리스너 추가
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
		// 프로필 키 설정
		_frame.getDockingManager().setProfileKey(PROFILE_NAME);

		// Uses light-weight outline. There are several options here.
		// 얇은 아웃라인 사용.
		_frame.getDockingManager().setOutlineMode(DockingManager.FULL_OUTLINE_MODE);
		
		// add tool bar
		// 툴바 추가
		_frame.getDockableBarManager().setProfileKey(PROFILE_NAME);
		//_frame.getDockableBarManager().addDockableBar(createToolBar());

		// add menu bar
		// 메뉴 바 추가
		_frame.setJMenuBar(createMenuBar());

		// Sets the number of steps you allow user to undo.
		// 되돌리기 기억 횟수 지정
		_frame.getDockingManager().setUndoLimit(10);
		_frame.getDockingManager().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				//refreshUndoRedoMenuItems();
			}
		});

		_frame.getDockingManager().beginLoadLayoutData();
		_frame.getDockingManager().setInitSplitPriority(DockingManager.SPLIT_EAST_WEST_SOUTH_NORTH);
        
		// add all dockable frames
		// 기본 도킹 프레임 추가 
		// 메시지 프레임, 분석기 프레임
		_frame.getDockingManager().addFrame(createMessageFrame());
		_frame.getDockingManager().addFrame(createAnalyzerFrame());
		
		// add workspace frames
		// 워크스페이스 문서탭 추가, 설정
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
		// 워크스페이스 영역에 도킹 허용하지 않기
		_frame.getDockingManager().getWorkspace().setAcceptDockableFrame(false);
		_frame.getDockingManager().getWorkspace().setAdjustOpacityOnFly(true);

		// load layout information from previous session. This indicates the end of beginLoadLayoutData() method above.
		// 이전 세션의 레이아웃 정보 로드 
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
	 * @설명 		: 워크스페이스 문서탭
	 * @변경이력 	:
	 *******************************/
	private static DocumentPane createDocumentTabs() {	
		DocumentPane documentPane = new DocumentPane() {
			@Override
			 protected IDocumentGroup createDocumentGroup() {
                IDocumentGroup group = super.createDocumentGroup();
                if (group instanceof JideTabbedPane) {
                    ((JideTabbedPane) group).addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {		// 워크스페이스 문서탭 더블클릭 시 전체화면
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
	 * @설명 		: 도킹 프레임 생성
	 * @변경이력 	:
	 *******************************/
	protected static DockableFrame createDockableFrame(String key, Icon icon) {
		DockableFrame frame = new DockableFrame(key, icon);
		//frame.setPreferredSize(new Dimension(200, 200));
		return frame;
	}

	/*******************************
	 * @date	: 2020. 4. 28.
	 * @설명 		: 분석기 패널
	 * @변경이력 	:
	 *******************************/
	protected static DockableFrame createAnalyzerFrame() {
		analysorTree = new DefaultTree();
		analysorTree.setVisible(false);
		
		DockableFrame frame = createDockableFrame("분석기", new ImageIcon(AnalyzerIconFactory.ANALYZE));
		frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		frame.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
		frame.getContext().setInitIndex(0);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.add(createScrollPane(analysorTree));

		return frame;
	}

	/*******************************
	 * @date	: 2020. 4. 28.
	 * @설명 		: 메시지 패널
	 * @변경이력 	:
	 *******************************/
	protected static DockableFrame createMessageFrame() {
		DockableFrame frame = createDockableFrame("메시지", new ImageIcon(AnalyzerIconFactory.MESSAGE));
		frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		frame.getContext().setInitSide(DockContext.DOCK_SIDE_SOUTH);

		JTabbedPane jtab = new JTabbedPane();
		jtab.addTab("System out", new JTextArea("System out 영역"));
		jtab.addTab("System err", new JTextArea("System err 영역"));

		frame.add(jtab);
		return frame;
	}

	/*******************************
	 * @date	: 2020. 4. 28.
	 * @설명 		: 클릭시 생성되는 테이블 패널 및 스크롤 기능
	 * @변경이력 	:
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
	 * @설명 		: 툴바
	 * @변경이력 	:
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
	 * @변경이력 	:
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

		item = new JMenuItem("분석기", new ImageIcon(AnalyzerIconFactory.ANALYZE));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		item.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (container instanceof DockableHolder) {
					((DockableHolder) container).getDockingManager().showFrame("분석기");
				}
			}
		});
		viewMenu.add(item);

		item = new JMenuItem("메시지", new ImageIcon(AnalyzerIconFactory.MESSAGE));
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
				openExcel();
			}
		});
		menu.add(fileMenuItem1);

		//분석기 메뉴 - 프로젝트 열기
		menu.add(actionFactory.getAction(AnalyzerActionFactory.OPEN_PROJECT));

		//분석기 메뉴 - 프로젝트 저장
		JMenuItem fileMenuItem2 = new JMenuItem("프로젝트 저장");
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
		
		//분석기 메뉴 - 프로젝트 닫기
		menu.add(actionFactory.getAction(AnalyzerActionFactory.CLOSE_PROJECT));

		menu.addSeparator();

		//분석기 메뉴 - 프로젝트 종료
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

		menu.add(new JMenuItem(actionFactory.getAction(500))); 					//존재하지 않는 태그 분석
		menu.add(new JMenuItem(actionFactory.getAction(501))); 					//가상태그 종속성 분석
		menu.add(new JMenuItem(actionFactory.getAction(502)));				 	//물리주소 종속성 분석
		menu.add(new JMenuItem(actionFactory.getAction(503))); 					//객체태그 연결정보 분석
		menu.add(new JMenuItem(actionFactory.getAction(504))); 					//이벤트 종속성 분석
		menu.addSeparator();
		menu.add(new JMenuItem(actionFactory.getAction(510))); 					//계산 스크립트 검증
		menu.add(new JMenuItem(actionFactory.getAction(505))); 					//객체효과 양립성 분석

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

		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.IO_TEST_CASE_GENERATOR))); 				//IO 테스트 케이스 생성기
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.WINDOW_TEST_CASE_GENERATOR))); 			//화면 테스트 케이스 생성기
		menu.addSeparator();		
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.PROJECT_INFO))); 							//프로젝트 정보
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
	private static void openExcel() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("엑셀 통합 문서(*.xlsx)","xlsx"));
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(false);
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			String path = fc.getSelectedFile().getPath();
			String name = fc.getSelectedFile().getName();
			
			SortableTable table = ExcelConnector.readTableFromExcel(path);
			if (_workspacePane.getDocument(path) != null) {	// 이미 파일이 열려있는 경우
				_workspacePane.setActiveDocument(path);
			} else {										// 새로 여는 경우
				final DocumentComponent document = new DocumentComponent(new JScrollPane(table), path, name, new ImageIcon(AnalyzerIconFactory.EXCEL));
				document.setDefaultFocusComponent(table);
				document.addDocumentComponentListener(new DocumentComponentAdapter() {
					@Override
					public void documentComponentClosing(DocumentComponentEvent e) {
						int ret = JOptionPane.showConfirmDialog(_frame, "저장하시겠습니까?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
						if (ret == JOptionPane.YES_OPTION) {
							// 저장
							document.setAllowClosing(true);
						}
						else if (ret == JOptionPane.NO_OPTION) {
							// 저장안함
							document.setAllowClosing(true);
						}
						else if (ret == JOptionPane.CANCEL_OPTION) {
							// 취소
							document.setAllowClosing(false);
						}
					}
				});
				_workspacePane.openDocument(document);
			}
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

	protected Project currentProject;					// 새로운 라이브러리 추가 필요!!
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
			_frame.setTitle(TITLE + " │ "+ currentProject.getProjectPath());
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
