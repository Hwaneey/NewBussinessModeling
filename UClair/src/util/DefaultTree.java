package util;

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
		
		this.setModel(treeModel);
		
		// Expand the tree.
		for (int row = 0; row < this.getRowCount(); row++)
		{
			this.expandRow(row);
		}
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
