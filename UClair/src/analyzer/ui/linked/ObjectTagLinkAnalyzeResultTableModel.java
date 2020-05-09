package analyzer.ui.linked;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import analyzer.analysis.linked.ObjectTagLinkResult;
import analyzer.constants.AnalyzerConstants;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 이승환
 * @설명  	: 이벤트 종속성 분석 결과 표시 TableModel 클래스.
 * @변경이력 	: 
 ************************************************/

public class ObjectTagLinkAnalyzeResultTableModel extends AbstractTableModel {

	/**
	 * 객체 직렬화 버전 아이디.
	 */
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {AnalyzerConstants.getString("ObjectTagLinkAnalyzeResultTableModel.ColumnName.WindowName"),  //$NON-NLS-1$
			AnalyzerConstants.getString("ObjectTagLinkAnalyzeResultTableModel.ColumnName.Figure"),  //$NON-NLS-1$
			AnalyzerConstants.getString("ObjectTagLinkAnalyzeResultTableModel.ColumnName.Emerge"),  //$NON-NLS-1$
			AnalyzerConstants.getString("ObjectTagLinkAnalyzeResultTableModel.ColumnName.Blink"),  //$NON-NLS-1$
			AnalyzerConstants.getString("ObjectTagLinkAnalyzeResultTableModel.ColumnName.Move.Horizontal"),  //$NON-NLS-1$
			AnalyzerConstants.getString("ObjectTagLinkAnalyzeResultTableModel.ColumnName.Move.Vertical"),  //$NON-NLS-1$
			AnalyzerConstants.getString("ObjectTagLinkAnalyzeResultTableModel.ColumnName.Drag.Horizontal"),  //$NON-NLS-1$
			AnalyzerConstants.getString("ObjectTagLinkAnalyzeResultTableModel.ColumnName.Drag.Vertical"),  //$NON-NLS-1$
			AnalyzerConstants.getString("ObjectTagLinkAnalyzeResultTableModel.ColumnName.Fill.Horizontal"),  //$NON-NLS-1$
			AnalyzerConstants.getString("ObjectTagLinkAnalyzeResultTableModel.ColumnName.Fill.Vertical"),  //$NON-NLS-1$
			AnalyzerConstants.getString("ObjectTagLinkAnalyzeResultTableModel.ColumnName.ColorChange"),  //$NON-NLS-1$
			AnalyzerConstants.getString("ObjectTagLinkAnalyzeResultTableModel.ColumnName.TagDisplay"),  //$NON-NLS-1$
			AnalyzerConstants.getString("ObjectTagLinkAnalyzeResultTableModel.ColumnName.Touch")}; //$NON-NLS-1$
	
	public List<ObjectTagLinkResult> resultList;

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
		
		ObjectTagLinkResult otlr = resultList.get(rowIndex);
		if(null == otlr) {
			return null;
		}
		
		switch(columnIndex) {
		case 0:
			return otlr.getWindowName();
		case 1:
			return otlr.getFigureId();
		case 2:
			return otlr.getEmergeTag();
		case 3:
			return otlr.getBlinkTag();
		case 4:
			return otlr.getHorizontalMoveTag();
		case 5:
			return otlr.getVerticalMoveTag();
		case 6:
			return otlr.getHorizontalDragTag();
		case 7:
			return otlr.getVerticalDragTag();
		case 8:
			return otlr.getHorizontalFillTag();
		case 9:
			return otlr.getVerticalFillTag();
		case 10:
			return otlr.getColorChangeTag();
		case 11:
			return otlr.getTagDisplayTag();
		case 12:
			return otlr.getTouchTag();
			default:
				return "N/A";
		}
	}

	public void setAnalyzeResult(List<ObjectTagLinkResult> resultList) {
		this.resultList = resultList;
		fireTableDataChanged();
	}

}
