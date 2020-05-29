package analyzer.util;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import analyzer.constants.AnalyzerConstants;
import analyzer.frame.AnalyzerFrame;

/**
 *  DefaultTree
 */
public class DefaultTree extends JTree
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a tree with the contact persons in the different countries.
	 *
	 */
	public DefaultTree()
	{
		super();
		TreeModel treeModel = getDefaultTreeModel();
		this.setCellRenderer(new AnalyTreeCellRenderer());
		this.setModel(treeModel);
	}
	
	public static TreeModel getDefaultTreeModel() {
		
		final String[] tools = { 	
									AnalyzerFrame.ANALYZE_PROJECT,
									AnalyzerFrame.ANALYZE_DANGLING_TAG, 
		        					AnalyzerFrame.ANALYZE_EACH_TAG_DEPENDENCY,
		        					AnalyzerFrame.ANALYZE_VIRTUAL_TAG_DEPENDENCY, 
		        					AnalyzerFrame.ANALYZE_PHYSICAL_TAG,
		        					AnalyzerFrame.ANALYZE_LINKED_TAG,
		        					AnalyzerFrame.ANALYZE_EVENT_DEPENDENCY, 
		        					AnalyzerFrame.ANALYZE_SCRIPT_SYNTAX,
		        					AnalyzerFrame.ANALYZE_EFFECT_COMPATIBILITY
		        				};
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("AFactoryMonitoring");
		
		for(int i = 0; i < tools.length; i++)
			rootNode.add(new DefaultMutableTreeNode(tools[i]));
		
		TreeModel treeModel = new DefaultTreeModel(rootNode);
		return treeModel;
	}
	
}
