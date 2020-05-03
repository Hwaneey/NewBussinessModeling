package analyzer.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.jidesoft.swing.JideMenu;
/*******************************
 * @date	: 2020. 4. 28.
 * @설명 		: 도움말 메뉴
 * @변경이력 	:
 *******************************/
public class HelpMenu {
	public static JMenu createHelpMenu() {
		JMenu menu = new JideMenu("도움말");

		menu.add(new JMenuItem("도움말 보기(목차)"));
		menu.addSeparator();
		menu.add(new JMenuItem("정보 편집기 정보"));
		menu.add(new JMenuItem("시스템 편집기 정보"));
		return menu;
	}
}
