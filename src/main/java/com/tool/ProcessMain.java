package com.tool;

import org.springframework.beans.BeanUtils;
import sun.util.locale.provider.DateFormatProviderImpl;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-12-12
 * Time: 17:46
 */
public class ProcessMain {
    public static void main(String[] args) {
        try {
            //初始化，包括起始日期，文件路径
            ProcessMain.init();
            System.out.println(Constants.firstDay);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("day init error");
            return;
        }
        //读取退服记录
        List<ExitCommunity> exitCommunities = null;
        try {
            exitCommunities = DataImport.importExitCommunity();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("读取退服记录出错");
            return;
        }
        //读取fdd 基站信息
        List<Fdd> fdds = DataImport.importFdd();
        //fdd放入map，小区名为key,基站为value，用于查找小区对应的基站
        Map<String,String> fddMap = new HashMap<>();
        //fdd和baseStation放入map，基站名为key，用来查找基站对应的厂家、区域信息
        Map<String,StationAddress> stationAddressMap = new HashMap<>();
      for(Fdd fdd:fdds){
          fddMap.put(fdd.getCommunityName(),fdd.getStationName());
          //将fdd基站信息放入stationAddressMap
          StationAddress stationAddress = new StationAddress();
          BeanUtils.copyProperties(fdd,stationAddress);
          stationAddressMap.put(fdd.getStationName(),stationAddress);
      }

      List<BaseStation> baseStations = DataImport.importBaseStation();
        //LTE放入map，小区名为key,基站为value，用于查找小区对应的基站
      Map<String,String> baseStationMap = new HashMap<>();
      for(BaseStation baseStation:baseStations){
          baseStationMap.put(baseStation.getCommunityName(),baseStation.getStationName());
          //将LTE基站信息放入stationAddressMap
          StationAddress stationAddress = new StationAddress();
          BeanUtils.copyProperties(baseStation,stationAddress);
          stationAddressMap.put(baseStation.getStationName(),stationAddress);
      }

      //将退服记录中小区名替换成基站名
      ProcessMain.changeCommunityNameByStationName(exitCommunities,baseStationMap,fddMap);
      //统计每个基站的三天退服记录，stationOfThreeDayMap的key为基站，value为统计记录
      Map<String,StationOfThreeDay> stationOfThreeDayMap = ProcessMain.statisticsExitCommunity(exitCommunities,stationAddressMap);
      //过滤掉非连续三天出现退服记录的基站
      stationOfThreeDayMap = ProcessMain.filterStationOfThreeDay(stationOfThreeDayMap);
      System.out.println("map size:"+stationOfThreeDayMap.size());
      //输出excell
      DataExport.writeThreeDayExcell(stationOfThreeDayMap);

    }
    public static Map<String,StationOfThreeDay> statisticsExitCommunity(List<ExitCommunity> exitCommunities,Map<String,StationAddress> stationAddressMap){
        //key 为基站名，value为基站连续三天的统计记录
        Map<String,StationOfThreeDay> stationOfThreeDayMap = new HashMap<>();
        for(ExitCommunity exitCommunity:exitCommunities){
            String communityName = exitCommunity.getCommunityName();
            StationOfThreeDay stationOfThreeDay = stationOfThreeDayMap.get(communityName);
            if(stationOfThreeDay == null){
                stationOfThreeDay = new StationOfThreeDay();
                stationOfThreeDay.setStationName(communityName);
                if(communityName.contains("W")) {
                    stationOfThreeDay.setOverrideType("室分");
                }else{
                    stationOfThreeDay.setOverrideType("宏站");
                }
                if(stationAddressMap.get(communityName)!=null) {
                    stationOfThreeDay.setArea(stationAddressMap.get(communityName).getArea());
                    stationOfThreeDay.setCompany(stationAddressMap.get(communityName).getCompany());
                    System.out.println("area:"+stationOfThreeDay.getArea()+"  comn:"+stationOfThreeDay.getCompany());
                }
                stationOfThreeDayMap.put(communityName,stationOfThreeDay);
            }

            Date date = exitCommunity.getDate();


            if(date.getTime() == Constants.firstDayTime){
                stationOfThreeDay.addFirstDay();

            }else if(date.getTime() == Constants.firstDayTime+24*3600*1000L){
                stationOfThreeDay.addSecondDay();

            }else if(date.getTime() == Constants.firstDayTime+48*3600*1000L){
                stationOfThreeDay.addThirdDay();
                // System.out.println(date);
            }
        }

        return stationOfThreeDayMap;
    }

    /**
     * 过滤非连续三天出现退服记录的基站
     * @param stationOfThreeDayMap
     * @return
     */
    public static Map<String,StationOfThreeDay> filterStationOfThreeDay(Map<String,StationOfThreeDay> stationOfThreeDayMap){
        Iterator<Map.Entry<String,StationOfThreeDay>> iterator = stationOfThreeDayMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,StationOfThreeDay> entry = iterator.next();
            String communityName = entry.getKey();
            StationOfThreeDay stationOfThreeDay = entry.getValue();
            if(!(stationOfThreeDay.getCountFirstDay()>0 && stationOfThreeDay.getCountSecondDay()>0 && stationOfThreeDay.getCountThirdDay()>0)){
                iterator.remove();
            }
        }
        return stationOfThreeDayMap;
    }

    /**
     * init do first
     * @throws Exception
     */
    public static void init()throws Exception{

        Constants.firstDay = "2018-12-09";
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(Constants.firstDay);
        Constants.firstDayTime = date.getTime();

        String exitCommunityFilePath = "E:\\excell\\输入\\20181212.xlsx";
        String baseStationFilePath = "E:\\excell\\输入\\维护质量资料LTE基站.xlsx";
        String fddFilePath = "E:\\excell\\输入\\FDD.xlsx";
        Constants.exitCommunityFilePath = exitCommunityFilePath;
        Constants.baseStationFilePath = baseStationFilePath;
        Constants.fddFilePath = fddFilePath;
    }

    public static void changeCommunityNameByStationName(List<ExitCommunity> exitCommunities,Map<String,String> baseStationMap,Map<String,String> fddMap){
        int exCount = 0;
        //小区名替换为基站名
        for(ExitCommunity exitCommunity:exitCommunities){
            String communityName = exitCommunity.getCommunityName();
            if(communityName == null || communityName.length()<1){
                System.out.println("小区名为空，跳过此小区");
                continue;
            }
            if((communityName.contains("-ZL") || communityName.contains("-HL"))&&communityName.contains("-E")){
                System.out.println("warn:"+communityName+" contain both (-ZL/-HL) and -E");
            }else if(communityName.contains("-ZL") || communityName.contains("-HL")){
                String stationName = baseStationMap.get(communityName);
                if(stationName!=null && stationName.length()<4){
                    System.out.println("!!"+communityName+"  "+stationName);
                }
                if(stationName == null){
                    stationName = baseStationMap.get(communityName.substring(0, communityName.length() - 2));

                    if(stationName == null) {
                        int flag = 0;
                        exCount++;
                        exitCommunity.setCommunityName(communityName + "N/A");
                        Set<Map.Entry<String, String>> set = baseStationMap.entrySet();
                        Iterator<Map.Entry<String, String>> iterator = set.iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> entry = iterator.next();
                            if (baseStationMap.get(communityName) == null && communityName.contains(entry.getValue())&&!entry.getKey().contains("-E")) {
                                System.out.println("%%"+communityName);
                                exitCommunity.setCommunityName(entry.getValue());
                                System.out.println("**"+entry.getValue()+":"+entry.getKey());
                                exCount--;
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 0) {
                            System.out.println("warn:" + communityName + " has no match station in LTE");
                        }
                    }
                }else{
                    exitCommunity.setCommunityName(stationName);
                }
            }else if(communityName.contains("-E")){
                String stationName  = fddMap.get(communityName);
                if(stationName!=null && stationName.length()<4){
                    System.out.println("!!"+communityName+"  "+stationName);
                }
                if(stationName == null){
                    stationName = fddMap.get(communityName.substring(0, communityName.length() - 2));

                    if(stationName == null) {
                        int flag = 0;
                        exCount++;
                        //  System.out.println("warn:" + communityName + " has no match station in fdd,it may be station name");
                        exitCommunity.setCommunityName(communityName );
                        Set<Map.Entry<String, String>> set = fddMap.entrySet();
                        Iterator<Map.Entry<String, String>> iterator = set.iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> entry = iterator.next();
                            if (fddMap.get(communityName) == null && communityName.contains(entry.getValue())) {
                                exitCommunity.setCommunityName(entry.getValue());
                                exCount--;
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 0) {
                            System.out.println("warn:" + communityName + " has no match station in fdd,it may be station name");
                        }
                    }
                }else{
                    exitCommunity.setCommunityName(stationName);
                }
            }
        }
        System.out.println("excount:"+exCount);
    }
}
