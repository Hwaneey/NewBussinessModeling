package analyzer.frame;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;

import analyzer.icon.AnalyzerIconFactory;

	/*******************************
	 * @date	: 2020. 4. 28.
	 * @설명 		: 메시지 패널
	 * @변경이력 	:
	 *******************************/
	
public class MessageFrame {
	
	static JTextArea system_msg = new JTextArea();
	static JTextArea system_err = new JTextArea();
	
	protected static DockableFrame createMessageFrame() {
		DockableFrame frame = AnalyzerMainFrame.createDockableFrame("메시지", new ImageIcon(AnalyzerIconFactory.MESSAGE));
		frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
		frame.getContext().setInitSide(DockContext.DOCK_SIDE_SOUTH);

		system_msg.setEditable(false);
		system_err.setEditable(false);
		
		JTabbedPane jtab = new JTabbedPane();
		jtab.addTab("System out", new JScrollPane(system_msg));
		jtab.addTab("System err", new JScrollPane(system_err));

		frame.add(jtab);
		return frame;
	}
	
	public static void appendMessage(String msg) {
		system_msg.append(msg + "\n");
		system_msg.setCaretPosition(system_msg.getDocument().getLength());
	}
	public static void WarningMessage() {
		String wrn = "프로젝트를 불러오는데 실패하였습니다.";
		system_msg.append(wrn + "\n");
		system_msg.setCaretPosition(system_err.getDocument().getLength());
//		system_msg.setActiveDocument();
	}
}
