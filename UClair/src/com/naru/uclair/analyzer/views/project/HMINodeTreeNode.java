package com.naru.uclair.analyzer.views.project;

import java.util.Enumeration;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.naru.common.NaruAssert;
import com.naru.uclair.node.HMINode;

/**
 * <p>
 * Title: iA-Canvas
 * </p>
 * <p>
 * Description: 모니터링 자동화 툴
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: (주) 나루기술
 * </p>
 * 
 * @author 정해문
 * @version 1.0
 */

public class HMINodeTreeNode implements MutableTreeNode {

	private MutableTreeNode parent;

	private HMINode node;

	public HMINodeTreeNode(HMINode userObject) {
		setUserObject(userObject);
	}

	public HMINode getNode() {
		return node;
	}

	@Override
	public void insert(MutableTreeNode child, int index) {
		// Do not anything.
		// throw new IllegalStateException("NodeTreeNode can't take a child");
	}

	@Override
	public void remove(int index) {
		// Do not anything.
		// throw new IllegalStateException("NodeTreeNode can't take a child");
	}

	@Override
	public void remove(MutableTreeNode node) {
		// Do not anything.
		// throw new IllegalStateException("NodeTreeNode can't take a child");
	}

	@Override
	public void setUserObject(Object object) {
		NaruAssert.isNotNull(object);
		NaruAssert.isTrue(object instanceof HMINode);

		if (node != object) {
			// if (null != node) {
			// node.removePropertyChangeListener(this);
			// }
			node = (HMINode) object;
			// node.addPropertyChangeListener(this);
		}
	}

	@Override
	public void removeFromParent() {
		if (null != parent) {
			parent.remove(this);
		}
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public void setParent(MutableTreeNode newParent) {
		this.parent = newParent;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		// Do not anything.
		return null;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public int getIndex(TreeNode node) {
		// Do not anything.
		return -1;
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public Enumeration children() {
		// Do not anything.
		return null;
	}

	@Override
	public String toString() {
		return node.getName();
	}
}
