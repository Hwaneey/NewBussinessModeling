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
 * @author �����
 * 
 */
public class AnalyzerCommandBarFactory extends CommandBarFactory {

	public static final String MENU_FILE_TITLE = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.filemenu.title");
	public static final String MENU_ANALYSIS_TITLE = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.analysismenu.title");
	public static final String MENU_TESTCASE_TITLE = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.testcasemenu.title");
	// ToolBar, ViewFrame ���� �޴�
	public static final String MENU_VIEW_TITLE = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.viewmenu.title");
	// Project ���� �޴�
	public static final String MENU_PROJECT_TITLE = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.projectmenu.title");
	// �ΰ� ���(Window Check, Deploy ���� �޴�
	public static final String MENU_TOOL_TITLE = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.toolmenu.title");
	// window list �� current window property ���� �޴�
	public static final String MENU_WINDOW_TITLE = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.windowmenu.title");
	// Look and Feel ���� �޴�
	public static final String MENU_LAF_TITLE = AnalyzerConstants
			.getString("AnalyzerCommandBarFactory.lafmenu.title");
	// Help ���� �޴�
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
	 * �ý��� �������� �ָ޴��� ���� ���� �޴� ����Ʈ
	 */
	private Hashtable<String, JMenu> topMenuTable;

	/**
	 * ToolBar�� ���� CommandBar ���̺�
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

		// ���� �ڷᱸ���� �ʱ�ȭ�Ѵ�.
		commandBarTable = new Hashtable<String, CommandBar>();
		// showMenuTable = new Hashtable<String, JCheckBoxMenuItem> ();

		// �� �޴��� �� ���ٸ� �����Ѵ�.
		// createCommandBar(MENU_BAR_KEY);
		// createCommandBar(FILE_TOOL_KEY);
		// createCommandBar(EDIT_TOOL_KEY);
		// createCommandBar(PROJECT_TOOL_KEY);
		// createCommandBar(UTILITY_TOOL_KEY);
	}

	/**
	 * Analyzer�� �����Ѵ�.
	 * 
	 * @param owner
	 */
	private final void setAnalyzer(Analyzer owner) {
		this.analyzer = owner;
	}

	/**
	 * Ŀ�ǵ�� �����ڿ� ��ϵ� ������(Developer)�� ��ȯ�ϴ� �޼���.
	 * 
	 * @return
	 */
	public final Analyzer getAnalyzer() {
		return this.analyzer;
	}

	/**
	 * �޴��� ���ٸ� �����Ѵ�.
	 * 
	 * @param key
	 * @return
	 */
	private CommandBar createCommandBar(String key) {
		CommandBar createdBar = null;

		if (FILE_TOOL_KEY.equals(key)) {
			// ���� ������ ����.
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
	 * ���� ���� ���� �޼���.
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
	 * �޴��� ���� �޼���.
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
	 * �޴����� ���� �޴��� �����ϴ� �޼���.
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
	 * �޴����� �м� �޴��� �����ϴ� �޼���
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
	 * �׽�Ʈ ���̽� �޴��� �����ϴ� �޼���
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
	 * �޴����� ���� �޴��� �����ϴ� �޼���.
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
	 * �м����� Ŀ�ǵ�� �����ڿ� ��ϵ� Ŀ�ǵ��(����, �޴���)�� ��ȯ�ϴ� �޼���.
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
	 * �޴��ٿ� ���ٸ� �����ӿ� ǥ���Ѵ�.
	 */
	public void showAllCommandBars() {
		// �޴��ٸ� ǥ���Ѵ�.
		this.analyzer.getDockableBarManager().addDockableBar(
				getCommandBar(MENU_BAR_KEY));

		// ���ٸ� ǥ���Ѵ�.
		this.analyzer.getDockableBarManager().addDockableBar(
				getCommandBar(FILE_TOOL_KEY));
	}
}
