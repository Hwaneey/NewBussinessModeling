package analyzer.frame;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;

import analyzer.icon.AnalyzerIconFactory;

public class MessageFrame {
	/*******************************
	 * @date	: 2020. 4. 28.
	 * @설명 		: 메시지 패널
	 * @변경이력 	:
	 *******************************/
	protected static DockableFrame createMessageFrame() {
		DockableFrame frame = AnalyzerMainFrame.createDockableFrame("메시지", new ImageIcon(AnalyzerIconFactory.MESSAGE));
		frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		frame.getContext().setInitSide(DockContext.DOCK_SIDE_SOUTH);

		JTabbedPane jtab = new JTabbedPane();
		jtab.addTab("System out", new JTextArea("System out 영역"));
		jtab.addTab("System err", new JTextArea("System err 영역"));

		frame.add(jtab);
		return frame;
	}
}
