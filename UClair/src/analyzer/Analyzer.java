package analyzer;

import java.awt.Component;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.jidesoft.action.DefaultDockableBarDockableHolder;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideTabbedPane;
import com.naru.uclair.common.SystemResourceManager;
import com.naru.uclair.draw.util.WindowSelectDialog;
import com.naru.uclair.project.Project;
import com.naru.uclair.script.EngineManager;
import com.naru.uclair.util.TagListWindowDialog;
import com.naru.uclair.workspace.HMIWorkspace;

import analyzer.constants.AnalyzerConstants;
import analyzer.frame.AnalyzerMainFrame;
import analyzer.listener.AnalyzerEventListener;
import analyzer.menu.MenuBar;


public class Analyzer extends DefaultDockableBarDockableHolder  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JideTabbedPane workspacePane;


	public Analyzer(JFrame frame) {
		super();
		listeners = Collections.synchronizedList(new ArrayList<AnalyzerEventListener>());
		initialize();
	}

	public enum Analysis_Selector {
		NonExistTag, VirtualTag, PhysicalAddress, ObjConnTag, Event, CalcScript, ObjEffectCompatibility, None
	}

	public Analyzer(String title) throws HeadlessException {
		super(title);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				/**
				 * 다크테마
				 * **/
//				FlatLightLaf.install();
//				try {
//				    UIManager.setLookAndFeel( new FlatDarkLaf() );
//				} catch( Exception ex ) {
//				    System.err.println( "Failed to initialize LaF" );
//				}
				
				LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
				LookAndFeelFactory.installJideExtension(LookAndFeelFactory.EXTENSION_STYLE_XERTO);
				new Analyzer(new JFrame());
				analyzer.frame.AnalyzerMainFrame.showDemo(true);
			}
		});
	}


	//========================================================
	// 새로운 작업 영역 
	//========================================================
	/************************************************
	 * @date	: 2020. 4. 27.
	 * @설명  	: 기본적인 초기화를 수행하는 부분   
	 * @변경이력 	: 
	 ************************************************/

	private void initialize() {
		AnalyzerActionFactory.createInstance(this);		
		AnalyzerEditorFactory.createInstance(this);
	}

	protected Project currentProject;					// 새로운 라이브러리 추가 필요!!
	private List<AnalyzerEventListener> listeners;

	public Project getProject() {		
		return currentProject;
	}

	public void closeProject() {
		if (null != currentProject) {
			//workspacePane.removeAll();
			setProject(null);
		}
	}
	public void setProject(final Project newProject) {
		// 분석메뉴와 테스트 케이스 메뉴 활성화
		boolean menuEnabled = (newProject != null);
		MenuBar.analyzeMenu.setEnabled(menuEnabled);
		MenuBar.testCaseMenu.setEnabled(menuEnabled);
	
		// 분석패널 트리 활성화
		AnalyzerMainFrame.analysorTree.setVisible(menuEnabled);
		

		if (null != currentProject) {
			// singleton 인스턴스의 dipose처리
			EngineManager.disposeEngines();
			SystemResourceManager.disposeResources();

			// WindowListDialog.setWindowPath(null);
			// 화면 선택창 변경.
			WindowSelectDialog.setWindowPath(null);
			TagListWindowDialog.setCurrentTagDictionary(null);
		}
		if (currentProject == newProject) {
			return;
		}

		currentProject = newProject;
		if (null != currentProject) {
			// 시스템 리소스 관리자 초기화
			final SystemResourceManager resourceManager = SystemResourceManager
					.createInstance(false);
			resourceManager.addTopUI(this);
			resourceManager.setProject(currentProject);

			// 기본 엔진(그루비) 생성
			final EngineManager scriptEngineManager = EngineManager.createInstance(false);

			// 정보 간의 링크된 속성들을 일관성 있게 유지시켜 주기 위한 객체.
			newProject.enableLinker();

			// TODO: 이 부분 확인 필요
			// 화면 선택 창 변경.
			//WindowSelectDialog.setWindowPath(currentProject
			//		.getWindowResourcePath());
			//TagListWindowDialog.setCurrentTagDictionary(currentProject
			//		.getTagDictionary());

			// Workspace에 열려진 프로젝트 내용 저장.
			final HMIWorkspace workspace = HMIWorkspace.getInstance();
			if (!workspace.isExist(currentProject)) {
				workspace.addProject(currentProject);
			}
			HMIWorkspace.getInstance().setStartProject(
					currentProject.getProjectPath());
			HMIWorkspace.getInstance().save();
			AnalyzerMainFrame._frame.setTitle(AnalyzerMainFrame.TITLE + " | " + currentProject.getProjectPath());
			
		} else {
			AnalyzerMainFrame._frame.setTitle(AnalyzerMainFrame.TITLE);
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
	 * Analyzer 이벤트 리스너 등록(프로젝트 변경 이벤트)
	 * 
	 * @param l
	 */
	public void addAnalyzerEventListener(final AnalyzerEventListener l) {
		listeners.add(l);
	}


	public void exitWithConfirm() {
		// TODO Auto-generated method stub
		String exitMessage = AnalyzerConstants
				.getString(AnalyzerConstants.ANALYZER_EXIT_DIALOG_MSG);
		String exitTitle = AnalyzerConstants
				.getString(AnalyzerConstants.ANALYZER_EXIT_DIALOG_TITLE);
		final int result = JOptionPane.showConfirmDialog(this, exitMessage,
				exitTitle, JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			System.exit(0);	// 종료
		} else {
			return;
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
				// editor 占쌩곤옙.
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
	
	/************************************************
					VirtualTag
	 ************************************************/


	public void showWorkspace(final String key) {
		// TODO Auto-generated method stub
		if (null != workspacePane) {
			final int index = workspacePane.indexOfTab(key);
			if (-1 != index) {
				
				if (index != workspacePane.getSelectedIndex()) {
					workspacePane.setSelectedIndex(index);
				}
			} else {
				
				final JComponent c = AnalyzerEditorFactory.getFactory()
						.getEditor(key);
				if (null != c) {
					workspacePane.addTab(key, (Component) c);
					workspacePane.setSelectedComponent((Component) c);
				}
			}
		}
	}
	
	
	
}
