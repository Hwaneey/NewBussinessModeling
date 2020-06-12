package analyzer.frame;

import javax.swing.ImageIcon;

import com.jidesoft.action.CommandBar;
import com.jidesoft.action.DockableBarContext;
import com.jidesoft.swing.JideButton;

import analyzer.Analyzer;
import analyzer.action.ProjectLoadAction;
import analyzer.icon.Office2003IconsFactory;

public class ToolBarFrame {
	/*******************************
	 * @date	: 2020. 4. 28.
	 * @설명 		: 툴바
	 * @변경이력 	: 
	 *******************************/
	private Analyzer analyzer;

	public ToolBarFrame(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public CommandBar createToolBar() {
		CommandBar commandBar = new CommandBar("파일");
		commandBar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
		commandBar.setInitMode(DockableBarContext.STATE_HORI_DOCKED);
		commandBar.setInitIndex(0);
		commandBar.setInitSubindex(0);

		JideButton btn_open_project = new JideButton(new ImageIcon(Office2003IconsFactory.Standard.OPEN));	
		btn_open_project.addActionListener(new ProjectLoadAction(analyzer));
		commandBar.add(btn_open_project);

		commandBar.add(new JideButton(new ImageIcon(Office2003IconsFactory.Standard.SAVE)));

		commandBar.addSeparator();

		commandBar.add(new JideButton(new ImageIcon(Office2003IconsFactory.Standard.UNDO)));
		commandBar.add(new JideButton(new ImageIcon(Office2003IconsFactory.Standard.REDO)));
		commandBar.add(new JideButton(new ImageIcon(Office2003IconsFactory.Standard.PERMISSION)));
		commandBar.add(new JideButton(new ImageIcon(Office2003IconsFactory.Standard.EMAIL)));
		commandBar.addSeparator();

		commandBar.add(new JideButton(new ImageIcon(Office2003IconsFactory.Standard.PRINT)));
		commandBar.add(new JideButton(new ImageIcon(Office2003IconsFactory.Standard.PRINT_PREVIEW)));
		commandBar.addSeparator();

		commandBar.add(new JideButton(new ImageIcon(Office2003IconsFactory.Standard.SPELLING_GRAMMAR)));
		commandBar.add(new JideButton(new ImageIcon(Office2003IconsFactory.Standard.RESEARCH)));
		commandBar.addSeparator();

		commandBar.add(new JideButton(new ImageIcon(Office2003IconsFactory.Standard.CUT)));
		commandBar.add(new JideButton(new ImageIcon(Office2003IconsFactory.Standard.COPY)));
		commandBar.add(new JideButton(new ImageIcon(Office2003IconsFactory.Standard.PASTE)));
		commandBar.add(new JideButton(new ImageIcon(Office2003IconsFactory.Standard.FORMAT_PAINTER)));

		return commandBar;
	}
}
