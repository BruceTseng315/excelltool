package com.tool;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-12-14
 * Time: 17:02
 */
public class ExportWordUtilTest {
    Map<String,StationOfThreeDay> stationOfThreeDayMap;
    @Before
    public void init(){

        try {
            //初始化，包括起始日期，文件路径
            ProcessMain.init();
            System.out.println(Constants.firstDay);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("day init error");
            return;
        }
        stationOfThreeDayMap = ProcessMain.getThreeDayForOutPut();
    }

    @Test
    public void exportEordTest()throws Exception {
        Map<String,AreaStatistics> areaStatisticsMap = WordStatisticsUtil.areaStatistics(stationOfThreeDayMap);
        ExportWordUtil.exportEordTest(areaStatisticsMap);

    }
}