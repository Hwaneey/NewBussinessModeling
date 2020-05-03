package analyzer.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.jidesoft.swing.JideMenu;

import analyzer.AnalyzerActionFactory;

/*******************************
 * @date	: 2020. 4. 28.
 * @설명 		: 파일 메뉴
 * @변경이력 	:
 *******************************/
public class FileMenu {
	public static JMenu createFileMenu() {
		AnalyzerActionFactory actionFactory = AnalyzerActionFactory.getFactory();	

		JMenu menu = new JideMenu("파일");
		
		menu.setMnemonic(KeyEvent.VK_F);

		JMenuItem fileMenuItem1 = new JMenuItem("테스트 테이블 열기");
		fileMenuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				excel.ExcelLoad.openExcel();
			}
		});
		menu.add(fileMenuItem1);

		//분석기 메뉴 - 프로젝트 열기
		menu.add(actionFactory.getAction(AnalyzerActionFactory.OPEN_PROJECT));

		//분석기 메뉴 - 프로젝트 저장
		JMenuItem fileMenuItem2 = new JMenuItem("프로젝트 저장");
		fileMenuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.ALT_MASK));
		fileMenuItem2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				//for (String key : dockMap.keySet()) {
				//	ExcelConnector.writeExcelFromTable(key, dockMap.get(key));
				//}
			}
		});
		menu.add(fileMenuItem2);
		
		//분석기 메뉴 - 프로젝트 닫기
		menu.add(actionFactory.getAction(AnalyzerActionFactory.CLOSE_PROJECT));

		menu.addSeparator();

		//분석기 메뉴 - 프로젝트 종료
		JMenuItem fileMenuItem4 = new JMenuItem("종료");
		fileMenuItem4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		fileMenuItem4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);
			}
		});
		menu.add(fileMenuItem4);
		
		return menu;
	}
}
