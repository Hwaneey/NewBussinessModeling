package com.naru.uclair.analyzer.views.analyzer;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import com.jidesoft.docking.DockableFrame;
import com.naru.uclair.analyzer.constants.AnalyzerConstants;
import analyzer.listener.AnalyzerEventListener;
import com.naru.uclair.project.Project;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.views.project.AnalyzerView.java
 * DESC   : 프로젝트의 정보를 분석하기 위한 메뉴 트리 화면. 
 * 이 트리의 각 노드들을 더블 클릭하므로써 각 항목별 정보를 분석한다.
 *
 * references : 설계서 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory ywpark
 * @since 2012. 6. 19.
 * @version 1.0
 *
 */
public class AnalyzerView extends DockableFrame implements
		AnalyzerEventListener {

	/**
	 * 객체 직렬화 버전 아이디.
	 */
	private static final long serialVersionUID = 1L;
	// TODO 국제화.
	public static final String ANALYZE_PROJECT = AnalyzerConstants.getString("AnalyzerView.NodeNode.ProjectInfo"); //$NON-NLS-1$
	public static final String ANALYZE_DANGLING_TAG = AnalyzerConstants.getString("AnalyzerView.Node.DanglingTag"); //$NON-NLS-1$
	public static final String ANALYZE_EACH_TAG_DEPENDENCY = AnalyzerConstants.getString("AnalyzerView.Node.EachTag"); //$NON-NLS-1$
	public static final String ANALYZE_VIRTUAL_TAG_DEPENDENCY = AnalyzerConstants.getString("AnalyzerView.NodeVirtualTag"); //$NON-NLS-1$
	public static final String ANALYZE_PHYSICAL_TAG = AnalyzerConstants.getString("AnalyzerView.NodePhysicalTag"); //$NON-NLS-1$
	public static final String ANALYZE_LINKED_TAG = AnalyzerConstants.getString("AnalyzerView.NodeLinkedTag"); //$NON-NLS-1$
	public static final String ANALYZE_EVENT_DEPENDENCY = AnalyzerConstants.getString("AnalyzerView.NodeEventDepend"); //$NON-NLS-1$
	public static final String ANALYZE_SCRIPT_SYNTAX = AnalyzerConstants.getString("AnalyzerView.NodeScriptSyntax"); //$NON-NLS-1$
	public static final String ANALYZE_EFFECT_COMPATIBILITY = AnalyzerConstants.getString("AnalyzerView.NodeEffectComptibility"); //$NON-NLS-1$
	
	/**
	 * 분석기 뷰 트리.
	 */
	private JTree analyzerViewTree;
	/**
	 * 분석기 뷰 트리 모델.
	 */
	private AnalyzerViewTreeModel viewTreeModel;
	
	/**
	 * AnalyzerView 기본 생성자.<br/>
	 * 
	 * @param key 분석기 뷰 키.
	 */
	public AnalyzerView(String key) {
		this(key, null);
	}

	/**
	 * AnalyzerView 기본 생성자.<br/>
	 * - 분석기 뷰 화면을 초기화 한다.
	 * 
	 * @param key 분석기 뷰 키.
	 * @param icon 분석기 뷰 아이콘.
	 */
	public AnalyzerView(String key, ImageIcon icon) {
		super(key, icon);
		initialize();
	}

	/**
	 * 
	 * 분석기 뷰 화면을 초기화 한다.<br/>
	 * - 뷰를 표시할 Tree를 생성 초기화 한다.
	 *
	 */
	private void initialize() {
		viewTreeModel = new AnalyzerViewTreeModel();
		analyzerViewTree = new JTree(viewTreeModel);
		analyzerViewTree.setRootVisible(false);
		analyzerViewTree.addMouseListener(new AnalyzerViewMouseListener());
		
		getContentPane().add(new JScrollPane(analyzerViewTree));
	}

	/**
	 * 프로젝트 정보 변경에 따른 이벤트를 처리한다.<br/>
	 * - 프로젝트 정보가 null인 경우 트리의 모든 노드를 제거한다.<br/>
	 * - 프로젝트 정보가 null이 아닌 경우 노드를 새로 그린다.
	 * 
	 * @see com.naru.uclair.analyzer.listener.AnalyzerEventListener#projectChanged(com.naru.uclair.project.Project)
	 */
	@Override
	public void projectChanged(Project newProject) {
		analyzerViewTree.setRootVisible(null != newProject);
		
		if(null != newProject) {
			viewTreeModel.createTreeNode(newProject);
		}
		else {
			viewTreeModel.clearTreeNode();
		}
	}

}
