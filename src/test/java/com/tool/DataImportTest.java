package com.tool;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-12-12
 * Time: 17:47
 */
public class DataImportTest {

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @Test
    public void importFdd(){

        String path = "E:\\excell\\输入\\FDD.xlsx";
        FileInputStream in = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
        }catch (Exception e){
            e.printStackTrace();
        }

        DataMode<Fdd> dataMode = new DataMode<>();
        //此处使用LinkedHashMap保持插入顺序与存取顺序一致
        Map<String, String> map = new LinkedHashMap<>();
        map.put("小区中文名称","communityName");
        map.put("基站名称","stationName");
        map.put("区县","area");
        map.put("厂家","company");

        dataMode.setColumnandFieldNameMap(map);
        dataMode.setDataClass(Fdd.class);

        List<Fdd> fdds = DataImport.importExcel(in,dataMode);
        System.out.println(fdds.size());
    }
    @org.junit.Test
    public void importStationExcel() {

        String path = "E:\\excell\\输入\\维护质量资料LTE基站.xlsx";
        FileInputStream in = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
        }catch (Exception e){
            e.printStackTrace();
        }

        DataMode<BaseStation> dataMode = new DataMode<BaseStation>();
        //此处使用LinkedHashMap保持插入顺序与存取顺序一致
        Map<String, String> map = new LinkedHashMap<>();
        map.put("小区中文名称","communityName");
        map.put("基站名称","stationName");
        map.put("区县","area");
        map.put("厂家","company");

        dataMode.setColumnandFieldNameMap(map);
        dataMode.setDataClass(BaseStation.class);

        List<BaseStation> baseStations = DataImport.importExcel(in,dataMode);
        System.out.println(baseStations.size());

    }
}