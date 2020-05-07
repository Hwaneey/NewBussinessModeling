/**
 * 
 */
package analyzer.constants;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Analyzer Constants 클래스
 * 
 * @author 김기태
 * 
 */
public class AnalyzerConstants {

	public static final String ANALYZER_TITLE = "Analyzer.title";
	public static final String ANALYZER_PROFILE = "Analyzer.profile";
	public static final String ANALYZER_LOG_FILE = "Analyzer.log.file";
	public static final String ANALYZER_ERROR_LOG_FILE = "Analyzer.errorlog.file";

	public static final String ANALYZER_EXIT_DIALOG_MSG = "Analyzer.exit.dialog.msg";
	public static final String ANALYZER_EXIT_DIALOG_TITLE = "Analyzer.exit.dialog.title";

	public static final String PROJECT_ABSTRACT_VIEWER_KEY = "Analyzer.project.abstract.viewer.key";
	public static final String TAG_VIEWER_KEY = "Analyzer.tag.viewer.key";
	public static final String EVENT_VIEWER_KEY = "Analyzer.datacollection.key";
	public static final String EVENT_EDITOR_KEY = "IOServerEditorFactory.event.key";
	public static final String DEVICE_VIEWER_KEY = "IOServerEditorFactory.device.key";
	public static final String ANALYSIS_RESULT_VIEWER_KEY = "IOServerEditorFactory.memory.key";

	private static final String BUNDLE_NAME = "resources.analyzer_constants"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			e.printStackTrace();
			return null;
		}
	}
}
