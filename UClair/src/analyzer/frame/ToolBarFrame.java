package analyzer.frame;

import javax.swing.ImageIcon;

import com.jidesoft.action.CommandBar;
import com.jidesoft.action.DockableBarContext;
import com.jidesoft.swing.JideButton;

import analyzer.icon.AnalyzerIconFactory;

public class ToolBarFrame {

	/*******************************
	 * @date	: 2020. 4. 28.
	 * @설명 		: 툴바
	 * @변경이력 	:
	 *******************************/
	protected static CommandBar createToolBar() {
		CommandBar commandBar = new CommandBar("ToolBar");
		commandBar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
		commandBar.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
		commandBar.setInitIndex(0);
		
		commandBar.add(new JideButton(new ImageIcon(AnalyzerIconFactory.UNDO)));
		commandBar.add(new JideButton(new ImageIcon(AnalyzerIconFactory.REDO)));
		return commandBar;
	}
}
