package analyzer.icon;

import javax.swing.ImageIcon;

import com.jidesoft.icons.IconsFactory;

public class AnalyzerIconFactory {
	public static final String UNDO = "../UClair/img/undo.png";
	public static final String REDO = "../UClair/img/redo.png";
	public static final String EXCEL = "../UClair/img/excel.png";
	public static final String MESSAGE = "../UClair/img/message.png";
	public static final String ANALYZE = "../UClair/img/analyze.png";
	
	public static class View {
		public final static String PROJECT_VIEW = "/resources/icon/view/project_view.png";
		public final static String PROPERTY = "/resources/icon/view/property_view.png";
		public final static String OUTLINE = "/resources/icon/view/outline_view.png";
		public final static String MESSAGE = "/resources/icon/view/message_view.png";
	}
	
	public static ImageIcon getImageIcon(String name) {
		if (null != name) {
			return IconsFactory.getImageIcon(AnalyzerIconFactory.class, name);
		} else {
			return null;
		}
	}
}
