package analyzer.ui.dangling;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import analyzer.analysis.dangling.DanglingTagResult;
import analyzer.constants.AnalyzerConstants;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 장성현
 * @설명  	:  존재하지 않는 태그 분석 결과 표시 TableModel 클래스
 * @변경이력 	: 
 ************************************************/

public class DanglingTagAnalyzeResultTableModel extends AbstractTableModel {
	/**
	 * 객체 직렬화 버전 아이디.
	 */
	private static final long serialVersionUID = 1L;
	private List<DanglingTagResult> resultList;
	private String[] columnNames = {AnalyzerConstants.getString("DanglingTagAnalyzeResultTableModel.ColumnName.Tag"),  //$NON-NLS-1$
			AnalyzerConstants.getString("DanglingTagAnalyzeResultTableModel.ColumnName.Type"),  //$NON-NLS-1$
			AnalyzerConstants.getString("DanglingTagAnalyzeResultTableModel.ColumnName.Message")}; //$NON-NLS-1$
	@Override
	public int getRowCount() {
		if(null == resultList) {
			return 0;
		}
		return resultList.size();
	}
	@Override
	public Object getValueAt(int row, int column) {
		if(null == resultList) {
			return null;
		}
		DanglingTagResult danglingTagResult = resultList.get(row);
		if(null == danglingTagResult) {
			return null;
		}
		switch(column) {
		case 0:
			return danglingTagResult.getDanglingTagName();
		case 1:
			return DanglingTagResult.RESULT_TYPE_NAMES[danglingTagResult.getDanglingInfoType()];
		case 2:
			return danglingTagResult.getDanglingResultMessage();
		default:
			return null;
		}
	}
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	public void setAnalyzeResult(List<DanglingTagResult> resultList) {
		this.resultList = resultList;
		fireTableDataChanged();
	}

}
