package analyzer.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.jidesoft.swing.JideMenu;

import analyzer.AnalyzerActionFactory;

/*******************************
 * @date	: 2020. 4. 28.
 * @설명 		: 테스트 케이스 메뉴
 * @변경이력 	:  
 *******************************/

public class TestCaseMenu {
	public static JMenu createTestCaseMenu() {
		AnalyzerActionFactory actionFactory = AnalyzerActionFactory.getFactory();
		JMenu menu = new JideMenu("테스트 케이스");

		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.IO_TEST_CASE_GENERATOR))); 				//IO 테스트 케이스 생성기
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.WINDOW_TEST_CASE_GENERATOR))); 			//화면 테스트 케이스 생성기
		menu.addSeparator();		
		menu.add(new JMenuItem(actionFactory.getAction(AnalyzerActionFactory.PROJECT_INFO))); 							//프로젝트 정보
		return menu;
	}

	public void setEnabled(boolean menuEnabled) {
		// TODO Auto-generated method stub
		
	}
}
