package analyzer.ui.effect;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import analyzer.analysis.effect.EffectCompatibilityResult;
import analyzer.constants.AnalyzerConstants;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 이승환
 * @설명  	: 객체효과 양립성 분석 결과 표시 TableModel 클래스.
 * @변경이력 	: 
 ************************************************/

public class EffectCompatibilityAnalyzeResultTableModel extends
		AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {AnalyzerConstants.getString("EffectCompatibilityAnalyzeResultTableModel.ColumnName.WindowName"),  //$NON-NLS-1$
			AnalyzerConstants.getString("EffectCompatibilityAnalyzeResultTableModel.ColumnName.Object"),  //$NON-NLS-1$
			AnalyzerConstants.getString("EffectCompatibilityAnalyzeResultTableModel.ColumnName.Priority"),  //$NON-NLS-1$
			AnalyzerConstants.getString("EffectCompatibilityAnalyzeResultTableModel.ColumnName.CompatibilityType"),  //$NON-NLS-1$
			AnalyzerConstants.getString("EffectCompatibilityAnalyzeResultTableModel.ColumnName.Emerge"),  //$NON-NLS-1$
			AnalyzerConstants.getString("EffectCompatibilityAnalyzeResultTableModel.ColumnName.Blink"),  //$NON-NLS-1$
			AnalyzerConstants.getString("EffectCompatibilityAnalyzeResultTableModel.ColumnName.Move"),  //$NON-NLS-1$
			AnalyzerConstants.getString("EffectCompatibilityAnalyzeResultTableModel.ColumnName.Drag"),  //$NON-NLS-1$
			AnalyzerConstants.getString("EffectCompatibilityAnalyzeResultTableModel.ColumnName.TagDisplay"),  //$NON-NLS-1$
			AnalyzerConstants.getString("EffectCompatibilityAnalyzeResultTableModel.ColumnName.Touch")};  //$NON-NLS-1$
	private List<EffectCompatibilityResult> resultList;

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
		EffectCompatibilityResult result = resultList.get(rowIndex);
		if(null == result) {
			return null;
		}
		
		switch(columnIndex) {
		case 0:
			return result.getWindowName();
		case 1:
			return result.getFigureId();
		case 2:
			return EffectCompatibilityResult.PRIORITY_NAMES[result.getPriority()];
		case 3:
			return EffectCompatibilityResult.TYPE_NAMES[result.getCompatibilityType()];
		case 4:
			return result.useEmerge();
		case 5:
			return result.useBlink();
		case 6:
			return result.useMove();
		case 7:
			return result.useDrag();
		case 8:
			return result.useTagDisplay();
		case 9:
			return result.useTouch();
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

	public void setAnalyzeResult(List<EffectCompatibilityResult> resultList) {
		this.resultList = resultList;
		fireTableDataChanged();
	}

}
