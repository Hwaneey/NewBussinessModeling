package analyzer;

import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import analyzer.action.DanglingTagAnalyzeAction;
import analyzer.action.EachTagDependAnalyzeAction;
import analyzer.action.EffectCompatibilityAnalyzeActioin;
import analyzer.action.EventDependAnalyzeAction;
import analyzer.action.IOTestSheetExportAction;
import analyzer.action.ObjectTagLinkAnalyzeAction;
import analyzer.action.PhysicalTagDependAction;
import analyzer.action.ProjectInfoAnalyzeAction;
import analyzer.action.ProjectLoadAction;
import analyzer.action.ScriptSyntaxAnalyzeAction;
import analyzer.action.VirtualTagDependAnalyzeAction;
import analyzer.action.WindowTestSheetGenerateAction;



/**
 * AnalyzerActionFactory 클래스
 * 
 * @author 김기태
 * 
 */
public class AnalyzerActionFactory {
	private static final String NAME_POSTFIX = ".name";
	private static final String MNEMONIC_POSTFIX = ".mnemonic";
	private static final String ACCELERATOR_POSTFIX = ".accelerator";
	private static final String TOOLTIP_POSTFIX = ".tooltip";

	// File Menu Action
	public static final int EXIT = 0;

	public static final int OPEN_PROJECT = 2;
	public static final int CLOSE_PROJECT = 3;

	public static final int DANGLING_TAG_ANALYSIS = 500;
	public static final int VIRTUAL_TAG_DEPENDENCY_ANALYSIS = 501;
	public static final int PHYSICAL_ADRESS_DEPENDENCY_ANALYSIS = 502;
	public static final int OBJECT_TAG_LINK_INFO_ANALYSIS = 503; // delegate
																	// action
	public static final int EVENT_TAG_DEPENDENCY_ANALYSIS = 504; // delegate
																	// action
	public static final int OBJECT_EFFECT_COMPATIBILITY_ANALYSIS = 505;
	// 개별 태그 종속성 분석
	public static final int EACH_TAG_DEPENDENCY_ANALYSIS = 506;

	public static final int SCRIPT_ANALYSIS = 510;
	public static final int IO_TEST_CASE_GENERATOR = 511;
	public static final int WINDOW_TEST_CASE_GENERATOR = 512;

	public static final int PAGE_SETUP = 30;
	public static final int PRINT_PREVIEW = 31; // delegate action
	public static final int PRINT = 32; // delegate action

	// Edit Menu Action
	public static final int UNDO = 100;
	public static final int REDO = 101;
	public static final int COPY = 102;
	public static final int CUT = 103;
	public static final int PASTE = 104;
	public static final int DELETE = 105;
	public static final int SEARCH = 106;
	public static final int SELECT_ALL = 107;

	// WindowEditor 관련 Action (Reserved)
	// 120 ~ 180

	// View Menu Action
	public static final int SHOW_FILE_TOOLBAR = 200;
	public static final int SHOW_EDIT_TOOLBAR = 201;
	public static final int SHOW_PROJECT_TOOLBAR = 202;
	public static final int SHOW_TOOL_TOOLBAR = 203;
	public static final int SHOW_PROJECT_VIEW = 204;
	public static final int SHOW_TAG_VIEW = 205;
	public static final int SHOW_OUTLINE_VIEW = 206;
	public static final int SHOW_PROPERTY_VIEW = 207;
	public static final int SHOW_MESSAGE_VIEW = 208;
	public static final int SHOW_MENUBAR = 209;

	// Project Menu Action
	public static final int PROJECT_INFO = 300;
	// public static final int NETWORK_CONFIG = 310;
	public static final int NEW_NODE = 311;
	public static final int EDIT_NODE = 312;
	public static final int DELETE_NODE = 313;

	public static final int TAG_CONFIG = 320;
	// public static final int NEW_TAG = 321;
	// public static final int EDIT_TAG = 322;
	// public static final int DELETE_TAG = 323;

	public static final int DEVICE_CONFIG = 330;
	// public static final int NEW_DEVICE = 331;
	// public static final int EDIT_DEVICE = 332;
	// public static final int DELETE_DEVICE = 333;

	public static final int DATA_COLLECT_CONFIG = 340;
	// public static final int NEW_DATACOLLECT = 341;
	// public static final int EDIT_DATACOLLECT = 342;
	// public static final int DELETE_DATACOLLECT = 343;

	public static final int EVENT_CONFIG = 350;
	// public static final int NEW_EVENT = 351;
	// public static final int EDIT_EVENT = 352;
	// public static final int DELETE_EVENT = 353;

	public static final int DATABASE_CONFIG = 360;
	public static final int NEW_DATABASE = 361;
	public static final int EDIT_DATABASE = 362;
	public static final int DELETE_DATABASE = 363;

	public static final int USER_CONFIG = 370;
	// public static final int NEW_USER = 371;
	// public static final int EDIT_USER = 372;
	// public static final int DELETE_USER = 373;

	public static final int SCRIPT_CONFIG = 380;
	// public static final int NEW_SCRIPT =381;
	// public static final int EDIT_SCRIPT = 382;
	// public static final int DELETE_SCRIPT = 383;

	public static final int ALARM_GROUP_CONFIG = 390;
	public static final int ALARM_UMS_CONFIG = 391;
	public static final int NEW_ALARMGROUP = 392;
	public static final int EDIT_ALARMGROUP = 393;
	public static final int DELETE_ALARMGROUP = 394;

	// Tool Menu Action
	public static final int VALIDATE = 400; // delegate action
	public static final int DEPLOY = 401;

	// Help Menu Action
	public static final int HELP_CONTENTS = 600;
	public static final int HELP_ABOUT_EDITOR = 601;
	public static final int HELP_ABOUT = 602;

	private static AnalyzerActionFactory actionFactory;

	private static ResourceBundle resource;

	static {
		// Resource 테이블을 구축한다.
		resource = ResourceBundle.getBundle("resources.analyzer_action", Locale.getDefault());
	}
	
	private Analyzer analyzer;
	private Map<Integer, Action> actionMap;
	
	public static final void createInstance(Analyzer analyzer) {
		
		if (null == actionFactory) {
			actionFactory = new AnalyzerActionFactory(analyzer);
		}
	}
	
	private AnalyzerActionFactory(Analyzer analyzer) {
		this.analyzer = analyzer;
		actionMap = new TreeMap<Integer, Action>();		
	}
	
	public static AnalyzerActionFactory getFactory() {
		return actionFactory;
	}
	
	public Action getAction(int actionType) {
		// 이미 생성된 Action은 테이블로 부터 반환.
		if (actionMap.containsKey(actionType)) {
			return actionMap.get(actionType);
		}

		Action action = null;
		String key = null;
		
		try {
			key = resource.getString(Integer.toString(actionType));
		} catch (MissingResourceException mre) {
			System.out.println(actionType + "에 대한 resource가 정의되지 않았습니다.");
			return null;
		}

		switch (actionType) {
		case EXIT:
			action = new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					analyzer.exitWithConfirm();
				}
			};
			configureAction(action, key);
			break;
		case OPEN_PROJECT:			
			action = new ProjectLoadAction(analyzer);
			configureAction(action, key);
			break;
//		case CLOSE_PROJECT:
//			action = new ProjectCloseAction(analyzer);
//			configureAction(action, key);
//			break;
		case DANGLING_TAG_ANALYSIS:
			action = new DanglingTagAnalyzeAction(analyzer);
			configureAction(action, key);
			break;
		case VIRTUAL_TAG_DEPENDENCY_ANALYSIS:
			action = new VirtualTagDependAnalyzeAction(analyzer);
			configureAction(action, key);
			break;
		case PHYSICAL_ADRESS_DEPENDENCY_ANALYSIS:
			action = new PhysicalTagDependAction(analyzer);
			configureAction(action, key);
			break;
		case OBJECT_TAG_LINK_INFO_ANALYSIS:
			action = new ObjectTagLinkAnalyzeAction(analyzer);
			configureAction(action, key);
			break;
		case EVENT_TAG_DEPENDENCY_ANALYSIS:
			action = new EventDependAnalyzeAction(analyzer);
			configureAction(action, key);
			break;
		case OBJECT_EFFECT_COMPATIBILITY_ANALYSIS:
			action = new EffectCompatibilityAnalyzeActioin(analyzer);
			configureAction(action, key);
			break;
		case EACH_TAG_DEPENDENCY_ANALYSIS:
			action = new EachTagDependAnalyzeAction(analyzer);
			configureAction(action, key);
			break;
		case SCRIPT_ANALYSIS:
			action = new ScriptSyntaxAnalyzeAction(analyzer);
			configureAction(action, key);
			break;
		case IO_TEST_CASE_GENERATOR:
			action = new IOTestSheetExportAction(analyzer);
			configureAction(action, key);
			break;
		case WINDOW_TEST_CASE_GENERATOR:
			action = new WindowTestSheetGenerateAction(analyzer);
			configureAction(action, key);
			break;		
		case PROJECT_INFO:			
			action = new ProjectInfoAnalyzeAction(analyzer);
			configureAction(action, key);			
			break;		
		default:
			// do nothing.
		}

		if (null != action) {
			actionMap.put(actionType, action);	
		}

		return action;
	}
	
	private void configureAction(Action action, String key) {
		action.putValue(Action.NAME, getName(key));
		action.putValue(Action.MNEMONIC_KEY, getMnemonic(key));
		action.putValue(Action.ACCELERATOR_KEY, getAccelerator(key));
		action.putValue(Action.SHORT_DESCRIPTION, getTooltip(key));
		action.putValue(Action.LONG_DESCRIPTION,"Context Sensitive Help Test.");
		
	}
	
	public String getName(String key) {
		String name = key;
		try {
			name = resource.getString(key + NAME_POSTFIX);
		} catch (MissingResourceException mre) {
			System.out.println(key + "에 대한 Name Resource가 없습니다.");
		}

		return name;
	}
	
	public int getMnemonic(String key) {
		String s = null;
		try {
			s = resource.getString(key + MNEMONIC_POSTFIX);
		} catch (MissingResourceException mre) {
			System.out.println(key + "에 대한 Mnemonic Resource가 없습니다.");
		}

		return ((null == s) || (s.length() == 0)) ? '\0' : s.charAt(0);
	}

	public String getTooltip(String key) {
		String s = null;
		try {
			s = resource.getString(key + TOOLTIP_POSTFIX);
		} catch (MissingResourceException mre) {
			System.out.println(key + "에 대한 Tooltip Resource가 없습니다.");
		}

		return s;
	}

	public KeyStroke getAccelerator(String key) {
		KeyStroke stroke = null;
		try {
			String s = resource.getString(key + ACCELERATOR_POSTFIX);
			stroke = KeyStroke.getKeyStroke(s);
		} catch (MissingResourceException mre) {
			System.out.println(key + "에 대한 Accelerator Resource가 없습니다.");
		}

		return stroke;
	}
}
