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
 * DESC   : ������Ʈ�� ������ �м��ϱ� ���� �޴� Ʈ�� ȭ��. 
 * �� Ʈ���� �� ������ ���� Ŭ���ϹǷν� �� �׸� ������ �м��Ѵ�.
 *
 * references : ���輭 NARU-XXX-XXX-XXX
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
	 * ��ü ����ȭ ���� ���̵�.
	 */
	private static final long serialVersionUID = 1L;
	// TODO ����ȭ.
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
	 * �м��� �� Ʈ��.
	 */
	private JTree analyzerViewTree;
	/**
	 * �м��� �� Ʈ�� ��.
	 */
	private AnalyzerViewTreeModel viewTreeModel;
	
	/**
	 * AnalyzerView �⺻ ������.<br/>
	 * 
	 * @param key �м��� �� Ű.
	 */
	public AnalyzerView(String key) {
		this(key, null);
	}

	/**
	 * AnalyzerView �⺻ ������.<br/>
	 * - �м��� �� ȭ���� �ʱ�ȭ �Ѵ�.
	 * 
	 * @param key �м��� �� Ű.
	 * @param icon �м��� �� ������.
	 */
	public AnalyzerView(String key, ImageIcon icon) {
		super(key, icon);
		initialize();
	}

	/**
	 * 
	 * �м��� �� ȭ���� �ʱ�ȭ �Ѵ�.<br/>
	 * - �並 ǥ���� Tree�� ���� �ʱ�ȭ �Ѵ�.
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
	 * ������Ʈ ���� ���濡 ���� �̺�Ʈ�� ó���Ѵ�.<br/>
	 * - ������Ʈ ������ null�� ��� Ʈ���� ��� ��带 �����Ѵ�.<br/>
	 * - ������Ʈ ������ null�� �ƴ� ��� ��带 ���� �׸���.
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
