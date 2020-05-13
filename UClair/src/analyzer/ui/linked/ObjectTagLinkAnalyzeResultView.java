package analyzer.ui.linked;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import analyzer.analysis.linked.ObjectTagLinkResult;
import analyzer.constants.AnalyzerConstants;
import analyzer.util.OpenView;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 이승환
 * @설명  	: 객체 태그 연결 분석 결과 표시 View.
 * @변경이력 	: 
 ************************************************/

public class ObjectTagLinkAnalyzeResultView extends JPanel {

	/**
	 * 객체 직렬화 버전 아이디.
	 */
	private static final long serialVersionUID = 1L;
	private JTable resultTable;
	public static final String OBJECT_TAG_LINK_RESULT_VIEW_KEY = AnalyzerConstants
			.getString("AnalyzerEditorFactory.ObjectTagLink.Key"); //$NON-NLS-1$
	ObjectTagLinkAnalyzeResultTableModel tableModel = null;
	/**
	 * Create the panel.
	 */
	public ObjectTagLinkAnalyzeResultView() {
		OpenView resultview = new OpenView();
		resultview.ResultView(initializeUi(), OBJECT_TAG_LINK_RESULT_VIEW_KEY);
	}
	
	private JComponent initializeUi() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JScrollPane resultTableScrollPane = new JScrollPane();
		GridBagConstraints gbc_resultTableScrollPane = new GridBagConstraints();
		gbc_resultTableScrollPane.fill = GridBagConstraints.BOTH;
		gbc_resultTableScrollPane.gridx = 0;
		gbc_resultTableScrollPane.gridy = 0;
		add(resultTableScrollPane, gbc_resultTableScrollPane);
		
		tableModel = new ObjectTagLinkAnalyzeResultTableModel();
		
		resultTable = new JTable(tableModel);
		TableRowSorter<ObjectTagLinkAnalyzeResultTableModel> tableRowSorter = 
			new TableRowSorter<ObjectTagLinkAnalyzeResultTableModel>(tableModel);
		resultTable.setRowSorter(tableRowSorter);
		resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		TableColumn column = resultTable.getColumnModel().getColumn(0);
		column.setPreferredWidth(150);
		
		column = resultTable.getColumnModel().getColumn(1);
		column.setPreferredWidth(80);
		
		for(int index = 2; index < tableModel.getColumnCount(); index++) {
			column = resultTable.getColumnModel().getColumn(index);
			column.setPreferredWidth(250);
		}
		
		resultTableScrollPane.setViewportView(resultTable);
		
		return resultTableScrollPane;
	}
	
	/**
	 * 
	 * 분석 결과를 화면에 표시하기 윈한 정보를 설정한다.<br/>
	 * - 메소드의 처리 절차 기술.
	 * 
	 * @param resultList 분석 결과.
	 */
	public void setAnalyzeResult(List<ObjectTagLinkResult> resultList) {
		tableModel.setAnalyzeResult(resultList);
	}

}
