package com.naru.uclair.analyzer.views.analyzer;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.naru.uclair.project.Project;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.views.analyzer.AnalyzerViewTreeModel.java
 * DESC   : 분석기 뷰 트리를 구성하는 정보를 가지는 트리 모델 클래스.
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
public class AnalyzerViewTreeModel extends DefaultTreeModel {
	
	/**
	 * 객체 직렬화 버전 아이디.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 분석기 트리의 root 노드.
	 */
	private DefaultMutableTreeNode rootNode;

	/**
	 * AnalyzerViewTreeModel 기본 생성자.<br/>
	 * - root 노드를 생성하여 설정한다.
	 */
	public AnalyzerViewTreeModel() {
		super(null);
		rootNode = new DefaultMutableTreeNode("root");
		setRoot(rootNode);
	}
	
	/**
	 * 
	 * 분석기 뷰 트리 노드를 새로 구성한다.<br/>
	 * - 프로젝트 정보로 root 노드의 이름을 변경하고, 기능별로 노드를 생성하여 설정한다.
	 * 
	 * @param newProject 프로젝트 정보.
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
