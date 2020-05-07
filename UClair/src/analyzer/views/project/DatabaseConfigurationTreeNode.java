package analyzer.views.project;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.naru.uclair.project.DatabaseConfiguration;

/**
 * <p>
 * Title: iA-Canvas
 * </p>
 * <p>
 * Description: ����͸� �ڵ�ȭ ��
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: (��) ������
 * </p>
 * 
 * @author ���ع�
 * @version 1.0
 */

public class DatabaseConfigurationTreeNode implements MutableTreeNode {

	public static final String DEFAULT_NAME = "�����ͺ��̽�";

	private MutableTreeNode parent;
	private Vector children;

	private DatabaseConfiguration userObject;

	public DatabaseConfigurationTreeNode() {
		this(null);
	}

	public DatabaseConfigurationTreeNode(DatabaseConfiguration userObject) {
		super();
		parent = null;
		this.userObject = userObject;
	}

	public void insert(MutableTreeNode child, int index) {
		if (!getAllowsChildren()) {
			throw new IllegalStateException("node does not allow children");
		} else if (child == null) {
			throw new IllegalArgumentException("new child is null");
		} else if (isNodeAncestor(child)) {
			throw new IllegalArgumentException("new child is an ancestor");
		}

		MutableTreeNode oldParent = (MutableTreeNode) child.getParent();

		if (oldParent != null) {
			oldParent.remove(child);
		}
		child.setParent(this);
		if (children == null) {
			children = new Vector();
		}
		children.insertElementAt(child, index);

	}

	public void remove(int index) {
		MutableTreeNode child = (MutableTreeNode) getChildAt(index);
		children.removeElementAt(index);
		child.setParent(null);
	}

	public void remove(MutableTreeNode node) {
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

	public void setUserObject(Object object) {
		if (object instanceof DatabaseConfiguration) {
			userObject = (DatabaseConfiguration) object;
		}
	}

	public void removeFromParent() {
		MutableTreeNode parent = (MutableTreeNode) getParent();
		if (parent != null) {
			parent.remove(this);
		}
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(MutableTreeNode newParent) {
		this.parent = newParent;
	}

	public TreeNode getChildAt(int childIndex) {
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

	public int getIndex(TreeNode node) {
		if (node == null) {
			throw new IllegalArgumentException("argument is null");
		}

		if (!isNodeChild(node)) {
			return -1;
		}
		return children.indexOf(node); // linear search
	}

	public boolean getAllowsChildren() {
		return false;
	}

	public boolean isLeaf() {
		return (getChildCount() == 0);
	}

	public Enumeration children() {
		if (children == null) {
			return DefaultMutableTreeNode.EMPTY_ENUMERATION;
		} else {
			return children.elements();
		}
	}

	/**
	 * DefaultMutableTreeNode �޼ҵ� ������. ���� ����� ancestor�� descendant������ Ȯ���ϱ� ����
	 * �޼ҵ�
	 * 
	 * @param anotherNode
	 *            DefaultMutableTreeNode
	 * @return boolean
	 */
	public boolean isNodeAncestor(MutableTreeNode anotherNode) {
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

	// public boolean isNodeDescendant (MutableTreeNode anotherNode) {
	// if (anotherNode == null)
	// return false;
	//
	// return anotherNode.isNodeAncestor(this);
	// }

	public boolean isNodeChild(TreeNode aNode) {
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

	@Override
	public String toString() {
		return DEFAULT_NAME;
	}
}
