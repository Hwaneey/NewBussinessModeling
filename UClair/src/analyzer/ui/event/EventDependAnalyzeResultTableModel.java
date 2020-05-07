package analyzer.ui.event;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import analyzer.analysis.event.EventDepend;
import analyzer.analysis.event.EventTagDepend;
import analyzer.constants.AnalyzerConstants;
/** @date	: 2020. 5.07.
* @책임자 : 지한별
* @설명  	: 이벤트 종속성 분석 결과 표시 TableModel 클래스.
* @변경이력 	: **/

public class EventDependAnalyzeResultTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {AnalyzerConstants.getString("EventDependAnalyzeResultTableModel.ColumnName.No"),  //$NON-NLS-1$
			AnalyzerConstants.getString("EventDependAnalyzeResultTableModel.ColumnName.Event"),  //$NON-NLS-1$
			AnalyzerConstants.getString("EventDependAnalyzeResultTableModel.ColumnName.ConditionTag"),  //$NON-NLS-1$
			AnalyzerConstants.getString("EventDependAnalyzeResultTableModel.ColumnName.ReserveTag"),  //$NON-NLS-1$
			AnalyzerConstants.getString("EventDependAnalyzeResultTableModel.ColumnName.DependTagList")}; //$NON-NLS-1$
	private List<EventTagDepend> list = null;

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return list.size();
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
		Object rowData = null;
		if( null != list) {
			if(getRowCount()>rowIndex){
				switch(columnIndex){
				case 0:
					rowData = rowIndex+1;
					break;
				case 1:
					rowData = list.get(rowIndex).getEventName(); 
					break;
				case 2:
					rowData = list.get(rowIndex).getConditionTag(); 
					break;
				case 3:
					rowData = list.get(rowIndex).getInhibitTagName();
					break;
				case 4:
					rowData = list.get(rowIndex).getTagString();
					break;
				}
			}
			return rowData;
			
		}
		return null;
	}

	public void setEventDepend(EventDepend depend) {
		if(null != depend) {
			list  = depend.getEventTagDepend();
			if(null != list) {
				for(EventTagDepend tagDepend : list) {
					tagDepend.analyzerToString();
				}
			}
		}
	}

}
