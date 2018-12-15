package com.tool;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-12-12
 * Time: 16:15
 */
public class DataImportUtil {

    public static <T> List<T> importExcel(InputStream inputStream, DataMode<T> dataMode) {

        List<T> datas = null;
        try {
            datas = readExcel(inputStream, dataMode);
        } catch (IOException e) {
            System.out.println(e);
        }

        return datas;
    }

    public static <T> List<T> readExcel(InputStream inputStream, DataMode<T> dataMode) throws IOException {

        List<T> datas = null;
        Workbook workbook = null;

        workbook = getWorkbok(inputStream);

        if (workbook == null) {
            return null;
        }
        datas = readWorkbook(workbook, dataMode);
        workbook.close();
        return datas;
    }

    public static <T> List<T> readWorkbook(Workbook workbook, DataMode<T> dataMode) {

        Sheet sheet = null;
        sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            return null;
        }
        Row row = sheet.getRow(dataMode.getHeadLine());

        if (row == null) {
            return null;
        }

        int dataEnd = dataMode.getDataEnd();

        int firstCellNum = row.getFirstCellNum();
        int lastCellNum = row.getPhysicalNumberOfCells();
        String[] fieldNames = new String[lastCellNum];//保存表头
        for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
            Cell cell = row.getCell(cellNum);
            fieldNames[cellNum - firstCellNum] = getCellValue(cell, String.class);
        }

        List<T> datas = new ArrayList<>();
        Map<String, String> filenames = dataMode.getColumnandFieldNameMap();

        for (int rowNum = dataMode.getDataStart(); rowNum <= dataEnd && rowNum <= sheet.getLastRowNum(); rowNum++) {
            // 获得当前行
            row = sheet.getRow(rowNum);
            try {
                datas.add(readDataRow(row, dataMode.getDataClass(), filenames,fieldNames));
            } catch (Exception e) {
                System.out.println(e);
            }

        }

        return datas;
    }

    /**
     *
     * @param row
     * @param class1
     * @param filenames
     * @return
     * @throws Exception
     */
    public static <T> T readDataRow(Row row, Class<T> class1, Map<String, String> filenames, String[] fieldNames)
            throws Exception {

        int firstCellNum = row.getFirstCellNum();
        int lastCellNum = row.getPhysicalNumberOfCells();
        T object = class1.newInstance();
        firstCellNum = row.getFirstCellNum();
        lastCellNum = row.getPhysicalNumberOfCells();
        // 循环当前行
        for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
            Cell cell = row.getCell(cellNum);

            String colname = fieldNames[cellNum - firstCellNum];
            if (!filenames.containsKey(colname)) {
                continue;
            }
            PropertyDescriptor descriptor=BeanUtils.getPropertyDescriptor(class1, filenames.get(colname));

            descriptor.getWriteMethod().invoke(object,
                    getCellValue(cell,descriptor.getWriteMethod().getParameterTypes()[0]));
        }

        return object;
    }

    /**
     *
     * @param cell
     *            单元格
     * @param class1
     *            期待类型
     * @return
     */
    public static <T> T getCellValue(Cell cell, Class<T> class1) {

        Object obj = null;
        if (cell == null) {
            return null;
        }

        CellType cellType = cell.getCellTypeEnum();

        switch (cellType) {
            case STRING:
                obj = cell.getStringCellValue();
                break;
            case BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case FORMULA:
                obj = cell.getCellFormula();
                break;
            case NUMERIC:
			/*
			 消除科学计数法以及浮点数
			 */
                DecimalFormat df = new DecimalFormat("0");
                obj = df.format(cell.getNumericCellValue());
                break;
            default:
                break;
        }

        return TypeConvertUtil.convertIfNecessary(obj, class1);
    }

    public static Workbook getWorkbok(InputStream in) throws IOException{
        Workbook wb = null;

        wb = new XSSFWorkbook(in);

        return wb;
    }


}
