package analyzer.views.project;

import java.io.File;
import java.io.FileFilter;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.naru.uclair.draw.common.HMIDrawingFileFilter;


/**
 * Project Tree���� Window ����Ʈ�� �����ֱ� ���� ��� Ŭ����
 * �� ���� ������ ��Ʈ��ũ ȯ�濡�� ��ϵ� ��� ��忡 ���� ȭ����� �� ��� �̸���
 * ���丮�� �˻��Ͽ� ��Ͻ����ش�.
 *
 * <p>Title: iA-Canvas</p>
 * <p>Description: ����͸� �ڵ�ȭ ��</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: (��) ������</p>
 * @author ���ع�
 * @version 1.0
 */

public class WindowTreeNode 
		implements MutableTreeNode {

    public static final FileFilter filter = new HMIDrawingFileFilter();

    private MutableTreeNode parent = null;
    private Vector children = null;

    private File windowDirectory = null;

    private String displayName = null;


    public WindowTreeNode(String displayName, File directory) {
        children = new Vector();
        parent = null;
        this.displayName = displayName;
        setUserObject(directory);
    }

    /**
     * ȭ���� �߰�/����/�����ÿ� �ش� ����� ���丮�� ������ ��˻��Ͽ�
     * Ʈ���� �籸���ϱ� ���� �޼ҵ�.
     */
    public void refreshNode() {
        setUserObject(windowDirectory);
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
        if ((index < 0) || (index >= children.size())) return;

        MutableTreeNode child = (MutableTreeNode)getChildAt(index);
        children.removeElementAt(index);
        child.setParent(null);
    }
    public void remove(MutableTreeNode node) {
        remove(getIndex(node));
    }
    public void removeAllChildren () {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            remove(i);
        }
    }


    public File getUserObject() {
        return windowDirectory;
    }
    
    public void setUserObject(Object object) {
//        if (children.size() > 0) children.clear();
        windowDirectory = (File) object;
//        File[] fList = windowDirectory.listFiles(filter);
//        if (fList != null) {
//            WindowNameNode wnn = null;
//            for (int i = 0; i < fList.length; i++) {
//                wnn = new WindowNameNode(fList[i]);
//                wnn.setParent(this);
//                children.add(wnn);
//            }
//        }
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
        return (WindowNameNode) children.get(childIndex);
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
        return true;
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
     * DefaultMutableTreeNode �޼ҵ� ������.
     * ���� ����� ancestor�� descendant������ Ȯ���ϱ� ���� �޼ҵ�
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
    	return (null == displayName) ? windowDirectory.getName() : displayName;
    }
}
