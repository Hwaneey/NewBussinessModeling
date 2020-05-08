package com.naru.uclair.analyzer.views.project;

import java.io.File;
import java.io.FileFilter;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.naru.uclair.draw.common.HMIDrawingFileFilter;


/**
 * Project Tree에서 Window 리스트를 보여주기 위한 노드 클래스
 * 이 노드는 하위에 네트워크 환경에서 등록된 모든 노드에 대한 화면들을 각 노드 이름의
 * 디렉토리를 검사하여 등록시켜준다.
 *
 * <p>Title: iA-Canvas</p>
 * <p>Description: 모니터링 자동화 툴</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: (주) 나루기술</p>
 * @author 정해문
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
     * 화면의 추가/변경/삭제시에 해당 노드의 디렉토리의 내용을 재검색하여
     * 트리를 재구성하기 위한 메소드.
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
    	return (null == displayName) ? windowDirectory.getName() : displayName;
    }
}
