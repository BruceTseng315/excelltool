package com.tool;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-12-12
 * Time: 20:37
 */
public class DataExport {
    public static void writeThreeDayExcell(Map<String,StationOfThreeDay> stationOfThreeDayMap){
        List<StationOfThreeDay> stationOfThreeDays = new ArrayList<>();
        Iterator<Map.Entry<String,StationOfThreeDay>> iterator = stationOfThreeDayMap.entrySet().iterator();
        while(iterator.hasNext()){
            stationOfThreeDays.add(iterator.next().getValue());
        }

        DataMode<StationOfThreeDay> dataMode = new DataMode<>();
        Map<String, String> map = new LinkedHashMap<>();
        map.put("站名", "stationName");
        map.put("firstDay", "countFirstDay");
        map.put("secondDay", "countSecondDay");
        map.put("thirdDay", "countThirdDay");
        map.put("总计", "countSum");
        map.put("厂家","company");
        map.put("区域","area");
        map.put("覆盖类型","overrideType");
        map.put("今日新增","isAdd");
        map.put("连续退服天数","continueDays");
        dataMode.setColumnandFieldNameMap(map);

        File file = new File(Constants.threeDayExcellPath);
        OutputStream ot = null;
        try {
            ot = new FileOutputStream(file);
        }catch (Exception e){
            e.printStackTrace();
        }
        DataExportUtil.exportExcel(ot,dataMode,stationOfThreeDays);
    }
}
