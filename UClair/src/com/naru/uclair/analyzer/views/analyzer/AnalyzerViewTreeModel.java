package com.naru.uclair.analyzer.views.analyzer;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.naru.uclair.project.Project;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.views.analyzer.AnalyzerViewTreeModel.java
 * DESC   : �м��� �� Ʈ���� �����ϴ� ������ ������ Ʈ�� �� Ŭ����.
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
public class AnalyzerViewTreeModel extends DefaultTreeModel {
	
	/**
	 * ��ü ����ȭ ���� ���̵�.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * �м��� Ʈ���� root ���.
	 */
	private DefaultMutableTreeNode rootNode;

	/**
	 * AnalyzerViewTreeModel �⺻ ������.<br/>
	 * - root ��带 �����Ͽ� �����Ѵ�.
	 */
	public AnalyzerViewTreeModel() {
		super(null);
		rootNode = new DefaultMutableTreeNode("root");
		setRoot(rootNode);
	}
	
	/**
	 * 
	 * �м��� �� Ʈ�� ��带 ���� �����Ѵ�.<br/>
	 * - ������Ʈ ������ root ����� �̸��� �����ϰ�, ��ɺ��� ��带 �����Ͽ� �����Ѵ�.
	 * 
	 * @param newProject ������Ʈ ����.
	 */
	public void createTreeNode(Project newProject) {
		if(null != newProject) {
			String projectName = newProject.getProjectConfiguration().getName();
			rootNode.setUserObject(projectName);
			
			rootNode.add(new DefaultMutableTreeNode(AnalyzerView.ANALYZE_PROJECT));
			rootNode.add(new DefaultMutableTreeNode(AnalyzerView.ANALYZE_DANGLING_TAG));
			rootNode.add(new DefaultMutableTreeNode(AnalyzerView.ANALYZE_EACH_TAG_DEPENDENCY));
			rootNode.add(new DefaultMutableTreeNode(AnalyzerView.ANALYZE_VIRTUAL_TAG_DEPENDENCY));
			rootNode.add(new DefaultMutableTreeNode(AnalyzerView.ANALYZE_PHYSICAL_TAG));
			rootNode.add(new DefaultMutableTreeNode(AnalyzerView.ANALYZE_LINKED_TAG));
			rootNode.add(new DefaultMutableTreeNode(AnalyzerView.ANALYZE_EVENT_DEPENDENCY));
			rootNode.add(new DefaultMutableTreeNode(AnalyzerView.ANALYZE_SCRIPT_SYNTAX));
			rootNode.add(new DefaultMutableTreeNode(AnalyzerView.ANALYZE_EFFECT_COMPATIBILITY));
			
			reload(rootNode);
		}
	}
	
	public void clearTreeNode() {
		rootNode.removeAllChildren();
		
		reload(rootNode);
	}

}
