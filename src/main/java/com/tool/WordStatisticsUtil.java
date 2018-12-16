package com.tool;

import com.tool.enums.AddEnum;
import com.tool.enums.OverrideEnum;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-12-15
 * Time: 12:56
 */
public class WordStatisticsUtil {

    public static Map<String,AreaStatistics>   areaStatistics(Map<String,StationOfThreeDay> stationOfThreeDays){
        Map<String,AreaStatistics> areaStatisticsMap = new HashMap<>();
        Iterator<Map.Entry<String,StationOfThreeDay>> iterator = stationOfThreeDays.entrySet().iterator();
        //今日新增
        while(iterator.hasNext()){
            Map.Entry<String,StationOfThreeDay> entry = iterator.next();
            StationOfThreeDay stationOfThreeDay = entry.getValue();
            String area = stationOfThreeDay.getArea();
            if(area == null){
                continue;
            }
            AreaStatistics areaStatistics = areaStatisticsMap.get(area);
            if(areaStatistics == null){
                areaStatistics = new AreaStatistics();
            }
            String overridrType = stationOfThreeDay.getOverrideType();
            String add = stationOfThreeDay.getIsAdd();
            if(overridrType.equals(OverrideEnum.inside.getType())){
                if(add.equals(AddEnum.add.getType())){
                    areaStatistics.incAddCountOfInside();//新增室分站点数+1
                }else {
                    areaStatistics.incSaveCountOfInside();//新增宏站站点数+1
                }

                //add室分退服总数
                areaStatistics.setSumOfEixtInside(areaStatistics.getSumOfEixtInside()+stationOfThreeDay.getCountSum());
            }else{
                if(add.equals(AddEnum.add.getType())){
                    areaStatistics.incAddCountOfOutside();//存量室分站点数+1
                }else {
                    areaStatistics.incSaveCountOfOutside();//存量室分站点数+1
                }
                //add宏站退服总数
                areaStatistics.setSumOfExitOutside(areaStatistics.getSumOfExitOutside()+stationOfThreeDay.getCountSum());
            }
            areaStatisticsMap.put(area,areaStatistics);
        }

        return areaStatisticsMap;
    }
}
