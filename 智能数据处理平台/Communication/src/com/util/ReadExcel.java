/** 
* 该类用于测试xlsx,xls格式的Excel解析
* @author Wang Jianyu
*/ 
package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

public class ReadExcel {

    private static final String EXTENSION_XLS = "xls";
    private static final String EXTENSION_XLSX = "xlsx";
    
    private javax.swing.JTextArea jTextArea1;
    private String file;
    
    public ReadExcel(javax.swing.JTextArea jTextArea1, String file) {
    	this.jTextArea1 = jTextArea1;
    	this.file = file;
    }

    /***
     * <pre>
     * 取得Workbook对象(xls和xlsx对象不同,不过都是Workbook的实现类)
     *   xls:HSSFWorkbook
     *   xlsx：XSSFWorkbook
     * @param filePath
     * @return
     * @throws IOException
     * </pre>
     */
    private Workbook getWorkbook(String filePath) throws IOException {
        Workbook workbook = null;
        InputStream is = new FileInputStream(filePath);
        if (filePath.endsWith(EXTENSION_XLS)) {
            workbook = new HSSFWorkbook(is);
        } else if (filePath.endsWith(EXTENSION_XLSX)) {
            workbook = new XSSFWorkbook(is);
        }
        return workbook;
    }

    /**
     * 文件检查
     * @param  filePath
     * @throws FileNotFoundException
     * @throws FileFormatException
     */
    private void preReadCheck(String filePath) throws FileNotFoundException, FileFormatException {
        // 常规检查
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("传入的文件不存在：" + filePath);
        }

        if (!(filePath.endsWith(EXTENSION_XLS) || filePath.endsWith(EXTENSION_XLSX))) {
            throw new FileFormatException("传入的文件不是excel");
        }
    }

    /**
     * 读取excel文件内容
     * @param filePath
     * @throws FileNotFoundException
     * @throws FileFormatException
     */
    public void readExcel() throws FileNotFoundException, FileFormatException {
        // 检查
        this.preReadCheck(file);
        // 获取workbook对象
        Workbook workbook = null;

        try {
            workbook = this.getWorkbook(file);
            
            // 读文件 一个sheet一个sheet地读取
            
            Sheet sheet = workbook.getSheetAt(0);//numSheet
            if (sheet == null) {
                return;         // continue;
            }
            
            int firstRowIndex = sheet.getFirstRowNum();
            int lastRowIndex = sheet.getLastRowNum();

            // 读取首行 即,表头
            Row firstRow = sheet.getRow(firstRowIndex);
            for (int i = firstRow.getFirstCellNum(); i <= firstRow.getLastCellNum(); i++) {
                Cell cell = firstRow.getCell(i);         
                String cellValue = this.getCellValue(cell, true);
                jTextArea1.append(" " + cellValue + "\t");
            }
            jTextArea1.append("\n");

            // 读取数据行
            for (int rowIndex = firstRowIndex + 1; rowIndex <= lastRowIndex; rowIndex++) {
                Row currentRow = sheet.getRow(rowIndex);// 当前行
                int firstColumnIndex = currentRow.getFirstCellNum(); // 首列
                int lastColumnIndex = currentRow.getLastCellNum();// 最后一列
                for (int columnIndex = firstColumnIndex; columnIndex <= lastColumnIndex; columnIndex++) {
                    Cell currentCell = currentRow.getCell(columnIndex);// 当前单元格
                    String currentCellValue = this.getCellValue(currentCell, true);// 当前单元格的值
                    jTextArea1.append(currentCellValue + "\t");
                }
                jTextArea1.append("\n");
            }        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 取单元格的值
     * @param cell 单元格对象
     * @param treatAsStr 为true时，当做文本来取值 (取到的是文本，不会把“1”取成“1.0”)
     * @return
     */
    private String getCellValue(Cell cell, boolean treatAsStr) {
        if (cell == null) {
            return "";
        }

        if (treatAsStr) {
            // 虽然excel中设置的都是文本，但是数字文本还被读错，如“1”取成“1.0”
            // 加上下面这句，临时把它当做文本来读取
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }

        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return String.valueOf(cell.getStringCellValue());
        }
    }
}