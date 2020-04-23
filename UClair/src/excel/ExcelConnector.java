package excel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JTable;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jidesoft.grid.SortableTable;


public class ExcelConnector {
	public static JTable readTableFromExcel(String path) {
		JTable table = new JTable();
		
		XSSFRow row;
		XSSFCell cell;

		try {
			FileInputStream inputStream = new FileInputStream(path);
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			
			//sheet¼ö Ãëµæ
			int sheetCn = workbook.getNumberOfSheets();
			for(int cn = 0; cn < sheetCn; cn++){
				//0¹øÂ° sheet Á¤º¸ Ãëµæ
				XSSFSheet sheet = workbook.getSheetAt(cn);

				//ÃëµæµÈ sheet¿¡¼­ rows¼ö Ãëµæ
				int rows = sheet.getPhysicalNumberOfRows();
				
				//ÃëµæµÈ row¿¡¼­ Ãëµæ´ë»ó cell¼ö Ãëµæ
				int cells = sheet.getRow(cn).getPhysicalNumberOfCells(); 
				String[] header = new String[cells];
				String[][] content = new String[rows][cells];
				
				for(int i=0; i<cells; i++)
					header[i] = Character.toString((char)('A' + i));
				
				for (int r = 0; r < rows; r++) {
					row = sheet.getRow(r); 
					if (row != null) {
						for (int c = 0; c < cells; c++) {
							cell = row.getCell(c);
							if (cell != null) {
								String value = null;
								switch (cell.getCellType()) {
								case XSSFCell.CELL_TYPE_FORMULA:
									value = cell.getCellFormula();
									break;
								case XSSFCell.CELL_TYPE_NUMERIC:
									value = "" + cell.getNumericCellValue();
									break;
								case XSSFCell.CELL_TYPE_STRING:
									value = "" + cell.getStringCellValue();
									break;
								case XSSFCell.CELL_TYPE_BLANK:
									value = "°ø¹é";
									break;
								case XSSFCell.CELL_TYPE_ERROR:
									value = "" + cell.getErrorCellValue();
									break;
								default:
								}
								content[r][c] = value;
							} else {
								content[r][c] = "";
							}
						}
					}
				} 
				table = new SortableTable(content, header);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}
	
	public static void writeExcelFromTable(String path, JTable table) {
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(path);
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int row = table.getRowCount();
			int col = table.getColumnCount();
			
			for(int r=0; r<row; r++) {
				for(int c=0; c<col; c++) {
					sheet.getRow(r).createCell(c).setCellValue((String)table.getValueAt(r, c));
				}
			}
			
			outputStream = new FileOutputStream(path);
			workbook.write(outputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
