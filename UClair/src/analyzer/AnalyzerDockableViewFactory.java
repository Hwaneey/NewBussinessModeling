/**
 * 
 */
package analyzer;

import java.awt.Dimension;
import java.util.Hashtable;

import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;
import com.naru.common.NaruAssert;
import com.naru.uclair.analyzer.constants.AnalyzerConstants;
import com.naru.uclair.analyzer.views.analyzer.AnalyzerView;
import com.naru.uclair.analyzer.views.message.MessageView;
import com.naru.uclair.analyzer.views.project.ProjectView;

/**
 * @author 占쏙옙占쏙옙占�
 * 
 */
public class AnalyzerDockableViewFactory {
	public static final String PROJECT_VIEW_TITLE = AnalyzerConstants
			.getString("AnalyzerDockableViewFactory.projectview.title"); //$NON-NLS-1$
	public static final String TAG_VIEW_TITLE = AnalyzerConstants
			.getString("AnalyzerDockableViewFactory.tagview.title"); //$NON-NLS-1$
	public static final String OUTLINE_VIEW_TITLE = AnalyzerConstants
			.getString("AnalyzerDockableViewFactory.outlineview.title"); //$NON-NLS-1$
	public static final String PROPERTY_VIEW_TITLE = AnalyzerConstants
			.getString("AnalyzerDockableViewFactory.propertyview.title"); //$NON-NLS-1$
	public static final String MESSAGE_VIEW_TITLE = AnalyzerConstants
			.getString("AnalyzerDockableViewFactory.messageview.title"); //$NON-NLS-1$

	public static final String PROJECT_VIEW_KEY = AnalyzerConstants
			.getString("AnalyzerDockableViewFactory.projectview.key"); //$NON-NLS-1$
	public static final String TAG_VIEW_KEY = AnalyzerConstants
			.getString("AnalyzerDockableViewFactory.tagview.key"); //$NON-NLS-1$
	public static final String OUTLINE_VIEW_KEY = AnalyzerConstants
			.getString("AnalyzerDockableViewFactory.outlineview.key"); //$NON-NLS-1$
	public static final String PROPERTY_VIEW_KEY = AnalyzerConstants
			.getString("AnalyzerDockableViewFactory.propertyview.key"); //$NON-NLS-1$
	public static final String MESSAGE_VIEW_KEY = AnalyzerConstants
			.getString("AnalyzerDockableViewFactory.messageview.key"); //$NON-NLS-1$
	
	public static final String ANALYZER_VIEW_TITLE = AnalyzerConstants
	.getString("AnalyzerDockableViewFactory.analyzerview.title"); //$NON-NLS-1$
	public static final String ANALYZER_VIEW_KEY = AnalyzerConstants
	.getString("AnalyzerDockableViewFactory.analyzerview.key"); //$NON-NLS-1$

	private static AnalyzerDockableViewFactory singleton;

	public static final void createInstance(Analyzer Analyzer) {
		NaruAssert.isNotNull(Analyzer);
		if (null == singleton) {
			singleton = new AnalyzerDockableViewFactory(Analyzer);
		}
	}

	public static final AnalyzerDockableViewFactory getFactory() {
		return singleton;
	}

	private Analyzer Analyzer = null;

	private Hashtable<String, DockableFrame> viewTable;

	private AnalyzerDockableViewFactory(Analyzer owner) {
		setAnalyzer(owner);

		// 占쏙옙占쏙옙 占쌘료구占쏙옙占쏙옙 占십깍옙화占싼댐옙.
		viewTable = new Hashtable<String, DockableFrame>();

		// 占쏙옙 占썰를 占쏙옙占쏙옙占싼댐옙.
		//createView(PROJECT_VIEW_KEY);
		createView(TAG_VIEW_KEY);
		createView(OUTLINE_VIEW_KEY);
		createView(PROPERTY_VIEW_KEY);
		createView(MESSAGE_VIEW_KEY);

		createView(ANALYZER_VIEW_KEY);
	}

	private final void setAnalyzer(Analyzer owner) {
		Analyzer = owner;
	}

	private DockableFrame createView(String key) {
		DockableFrame view = null;

		if (PROJECT_VIEW_KEY.equals(key)) {
			view = createProjectView();
		} else if (TAG_VIEW_KEY.equals(key)) {
			view = createTagView();
		} else if (OUTLINE_VIEW_KEY.equals(key)) {
			view = createOutlineView();
		} else if (PROPERTY_VIEW_KEY.equals(key)) {
			view = createPropertyView();
		} else if (MESSAGE_VIEW_KEY.equals(key)) {
			view = createMessageView();
		} else if (ANALYZER_VIEW_KEY.equals(key)) {
			view = createAnalyzerView();
		} else {
			System.out
					.println(key + " is not exist in the view frame factory."); //$NON-NLS-1$
			view = null;
		}

		if (null != view) {
			viewTable.put(key, view);
		}

		return view;
	}

	private DockableFrame createProjectView() {
		final ProjectView frame = new ProjectView(PROJECT_VIEW_KEY,
				AnalyzerIconFactory
						.getImageIcon(AnalyzerIconFactory.View.PROJECT_VIEW));
		frame.setSideTitle(PROJECT_VIEW_TITLE);
		frame.setTabTitle(PROJECT_VIEW_TITLE);

		frame.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
		frame.setInitIndex(0);
		frame.setPreferredSize(new Dimension(180, 200));

		getAnalyzer().addAnalyzerEventListener(frame);

		return frame;
	}

	private DockableFrame createTagView() {
		// final TagFrame frame = new TagFrame(TAG_VIEW_KEY,
		//AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory.System.TAG_DIC));
		// frame.setSideTitle(TAG_VIEW_TITLE);
		// frame.setTabTitle(TAG_VIEW_TITLE);
		// // frame.setTreeModel(getAnalyzer().getProject().getTagDictionary());
		// //
		// frame.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
		// frame.setInitIndex(0);
		// frame.setPreferredSize(new Dimension(180, 200));
		//		
		// getAnalyzer().addAnalyzerEventListener(frame);
		//		
		// return frame;
		return null;
	}

	private DockableFrame createOutlineView() {
		// final OutlineView frame = new OutlineView(OUTLINE_VIEW_KEY,
		// AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory.Window.
		// OUTLINE_VIEW));
		// frame.setSideTitle(OUTLINE_VIEW_TITLE);
		// frame.setTabTitle(OUTLINE_VIEW_TITLE);
		//
		// frame.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
		// frame.setInitIndex(1);
		// frame.setPreferredSize(new Dimension(180, 200));
		//		
		// return frame;
		return null;
	}

	private DockableFrame createPropertyView() {
		// final PropertyView frame = new PropertyView(PROPERTY_VIEW_KEY,
		// AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory.Window.
		// PROPERTY_VIEW));
		// frame.setSideTitle(PROPERTY_VIEW_TITLE);
		// frame.setTabTitle(PROPERTY_VIEW_TITLE);
		//
		// frame.getContext().setInitSide(DockContext.DOCK_SIDE_EAST);
		// frame.setInitIndex(3);
		// frame.setPreferredSize(new Dimension(200, 200));
		//		
		// return frame;
		return null;
	}

	private DockableFrame createMessageView() {
		final DockableFrame frame = new MessageView(MESSAGE_VIEW_KEY,
				AnalyzerIconFactory
						.getImageIcon(AnalyzerIconFactory.Window.MESSAGE_VIEW));
		// final DockableFrame frame = new DockableFrame(MESSAGE_VIEW_KEY,
		// AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory.Window.
		// MESSAGE_VIEW));
		frame.setSideTitle(MESSAGE_VIEW_TITLE);
		frame.setTabTitle(MESSAGE_VIEW_TITLE);

		frame.getContext().setInitSide(DockContext.DOCK_SIDE_SOUTH);
		frame.setInitIndex(2);
		frame.setPreferredSize(new Dimension(200, 150));

		return frame;
	}

	public Analyzer getAnalyzer() {
		return Analyzer;
	}

	public DockableFrame getView(String key) {
		DockableFrame view = viewTable.get(key);

		if (null == view) {
			view = createView(key);
		}

		return view;
	}

	public void showAllViews() {
		getAnalyzer().getDockingManager().addFrame(getView(ANALYZER_VIEW_KEY));
		// getAnalyzer().getDockingManager().addFrame(getView(PROJECT_VIEW_KEY));
		// getAnalyzer().getDockingManager().addFrame(getView(TAG_VIEW_KEY));
		//getAnalyzer().getDockingManager().addFrame(getView(OUTLINE_VIEW_KEY));
		//getAnalyzer().getDockingManager().addFrame(getView(PROPERTY_VIEW_KEY))
		// ;
		getAnalyzer().getDockingManager().addFrame(getView(MESSAGE_VIEW_KEY));
	}

	// /**
	// * 占쏙옙占� Editor 占쏙옙占쏙옙占쏙옙 Evnent占쏙옙 占쏙옙占쏙옙占쏙옙 占쌍깍옙 占쏙옙占쏙옙 占쌨쇽옙占쏙옙.
	// */
	// public void linkEvents() {
	// WindowEditor workspace = getAnalyzer().getWindowWorkspace();
	//		
	// OutlineView outlineView = (OutlineView) getView(OUTLINE_VIEW_KEY);
	// workspace.addPropertyChangeListener(outlineView);
	//		
	// PropertyView propertyView = (PropertyView) getView(PROPERTY_VIEW_KEY);
	// workspace.setPropertyView(propertyView);
	// // workspace.addViewChangeListener(propertyView);
	//
	// }
	//	
	// public void unlinkEvents() {
	// WindowEditor workspace = getAnalyzer().getWindowWorkspace();
	//		
	// OutlineView outlineView = (OutlineView) getView(OUTLINE_VIEW_KEY);
	// workspace.removePropertyChangeListener(outlineView);
	//		
	// PropertyView propertyView = (PropertyView) getView(PROPERTY_VIEW_KEY);
	// // workspace.removeViewChangeListener(propertyView);
	// }
	
	/**
	 * 占싻쇽옙占쏙옙 占썰를 占쏙옙占쏙옙占싹울옙 占쏙옙환占싼댐옙.<br/>
	 * - 占싻쇽옙占쏙옙 占쏙옙 占십깍옙화 占쏙옙 占싻쇽옙占썩에 占쏙옙占쏙옙占쏙옙트 占쏙옙占쏙옙 占쏙옙占쏙옙 占싱븝옙트 占쏙옙占쏙옙占십뤄옙 占쌩곤옙占싼댐옙.
	 * 
	 * @return DockableFrame 占싻쇽옙占쏙옙 占쏙옙.
	 */
	private DockableFrame createAnalyzerView() {
		AnalyzerView frame = new AnalyzerView(ANALYZER_VIEW_KEY, 
				AnalyzerIconFactory.getImageIcon(AnalyzerIconFactory.View.PROJECT_VIEW));
		
		frame.setSideTitle(ANALYZER_VIEW_TITLE);
		frame.setTabTitle(ANALYZER_VIEW_TITLE);

		frame.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
		frame.setInitIndex(0);
		frame.setPreferredSize(new Dimension(180, 200));

		getAnalyzer().addAnalyzerEventListener(frame);

		return frame;
	}
}
