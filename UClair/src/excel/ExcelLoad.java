package excel;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jidesoft.document.DocumentComponent;
import com.jidesoft.document.DocumentComponentAdapter;
import com.jidesoft.document.DocumentComponentEvent;
import com.jidesoft.document.DocumentPane;
import com.jidesoft.grid.SortableTable;

import analyzer.frame.AnalyzerMainFrame;
import analyzer.icon.AnalyzerIconFactory;

/*******************************
 * @date	: 2020. 4. 28.
 * @설명 		: 엑셀 파일 로드 및 패널 생성
 * @변경이력 	:
 *******************************/

public class ExcelLoad {
	protected static JFrame _frame =  AnalyzerMainFrame._frame ;
	private static DocumentPane _workspacePane =  AnalyzerMainFrame._workspacePane;		
	public static void openExcel() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("엑셀 통합 문서(*.xlsx)","xlsx"));
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(false);
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			String path = fc.getSelectedFile().getPath();
			String name = fc.getSelectedFile().getName();
			
			SortableTable table = ExcelConnector.readTableFromExcel(path);
			if (_workspacePane.getDocument(path) != null) {	// 이미 파일이 열려있는 경우
				_workspacePane.setActiveDocument(path);
			} else {										// 새로 여는 경우
				final DocumentComponent document = new DocumentComponent(new JScrollPane(table), path, name, new ImageIcon(AnalyzerIconFactory.EXCEL));
				document.setDefaultFocusComponent(table);
				document.addDocumentComponentListener(new DocumentComponentAdapter() {
					@Override
					public void documentComponentClosing(DocumentComponentEvent e) {
						int ret = JOptionPane.showConfirmDialog(_frame, "저장하시겠습니까?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
						if (ret == JOptionPane.YES_OPTION) {
							// 저장
							document.setAllowClosing(true);
						}
						else if (ret == JOptionPane.NO_OPTION) {
							// 저장안함
							document.setAllowClosing(true);
						}
						else if (ret == JOptionPane.CANCEL_OPTION) {
							// 취소
							document.setAllowClosing(false);
						}
					}
				});
				_workspacePane.openDocument(document);
			}
		}
	}
}
