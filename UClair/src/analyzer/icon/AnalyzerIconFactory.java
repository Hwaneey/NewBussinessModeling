package analyzer.icon;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import analyzer.AnalyzerActionFactory;

public class AnalyzerIconFactory {
	public static final String UNDO = "../UClair/img/undo.png";
	public static final String REDO = "../UClair/img/redo.png";
	public static final String EXCEL = "../UClair/img/excel.png";
	public static final String MESSAGE = "../UClair/img/message.png";
	public static final String ANALYZE = "../UClair/img/analyze.png";
	
	public static final String PROJECT_INFO_EDITOR_ICON = "../UClair/img/info.png";
	public static final String DANGLING_TAG_RESULT_VIEW_ICON = "../UClair/img/analysis.png";
	public static final String EACH_TAG_DEPENDENCY_RESULT_VIEW_ICON = "../UClair/img/analysis.png";
	public static final String VIRTUAL_TAG_DEPENDENCY_RESULT_VIEW_ICON = "../UClair/img/analysis.png";
	public static final String PHYSICAL_ADRESS_DEPENDENCY_RESULT_VIEW_ICON = "../UClair/img/address.png";
	public static final String OBJECT_TAG_LINK_RESULT_VIEW_ICON = "../UClair/img/join.png";
	public static final String EVENT_TAG_DEPENDENCY_RESULT_VIEW_ICON = "../UClair/img/event.png";
	public static final String SCRIPT_SYNTAX_RESULT_VIEW_ICON = "../UClair/img/script.png";
	public static final String OBJECT_EFFECT_COMPATIBILITY_RESULT_VIEW_ICON = "../UClair/img/object.png";
	
	public static Icon getIcon(int key) {
		Icon icon = null;
		switch(key) {
		case AnalyzerActionFactory.DANGLING_TAG_ANALYSIS:
			icon = new ImageIcon(DANGLING_TAG_RESULT_VIEW_ICON);
			break;
		case AnalyzerActionFactory.VIRTUAL_TAG_DEPENDENCY_ANALYSIS:
			icon = new ImageIcon(VIRTUAL_TAG_DEPENDENCY_RESULT_VIEW_ICON);
			break;
		case AnalyzerActionFactory.PHYSICAL_ADRESS_DEPENDENCY_ANALYSIS:
			icon = new ImageIcon(PHYSICAL_ADRESS_DEPENDENCY_RESULT_VIEW_ICON);
			break;
		case AnalyzerActionFactory.OBJECT_TAG_LINK_INFO_ANALYSIS:
			icon = new ImageIcon(OBJECT_TAG_LINK_RESULT_VIEW_ICON);
			break;
		case AnalyzerActionFactory.EVENT_TAG_DEPENDENCY_ANALYSIS :
			icon = new ImageIcon(EVENT_TAG_DEPENDENCY_RESULT_VIEW_ICON);
			break;
		case AnalyzerActionFactory.OBJECT_EFFECT_COMPATIBILITY_ANALYSIS:
			icon = new ImageIcon(OBJECT_EFFECT_COMPATIBILITY_RESULT_VIEW_ICON);
			break;
		case AnalyzerActionFactory.EACH_TAG_DEPENDENCY_ANALYSIS:
			icon = new ImageIcon(EACH_TAG_DEPENDENCY_RESULT_VIEW_ICON);
			break;
		case AnalyzerActionFactory.SCRIPT_ANALYSIS:
			icon = new ImageIcon(SCRIPT_SYNTAX_RESULT_VIEW_ICON);
			break;
		case AnalyzerActionFactory.PROJECT_INFO_ANALYSIS:
			icon = new ImageIcon(PROJECT_INFO_EDITOR_ICON);
			break;
		}
		return icon;
	}
}
