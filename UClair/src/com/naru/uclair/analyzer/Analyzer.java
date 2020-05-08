/**
 * 
 */
package com.naru.uclair.analyzer;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jidesoft.action.DefaultDockableBarDockableHolder;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.status.StatusBar;
import com.jidesoft.swing.JideTabbedPane;
import com.naru.common.JideLicenseInstaller;
import com.naru.uclair.analyzer.constants.AnalyzerConstants;
import com.naru.uclair.analyzer.listener.AnalyzerEventListener;
import com.naru.uclair.common.SystemResourceManager;
import com.naru.uclair.draw.util.WindowSelectDialog;
import com.naru.uclair.project.Project;
import com.naru.uclair.script.EngineManager;
import com.naru.uclair.util.HmiVMOptions;
import com.naru.uclair.util.TagListWindowDialog;
import com.naru.uclair.workspace.HMIWorkspace;

/**
 * @author �����
 * 
 */
public class Analyzer extends DefaultDockableBarDockableHolder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JideLicenseInstaller.install();
		boolean useDefaultLnf = true;
		if (HmiVMOptions.getDefinedLookNFeel() != null) {
			try {
				UIManager.setLookAndFeel(HmiVMOptions.getDefinedLookNFeel());
				useDefaultLnf = false;
			} catch (Exception e) {
				useDefaultLnf = true;
			}
		}
		if (useDefaultLnf) {
			LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
			LookAndFeelFactory
					.installJideExtension(LookAndFeelFactory.ECLIPSE3X_STYLE);
		}
		
//		String infoLog = AnalyzerConstants
//		.getString(AnalyzerConstants.ANALYZER_LOG_FILE);
//String errorLog = AnalyzerConstants
//		.getString(AnalyzerConstants.ANALYZER_ERROR_LOG_FILE);
//LoggerManager
//		.createSystemLogger(LoggerManager.SYSTEM_OUT_NAME, infoLog);
//LoggerManager.createSystemLogger(LoggerManager.SYSTEM_ERR_NAME,
//		errorLog);

		final Analyzer analyzer = new Analyzer();
		//analyzer.lookAndFeelInit();

		analyzer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// ������ �̹��� ����
		// analyzer.setIconImage(DeveloperIconFactory.getImageIcon(
		// DeveloperIconFactory.Logo.SMALL).getImage());

		analyzer.getLayoutPersistence().loadLayoutData();
		analyzer.enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		analyzer.toFront();
		analyzer.setExtendedState(Frame.MAXIMIZED_BOTH);
	}

	/**
	 * ���¹�
	 */
	private StatusBar statusBar;

	/**
	 * �м��ؾߵǴ� ������Ʈ
	 */
	protected Project currentProject;

	private List<AnalyzerEventListener> listeners;

	private JideTabbedPane workspacePane;

	/**
	 * ������
	 * 
	 * @throws HeadlessException
	 */
	public Analyzer() throws HeadlessException {
		this(AnalyzerConstants.getString(AnalyzerConstants.ANALYZER_TITLE));
	}

	/**
	 * ������
	 * 
	 * @param title
	 */
	public Analyzer(String title) {
		// TODO Auto-generated constructor stub
		super(title);
		listeners = Collections
				.synchronizedList(new ArrayList<AnalyzerEventListener>());
		initialize();
	}

	/**
	 * Analyzer �̺�Ʈ ������ ���(������Ʈ ���� �̺�Ʈ)
	 * 
	 * @param l
	 */
	public void addAnalyzerEventListener(final AnalyzerEventListener l) {
		listeners.add(l);
	}

	/**
	 * UI �ʱ�ȭ
	 */
	protected void initialize() {
		AnalyzerEditorFactory.createInstance(this);
		AnalyzerActionFactory.createInstance(this);
		AnalyzerDockableViewFactory.createInstance(this);
		AnalyzerCommandBarFactory.createInstance(this);

		getLayoutPersistence()
				.setProfileKey(
						AnalyzerConstants
								.getString(AnalyzerConstants.ANALYZER_PROFILE));

		AnalyzerEditorFactory editorFactory = AnalyzerEditorFactory
				.getFactory();

		workspacePane = new JideTabbedPane();
		workspacePane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Component selectedTab = workspacePane.getSelectedComponent();

				// AnalyzerEditorFactory.getFactory()
				// .actionDelegation(selectedTab);
			}
		});

		workspacePane.setShowCloseButtonOnTab(true);
		workspacePane.setShowCloseButton(true);
		workspacePane.setShowCloseButtonOnSelectedTab(true);

		//workspacePane.setTabClosableAt(0, false);
		//workspacePane.setTabClosableAt(1, true);
		// workspacePane.setTabPlacement(SwingConstants.BOTTOM);
		workspacePane.setTabPlacement(SwingConstants.TOP);
		getDockingManager().getWorkspace().add(workspacePane);

		AnalyzerCommandBarFactory.getFactory().showAllCommandBars();
		AnalyzerDockableViewFactory.getFactory().showAllViews();
	}

	public Project getProject() {
		// TODO Auto-generated method stub
		return currentProject;
	}

	public void saveProject() {
		if (getProject() != null) {
			getProject().save(getProject().getProjectPath());
		}
	}

	public void closeProject() {
		if (null != currentProject) {
			// TODO ȭ�� �ݱ� ���� ����.
			// final Action action = getWindowWorkspace().getAction(
			// EditorActionFactory.CLOSE_ALL);
			// if (null != action) {
			// action.actionPerformed(new ActionEvent(this,
			// EditorActionFactory.CLOSE_ALL, null));
			// }
			workspacePane.removeAll();

			setProject(null);
		}
	}

	public void setProject(final Project newProject) {
		if (null != currentProject) {
			// singleton �ν��Ͻ��� diposeó��
			EngineManager.disposeEngines();
			SystemResourceManager.disposeResources();

			// WindowListDialog.setWindowPath(null);
			// ȭ�� ����â ����.
			WindowSelectDialog.setWindowPath(null);
			TagListWindowDialog.setCurrentTagDictionary(null);
		}

		if (currentProject == newProject) {
			return;
		}

		currentProject = newProject;
		if (null != currentProject) {
			// �ý��� ���ҽ� ������ �ʱ�ȭ
			final SystemResourceManager resourceManager = SystemResourceManager
					.createInstance(false);
			resourceManager.addTopUI(this);
			resourceManager.setProject(currentProject);

			// ��ũ��Ʈ ���� �� �ý��� �Լ� �ʱ�ȭ. �⺻ �Լ���(Mockup) ����.
			// final FunctionManager functionManager = FunctionManager
			// .getInstance();
			// functionManager.setBindingFunction(ITagFunction.BIND_NAME,
			// new DeveloperTagFunctionImpl(currentProject.getTagDictionary()));
			// functionManager.setBindingFunction(ISystemFunction.BIND_NAME,
			// new DeveloperSystemFunctionImpl());
			// functionManager.setBindingFunction(IWindowFunction.BIND_NAME,
			// new DeveloperWindowFunctionImpl());

			// Java �׽�Ʈ �� ���� ����
			// final EngineManager scriptEngineManager = EngineManager
			// .createInstance(false, IScriptConstants.ENGINE_NAME_JAVA);
			// // ���̽� �׽�Ʈ �� ���� ����
			// final EngineManager scriptEngineManager = EngineManager
			// .createInstance(false, IScriptConstants.ENGINE_NAME_JYTHON);
			// �⺻ ����(�׷��) ����
			final EngineManager scriptEngineManager = EngineManager
					.createInstance(false);

			// ��ũ��Ʈ ������ ���� �Լ� Binding
			// scriptEngineManager.setBindings(functionManager
			// .getBindingFunctions());

			// ���� ���� ��ũ�� �Ӽ����� �ϰ��� �ְ� �������� �ֱ� ���� ��ü.
			newProject.enableLinker();

//			final Dimension defaultSize = currentProject
//					.getProjectConfiguration().getResolution();
//			HMIDrawingWindowProperty.setDefaultWidth(defaultSize.width);
//			HMIDrawingWindowProperty.setDefaultHeight(defaultSize.height);

			//ImageFigure.setResourceRoot(currentProject.getImageResourcePath())
			// ;

			// WindowListDialog.setWindowPath(currentProject
			// .getWindowResourcePath());
			// ȭ�� ���� â ����.
			WindowSelectDialog.setWindowPath(currentProject
					.getWindowResourcePath());
			TagListWindowDialog.setCurrentTagDictionary(currentProject
					.getTagDictionary());

			// // ȭ�鿡 ���Ǵ� SVG�ɺ��� �����ϴ� ����� �ʱ�ȭ
			// try {
			// SVGSymbolLoader.createInstance(
			// new File(currentProject.getImageResourcePath()));
			// }
			// catch (IOException ioe) {
			// ioe.printStackTrace();
			// }

			// Workspace�� ������ ������Ʈ ���� ����.
			final HMIWorkspace workspace = HMIWorkspace.getInstance();
			if (!workspace.isExist(currentProject)) {
				workspace.addProject(currentProject);
			}
			HMIWorkspace.getInstance().setStartProject(
					currentProject.getProjectPath());
			HMIWorkspace.getInstance().save();
		}

		fireProjectChangeEvent(currentProject);
	}

	protected void fireProjectChangeEvent(final Project newProject) {
		for (final AnalyzerEventListener l : listeners) {
			try {
				l.projectChanged(newProject);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ������ȭ���� ������ �̺�Ʈ�� �޾Ƽ� ����Ȯ�� ó���� �Ѵ�.
	 */
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			exitWithConfirm();
		}
	}

	/**
	 * �������� Ȯ���� ���� �����Ѵ�.
	 */
	public void exitWithConfirm() {
		// TODO Auto-generated method stub
		String exitMessage = AnalyzerConstants
				.getString(AnalyzerConstants.ANALYZER_EXIT_DIALOG_MSG);
		String exitTitle = AnalyzerConstants
				.getString(AnalyzerConstants.ANALYZER_EXIT_DIALOG_TITLE);
		final int result = JOptionPane.showConfirmDialog(this, exitMessage,
				exitTitle, JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			exit();
		} else {
			return;
		}
	}

	/**
	 * �м��⸦ �����Ѵ�.
	 */
	private void exit() {
		System.exit(0);
	}

	// public void showWorkspace(final Editor editor) {
	// showWorkspace(editor.getEditorName());
	// }

	public void showWorkspace(final String key) {
		if (null != workspacePane) {
			final int index = workspacePane.indexOfTab(key);
			if (-1 != index) {
				// ���� Ȱ��ȭ�� Editor�� �ƴϸ� Ȱ��ȭ ��Ų��.
				if (index != workspacePane.getSelectedIndex()) {
					workspacePane.setSelectedIndex(index);
				}
			} else {
				// editor �߰�.
				final JComponent c = AnalyzerEditorFactory.getFactory()
						.getEditor(key);
				if (null != c) {
					workspacePane.addTab(key, (Component) c);
					workspacePane.setSelectedComponent((Component) c);
				}
			}
		}
	}

	public JComponent getEditor(final String key) {
		if (null != workspacePane) {

			final int index = workspacePane.indexOfTab(key);
			if (-1 != index) {
				if (index != workspacePane.getSelectedIndex()) {
					final JComponent c = (JComponent) workspacePane
							.getComponentAt(index);
					return c;
				}
			} else {
				// editor �߰�.
				final JComponent c = AnalyzerEditorFactory.getFactory()
						.getEditor(key);
				if (null != c) {
					workspacePane.addTab(key, (Component) c);
					workspacePane.setSelectedComponent((Component) c);
					return c;
				}
			}
		}
		return null;
	}

}
