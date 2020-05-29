package analyzer.util;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import analyzer.AnalyzerEditorFactory;

public class AnalyTreeCellRenderer extends DefaultTreeCellRenderer{
	public AnalyTreeCellRenderer() {
	}
	
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		
		
		Object nodeObj = ((DefaultMutableTreeNode) value).getUserObject();
		final Icon icon = AnalyzerEditorFactory.getFactory().getIcon(nodeObj.toString());
		if (icon != null) setIcon(icon);
		
		return this;
	}
}
