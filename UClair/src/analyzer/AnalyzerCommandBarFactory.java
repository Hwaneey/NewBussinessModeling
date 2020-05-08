/**
 * 
 */
package analyzer;

import java.awt.Container;
import java.util.Hashtable;

import javax.swing.JMenu;

import com.jidesoft.action.CommandBar;
import com.jidesoft.action.CommandBarFactory;
import com.jidesoft.action.CommandMenuBar;
import com.jidesoft.action.DockableBarContext;
import com.jidesoft.swing.JideMenu;
import com.naru.common.NaruAssert;
import com.naru.uclair.analyzer.constants.AnalyzerConstants;

/**
 * @author 김기태
 * 
 */
public class AnalyzerCommandBarFactory extends CommandBarFactory {

	public static final String MENU_FILE_TITLE = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.filemenu.title");
	public static final String MENU_ANALYSIS_TITLE = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.analysismenu.title");
	public static final String MENU_TESTCASE_TITLE = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.testcasemenu.title");
	// ToolBar, ViewFrame 관련 메뉴
	public static final String MENU_VIEW_TITLE = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.viewmenu.title");
	// Project 관련 메뉴
	public static final String MENU_PROJECT_TITLE = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.projectmenu.title");
	// 부가 기능(Window Check, Deploy 관련 메뉴
	public static final String MENU_TOOL_TITLE = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.toolmenu.title");
	// window list 및 current window property 관련 메뉴
	public static final String MENU_WINDOW_TITLE = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.windowmenu.title");
	// Look and Feel 관련 메뉴
	public static final String MENU_LAF_TITLE = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.lafmenu.title");
	// Help 관련 메뉴
	public static final String MENU_HELP_TITLE = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.helpmenu.title");

	public static final String MENU_BAR_KEY = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.menubar.key"); //$NON-NLS-1$
	public static final String FILE_TOOL_KEY = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.filetool.key"); //$NON-NLS-1$
	public static final String EDIT_TOOL_KEY = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.edittool.key"); //$NON-NLS-1$
	public static final String PROJECT_TOOL_KEY = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.projecttool.key"); //$NON-NLS-1$
	public static final String UTILITY_TOOL_KEY = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.utilitytool.key"); //$NON-NLS-1$

	private static AnalyzerCommandBarFactory singleton = null;

	/**
	 * 시스템 편집기의 주메뉴에 대한 단위 메뉴 리스트
	 */
	private Hashtable<String, JMenu> topMenuTable;

	/**
	 * ToolBar에 대한 CommandBar 테이블
	 */
	private Hashtable<String, CommandBar> commandBarTable;

	private Analyzer analyzer = null;

	public static final void createInstance(Analyzer analyzer) {
		NaruAssert.isNotNull(analyzer);
		if (null == singleton) {
			singleton = new AnalyzerCommandBarFactory(analyzer);
		}
	}

	public static final AnalyzerCommandBarFactory getFactory() {
		return singleton;
	}

	private AnalyzerCommandBarFactory(Analyzer owner) {
		setAnalyzer(owner);

		// 보관 자료구조를 초기화한다.
		commandBarTable = new Hashtable<String, CommandBar>();
		// showMenuTable = new Hashtable<String, JCheckBoxMenuItem> ();

		// 각 메뉴바 및 툴바를 생성한다.
		// createCommandBar(MENU_BAR_KEY);
		// createCommandBar(FILE_TOOL_KEY);
		// createCommandBar(EDIT_TOOL_KEY);
		// createCommandBar(PROJECT_TOOL_KEY);
		// createCommandBar(UTILITY_TOOL_KEY);
	}

	/**
	 * Analyzer를 설정한다.
	 * 
	 * @param owner
	 */
	private final void setAnalyzer(Analyzer owner) {
		this.analyzer = owner;
	}

	/**
	 * 커맨드바 관리자에 등록된 소유자(Developer)를 반환하는 메서드.
	 * 
	 * @return
	 */
	public final Analyzer getAnalyzer() {
		return this.analyzer;
	}

	/**
	 * 메뉴및 툴바를 구성한다.
	 * 
	 * @param key
	 * @return
	 */
	private CommandBar createCommandBar(String key) {
		CommandBar createdBar = null;

		if (FILE_TOOL_KEY.equals(key)) {
			// 현재 아이콘 없음.
			// createdBar = createFileCommandBar();
		} else if (EDIT_TOOL_KEY.equals(key)) {
			// createdBar = createEditCommandBar();
		} else if (PROJECT_TOOL_KEY.equals(key)) {
			// createdBar = createProjectCommandBar();
		} else if (UTILITY_TOOL_KEY.equals(key)) {
			// createdBar = createUtilityCommandBar();
		} else if (MENU_BAR_KEY.equals(key)) {
			createdBar = createMenuCommandBar(getAnalyzer());
		} else {
			System.out.println(key
					+ " is not exist in the command bar factory.."); //$NON-NLS-1$
			createdBar = null;
		}

		if (null != createdBar) {
			commandBarTable.put(key, createdBar);
		}
		return createdBar;
	}

	/**
	 * 파일 툴바 생성 메서드.
	 * 
	 * @return
	 */
	private CommandBar createFileCommandBar() {
		AnalyzerActionFactory actionFactory = AnalyzerActionFactory
				.getFactory();

		CommandBar commandBar = new CommandBar(FILE_TOOL_KEY);
		commandBar.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
		commandBar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
		commandBar.setInitIndex(1);
		commandBar.setInitSubindex(0);

		commandBar.add(actionFactory
				.getAction(AnalyzerActionFactory.OPEN_PROJECT));
		commandBar.add(actionFactory
				.getAction(AnalyzerActionFactory.CLOSE_PROJECT));

		return commandBar;
	}

	/**
	 * 메뉴바 생성 메서드.
	 * 
	 * @param container
	 * @return
	 */
	private CommandBar createMenuCommandBar(Container container) {
		CommandBar commandBar = new CommandMenuBar(MENU_BAR_KEY);

		commandBar.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
		commandBar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
		commandBar.setInitIndex(0);
		commandBar.setPaintBackground(false);
		commandBar.setStretch(true);
		commandBar.setFloatable(false);
		commandBar.setHidable(false);

		commandBar.add(createFileMenu());
		commandBar.add(createAnalysisMenu());
		commandBar.add(createTestMenu());
		commandBar.add(createHelpMenu());

		return commandBar;
	}

	/**
	 * 메뉴바의 파일 메뉴를 생성하는 메서드.
	 * 
	 * @return
	 */
	private JMenu createFileMenu() {
		AnalyzerActionFactory actionFactory = AnalyzerActionFactory
				.getFactory();

		JMenu fileMenu = new JideMenu(MENU_FILE_TITLE);

		fileMenu.add(actionFactory
				.getAction(AnalyzerActionFactory.OPEN_PROJECT));
		fileMenu.add(actionFactory
				.getAction(AnalyzerActionFactory.CLOSE_PROJECT));
		fileMenu.addSeparator();

		fileMenu.add(actionFactory.getAction(AnalyzerActionFactory.EXIT));

		return fileMenu;
	}

	/**
	 * 메뉴바의 분석 메뉴를 생성하는 메서드
	 */
	private JMenu createAnalysisMenu() {
		AnalyzerActionFactory actionFactory = AnalyzerActionFactory
				.getFactory();

		JMenu menu = new JideMenu(MENU_ANALYSIS_TITLE);

		menu.add(actionFactory
				.getAction(AnalyzerActionFactory.DANGLING_TAG_ANALYSIS));
		menu
				.add(actionFactory
						.getAction(AnalyzerActionFactory.VIRTUAL_TAG_DEPENDENCY_ANALYSIS));
		menu
				.add(actionFactory
						.getAction(AnalyzerActionFactory.PHYSICAL_ADRESS_DEPENDENCY_ANALYSIS));
		menu
				.add(actionFactory
						.getAction(AnalyzerActionFactory.OBJECT_TAG_LINK_INFO_ANALYSIS));
		menu
				.add(actionFactory
						.getAction(AnalyzerActionFactory.EVENT_TAG_DEPENDENCY_ANALYSIS));
		menu.addSeparator();
		menu
				.add(actionFactory
						.getAction(AnalyzerActionFactory.SCRIPT_ANALYSIS));
		menu
				.add(actionFactory
						.getAction(AnalyzerActionFactory.OBJECT_EFFECT_COMPATIBILITY_ANALYSIS));

		return menu;
	}

	/**
	 * 테스트 케이스 메뉴를 생성하는 메서드
	 * 
	 * @return
	 */
	private JMenu createTestMenu() {
		AnalyzerActionFactory actionFactory = AnalyzerActionFactory
				.getFactory();

		JMenu menu = new JideMenu(MENU_TESTCASE_TITLE);

		menu.add(actionFactory
				.getAction(AnalyzerActionFactory.IO_TEST_CASE_GENERATOR));
		menu.add(actionFactory
				.getAction(AnalyzerActionFactory.WINDOW_TEST_CASE_GENERATOR));

		return menu;
	}

	/**
	 * 메뉴바의 도움말 메뉴를 생성하는 메서드.
	 * 
	 * @return
	 */
	private JMenu createHelpMenu() {
		AnalyzerActionFactory actionFactory = AnalyzerActionFactory
				.getFactory();

		JMenu helpMenu = new JideMenu(MENU_HELP_TITLE);
		helpMenu.add(actionFactory
				.getAction(AnalyzerActionFactory.HELP_CONTENTS));
		helpMenu.addSeparator();
		helpMenu.add(actionFactory
				.getAction(AnalyzerActionFactory.HELP_ABOUT_EDITOR));
		helpMenu.add(actionFactory.getAction(AnalyzerActionFactory.HELP_ABOUT));

		return helpMenu;
	}

	/**
	 * 분석기의 커맨드바 관리자에 등록된 커맨드바(툴바, 메뉴바)를 반환하는 메서드.
	 * 
	 * @param key
	 *            {@link MENU_BAR_KEY}
	 * @return
	 */
	public CommandBar getCommandBar(String key) {
		CommandBar bar = commandBarTable.get(key);

		if (null == bar) {
			bar = createCommandBar(key);
		}

		return bar;
	}

	/**
	 * 메뉴바와 툴바를 프레임에 표시한다.
	 */
	public void showAllCommandBars() {
		// 메뉴바를 표시한다.
		this.analyzer.getDockableBarManager().addDockableBar(
				getCommandBar(MENU_BAR_KEY));

		// 툴바를 표시한다.
		this.analyzer.getDockableBarManager().addDockableBar(
				getCommandBar(FILE_TOOL_KEY));
	}
}
