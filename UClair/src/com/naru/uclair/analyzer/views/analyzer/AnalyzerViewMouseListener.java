package com.naru.uclair.analyzer.views.analyzer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import analyzer.AnalyzerActionFactory;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.views.analyzer.AnalyzerViewMouseListener.java
 * DESC   : 분석기 뷰 마우스 이벤트 처리 클래스.
 *
 * references : 설계서 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 20.
 * @version 1.0
 *
 */
public class AnalyzerViewMouseListener extends MouseAdapter {
	
	/**
	 * 마우스 클릭 이벤트를 처리한다.<br/>
	 * - 노드를 더블클릭시 해당 노드의 분석 action을 호출한다.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!(e.getSource() instanceof JTree)) {
			return;
		}
		
		if(e.getButton() == MouseEvent.BUTTON1
				&& e.getClickCount() == 2) {
			JTree viewTree = (JTree) e.getSource();
			
			Object obj = viewTree.getLastSelectedPathComponent();
			
			if(obj instanceof DefaultMutableTreeNode) {
				performedTreeNodeClick((DefaultMutableTreeNode) obj);
			}
		}
	}
	
	/**
	 * 
	 * 선택된 트리 노드의 클릭 이벤트를 처리한다.<br/>
	 * - 트리 노드명을 확인하여, 각 노드에 맞는 action을 호출한다.
	 * 
	 * @param treeNode 트리 노드.
	 */
	private void performedTreeNodeClick(DefaultMutableTreeNode treeNode) {
		if(null != treeNode) {
			Action action = null;
			AnalyzerActionFactory actionFactory = AnalyzerActionFactory.getFactory();
			
			if(AnalyzerView.ANALYZE_PROJECT.equals(treeNode.getUserObject())) {
				action = actionFactory.getAction(AnalyzerActionFactory.PROJECT_INFO);
			}
			else if(AnalyzerView.ANALYZE_DANGLING_TAG.equals(treeNode.getUserObject())) {
				action = actionFactory.getAction(AnalyzerActionFactory.DANGLING_TAG_ANALYSIS);
			}
			else if(AnalyzerView.ANALYZE_EACH_TAG_DEPENDENCY.equals(treeNode.getUserObject())) {
				action = actionFactory.getAction(AnalyzerActionFactory.EACH_TAG_DEPENDENCY_ANALYSIS);
			}
			else if(AnalyzerView.ANALYZE_VIRTUAL_TAG_DEPENDENCY.equals(treeNode.getUserObject())) {
				action = actionFactory.getAction(AnalyzerActionFactory.VIRTUAL_TAG_DEPENDENCY_ANALYSIS);
			}
			else if(AnalyzerView.ANALYZE_PHYSICAL_TAG.equals(treeNode.getUserObject())) {
				action = actionFactory.getAction(AnalyzerActionFactory.PHYSICAL_ADRESS_DEPENDENCY_ANALYSIS);
			}
			else if(AnalyzerView.ANALYZE_LINKED_TAG.equals(treeNode.getUserObject())) {
				action = actionFactory.getAction(AnalyzerActionFactory.OBJECT_TAG_LINK_INFO_ANALYSIS);
			}
			else if(AnalyzerView.ANALYZE_EVENT_DEPENDENCY.equals(treeNode.getUserObject())) {
				action = actionFactory.getAction(AnalyzerActionFactory.EVENT_TAG_DEPENDENCY_ANALYSIS);
			}
			else if(AnalyzerView.ANALYZE_SCRIPT_SYNTAX.equals(treeNode.getUserObject())) {
				action = actionFactory.getAction(AnalyzerActionFactory.SCRIPT_ANALYSIS);
			}
			else if(AnalyzerView.ANALYZE_EFFECT_COMPATIBILITY.equals(treeNode.getUserObject())) {
				action = actionFactory.getAction(AnalyzerActionFactory.OBJECT_EFFECT_COMPATIBILITY_ANALYSIS);
			}
			
			if(null != action) {
				action.actionPerformed(null);
			}
		}
	}

}
