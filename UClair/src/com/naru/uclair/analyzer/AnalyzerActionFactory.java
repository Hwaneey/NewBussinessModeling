/**
 * 
 */
package com.naru.uclair.analyzer;

import java.awt.event.ActionEvent;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.KeyStroke;

import com.jidesoft.action.CommandBar;
import com.naru.common.NaruAssert;
import com.naru.uclair.analyzer.action.DanglingTagAnalyzeAction;
import com.naru.uclair.analyzer.action.EachTagDependAnalyzeAction;
import com.naru.uclair.analyzer.action.EffectCompatibilityAnalyzeActioin;
import com.naru.uclair.analyzer.action.EmptyAction;
import com.naru.uclair.analyzer.action.EventDependAnalyzeAction;
import com.naru.uclair.analyzer.action.IOTestSheetExportAction;
import com.naru.uclair.analyzer.action.ObjectTagLinkAnalyzeAction;
import com.naru.uclair.analyzer.action.PhysicalTagDependAction;
import com.naru.uclair.analyzer.action.ProjectCloseAction;
import com.naru.uclair.analyzer.action.ProjectInfoAnalyzeAction;
import com.naru.uclair.analyzer.action.ProjectLoadAction;
import com.naru.uclair.analyzer.action.ScriptSyntaxAnalyzeAction;
import com.naru.uclair.analyzer.action.VirtualTagDependAnalyzeAction;
import com.naru.uclair.analyzer.action.WindowTestSheetGenerateAction;

/**
 * @author �����
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
	// ���� �±� ���Ӽ� �м�
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

	// WindowEditor ���� Action (Reserved)
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
		// Resource ���̺��� �����Ѵ�.
		resource = ResourceBundle.getBundle("resources.analyzer_action", Locale
				.getDefault());
	}

	public static final void createInstance(Analyzer analyzer) {
		NaruAssert.isNotNull(analyzer);
		if (null == actionFactory) {
			actionFactory = new AnalyzerActionFactory(analyzer);
		}
	}

	public static AnalyzerActionFactory getFactory() {
		return actionFactory;
	}

	private Analyzer analyzer;
	private Map<Integer, Action> actionMap;

	/**
	 * Ȱ��ȭ�� Editor�� ���� ���� �ٸ� ������ �ϱ� ���� Action���. Ȱ��ȭ Editor�� ����Ǵ� �̺�Ʈ�� ���� ���εȴ�.
	 */
	// private Map<String, DelegateAction> delegateActionMap;
	private Hashtable<String, CommandBar> commandBarTable;

	private AnalyzerActionFactory(Analyzer analyzer) {
		this.analyzer = analyzer;
		actionMap = new TreeMap<Integer, Action>();
		// delegateActionMap = new TreeMap<String, DelegateAction> ();
	}

	public String getName(String key) {
		String name = key;
		try {
			name = resource.getString(key + NAME_POSTFIX);
		} catch (MissingResourceException mre) {
			System.out.println(key + "�� ���� Name Resource�� �����ϴ�.");
		}

		return name;
	}

	public int getMnemonic(String key) {
		String s = null;
		try {
			s = resource.getString(key + MNEMONIC_POSTFIX);
		} catch (MissingResourceException mre) {
			System.out.println(key + "�� ���� Mnemonic Resource�� �����ϴ�.");
		}

		return ((null == s) || (s.length() == 0)) ? '\0' : s.charAt(0);
	}

	public String getTooltip(String key) {
		String s = null;
		try {
			s = resource.getString(key + TOOLTIP_POSTFIX);
		} catch (MissingResourceException mre) {
			System.out.println(key + "�� ���� Tooltip Resource�� �����ϴ�.");
		}

		return s;
	}

	public KeyStroke getAccelerator(String key) {
		KeyStroke stroke = null;
		try {
			String s = resource.getString(key + ACCELERATOR_POSTFIX);
			stroke = KeyStroke.getKeyStroke(s);
		} catch (MissingResourceException mre) {
			System.out.println(key + "�� ���� Accelerator Resource�� �����ϴ�.");
		}

		return stroke;
	}

	private void configureAction(Action action, String key) {
		action.putValue(Action.NAME, getName(key));
		action.putValue(Action.MNEMONIC_KEY, getMnemonic(key));
		action.putValue(Action.ACCELERATOR_KEY, getAccelerator(key));
		action.putValue(Action.SHORT_DESCRIPTION, getTooltip(key));
		action
				.putValue(Action.LONG_DESCRIPTION,
						"Context Sensitive Help Test.");
		// �̰��� ���Ŀ� �����ϱ�� �Ѵ�.
		// action.putValue(Action.SMALL_ICON, getIcon(key));
		// action.putValue(Action.LARGE_ICON_KEY, getLargeIcon(key));
	}

	public Action getAction(int actionType) {
		// �̹� ������ Action�� ���̺�� ���� ��ȯ.
		if (actionMap.containsKey(actionType)) {
			return actionMap.get(actionType);
		}

		Action action = null;

		String key = null;
		try {
			key = resource.getString(Integer.toString(actionType));
		} catch (MissingResourceException mre) {
			System.out.println(actionType + "�� ���� resource�� ���ǵ��� �ʾҽ��ϴ�.");
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
		case CLOSE_PROJECT:
			action = new ProjectCloseAction(analyzer);
			configureAction(action, key);
			break;
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
		case PAGE_SETUP:
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.File.PAGE_SETUP));
			break;
		case PRINT_PREVIEW:
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.File.PRINT_PREVIEW));
			break;
		case PRINT:
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.File.PRINT));
			break;

		case SEARCH:
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.Edit.SEARCH));
			break;
		case SELECT_ALL:
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			break;
		case SHOW_MENUBAR:
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			break;
		case SHOW_FILE_TOOLBAR:
			// action = new ShowToolBarAction(developer,
			// DeveloperCommandBarFactory.FILE_TOOL_KEY);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			break;
		case SHOW_EDIT_TOOLBAR:
			// action = new ShowToolBarAction(developer,
			// DeveloperCommandBarFactory.EDIT_TOOL_KEY);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			break;
		case SHOW_PROJECT_TOOLBAR:
			// action = new ShowToolBarAction(developer,
			// DeveloperCommandBarFactory.PROJECT_TOOL_KEY);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			break;
		case SHOW_TOOL_TOOLBAR:
			// action = new ShowToolBarAction(developer,
			// DeveloperCommandBarFactory.UTILITY_TOOL_KEY);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			break;
		case SHOW_PROJECT_VIEW:
			// action = new ShowDockableViewAction(developer,
			// DeveloperDockableViewFactory.PROJECT_VIEW_KEY);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon(
			// AnalyzerIconFactory.System.PROJECT));
			break;
		case SHOW_TAG_VIEW:
			// action = new ShowDockableViewAction(developer,
			// DeveloperDockableViewFactory.TAG_VIEW_KEY);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon(
			// AnalyzerIconFactory.System.TAG_DIC));
			break;
		case SHOW_OUTLINE_VIEW:
			// action = new ShowDockableViewAction(developer,
			// DeveloperDockableViewFactory.OUTLINE_VIEW_KEY);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon(
			// AnalyzerIconFactory.Window.OUTLINE_VIEW));
			break;
		case SHOW_PROPERTY_VIEW:
			// action = new ShowDockableViewAction(developer,
			// DeveloperDockableViewFactory.PROPERTY_VIEW_KEY);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon(
			// AnalyzerIconFactory.Window.PROPERTY_VIEW));
			break;
		case SHOW_MESSAGE_VIEW:
			// action = new ShowDockableViewAction(developer,
			// DeveloperDockableViewFactory.MESSAGE_VIEW_KEY);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon(
			// AnalyzerIconFactory.Window.MESSAGE_VIEW));
			break;
		case PROJECT_INFO:
			// action = new ProjectInformationEditAction(developer);
			//action = new ShowProjectInfoEditorAction(analyzer, AnalyzerEditorFactory.PROJECT_INFO_EDITOR_KEY);
			action = new ProjectInfoAnalyzeAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.System.PROJECT));
			break;
		// case NETWORK_CONFIG:
		// action = new EmptyAction(developer);
		// configureAction(action, key);
		// action.putValue(Action.SMALL_ICON,
		// AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory
		// .System.NETWORK));
		// break;
		case NEW_NODE:
			// action = new NodeCreateAction(developer);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.System.NODE));
			break;
		case EDIT_NODE:
			// action = new NodeEditAction(developer);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			break;
		case DELETE_NODE:
			// action = new NodeDeleteAction(developer);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.Edit.DELETE));
			break;
		case TAG_CONFIG:
			// action = new ShowEditorAction(developer,
			// DeveloperEditorFactory.TAG_EDITOR_KEY);
//			action = new ShowEditorAction(analyzer, AnalyzerEditorFactory.TAG_INFO_EDITOR_KEY);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.System.TAG_DIC));
			break;
		// case NEW_TAG:
		// action = new TagCreateAction(developer);
		// configureAction(action, key);
		// action.putValue(Action.SMALL_ICON,
		// AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory
		// .System.TAG_DIC));
		// break;
		// case EDIT_TAG:
		// action = new TagEditAction(developer);
		// configureAction(action, key);
		// break;
		// case DELETE_TAG:
		// action = new TagDeleteAction(developer);
		// configureAction(action, key);
		// action.putValue(Action.SMALL_ICON,
		// AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory.Edit.DELETE));
		// break;
		case DEVICE_CONFIG:
			// action = new ShowEditorAction(developer,
			// DeveloperEditorFactory.DEVICE_EDITOR_KEY);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.System.DEVICE_CFG));
			break;
		// case NEW_DEVICE:
		// action = new DeviceCreateAction(developer);
		// configureAction(action, key);
		// action.putValue(Action.SMALL_ICON,
		// AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory.System.DEVICE));
		// break;
		// case EDIT_DEVICE:
		// action = new DeviceEditAction(developer);
		// configureAction(action, key);
		// break;
		// case DELETE_DEVICE:
		// action = new DeviceDeleteAction(developer);
		// configureAction(action, key);
		// action.putValue(Action.SMALL_ICON,
		// AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory.Edit.DELETE));
		// break;
		case DATA_COLLECT_CONFIG:
			// action = new ShowEditorAction(developer,
			// DeveloperEditorFactory.DATA_COLLECT_EDITOR_KEY);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.System.DATA_COLLECT_CFG));
			break;
		// case NEW_DATACOLLECT:
		// action = new CollectionCreateAction(developer);
		// configureAction(action, key);
		// action.putValue(Action.SMALL_ICON,
		// AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory.File.NEW));
		// break;
		// case EDIT_DATACOLLECT:
		// action = new CollectionEditAction(developer);
		// configureAction(action, key);
		// break;
		// case DELETE_DATACOLLECT:
		// action = new CollectionDeleteAction(developer);
		// configureAction(action, key);
		// action.putValue(Action.SMALL_ICON,
		// AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory.Edit.DELETE));
		// break;
		case EVENT_CONFIG:
			// action = new ShowEditorAction(developer,
			// DeveloperEditorFactory.EVENT_EDITOR_KEY);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.System.EVENT_DIC));
			break;
		// case NEW_EVENT:
		// action = new EventCreateAction(developer);
		// configureAction(action, key);
		// action.putValue(Action.SMALL_ICON,
		// AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory
		// .System.EVENT_DIC));
		// break;
		// case EDIT_EVENT:
		// action = new EventEditAction(developer);
		// configureAction(action, key);
		// break;
		// case DELETE_EVENT:
		// action = new EventDeleteAction(developer);
		// configureAction(action, key);
		// action.putValue(Action.SMALL_ICON,
		// AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory.Edit.DELETE));
		// break;
		// case DATABASE_CONFIG:
		// action = new EmptyAction(developer);
		// configureAction(action, key);
		// action.putValue(Action.SMALL_ICON,
		// AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory
		// .System.DATABASE_CFG));
		// break;
		case NEW_DATABASE:
			// action = new DatabaseCreateAction(developer);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory.File.NEW));
			break;
		case EDIT_DATABASE:
			// action = new DatabaseEditAction(developer);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			break;
		case DELETE_DATABASE:
			// action = new DatabaseDeleteAction(developer);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.Edit.DELETE));
			break;
		case USER_CONFIG:
			// action = new ShowEditorAction(developer,
			// DeveloperEditorFactory.USER_EDITOR_KEY);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.System.SECURITY_CFG));
			break;
		// case NEW_USER:
		// action = new UserCreateAction(developer);
		// configureAction(action, key);
		// action.putValue(Action.SMALL_ICON,
		// AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory.System.USER));
		// break;
		// case EDIT_USER:
		// action = new UserEditAction(developer);
		// configureAction(action, key);
		// break;
		// case DELETE_USER:
		// action = new UserDeleteAction(developer);
		// configureAction(action, key);
		// action.putValue(Action.SMALL_ICON,
		// AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory.Edit.DELETE));
		// break;
		case SCRIPT_CONFIG:
			// action = new ShowEditorAction(developer,
			// DeveloperEditorFactory.SCRIPT_EDITOR_KEY);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.System.SCRIPT_DIC));
			break;
		// case NEW_SCRIPT:
		// action = new ScriptCreateAction(developer);
		// configureAction(action, key);
		// action.putValue(Action.SMALL_ICON,
		// AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory
		// .System.SCRIPT_DIC));
		// break;
		// case EDIT_SCRIPT:
		// // TODO
		// break;
		// case DELETE_SCRIPT:
		// // TODO
		// break;
		case ALARM_GROUP_CONFIG:
			// action = new ShowAlarmManagerAction(developer);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.System.ALARM));
			break;
		case ALARM_UMS_CONFIG:
			// action = new ShowUMSOperationAction(developer);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			break;
		case NEW_ALARMGROUP:
			break;
		case EDIT_ALARMGROUP:
			break;
		case DELETE_ALARMGROUP:
			break;
		case VALIDATE:
			// action = new DelegateAction();
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.Util.WINDOW_CHECK));
			break;
		case DEPLOY:
			// action = new DeployAction(developer);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.Util.DEPLOY));
			break;
		case HELP_CONTENTS:
			// action = new HelpContentsAction(developer);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory.Help.HELP));
			break;
		case HELP_ABOUT_EDITOR:
			// action = new DelegateAction();
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.Help.HELP_EDITOR));
			break;
		case HELP_ABOUT:
			// action = new AboutDeveloperAction(developer);
			action = new EmptyAction(analyzer);
			configureAction(action, key);
			// action.putValue(Action.SMALL_ICON,
			// AnalyzerIconFactory.getImageIcon
			// (AnalyzerIconFactory.Help.HELP_ABOUT));
			break;
		default:
			// do nothing.
		}

		if (null != action) {
			actionMap.put(actionType, action);
			// if (action instanceof DelegateAction) {
			// delegateActionMap.put(Integer.toString(actionType),
			// (DelegateAction) action);
			// }
		}

		return action;
	}

	public void mappingDelegator(ActionMap map) {
		if (null != map.allKeys()) {
			// for (Object key : delegateActionMap.keySet()) {
			// DelegateAction action = delegateActionMap.get(key);
			// if (map.get(key) != null) {
			// action.setDelegateAction(map.get(key));
			// }
			// else {
			// action.setDelegateAction(null);
			// }
			// }
		}

		// PageSetupAction���� ���� Ȱ��ȭ�� Editor�� �����Ѵ�.
		//PageSetupAction.getAction().setEditor(getDeveloper().getActiveEditor()
		// );
	}

	public Analyzer getDeveloper() {
		return analyzer;
	}
}
