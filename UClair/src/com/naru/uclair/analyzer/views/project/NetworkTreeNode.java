package com.naru.uclair.analyzer.views.project;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.naru.uclair.project.NetworkConfiguration;

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

public class NetworkTreeNode implements MutableTreeNode {

	public static final String DEFAULT_NAME = "네트워크";

	private MutableTreeNode parent;
	private Vector children;

	private NetworkConfiguration userObject;

	public NetworkTreeNode() {
		this(null);
	}

	public NetworkTreeNode(final NetworkConfiguration userObject) {
		super();
		parent = null;
		this.userObject = userObject;
	}

	public Enumeration children() {
		if (children == null) {
			return DefaultMutableTreeNode.EMPTY_ENUMERATION;
		} else {
			return children.elements();
		}
	}

	public boolean getAllowsChildren() {
		return false;
	}

	public TreeNode getChildAt(final int childIndex) {
		if (children == null) {
			throw new ArrayIndexOutOfBoundsException("node has no children");
		}
		return (TreeNode) children.elementAt(childIndex);
	}

	public int getChildCount() {
		if (children == null) {
			return 0;
		} else {
			return children.size();
		}
	}

	public int getIndex(final TreeNode node) {
		if (node == null) {
			throw new IllegalArgumentException("argument is null");
		}

		if (!isNodeChild(node)) {
			return -1;
		}
		return children.indexOf(node); // linear search
	}

	public TreeNode getParent() {
		return parent;
	}

	public void insert(final MutableTreeNode child, final int index) {
		if (!getAllowsChildren()) {
			throw new IllegalStateException("node does not allow children");
		} else if (child == null) {
			throw new IllegalArgumentException("new child is null");
		} else if (isNodeAncestor(child)) {
			throw new IllegalArgumentException("new child is an ancestor");
		}

		final MutableTreeNode oldParent = (MutableTreeNode) child.getParent();

		if (oldParent != null) {
			oldParent.remove(child);
		}
		child.setParent(this);
		if (children == null) {
			children = new Vector();
		}
		children.insertElementAt(child, index);

	}

	public boolean isLeaf() {
		return (getChildCount() == 0);
	}

	/**
	 * DefaultMutableTreeNode 메소드 가져옴. 현재 노드의 ancestor나 descendant인지를 확인하기 위한
	 * 메소드
	 * 
	 * @param anotherNode
	 *            DefaultMutableTreeNode
	 * @return boolean
	 */
	public boolean isNodeAncestor(final MutableTreeNode anotherNode) {
		if (anotherNode == null) {
			return false;
		}

		TreeNode ancestor = this;

		do {
			if (ancestor == anotherNode) {
				return true;
			}
		} while ((ancestor = ancestor.getParent()) != null);

		return false;
	}

	public boolean isNodeChild(final TreeNode aNode) {
		boolean retval;

		if (aNode == null) {
			retval = false;
		} else {
			if (getChildCount() == 0) {
				retval = false;
			} else {
				retval = (aNode.getParent() == this);
			}
		}

		return retval;
	}

	public void remove(final int index) {
		final MutableTreeNode child = (MutableTreeNode) getChildAt(index);
		children.removeElementAt(index);
		child.setParent(null);
	}

	public void remove(final MutableTreeNode node) {
		if (node == null) {
			throw new IllegalArgumentException("argument is null");
		}

		if (!isNodeChild(node)) {
			throw new IllegalArgumentException("argument is not a child");
		}
		remove(getIndex(node)); // linear search
	}

	public void removeAllChildren() {
		for (int i = getChildCount() - 1; i >= 0; i--) {
			remove(i);
		}
	}

	public void removeFromParent() {
		final MutableTreeNode parent = (MutableTreeNode) getParent();
		if (parent != null) {
			parent.remove(this);
		}
	}

	public void setParent(final MutableTreeNode newParent) {
		parent = newParent;
	}

	// public boolean isNodeDescendant (MutableTreeNode anotherNode) {
	// if (anotherNode == null)
	// return false;
	//
	// return anotherNode.isNodeAncestor(this);
	// }

	public void setUserObject(final Object object) {
		if (object instanceof NetworkConfiguration) {
			userObject = (NetworkConfiguration) object;
		}
	}

	@Override
	public String toString() {
		return DEFAULT_NAME;
	}
}
