package analyzer.frame;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;

import analyzer.AnalyzerActionFactory;
import analyzer.constants.AnalyzerConstants;
import analyzer.icon.AnalyzerIconFactory;
import analyzer.util.DefaultTree;
/*******************************
 * @date	: 2020. 4. 28.
 * @설명 		: 분석기 패널
 * @변경이력 	:
 *******************************/
public class AnalyzerFrame {
	public static final String ANALYZE_PROJECT = AnalyzerConstants.getString("AnalyzerView.NodeNode.ProjectInfo"); //$NON-NLS-1$
	public static final String ANALYZE_DANGLING_TAG = AnalyzerConstants.getString("AnalyzerView.Node.DanglingTag"); //$NON-NLS-1$
	public static final String ANALYZE_EACH_TAG_DEPENDENCY = AnalyzerConstants.getString("AnalyzerView.Node.EachTag"); //$NON-NLS-1$
	public static final String ANALYZE_VIRTUAL_TAG_DEPENDENCY = AnalyzerConstants.getString("AnalyzerView.NodeVirtualTag"); //$NON-NLS-1$
	public static final String ANALYZE_PHYSICAL_TAG = AnalyzerConstants.getString("AnalyzerView.NodePhysicalTag"); //$NON-NLS-1$
	public static final String ANALYZE_LINKED_TAG = AnalyzerConstants.getString("AnalyzerView.NodeLinkedTag"); //$NON-NLS-1$
	public static final String ANALYZE_EVENT_DEPENDENCY = AnalyzerConstants.getString("AnalyzerView.NodeEventDepend"); //$NON-NLS-1$
	public static final String ANALYZE_SCRIPT_SYNTAX = AnalyzerConstants.getString("AnalyzerView.NodeScriptSyntax"); //$NON-NLS-1$
	public static final String ANALYZE_EFFECT_COMPATIBILITY = AnalyzerConstants.getString("AnalyzerView.NodeEffectComptibility"); //$NON-NLS-1$

	protected static DockableFrame createAnalyzerFrame() {
		AnalyzerMainFrame.analysorTree = new DefaultTree();
		AnalyzerMainFrame.analysorTree.setVisible(false);
		AnalyzerMainFrame.analysorTree.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(!(e.getSource() instanceof JTree)) {
					return;
				}
				
				if(e.getButton() == MouseEvent.BUTTON1
						&& e.getClickCount() == 2) {
					JTree viewTree = (JTree) e.getSource();
					
					Object obj = viewTree.getLastSelectedPathComponent();
					
					if(obj instanceof DefaultMutableTreeNode) {
						performedTreeNodeClick((DefaultMutableTreeNode) obj);
					}
				}

			}

						
			private void performedTreeNodeClick(DefaultMutableTreeNode treeNode) {
				if(null != treeNode) {
					Action action = null;
					AnalyzerActionFactory actionFactory = AnalyzerActionFactory.getFactory();
					
					if(ANALYZE_PROJECT.equals(treeNode.getUserObject())) {
						action = actionFactory.getAction(AnalyzerActionFactory.PROJECT_INFO);
					}
					else if(ANALYZE_DANGLING_TAG.equals(treeNode.getUserObject())) {
						action = actionFactory.getAction(AnalyzerActionFactory.DANGLING_TAG_ANALYSIS);
					}
					else if(ANALYZE_EACH_TAG_DEPENDENCY.equals(treeNode.getUserObject())) {
						action = actionFactory.getAction(AnalyzerActionFactory.EACH_TAG_DEPENDENCY_ANALYSIS);
					}
					else if(ANALYZE_VIRTUAL_TAG_DEPENDENCY.equals(treeNode.getUserObject())) {
						action = actionFactory.getAction(AnalyzerActionFactory.VIRTUAL_TAG_DEPENDENCY_ANALYSIS);
					}
					else if(ANALYZE_PHYSICAL_TAG.equals(treeNode.getUserObject())) {
						action = actionFactory.getAction(AnalyzerActionFactory.PHYSICAL_ADRESS_DEPENDENCY_ANALYSIS);
					}
					else if(ANALYZE_LINKED_TAG.equals(treeNode.getUserObject())) {
						action = actionFactory.getAction(AnalyzerActionFactory.OBJECT_TAG_LINK_INFO_ANALYSIS);
					}
					else if(ANALYZE_EVENT_DEPENDENCY.equals(treeNode.getUserObject())) {
						action = actionFactory.getAction(AnalyzerActionFactory.EVENT_TAG_DEPENDENCY_ANALYSIS);
					}
					else if(ANALYZE_SCRIPT_SYNTAX.equals(treeNode.getUserObject())) {
						action = actionFactory.getAction(AnalyzerActionFactory.SCRIPT_ANALYSIS);
					}
					else if(ANALYZE_EFFECT_COMPATIBILITY.equals(treeNode.getUserObject())) {
						action = actionFactory.getAction(AnalyzerActionFactory.OBJECT_EFFECT_COMPATIBILITY_ANALYSIS);
					}
					
					if(null != action) {
						action.actionPerformed(null);
					}
				}
			}
		});
		
		DockableFrame frame = AnalyzerMainFrame.createDockableFrame("분석기", new ImageIcon(AnalyzerIconFactory.ANALYZE));
		frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		frame.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
		frame.getContext().setInitIndex(0);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.add(AnalyzerMainFrame.createScrollPane(AnalyzerMainFrame.analysorTree));

		return frame;
	}
}
