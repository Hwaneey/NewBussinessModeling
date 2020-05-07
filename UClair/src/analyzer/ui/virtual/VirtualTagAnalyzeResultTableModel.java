package analyzer.ui.virtual;

import java.util.List;


import javax.swing.table.AbstractTableModel;

import analyzer.analysis.virtual.VirtualDepend;
import analyzer.constants.AnalyzerConstants;
import com.naru.uclair.tag.DataTag;
import com.naru.uclair.tag.ITagConstants;
import com.naru.uclair.tag.ItemDeviceAdapter;
import com.naru.uclair.tag.MapDeviceAdapter;

/**
 *
 * <pre>
 * NAME   : com.naru.uclair.analyzer.ui.virtual.VirtualTagAnalyzeResultTableModel.java
 * DESC   : �����±� ���Ӽ� �м� ��� ǥ�� TableModel Ŭ����.
 *
 * references : ���輭 NARU-XXX-XXX-XXX
 *
 * Copyright 2012 NARU Technology All rights reserved
 * <pre>
 *
 * @author US Laboratory naruteclab4
 * @since 2012. 6. 21.
 * @version 1.0
 *
 */
public class VirtualTagAnalyzeResultTableModel extends AbstractTableModel {

	/**
	 * 0: �±׸�
	 * 1: ����
	 * 2: ������ ����
	 * 3: ����
	 * 4: ����
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
