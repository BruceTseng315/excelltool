package com.tool;

import com.tool.enums.AddEnum;
import com.tool.enums.OverrideEnum;
import org.apache.poi.hssf.model.InternalWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-12-13
 * Time: 19:36
 */
public class DataExportUtil {
    public static <T> void exportExcel(OutputStream excelOut, DataMode<T> dataMode, List<T> datas) {

        try {
            writeExcel(excelOut, dataMode, datas);
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    public <T> void  exportCsv(OutputStream csvOut, DataMode<T> dataMode,List<T> datas) {


    }

    /**
     * 写入到数据到Excel
     * @param excelOut
     * @param dataMode
     * @param datas
     * @throws IOException
     */
    public static <T> void writeExcel(OutputStream excelOut, DataMode<T> dataMode, List<T> datas) throws IOException {

        //HSSFWorkbook workbook=HSSFWorkbook.create(InternalWorkbook.createWorkbook());


        Workbook workbook = null;
        if(Constants.threeDayExcellPath.endsWith(".xls")){
            workbook = new HSSFWorkbook();
        }else {
            workbook = new XSSFWorkbook();
        }

        writeWorkbook(workbook, dataMode, datas);

        try {
            workbook.write(excelOut);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            workbook.close();
        }

    }

    /**
     * 写一个sheet
     * @param workbook
     * @param dataMode
     * @param datas
     * @return
     */
    public static  <T> List<T> writeWorkbook(Workbook workbook, DataMode<T> dataMode, List<T> datas) {

        if (datas == null || datas.isEmpty()) {
            return null;
        }

        Sheet sheet = workbook.createSheet();
        final Row headRow = sheet.createRow(dataMode.getHeadLine());


        Map<String, String> filenames = dataMode.getColumnandFieldNameMap();

        String[] fieldNames = new String[filenames.size()];
        int i=0;

        Iterator<Map.Entry<String, String>> iterator=
                filenames.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, String> e=iterator.next();
            fieldNames[i]=e.getKey();
            String colname = fieldNames[i];
            Cell cell = headRow.createCell(i);
            setCellValue(cell,colname);
            i++;
        }


        i=dataMode.getDataStart();
        for(T t:datas) {
            Row dataRow = sheet.createRow(i);//创造当前行
            try {
                writeDataRow(dataRow, t, filenames, fieldNames);
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;

            if(i>=dataMode.getDataEnd()) {
                break;
            }
        }

        return datas;
    }

    /**
     * 写入一行
     * @param row
     * @param object
     * @param filenames
     * @param fieldNames
     * @throws Exception
     */
    public static void writeDataRow(Row row, Object object, Map<String, String> filenames, String[] fieldNames)
            throws Exception {

        for (int i = 0; i < fieldNames.length; i++) {

            String colname = fieldNames[i];
            if (!filenames.containsKey(colname)) {
                continue;
            }
            Cell cell = row.createCell(i);
            Object cellValue = null;
            try {
                cellValue = BeanUtils.getPropertyDescriptor(object.getClass(), filenames.get(colname)).getReadMethod()
                        .invoke(object);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (cellValue != null) {
                setCellValue(cell, cellValue);
            }
        }

    }

    /**
     * 设置单元格格式的值
     * @param cell
     * @param value
     */
    public static void setCellValue(Cell cell, Object value) {

        if (value instanceof String || value instanceof Date) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(TypeConvertUtil.convertIfNecessary(value, String.class));
        }

        else if (value instanceof Number) {

            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(TypeConvertUtil.convertIfNecessary(value, Double.class));
        }

        else if(value instanceof Collection) {

            Collection<?> collection=(Collection<Object>)value;
            cell.setCellType(CellType.STRING);
            StringBuilder builder=new StringBuilder();
            for(Object object:collection) {
                builder.append(String.valueOf(object));
                builder.append(",");
            }

            cell.setCellValue(builder.toString());
        }else if(value instanceof OverrideEnum){
            cell.setCellType(CellType.STRING);
            cell.setCellValue(((OverrideEnum)value).getType());
        }else if(value instanceof AddEnum){
            cell.setCellType(CellType.STRING);
            cell.setCellValue(((AddEnum)value).getType());
        }

    }
}
