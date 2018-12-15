package com.tool;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-12-13
 * Time: 9:39
 */
public class DataImport {
    //读取维护质量资料LTE
    public static List<BaseStation> importBaseStation(){
        FileInputStream in = null;
        try {
            File file = new File(Constants.baseStationFilePath);
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
        System.out.println("导入基站数量："+baseStations.size());
        return baseStations;
    }

    //读取FDD
    public static List<Fdd> importFdd(){
        FileInputStream in = null;
        try {
            File file = new File(Constants.fddFilePath);
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
        map.put("覆盖类型","overrideType");

        dataMode.setColumnandFieldNameMap(map);
        dataMode.setDataClass(Fdd.class);

        List<Fdd> fdds = DataImportUtil.importExcel(in,dataMode);
        System.out.println("导入fdd基站数量："+fdds.size());
        return fdds;
    }

    //读取退服记录
    public static List<ExitCommunity> importExitCommunity()throws Exception{
        FileInputStream in = null;
        try {
            File file = new File(Constants.exitCommunityFilePath);
            in = new FileInputStream(file);
        }catch (Exception e){
            e.printStackTrace();
        }

        DataMode<ExitCommunity> dataMode = new DataMode<>();
        //此处使用LinkedHashMap保持插入顺序与存取顺序一致
        Map<String, String> map = new LinkedHashMap<>();
        map.put("NE_LABEL","communityName");
        map.put("EVENT_TIME","date");


        dataMode.setColumnandFieldNameMap(map);
        dataMode.setDataClass(ExitCommunity.class);

        List<ExitCommunity> exitCommunities = DataImportUtil.importExcel(in,dataMode);
        System.out.println("导入退出小区记录数量："+exitCommunities.size());
        exitCommunities = filterExitCommunity(exitCommunities);
        return exitCommunities;
    }

    /**
     * 根据日期过滤退服记录,例：2018-12-09 00：00：00 -- 2018-12-11 23：59：59 之外的记录都被过滤
     * @param exitCommunities
     * @return 返回之后记录的日期都被格式化为天，去除零头
     * @throws Exception
     */
    public static List<ExitCommunity> filterExitCommunity(List<ExitCommunity> exitCommunities)throws Exception{
       String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

       Date dateBegin = sdf.parse(Constants.firstDay);
       long beginTime = dateBegin.getTime();//00:00:00
       long endTime = beginTime+3*24*3600*1000L-1L;//23:59:59

        Iterator<ExitCommunity> iterator = exitCommunities.iterator();
        while(iterator.hasNext()){
            ExitCommunity exitCommunity = iterator.next();
            if(exitCommunity.getDate().getTime()<beginTime || exitCommunity.getDate().getTime()>endTime){
               iterator.remove();
            }
        }

        return exitCommunities;
    }

    public static List<StationOfThreeDay> importYesterday(){
        FileInputStream in = null;
        try {
            File file = new File(Constants.yesterdayThreeDayFilePath);
            in = new FileInputStream(file);
        }catch (Exception e){
            e.printStackTrace();
        }

        DataMode<StationOfThreeDay> dataMode = new DataMode<>();
        //此处使用LinkedHashMap保持插入顺序与存取顺序一致
        Map<String, String> map = new LinkedHashMap<>();
        map.put("站名","stationName");//必须与表头一致
        map.put("firstday","countFirstDay");
        map.put("secondday","countSecondDay");
        map.put("thirdday","countThirdDay");
        map.put("总计","countSum");
        map.put("厂家","company");
        map.put("区域","area");
        map.put("覆盖类型","overrideType");
        map.put("今日新增","isAdd");
        map.put("连续退服天数","continueDays");

        dataMode.setColumnandFieldNameMap(map);
        dataMode.setDataClass(StationOfThreeDay.class);

        List<StationOfThreeDay> stationOfThreeDays = DataImportUtil.importExcel(in,dataMode);
        System.out.println("导入数量："+stationOfThreeDays.size());
        System.out.println("list:"+stationOfThreeDays);
        return stationOfThreeDays;
    }
}
