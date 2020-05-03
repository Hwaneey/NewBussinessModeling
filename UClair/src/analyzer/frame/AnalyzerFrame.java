package analyzer.frame;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;

import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;

import analyzer.icon.AnalyzerIconFactory;
import analyzer.util.DefaultTree;

public class AnalyzerFrame {
	/*******************************
	 * @date	: 2020. 4. 28.
	 * @설명 		: 분석기 패널
	 * @변경이력 	:
	 *******************************/
	protected static DockableFrame createAnalyzerFrame() {
		AnalyzerMainFrame.analysorTree = new DefaultTree();
		AnalyzerMainFrame.analysorTree.setVisible(false);
		
		DockableFrame frame = analyzer.frame.AnalyzerMainFrame.createDockableFrame("분석기", new ImageIcon(AnalyzerIconFactory.ANALYZE));
		frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		frame.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
		frame.getContext().setInitIndex(0);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.add(analyzer.frame.AnalyzerMainFrame.createScrollPane(AnalyzerMainFrame.analysorTree));

		return frame;
	}
}
