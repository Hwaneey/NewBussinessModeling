package analyzer.ui.virtual;

import java.util.List;


import javax.swing.table.AbstractTableModel;

import analyzer.analysis.virtual.VirtualDepend;
import analyzer.constants.AnalyzerConstants;
import com.naru.uclair.tag.DataTag;
import com.naru.uclair.tag.ITagConstants;
import com.naru.uclair.tag.ItemDeviceAdapter;
import com.naru.uclair.tag.MapDeviceAdapter;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 박보미
 * @설명  	:  가상태그 종속성 분석 결과 표시 TableModel 클래스.
 * @변경이력 	: 
 ************************************************/

public class VirtualTagAnalyzeResultTableModel extends AbstractTableModel {

	/**
	 * 0: 태그명
	 * 1: 노드명
	 * 2: 데이터 형식
	 * 3: 장비명
	 * 4: 영역
	 */
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {AnalyzerConstants.getString("VirtualTagAnalyzeResultTableModel.ColumnName.Event"),
			AnalyzerConstants.getString("VirtualTagAnalyzeResultTableModel.ColumnName.Tag"), //$NON-NLS-1$
			AnalyzerConstants.getString("VirtualTagAnalyzeResultTableModel.ColumnName.Node"), //$NON-NLS-1$
			AnalyzerConstants.getString("VirtualTagAnalyzeResultTableModel.ColumnName.DataType"), //$NON-NLS-1$
			AnalyzerConstants.getString("VirtualTagAnalyzeResultTableModel.ColumnName.Device"), //$NON-NLS-1$
			AnalyzerConstants.getString("VirtualTagAnalyzeResultTableModel.ColumnName.Area")}; //$NON-NLS-1$
	private List<VirtualDepend> tagList;

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		if(null != tagList) {
			return tagList.size();
		}
		return 0;
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
		if( null != tagList) {
			if(getRowCount()>rowIndex){
				switch(columnIndex){
				case 0:
					rowData = tagList.get(rowIndex).getEventName();
					break;
				case 1:
					rowData = tagList.get(rowIndex).getTag().getKey();
					break;
				case 2:
					rowData = tagList.get(rowIndex).getTag().getNode(); 
					break;
				case 3:
					rowData = getDataType(tagList.get(rowIndex).getTag().getDataType());
					break;
				case 4:
					if(tagList.get(rowIndex).getTag() instanceof DataTag) {
						rowData = ((DataTag)tagList.get(rowIndex).getTag()).getDeviceAdapter().getDeviceName();
					}
					break;
				case 5:
					if(tagList.get(rowIndex).getTag() instanceof DataTag) {
						if(((DataTag)tagList.get(rowIndex).getTag()).getDeviceAdapter() instanceof MapDeviceAdapter) {
							rowData = String.valueOf(((MapDeviceAdapter)((DataTag)tagList.get(rowIndex).getTag()).getDeviceAdapter()).getArea());
						}
						if(((DataTag)tagList.get(rowIndex).getTag()).getDeviceAdapter() instanceof ItemDeviceAdapter) {
							rowData = ((ItemDeviceAdapter)((DataTag)tagList.get(rowIndex).getTag()).getDeviceAdapter()).getItemName();
						}
					}
					break;
				}
			}
			return rowData;
			
		}
		return null;
	}
	
	private String getDataType(int type) {
		String returnString = null;
		switch (type) {
		case ITagConstants.TAG_DATATYPE_DIGITAL:
			returnString = "DIGITAL";
			break;
		case ITagConstants.TAG_DATATYPE_INTEGER:
			returnString = "INTEGER";
			break;
		case ITagConstants.TAG_DATATYPE_REAL:
			returnString = "REAL";
			break;
		case ITagConstants.TAG_DATATYPE_STRING:
			returnString = "STRING";
			break;
		}
		return returnString;
	}

//	public void setVirtualTagDepends(List<Tag> tagList) {
//		this.tagList =tagList;
//		
//		fireTableDataChanged();
//	}
	
	public void setVirtualDepends(List<VirtualDepend> tagList) {
		this.tagList =tagList;
		
		fireTableDataChanged();
	}

}
