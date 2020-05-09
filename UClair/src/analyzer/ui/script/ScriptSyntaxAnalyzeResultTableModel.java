package analyzer.ui.script;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import analyzer.constants.AnalyzerConstants;

import analyzer.analysis.script.ScriptSyntaxAnalysisResult;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 장성현
 * @설명  	:  스크립트 검증 결과 표시 TableModel 클래스.
 * @변경이력 	: 
 ************************************************/

public class ScriptSyntaxAnalyzeResultTableModel extends AbstractTableModel {

	/**
	 * 객체 직렬화 버전 아이디.
	 */
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {/*AnalyzerConstants.getString("ScriptSyntaxAnalyzeResultTableModel.ColumnName.No"),*/  //$NON-NLS-1$
			AnalyzerConstants.getString("ScriptSyntaxAnalyzeResultTableModel.ColumnName.Script"),  //$NON-NLS-1$
			AnalyzerConstants.getString("ScriptSyntaxAnalyzeResultTableModel.ColumnName.Validation")}; //$NON-NLS-1$
	
	private List<ScriptSyntaxAnalysisResult> resultList;

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		if(null == resultList) {
			return 0;
		}
		return resultList.size();
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(null == resultList) {
			return null;
		}
		ScriptSyntaxAnalysisResult result = resultList.get(rowIndex);
		Object data = null;
		switch(columnIndex) {
		case 0:
			data = result.getScriptSyntaxName();
			break;
		case 1:
			data = result.getScriptSyntaxAnalysisMessage();
			break;
		default:
			break;
		}
		return data;
	}

	public void setAnalyzeResult(List<ScriptSyntaxAnalysisResult> resultList) {
		this.resultList = resultList;
		fireTableDataChanged();
	}

}
