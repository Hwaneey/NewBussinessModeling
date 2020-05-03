package analyzer.menu;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.jidesoft.docking.DockableHolder;
import com.jidesoft.docking.DockingManager;
import com.jidesoft.swing.JideMenu;

import analyzer.icon.AnalyzerIconFactory;
/*******************************
 * @date	: 2020. 4. 28.
 * @설명 		: 뷰 메뉴
 * @변경이력 	:
 *******************************/
public class ViewMenu {
	public static JMenu createViewMenu(final Container container) {
		JMenuItem item;
		JMenu viewMenu = new JideMenu("뷰");

		item = new JMenuItem("다음 뷰");
		item.setMnemonic('N');
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
		item.addActionListener(new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2936426823778581714L;

			public void actionPerformed(ActionEvent e) {
				if (container instanceof DockableHolder) {
					DockingManager dockingManager = ((DockableHolder) container).getDockingManager();
					String frameKey = dockingManager.getNextFrame(dockingManager.getActiveFrameKey());
					if (frameKey != null) {
						dockingManager.showFrame(frameKey);
					}
				}
			}
		});
		viewMenu.add(item);

		item = new JMenuItem("이전 뷰");
		item.setMnemonic('P');
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, InputEvent.SHIFT_MASK));
		item.addActionListener(new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 832843222791401941L;

			public void actionPerformed(ActionEvent e) {
				if (container instanceof DockableHolder) {
					DockingManager dockingManager = ((DockableHolder) container).getDockingManager();
					String frameKey = dockingManager.getPreviousFrame(dockingManager.getActiveFrameKey());
					if (frameKey != null) {
						dockingManager.showFrame(frameKey);
					}
				}
			}
		});
		viewMenu.add(item);

		viewMenu.addSeparator();

		item = new JMenuItem("분석기", new ImageIcon(AnalyzerIconFactory.ANALYZE));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		item.addActionListener(new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2149405618886340019L;

			public void actionPerformed(ActionEvent e) {
				if (container instanceof DockableHolder) {
					((DockableHolder) container).getDockingManager().showFrame("분석기");
				}
			}
		});
		viewMenu.add(item);

		item = new JMenuItem("메시지", new ImageIcon(AnalyzerIconFactory.MESSAGE));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		item.addActionListener(new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5432420564231582957L;

			public void actionPerformed(ActionEvent e) {
				if (container instanceof DockableHolder) {
					((DockableHolder) container).getDockingManager().showFrame("메시지");
				}
			}
		});
		viewMenu.add(item);

		return viewMenu;
	}
}
