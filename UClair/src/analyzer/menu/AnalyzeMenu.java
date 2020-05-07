package analyzer.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.jidesoft.swing.JideMenu;

import analyzer.AnalyzerActionFactory;
/*******************************
 * @date	: 2020. 4. 28.
 * @설명 		: 분석 메뉴
 * @변경이력 	: 
 *******************************/

public class AnalyzeMenu {
	public static JMenu createAnalyzeMenu() {
		AnalyzerActionFactory actionFactory = AnalyzerActionFactory.getFactory();

		JMenu menu = new JideMenu("분석");

		menu.add(new JMenuItem(actionFactory.getAction(500))); 					//존재하지 않는 태그 분석
		menu.add(new JMenuItem(actionFactory.getAction(506))); 					//개별태그
		menu.add(new JMenuItem(actionFactory.getAction(501))); 					//가상태그 종속성 분석
		menu.add(new JMenuItem(actionFactory.getAction(502)));				 	//물리주소 종속성 분석
		menu.add(new JMenuItem(actionFactory.getAction(503))); 					//객체태그 연결정보 분석
		menu.add(new JMenuItem(actionFactory.getAction(504))); 					//이벤트 종속성 분석
		menu.addSeparator();
		menu.add(new JMenuItem(actionFactory.getAction(510))); 					//계산 스크립트 검증
		menu.add(new JMenuItem(actionFactory.getAction(505))); 					//객체효과 양립성 분석

		return menu;
	}



}