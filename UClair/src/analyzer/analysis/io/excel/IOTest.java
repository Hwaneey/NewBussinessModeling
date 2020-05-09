package analyzer.analysis.io.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import analyzer.Analyzer;
import analyzer.constants.AnalyzerConstants;
import com.naru.uclair.project.TagDictionary;
import com.naru.uclair.script.functions.object.ExcelObject;
import com.naru.uclair.tag.DataTag;
import com.naru.uclair.tag.ITagConstants;
import com.naru.uclair.tag.ItemDeviceAdapter;
import com.naru.uclair.tag.MapDeviceAdapter;
import com.naru.uclair.tag.Tag;

/************************************************
 * @date	: 2020. 5.07.
 * @책임자 : 지이삭
 * @설명  	:  IO 테스트
 * @변경이력 	: 
 ************************************************/

public class IOTest {

	private HashMap<String, IOTestDevice> map =  null;
	private String filePath = "src/resources/excel/template/io.xls";
	private String saveFileName = "IO_SERVER_TEST_Sheet.xls";
	
	public IOTest() {
		map = new HashMap<String, IOTestDevice>();
	}
	
	public Collection<IOTestDevice> getDevices() {
		return map.values();
	}
	
	public HashMap<String, IOTestDevice> getDevicesMap() {
		return map;
	}

	public void analyzer(TagDictionary tagDic) {
		List<DataTag> tagList = tagDic.getAllNoneSystemDataTags();
		String key = null;
		for(Tag tag : tagList) {
			if(tag.isHardwareTag()) {
				if(tag instanceof DataTag) {
					key = ((DataTag)tag).getDeviceAdapter().getDeviceName();
					IOTestDevice ioTestDevice = map.get(key);
					if(null == ioTestDevice) {
						ioTestDevice = new IOTestDevice();
						ioTestDevice.setDeviceName(key);
						ioTestDevice.setNodeName(tag.getNode());
						map.put(key, ioTestDevice);
					}
					
					ioTestDevice.addTag(tag);
				}
			}
		}
	}

	/**
	 * 엑셀을 내보내기 한다.
	 * @param analyzer 
	 * @return
	 */
	public void exportExcel(Analyzer analyzer) {
		Iterator<IOTestDevice> it = getDevices().iterator();
		List<Tag> digitalList = new ArrayList<Tag>();
		List<Tag> analogList = new ArrayList<Tag>();
		List<Tag> stringList = new ArrayList<Tag>();
		
		while(it.hasNext()) {
			IOTestDevice ioTestDevice = it.next();
			for(Tag tag : ioTestDevice.getTagList()) {
				if(tag.getTagType() == ITagConstants.DIGITAL) {
					digitalList.add(tag);
				}
				else if(tag.getTagType() == ITagConstants.ANALOG) {
					analogList.add(tag);
				}
				else if(tag.getTagType() == ITagConstants.STRING) {
					stringList.add(tag);
				}
			}
		}
		
		File file = new File(filePath);
		ExcelObject excel = null;
		try {
			excel = new ExcelObject(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(null != excel) {
			// digital
			writeExcel(excel, digitalList);
			// analog
			excel.selectSheet(1);
			writeExcel(excel, analogList);
			// string
			excel.selectSheet(2);
			writeExcel(excel, stringList);
			
			// 실제 저장될 파일 이름
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setDialogTitle(AnalyzerConstants.getString("IOTest.Msg.folder"));
			
			int result = chooser.showDialog(analyzer, AnalyzerConstants.getString("IOTest.Msg.folder"));
			if (result == JFileChooser.APPROVE_OPTION) {
				File f = chooser.getSelectedFile();
				if (!f.canWrite()) {
					JOptionPane.showMessageDialog(analyzer, AnalyzerConstants.getString("IOTest.Msg.write.error")); //$NON-NLS-1$
				}
				else {
					String path = f.toString();
					
					// 실제로 저장될 파일 풀 경로
					File files = new File(path, saveFileName);
					
					//엑셀 파일을 만듬
					FileOutputStream fileOutput;
					try {
						fileOutput = new FileOutputStream(files);
						excel.save(fileOutput);
						fileOutput.close();
						JOptionPane.showMessageDialog(analyzer, f.getCanonicalPath() + saveFileName + AnalyzerConstants.getString("IOTest.Msg.create.success"));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void writeExcel(ExcelObject excel, List<Tag> list) {
		for(int index=0; index<list.size(); index++) {
			excel.setCellValue(index+1, index+7, 0);
			excel.setCellValue(list.get(index).getKey(), index+7, 1);
			excel.setCellValue(((DataTag)list.get(index)).getDeviceAdapter().getDeviceName(), index+7, 2);
			excel.setCellValue(getDataType(list.get(index).getDataType()), index+7, 3);
			if(((DataTag)list.get(index)).getDeviceAdapter() instanceof MapDeviceAdapter) {
				excel.setCellValue(String.valueOf(((MapDeviceAdapter)((DataTag)list.get(index)).getDeviceAdapter()).getAddress()), index+7, 4);
			}
			if(((DataTag)list.get(index)).getDeviceAdapter() instanceof ItemDeviceAdapter) {
				excel.setCellValue(((ItemDeviceAdapter)((DataTag)list.get(index)).getDeviceAdapter()).getItemName(), index+7, 4);
			}
			excel.setCellValue(getDeviceDataType(((DataTag)list.get(index)).getDeviceAdapter().getDeviceDataType()), index+7, 5);
			excel.setCellValue((((DataTag)list.get(index)).isAlarmTag() == true) ? "Y" : "N", index+7, 6);
		}
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
	
}
