package excel;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.DropMode;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jidesoft.grid.AutoResizePopupMenuCustomizer;
import com.jidesoft.grid.DefaultUndoableTableModel;
import com.jidesoft.grid.JideTableTransferHandler;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.grid.TableColumnChooserPopupMenuCustomizer;
import com.jidesoft.grid.TableHeaderPopupMenuInstaller;


public class ExcelConnector {
	public static SortableTable readTableFromExcel(String path) {
		SortableTable _sortableTable = null;
	    DefaultUndoableTableModel _defaultModel;
		
		XSSFRow row;
		XSSFCell cell;

		try {
			FileInputStream inputStream = new FileInputStream(path);
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			
			//sheet수 취득
			int sheetCn = workbook.getNumberOfSheets();
			for(int cn = 0; cn < sheetCn; cn++){
				//0번째 sheet 정보 취득
				XSSFSheet sheet = workbook.getSheetAt(cn);

				//취득된 sheet에서 rows수 취득
				int rows = sheet.getPhysicalNumberOfRows();
				
				//취득된 row에서 취득대상 cell수 취득
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
									value = "공백";
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
				_defaultModel = (DefaultUndoableTableModel) new DefaultUndoableTableModel(content, header);
				_sortableTable = new SortableTable(_defaultModel);
				InputMap map = _sortableTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		        map.put(KeyStroke.getKeyStroke("control Z"), "undo");
		        map.put(KeyStroke.getKeyStroke("control Y"), "redo");

		        ((JideTableTransferHandler) _sortableTable.getTransferHandler()).setAcceptImport(true);
		        
		        _sortableTable.setNonContiguousCellSelection(false);
		        
		        _sortableTable.setDragEnabled(true);
		        
		        _sortableTable.setDropMode(DropMode.INSERT);
		        
		        
		         _sortableTable.setClickCountToStart(2);		
		        TableHeaderPopupMenuInstaller installer = new TableHeaderPopupMenuInstaller(_sortableTable);
		        installer.addTableHeaderPopupMenuCustomizer(new AutoResizePopupMenuCustomizer());
		        installer.addTableHeaderPopupMenuCustomizer(new TableColumnChooserPopupMenuCustomizer());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _sortableTable;
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
