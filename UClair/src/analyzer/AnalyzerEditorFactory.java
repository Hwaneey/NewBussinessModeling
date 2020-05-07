/**
 * 
 */
package analyzer;

import java.util.Hashtable;

import javax.swing.JComponent;

import com.naru.common.NaruAssert;
import analyzer.constants.AnalyzerConstants;
import analyzer.ui.dangling.DanglingTagAnalyzeResultView;
//import analyzer.ui.dangling.DanglingTagAnalyzeResultView;
import analyzer.ui.each.EachTagDependAnalyzeResultView;
import analyzer.ui.effect.EffectCompatibilityAnalyzeResultView;
import analyzer.ui.event.EventDependAnalyzeResultView;
import analyzer.ui.linked.ObjectTagLinkAnalyzeResultView;
import analyzer.views.projectinfo.ProjectInfoView;

//import com.naru.uclair.analyzer.ui.effect.EffectCompatibilityAnalyzeResultView;
//import com.naru.uclair.analyzer.ui.event.EventDependAnalyzeResultView;
import analyzer.ui.linked.ObjectTagLinkAnalyzeResultView;
import analyzer.ui.physical.PhysicalTagDependAnalyzeResultView;
import analyzer.ui.virtual.VirtualTagAnalyzeResultView;
//import com.naru.uclair.analyzer.ui.script.ScriptSyntaxAnalyzeResultView;
//import com.naru.uclair.analyzer.ui.virtual.VirtualTagAnalyzeResultView;
import analyzer.views.projectinfo.ProjectInfoView;
import com.naru.uclair.util.HmiVMOptions;

/**
 * @author 김기태
 * 
 */
public class AnalyzerEditorFactory {

	public static final String PROJECT_INFO_EDITOR_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.project.key"); //$NON-NLS-1$
	public static final String DATABASE_INFO_EDITOR_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.database.key"); //$NON-NLS-1$
	public static final String NETWORK_INFO_EDITOR_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.network.key"); //$NON-NLS-1$
	public static final String ALARM_INFO_EDITOR_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.alarm.key"); //$NON-NLS-1$
	public static final String TAG_INFO_EDITOR_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.tagdictionary.key"); //$NON-NLS-1$
	public static final String DEVICE_INFO_EDITOR_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.device.key"); //$NON-NLS-1$
	public static final String DATA_COLLECT_EDITOR_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.datacollection.key"); //$NON-NLS-1$
	public static final String EVENT_EDITOR_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.event.key"); //$NON-NLS-1$
	public static final String USER_EDITOR_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.user.key"); //$NON-NLS-1$
	public static final String SCRIPT_EDITOR_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.script.key"); //$NON-NLS-1$
	public static final String WINDOW_INFO_EDITOR_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.window.key"); //$NON-NLS-1$
	
	public static final String DANGLING_TAG_RESULT_VIEW_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.DanglingTag.key"); //$NON-NLS-1$
	public static final String EACH_TAG_DEPENDENCY_RESULT_VIEW_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.EachTag.Key"); //$NON-NLS-1$
	public static final String VIRTUAL_TAG_DEPENDENCY_RESULT_VIEW_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.VirtualTag.Key"); //$NON-NLS-1$
	public static final String PHYSICAL_ADRESS_DEPENDENCY_RESULT_VIEW_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.PysicalAddress.Key"); //$NON-NLS-1$
	public static final String OBJECT_TAG_LINK_RESULT_VIEW_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.ObjectTagLink.Key"); //$NON-NLS-1$
	public static final String EVENT_TAG_DEPENDENCY_RESULT_VIEW_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.EventTag.Key"); //$NON-NLS-1$
	public static final String EFFECT_COMPATIBILITY_RESULT_VIEW_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.EffectCompatibility.Key"); //$NON-NLS-1$
	public static final String SCRIPT_SYNTAX_RESULT_VIEW_KEY = AnalyzerConstants.getString("AnalyzerEditorFactory.ScriptSyntax.Key"); //$NON-NLS-1$
	
	private Hashtable<String, JComponent> editorTable;

	private static AnalyzerEditorFactory singleton;

	private Analyzer analyzer;

	public AnalyzerEditorFactory(Analyzer analyzer) {
		setAnalyzer(analyzer);
		this.editorTable = new Hashtable<String, JComponent>();
	}

	/**
	 * 인스턴스 초기화 혹은 반환한다.
	 * 
	 * @param analyzer
	 */
	public static final void createInstance(Analyzer analyzer) {
		NaruAssert.isNotNull(analyzer);
		if (null == singleton) {
			singleton = new AnalyzerEditorFactory(analyzer);
		}
	}

	/**
	 * AnalyzerEditorFactory를 반환한다.
	 * 
	 * @return
	 */
	public static final AnalyzerEditorFactory getFactory() {

		return singleton;
	}

	private void setAnalyzer(Analyzer owner) {
		this.analyzer = owner;
	}
	
	private Analyzer getAnalyzer() {
		return this.analyzer;
	}

	/**
	 * 편집기 및 뷰어를 생성한다.
	 * 
	 * @param key
	 * @return
	 */
	private JComponent createEditor(String key) {
		JComponent resultView = null;

		if(PROJECT_INFO_EDITOR_KEY.equals(key)) {
			resultView = createProjectInfoEditor();
		}
		else if(DANGLING_TAG_RESULT_VIEW_KEY.equals(key)) {
			resultView = new DanglingTagAnalyzeResultView();
		}
		else if(EACH_TAG_DEPENDENCY_RESULT_VIEW_KEY.equals(key)) {
			resultView = new EachTagDependAnalyzeResultView();
		}
		else if(VIRTUAL_TAG_DEPENDENCY_RESULT_VIEW_KEY.equals(key)) {
			resultView = new VirtualTagAnalyzeResultView();
		}
		else if(PHYSICAL_ADRESS_DEPENDENCY_RESULT_VIEW_KEY.equals(key)) {
			resultView = new PhysicalTagDependAnalyzeResultView();
		}
		else if(OBJECT_TAG_LINK_RESULT_VIEW_KEY.equals(key)) {
			resultView = new ObjectTagLinkAnalyzeResultView();
		}
		else if(EVENT_TAG_DEPENDENCY_RESULT_VIEW_KEY.equals(key)) {
			resultView = new EventDependAnalyzeResultView();
		}
//		else if(SCRIPT_SYNTAX_RESULT_VIEW_KEY.equals(key)) {
//			resultView = new ScriptSyntaxAnalyzeResultView();
//		}
		else if(EFFECT_COMPATIBILITY_RESULT_VIEW_KEY.equals(key)) {
			resultView = new EffectCompatibilityAnalyzeResultView();
		}
		else {
			if (HmiVMOptions.isDebug()) {
				System.out.println(key + " is not exist in editor factory"); //$NON-NLS-1$
			}
			resultView = null;
		}

		if (null != resultView) {
			this.editorTable.put(key, resultView);
		}

		return resultView;
	}

	private JComponent createProjectInfoEditor() {
		final ProjectInfoView editor = new ProjectInfoView(PROJECT_INFO_EDITOR_KEY);
		return editor;
	}

	public JComponent getEditor(String key) {
		JComponent editor = editorTable.get(key);
		if (null == editor) {
			editor = createEditor(key);
		}
		return editor;
	}
	
}
