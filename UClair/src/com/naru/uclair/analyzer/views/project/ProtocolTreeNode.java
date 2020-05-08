package com.naru.uclair.analyzer.views.project;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.naru.uclair.project.DeviceConfiguration;



public class ProtocolTreeNode implements MutableTreeNode {

	public static final String DEFAULT_NAME = "프로토콜";
	
    private MutableTreeNode parent;
    private Vector children;

    private DeviceConfiguration userObject;


    public ProtocolTreeNode() {
        this(null);
    }
    public ProtocolTreeNode(DeviceConfiguration userObject) {
        super();
        parent = null;
        this.userObject = userObject;
    }


    public void insert(MutableTreeNode child, int index) {
        if (!getAllowsChildren()) {
            throw new IllegalStateException("node does not allow children");
        }
        else if (child == null) {
            throw new IllegalArgumentException("new child is null");
        }
        else if (isNodeAncestor(child)) {
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
        MutableTreeNode child = (MutableTreeNode)getChildAt(index);
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
    public void removeAllChildren () {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            remove(i);
        }
    }
    public void setUserObject(Object object) {
        if (object instanceof DeviceConfiguration) {
            userObject = (DeviceConfiguration) object;
        }
    }
    public void removeFromParent() {
        MutableTreeNode parent = (MutableTreeNode)getParent();
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
        return (TreeNode)children.elementAt(childIndex);
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
        return children.indexOf(node);	// linear search
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
     * DefaultMutableTreeNode 메소드 가져옴.
     * 현재 노드의 ancestor나 descendant인지를 확인하기 위한 메소드
     * @param anotherNode DefaultMutableTreeNode
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
        }
        while ((ancestor = ancestor.getParent()) != null);

        return false;
    }

//    public boolean isNodeDescendant (MutableTreeNode anotherNode) {
//        if (anotherNode == null)
//            return false;
//
//        return anotherNode.isNodeAncestor(this);
//    }

    public boolean isNodeChild (TreeNode aNode) {
        boolean retval;

        if (aNode == null) {
            retval = false;
        }
        else {
            if (getChildCount() == 0) {
                retval = false;
            }
            else {
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
