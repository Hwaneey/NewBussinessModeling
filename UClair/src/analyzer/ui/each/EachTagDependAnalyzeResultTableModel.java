package analyzer.ui.each;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import analyzer.analysis.each.EachTagDependResult;
import analyzer.constants.AnalyzerConstants;
import com.naru.uclair.tag.ITagConstants;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.ui.each.EachTagDependAnalyzeResultTableModel.java
 * DESC   : 개별 태그 종속성 분석 결과 표시 TableModel 클래스.
 *
 * references : 설계서 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 21.
 * @version 1.0
 *
 */
public class EachTagDependAnalyzeResultTableModel extends AbstractTableModel {

	/**
	 * 객체 직렬화 버전 아이디.
	 */
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {AnalyzerConstants.getString("EachTagDependAnalyzeResultTableModel.ColumnName.Tag"),  //$NON-NLS-1$
			AnalyzerConstants.getString("EachTagDependAnalyzeResultTableModel.ColumnName.Node"),  //$NON-NLS-1$
			AnalyzerConstants.getString("EachTagDependAnalyzeResultTableModel.ColumnName.DataType"),  //$NON-NLS-1$
			AnalyzerConstants.getString("EachTagDependAnalyzeResultTableModel.ColumnName.Device"),  //$NON-NLS-1$
			AnalyzerConstants.getString("EachTagDependAnalyzeResultTableModel.ColumnName.AlarmTrend"),  //$NON-NLS-1$
			AnalyzerConstants.getString("EachTagDependAnalyzeResultTableModel.ColumnName.RunTrend"),
			AnalyzerConstants.getString("EachTagDependAnalyzeResultTableModel.ColumnName.ChangeTrend"),
			AnalyzerConstants.getString("EachTagDependAnalyzeResultTableModel.ColumnName.MinTrend"),
			AnalyzerConstants.getString("EachTagDependAnalyzeResultTableModel.ColumnName.HourTrend"),
			AnalyzerConstants.getString("EachTagDependAnalyzeResultTableModel.ColumnName.DayTrend"),
			AnalyzerConstants.getString("EachTagDependAnalyzeResultTableModel.ColumnName.TagEvent"),
			AnalyzerConstants.getString("EachTagDependAnalyzeResultTableModel.ColumnName.AlarmEvent"),
			AnalyzerConstants.getString("EachTagDependAnalyzeResultTableModel.ColumnName.TimeEvent"),
			AnalyzerConstants.getString("EachTagDependAnalyzeResultTableModel.ColumnName.ScriptFunction"),
			AnalyzerConstants.getString("EachTagDependAnalyzeResultTableModel.ColumnName.Window")}; //$NON-NLS-1$
	
	private List<EachTagDependResult> resultList;

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
		
		EachTagDependResult etdr = resultList.get(rowIndex);
		if(null == etdr) {
			return null;
		}
		
		switch(columnIndex) {
		case 0:
			return etdr.getTagId();
		case 1:
			return etdr.getNodeName();
		case 2:
			return ITagConstants.TAG_DATATYPE_LIST[etdr.getDataType() + 1];
		case 3:
			return etdr.getDeviceName();
		case 4:
			return etdr.isUseAlarmCollection();
		case 5:
			return etdr.isUseOperationCollection();
		case 6:
			return etdr.isUseChangeCollection();
		case 7:
			return etdr.isUseMinTrendCollection();
		case 8:
			return etdr.isUseHourTrendCollection();
		case 9:
			return etdr.isUseDayTrendCollection();
		case 10:
			return etdr.isUseTagEvent();
		case 11:
			return etdr.isUseAlarmEvent();
		case 12:
			return etdr.isUseTimeEvent();
		case 13:
			return etdr.isUseScriptFunction();
		case 14:
			return etdr.isUseWindow();
		default:
			return null;
		}
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex >= 4) {
			return Boolean.class;
		}
		return super.getColumnClass(columnIndex);
	}

	public void setAnalyzeResult(List<EachTagDependResult> resultList) {
		this.resultList = resultList;
		fireTableDataChanged();
	}

}
