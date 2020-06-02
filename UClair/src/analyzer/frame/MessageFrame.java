package analyzer.frame;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;

import analyzer.icon.AnalyzerIconFactory;
import analyzer.icon.Office2003IconsFactory;

	/*******************************
	 * @date	: 2020. 4. 28.
	 * @설명 		: 메시지 패널
	 * @변경이력 	:
	 *******************************/
	
public class MessageFrame {
	
	static JTextArea system_msg = new JTextArea();

	protected static DockableFrame createMessageFrame() {
		DockableFrame frame = AnalyzerMainFrame.createDockableFrame("메시지", new ImageIcon(AnalyzerIconFactory.MESSAGE));
		frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		frame.getContext().setInitSide(DockContext.DOCK_SIDE_SOUTH);

		JTabbedPane jtab = new JTabbedPane();
		jtab.addTab("System out", new JScrollPane(system_msg));
		jtab.addTab("System err", new JTextArea("System err 영역"));

		frame.add(jtab);
		return frame;
	}
	
	public static void appendMessage(String msg) {
		system_msg.append(msg + "\n");
		system_msg.setCaretPosition(system_msg.getDocument().getLength());
	}
}
