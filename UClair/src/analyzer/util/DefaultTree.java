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
		rootNode.add(new DefaultMutableTreeNode("프로젝트 정보 분석"));
		rootNode.add(new DefaultMutableTreeNode("존재하지 않는 태그 분석"));
		rootNode.add(new DefaultMutableTreeNode("개별태그 종속성 분석"));
		rootNode.add(new DefaultMutableTreeNode("가상태그 종속성 분석"));
		rootNode.add(new DefaultMutableTreeNode("물리주소 종속성 분석"));
		rootNode.add(new DefaultMutableTreeNode("객체태그 연결정보 분석"));
		rootNode.add(new DefaultMutableTreeNode("이벤트 종속성 분석"));
		rootNode.add(new DefaultMutableTreeNode("계산스크립트 검증"));
		rootNode.add(new DefaultMutableTreeNode("객체효과 양립성 분석"));
		
		TreeModel treeModel = new DefaultTreeModel(rootNode);
		
		return treeModel;
	}
	
}
