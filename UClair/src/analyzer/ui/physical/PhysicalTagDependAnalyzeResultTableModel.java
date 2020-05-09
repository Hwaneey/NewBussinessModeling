package analyzer.ui.physical;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import analyzer.analysis.tag.PhysicalDepend;
import analyzer.constants.AnalyzerConstants;
import com.naru.uclair.tag.DataTag;
import com.naru.uclair.tag.ITagConstants;
import com.naru.uclair.tag.ItemDeviceAdapter;
import com.naru.uclair.tag.MapDeviceAdapter;
import com.naru.uclair.tag.Tag;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 지이삭
 * @설명  	: 물리주소 종속성 분석 결과 표시 TableModel 클래스.
 * @변경이력 	: 
 ************************************************/

public class PhysicalTagDependAnalyzeResultTableModel extends AbstractTableModel {

	/**
	 * 객체 직렬화 버전 아이디.
	 * 1. 태그 명
	 * 2. 노드 명
	 * 3. 데이터 형식
	 * 4. 장비명
	 * 5. 영역
	 * 6. 주소
	 * 7. 데이터
	 */
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {AnalyzerConstants.getString("PhysicalTagDependAnalyzeResultTableModel.ColumnNameTag"),  //$NON-NLS-1$
			AnalyzerConstants.getString("PhysicalTagDependAnalyzeResultTableModel.ColumnNameNode"),  //$NON-NLS-1$
			AnalyzerConstants.getString("PhysicalTagDependAnalyzeResultTableModel.ColumnNameDataType"),  //$NON-NLS-1$
			AnalyzerConstants.getString("PhysicalTagDependAnalyzeResultTableModel.ColumnNameDevice"),  //$NON-NLS-1$
			AnalyzerConstants.getString("PhysicalTagDependAnalyzeResultTableModel.ColumnNameArea"),  //$NON-NLS-1$
			AnalyzerConstants.getString("PhysicalTagDependAnalyzeResultTableModel.ColumnNameAddress"),  //$NON-NLS-1$
			AnalyzerConstants.getString("PhysicalTagDependAnalyzeResultTableModel.ColumnNameData")}; //$NON-NLS-1$

//	private PhysicalDepend physicalDepend = null;
	private List<Tag> tagList = null;

	public PhysicalTagDependAnalyzeResultTableModel() {
	}
	
	public PhysicalTagDependAnalyzeResultTableModel(PhysicalDepend physicalDepend) {
//		this.physicalDepend = physicalDepend;
		this.tagList = physicalDepend.getPhysicalTagDepends();
	}
	
	public void setPhysicalDepend(PhysicalDepend physicalDepend) {
//		this.physicalDepend = physicalDepend;
		this.tagList = physicalDepend.getPhysicalTagDepends();
	}
	
	@Override
	public int getRowCount() {
		if(null != tagList) {
			return tagList.size();
		}
		else {
			return 0;
		}
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
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
	
	private String getDeviceDataType(int type) {
		String returnString = null;
		if (type == ITagConstants.BIT_DATA) {
			returnString = ITagConstants.DEVICE_DATATYPE_DIGITAL_LIST[0];
		}
		else if (type == ITagConstants.INT8_DATA) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[0];
		}
		else if (type == ITagConstants.UINT8_DATA) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[1];
		}
		else if (type == ITagConstants.INT16_DATA) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[2];
		}
		else if (type == ITagConstants.UINT16_DATA) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[3];
		}
		else if (type == ITagConstants.INT32_DATA) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[4];
		}
		else if (type == ITagConstants.UINT32_DATA) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[5];
		}
		else if (type == ITagConstants.LONG_DATA) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[6];
		}
		else if (type == ITagConstants.BCD8_DATA) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[7];
		}
		else if (type == ITagConstants.BCD16_DATA) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[8];
		}
		else if (type == ITagConstants.BCD32_DATA) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[9];
		}
		else if (type == ITagConstants.FLOAT_DATA) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[10];
		}
		else if (type == ITagConstants.INT16_DATA_REV) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[11];
		}
		else if (type == ITagConstants.UINT16_DATA_REV) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[12];
		}
		else if (type == ITagConstants.BCD16_DATA_REV) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[13];
		}
		else if (type == ITagConstants.INT32_DATA_REV) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[14];
		}
		else if (type == ITagConstants.UINT32_DATA_REV) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[15];
		}
		else if (type == ITagConstants.BCD32_DATA_REV) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[16];
		}
		else if (type == ITagConstants.TEXT_ASCII_DATA) {
			returnString = ITagConstants.DEVICE_DATATYPE_STRING_LIST[0];
		}
		else if (type == ITagConstants.HEX_STRING_DATA) {
			returnString = ITagConstants.DEVICE_DATATYPE_STRING_LIST[1];
		}
		else if (type == ITagConstants.INT16_DATA_H) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[17];
		}
		else if (type == ITagConstants.INT16_DATA_L) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[18];
		}
		else if (type == ITagConstants.UINT16_DATA_H) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[19];
		}
		else if (type == ITagConstants.UINT16_DATA_L) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[20];
		}
		else if (type == ITagConstants.BCD16_DATA_H) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[21];
		}
		else if (type == ITagConstants.BCD16_DATA_L) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[22];
		}
		else if (type == ITagConstants.INT32_DATA_0) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[23];
		}
		else if (type == ITagConstants.INT32_DATA_1) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[24];
		}
		else if (type == ITagConstants.INT32_DATA_2) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[25];
		}
		else if (type == ITagConstants.INT32_DATA_3) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[26];
		}
		else if (type == ITagConstants.UINT32_DATA_0) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[27];
		}
		else if (type == ITagConstants.UINT32_DATA_1) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[28];
		}
		else if (type == ITagConstants.UINT32_DATA_0) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[29];
		}
		else if (type == ITagConstants.UINT32_DATA_1) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[30];
		}
		else if (type == ITagConstants.BCD32_DATA_0) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[31];
		}
		else if (type == ITagConstants.BCD32_DATA_1) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[32];
		}
		else if (type == ITagConstants.BCD32_DATA_2) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[33];
		}
		else if (type == ITagConstants.BCD32_DATA_3) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[33];
		}
		else if (type == ITagConstants.FLOAT_DATA_0123) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[35];
		}
		else if (type == ITagConstants.FLOAT_DATA_1032) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[36];
		}
		else if (type == ITagConstants.FLOAT_DATA_3210) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[37];
		}
		else if (type == ITagConstants.FLOAT_DATA_2301) {
			returnString = ITagConstants.DEVICE_DATATYPE_ANALOG_LIST[38];
		}
		return returnString;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object rowData = null;
		if( null != tagList) {
			if(getRowCount()>rowIndex){
				switch(columnIndex){
				case 0:
					rowData = tagList.get(rowIndex).getKey();
					break;
				case 1:
					rowData = tagList.get(rowIndex).getNode(); 
					break;
				case 2:
					rowData = getDataType(tagList.get(rowIndex).getDataType());
					break;
				case 3:
					if(tagList.get(rowIndex) instanceof DataTag) {
						rowData = ((DataTag)tagList.get(rowIndex)).getDeviceAdapter().getDeviceName();
					}
					break;
				case 4:
					if(tagList.get(rowIndex) instanceof DataTag) {
						if(((DataTag)tagList.get(rowIndex)).getDeviceAdapter() instanceof MapDeviceAdapter) {
							rowData = String.valueOf(((MapDeviceAdapter)((DataTag)tagList.get(rowIndex)).getDeviceAdapter()).getArea());
						}
						if(((DataTag)tagList.get(rowIndex)).getDeviceAdapter() instanceof ItemDeviceAdapter) {
							rowData = ((ItemDeviceAdapter)((DataTag)tagList.get(rowIndex)).getDeviceAdapter()).getItemName();
						}
					}
					break;
				case 5:
					if(tagList.get(rowIndex) instanceof DataTag) {
						if(((DataTag)tagList.get(rowIndex)).getDeviceAdapter() instanceof MapDeviceAdapter) {
							rowData = String.valueOf(((MapDeviceAdapter)((DataTag)tagList.get(rowIndex)).getDeviceAdapter()).getAddress());
						}
						if(((DataTag)tagList.get(rowIndex)).getDeviceAdapter() instanceof ItemDeviceAdapter) {
							rowData = ((ItemDeviceAdapter)((DataTag)tagList.get(rowIndex)).getDeviceAdapter()).getItemName();
						}
					}
					break;
				case 6:
					if(tagList.get(rowIndex) instanceof DataTag) {
						rowData = getDeviceDataType(((DataTag)tagList.get(rowIndex)).getDeviceAdapter().getDeviceDataType());
					}
					break;
				}
			}
			return rowData;
			
		}
		return null;
	}

}
