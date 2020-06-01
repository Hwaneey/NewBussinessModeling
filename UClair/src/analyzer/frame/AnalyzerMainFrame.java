package analyzer.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

import com.jidesoft.action.DefaultDockableBarDockableHolder;
import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.docking.DockingManager;
import com.jidesoft.document.DocumentPane;
import com.jidesoft.swing.JideScrollPane;
import com.jidesoft.swing.JideTabbedPane;

import analyzer.Analyzer;
import analyzer.constants.AnalyzerConstants;
import analyzer.icon.AnalyzerIconFactory;
import analyzer.menu.MenuBar;

public class AnalyzerMainFrame {
	public static Analyzer _frame;
	public static String _lastDirectory = ".";
	public static final String TITLE = AnalyzerConstants.getString(AnalyzerConstants.ANALYZER_TITLE);
	private static final String PROFILE_NAME = "UClair Analyzer";
	private static WindowAdapter _windowListener; // 창 종료시 클리어를 위한 리스너
	public static DocumentPane _workspacePane; // 워크스페이스 패널
	public static JMenu analyzeMenu; // 메뉴바 - 분석
	public static JMenu testCaseMenu; // 메뉴바 - 테스트 케이스
	public static JMenuItem _redoMenuItem;
	public static JMenuItem _undoMenuItem;
	public static JTree analysorTree; // 분석메뉴 트리

	public static DefaultDockableBarDockableHolder show(final boolean exit) {
		_frame = new Analyzer(TITLE);
		_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		_frame.setIconImage(new ImageIcon(AnalyzerIconFactory.ANALYZE).getImage());
		_frame.getDockingManager().setXmlFormat(true);
		_frame.setUndecorated(false);
		
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
		_frame.getDockingManager().setProfileKey(PROFILE_NAME);  						
		_frame.getDockingManager().setOutlineMode(DockingManager.FULL_OUTLINE_MODE);  	
		_frame.getDockableBarManager().setProfileKey(PROFILE_NAME);  

		
		ToolBarFrame toolBar = new ToolBarFrame(_frame);
		_frame.getDockableBarManager().addDockableBar(toolBar.createToolBar());    						// 툴바 추가
		JMenuBar menuBar = MenuBar.createMenuBar();   													// 메뉴 바 추가
		_frame.setJMenuBar(menuBar);  	
		
		_frame.getDockingManager().setUndoLimit(10);													// 되돌리기 기억 횟수 지정
		_frame.getDockingManager().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
// 				refreshUndoRedoMenuItems();
			}
		});
		_frame.getDockingManager().beginLoadLayoutData();
		_frame.getDockingManager().setInitSplitPriority(DockingManager.SPLIT_EAST_WEST_SOUTH_NORTH);  	// 기본 도킹 프레임 추가 
		_frame.getDockingManager().addFrame(MessageFrame.createMessageFrame());   						// 메시지 프레임, 분석기 프레임
		_frame.getDockingManager().addFrame(AnalyzerFrame.createAnalyzerFrame());   					// 워크스페이스 문서탭 추가, 설정
		_workspacePane = analyzer.util.DocumentTabs.createDocumentTabs();
		_workspacePane.setTabbedPaneCustomizer(new DocumentPane.TabbedPaneCustomizer() {
			public void customize(final JideTabbedPane tabbedPane) {
				tabbedPane.setShowCloseButton(true);
				tabbedPane.setShowCloseButtonOnTab(true);
				tabbedPane.setUseDefaultShowCloseButtonOnTab(false);
			}
		});

		_frame.getDockingManager().getWorkspace().setLayout(new BorderLayout());
		_frame.getDockingManager().getWorkspace().add((Component) _workspacePane, BorderLayout.CENTER);  // 워크스페이스 영역에 도킹 허용하지 않기
		_frame.getDockingManager().getWorkspace().setAcceptDockableFrame(false);
		_frame.getDockingManager().getWorkspace().setAdjustOpacityOnFly(true);  						 // 이전 세션의 레이아웃 정보 로드
		_frame.getLayoutPersistence().loadLayoutData();
		_frame.toFront();

		return _frame;
	}

	public static void clearUp() {
		_frame.removeWindowListener(_windowListener);
		_windowListener = null;
		if (_frame.getDockingManager() != null) {
			_frame.getDockingManager().saveLayoutData();
		}
		_frame.dispose();
		_frame = null;
	}

	/*******************************
	 * @date 	: 2020. 4. 28.
	 * @설명 		: 도킹 프레임 생성
	 * @변경이력 	:
	 *******************************/
	protected static DockableFrame createDockableFrame(String key, Icon icon) {
		DockableFrame frame = new DockableFrame(key, icon);
		// frame.setPreferredSize(new Dimension(200, 200));
		return frame;
	}

	/*******************************
	 * @date 	: 2020. 4. 28.
	 * @설명 		: 클릭시 생성되는 테이블 패널 및 스크롤 기능
	 * @변경이력 	:
	 *******************************/
	public static DockableFrame createOutputFrame(String key, String path) {
		// dockMap.put(path, ExcelConnector.readTableFromExcel(path));
		DockableFrame frame = createDockableFrame(key, new ImageIcon("../UClair/img/excel.png"));
		frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		frame.getContext().setInitSide(DockContext.DOCK_SIDE_CENTER);
		// frame.add(createScrollPane(dockMap.get(path)));
		return frame;
	}
	public static DockableFrame createOutputFrame(String key) {
		DockableFrame frame = createDockableFrame(key, new ImageIcon("../UClair/img/excel.png"));
		frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		frame.getContext().setInitSide(DockContext.DOCK_SIDE_CENTER);
		return frame;
	}

	static JScrollPane createScrollPane(Component component) {
		JScrollPane pane = new JideScrollPane(component);
		pane.setVerticalScrollBarPolicy(JideScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return pane;
	}

}
