package com.tool;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

        List<Fdd> fdds = DataImportUtil.importExcel(in,dataMode);
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

        List<BaseStation> baseStations = DataImportUtil.importExcel(in,dataMode);
        System.out.println(baseStations.size());

    }

    @Test
    public void importBaseStation() {
        String path = WordStatistics.class.getResource("FDD.xlsx").getPath();
        System.out.println(path);
    }

    @Test
    public void importFdd1() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startTime = sdf.parse("2018-12-09 00:00:00").getTime();
        long endtime = sdf.parse("2018-12-11 23:59:59").getTime();
        Date date = new Date(startTime);
        System.out.println(date.getTimezoneOffset());
    }

    @Test
    public void importExitCommunity()throws Exception {
        ProcessMain.init();
        List<ExitCommunity> exitCommunities = DataImport.importExitCommunity();
        System.out.println("after filter,size:"+exitCommunities.size());
    }

    @Test
    public void importYesterday()throws Exception {
        ProcessMain.init();
        List<StationOfThreeDay> stationOfThreeDays = DataImport.importYesterday();
    }
}