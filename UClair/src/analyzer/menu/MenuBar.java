package analyzer.menu;

import java.awt.Container;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import analyzer.frame.AnalyzerMainFrame;

/*******************************
 * @date	: 2020. 4. 28.
 * @설명 		: 메뉴바
 * @변경이력 	:
 *******************************/

public class MenuBar {
	
	private static final Container container = AnalyzerMainFrame._frame;
	private static JMenu fileMenu;								// 메뉴바 - 파일 
	public static JMenu analyzeMenu;							// 메뉴바 - 분석 
	public static JMenu testCaseMenu;						// 메뉴바 - 테스트 케이스
	private static JMenu helpMenu;							// 메뉴바 - 도움말
	private static JMenu viewMenu;							// 메뉴바 - 뷰
	

	public static JMenuBar createMenuBar() {

		JMenuBar menu = new JMenuBar();
				
		fileMenu = FileMenu.createFileMenu();
		analyzeMenu = AnalyzeMenu.createAnalyzeMenu();
		testCaseMenu = TestCaseMenu.createTestCaseMenu();
		helpMenu = HelpMenu.createHelpMenu();
		viewMenu = ViewMenu.createViewMenu(container);

		analyzeMenu.setEnabled(false);
		testCaseMenu.setEnabled(false);

		menu.add(fileMenu);
		menu.add(analyzeMenu);
		menu.add(testCaseMenu);
		//menu.add(helpMenu);
		menu.add(viewMenu);

		return menu;
	}
}
