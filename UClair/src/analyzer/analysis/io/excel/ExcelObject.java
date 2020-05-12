package analyzer.analysis.io.excel;

import com.naru.common.db.result.Result;
import com.naru.common.db.result.ResultRecord;
import com.naru.uclair.script.functions.object.IExcelObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelObject implements IExcelObject {
     private POIFSFileSystem poiFS;
     
     private HSSFWorkbook document;
     
     private HSSFSheet currentSheet;
     
     private void setCellValue(HSSFCell paramHSSFCell, Object paramObject) {
       if (null == paramHSSFCell)
         return; 
       if (null == paramObject) {
         paramHSSFCell.setCellValue((HSSFRichTextString)null);
       } else if (paramObject instanceof String) {
         paramHSSFCell.setCellType(1);
         paramHSSFCell.setCellValue(new HSSFRichTextString((String)paramObject));
       } else if (paramObject instanceof Calendar) {
         paramHSSFCell.setCellType(1);
         paramHSSFCell.setCellValue(((Calendar)paramObject).getTime());
       } else if (paramObject instanceof Date) {
         paramHSSFCell.setCellType(1);
         paramHSSFCell.setCellValue((Date)paramObject);
       } else if (paramObject instanceof Number) {
         paramHSSFCell.setCellType(0);
         paramHSSFCell.setCellValue(((Number)paramObject).doubleValue());
       } else if (paramObject instanceof Boolean) {
         paramHSSFCell.setCellType(4);
         paramHSSFCell.setCellValue(((Boolean)paramObject).booleanValue());
       } else {
         paramHSSFCell.setCellValue(new HSSFRichTextString(paramObject.toString()));
       } 
     }
     
     public ExcelObject() {
       this.document = new HSSFWorkbook();
       this.currentSheet = this.document.createSheet();
     }
     
     public ExcelObject(InputStream paramInputStream) {
       try {
         this.poiFS = new POIFSFileSystem(paramInputStream);
         this.document = new HSSFWorkbook(this.poiFS);
         this.currentSheet = this.document.getSheetAt(0);
       } catch (IOException iOException) {
         iOException.printStackTrace();
       } 
     }
     
     public boolean save(OutputStream paramOutputStream) {
       boolean bool = false;
       try {
         this.document.write(paramOutputStream);
         bool = true;
       } catch (Exception exception) {
         exception.printStackTrace();
       } finally {
         try {
           paramOutputStream.close();
         } catch (Exception exception) {}
       } 
       return bool;
     }
     
     public boolean selectSheet(String paramString) {
       boolean bool = false;
       if (null != this.document) {
         HSSFSheet hSSFSheet = this.document.getSheet(paramString);
         if (null != hSSFSheet) {
           this.currentSheet = hSSFSheet;
           bool = true;
         } 
       } 
       return bool;
     }
     
     public boolean selectSheet(int paramInt) {
       boolean bool = false;
       if (null != this.document) {
         HSSFSheet hSSFSheet = this.document.getSheetAt(paramInt);
         if (null != hSSFSheet) {
           this.currentSheet = hSSFSheet;
           bool = true;
         } 
       } 
       return bool;
     }
     
     public void setCellValue(Object paramObject, int paramInt1, int paramInt2) {
       if (null != this.currentSheet) {
         HSSFRow hSSFRow = this.currentSheet.getRow(paramInt1);
         if (null == hSSFRow)
           hSSFRow = this.currentSheet.createRow(paramInt1); 
         if (null != hSSFRow) {
           HSSFCell hSSFCell = this.currentSheet.getRow(paramInt1).getCell(paramInt2);
           if (null == hSSFCell)
             hSSFCell = hSSFRow.createCell(paramInt2); 
           if (null != hSSFCell)
             setCellValue(hSSFCell, paramObject); 
         } 
       } 
     }
     
     public Object getCellValue(int paramInt1, int paramInt2) {
       String str = null;
       if (null != this.currentSheet) {
         HSSFRow hSSFRow = this.currentSheet.getRow(paramInt1);
         if (null != hSSFRow) {
           HSSFCell hSSFCell = hSSFRow.getCell(paramInt2);
           if (null != hSSFCell) {
             Boolean bool;
             Double double_;
             switch (hSSFCell.getCellType()) {
               case 1:
                 str = hSSFCell.getRichStringCellValue().getString();
                 break;
               case 4:
                 bool = Boolean.valueOf(hSSFCell.getBooleanCellValue());
                 break;
               case 0:
                 double_ = Double.valueOf(hSSFCell.getNumericCellValue());
                 break;
               case 2:
                 str = hSSFCell.getCellFormula();
                 break;
             } 
           } 
         } 
       } 
       return str;
     }
     
     public void setRow(int paramInt, Object[] paramArrayOfObject) {
       setRow(paramInt, 0, paramArrayOfObject);
     }
     
     public void setRow(int paramInt, List<Object> paramList) {
       try {
         setRow(paramInt, 0, paramList.toArray());
       } catch (Exception exception) {
         System.out.println("setRow() has wrong values parameter");
       } 
     }
     
     public void setRow(int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
       if (null != this.currentSheet && null != paramArrayOfObject) {
         HSSFRow hSSFRow = this.currentSheet.getRow(paramInt1);
         if (null == hSSFRow) {
           hSSFRow = this.currentSheet.createRow(paramInt1);
           if (null != hSSFRow) {
             byte b1 = 0;
             for (byte b2 = 0; b2 < paramArrayOfObject.length; b2++) {
               HSSFCell hSSFCell = hSSFRow.getCell(paramInt2 + b2);
               if (null == hSSFCell) {
                 hSSFCell = hSSFRow.createCell(paramInt2 + b2);
                 if (null != hSSFCell)
                   setCellValue(hSSFCell, paramArrayOfObject[b1]); 
               } 
               b1++;
             } 
           } 
         } 
       } 
     }
     
     public void setRow(int paramInt1, int paramInt2, List<Object> paramList) {
       try {
         setRow(paramInt1, paramInt2, paramList.toArray());
       } catch (Exception exception) {
         System.out.println("setRow() has wrong values parameter");
       } 
     }
     
     public void setRow(int paramInt, ResultRecord paramResultRecord) {
       setRow(paramInt, 0, paramResultRecord);
     }
     
     public void setRow(int paramInt1, int paramInt2, ResultRecord paramResultRecord) {
       if (null != paramResultRecord)
         setRow(paramInt1, paramInt2, paramResultRecord.getRecordData()); 
     }
     
     public void setDBResult(int paramInt, Result paramResult) {
       setDBResult(paramInt, 0, paramResult);
     }
     
     public void setDBResult(int paramInt1, int paramInt2, Result paramResult) {
       if (null != this.currentSheet && null != paramResult) {
         int i = paramInt1;
         for (ResultRecord resultRecord : paramResult.getResult()) {
           setRow(i, paramInt2, resultRecord);
           i++;
         } 
         System.gc();
       } 
     }
     
     public boolean close() {
       this.currentSheet = null;
       this.document = null;
       this.poiFS = null;
       return true;
     }
   }