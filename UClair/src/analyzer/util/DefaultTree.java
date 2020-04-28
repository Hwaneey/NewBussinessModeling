package analyzer.util;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

/**
 *  DefaultTree
 */
public class DefaultTree extends JTree
{
	/**
	 * Constructs a tree with the contact persons in the different countries.
	 *
	 */
	public DefaultTree()
	{
		super();
		TreeModel treeModel = getDefaultTreeModel();
		
		this.setModel(treeModel);
	}
	
	public static TreeModel getDefaultTreeModel() {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("AFactoryMonitoring");
		rootNode.add(new DefaultMutableTreeNode("������Ʈ ���� �м�"));
		rootNode.add(new DefaultMutableTreeNode("�������� �ʴ� �±� �м�"));
		rootNode.add(new DefaultMutableTreeNode("�����±� ���Ӽ� �м�"));
		rootNode.add(new DefaultMutableTreeNode("�����±� ���Ӽ� �м�"));
		rootNode.add(new DefaultMutableTreeNode("�����ּ� ���Ӽ� �м�"));
		rootNode.add(new DefaultMutableTreeNode("��ü�±� �������� �м�"));
		rootNode.add(new DefaultMutableTreeNode("�̺�Ʈ ���Ӽ� �м�"));
		rootNode.add(new DefaultMutableTreeNode("��꽺ũ��Ʈ ����"));
		rootNode.add(new DefaultMutableTreeNode("��üȿ�� �縳�� �м�"));
		
		TreeModel treeModel = new DefaultTreeModel(rootNode);
		
		return treeModel;
	}
	
}
