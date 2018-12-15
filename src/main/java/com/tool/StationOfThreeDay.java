package com.tool;

import com.tool.enums.AddEnum;
import com.tool.enums.OverrideEnum;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-12-13
 * Time: 9:27
 */
public class StationOfThreeDay {
    //基站名称
    private String stationName;
    //第一天退服数
    private Integer countFirstDay = 0;
    //第二天退服数
    private Integer countSecondDay = 0;
    //第三天退服数
    private Integer countThirdDay = 0;
    //三天退服数之和
    private Integer countSum = 0;
    //基站厂家
    private String company;
    //基站所在区域
    private String area;
    //覆盖类型
    private String overrideType;
    //新增
    private String isAdd;
    //连续退服天数
    private Integer continueDays;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Integer getCountFirstDay() {
        return countFirstDay;
    }

    public void setCountFirstDay(Integer countFirstDay) {
        this.countFirstDay = countFirstDay;
    }

    public Integer getCountSecondDay() {
        return countSecondDay;
    }

    public void setCountSecondDay(Integer countSecondDay) {
        this.countSecondDay = countSecondDay;
    }

    public Integer getCountThirdDay() {
        return countThirdDay;
    }

    public void setCountThirdDay(Integer countThirdDay) {
        this.countThirdDay = countThirdDay;
    }

    public Integer getCountSum() {
        return countSum;
    }

    public void setCountSum(Integer countSum) {
        this.countSum = countSum;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOverrideType() {
        return overrideType;
    }

    public void setOverrideType(String overrideType) {
        this.overrideType = overrideType;
    }

    public String getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(String isAdd) {
        this.isAdd = isAdd;
    }

    public Integer getContinueDays() {
        return continueDays;
    }

    public void setContinueDays(Integer continueDays) {
        this.continueDays = continueDays;
    }

    public void addFirstDay(){
        this.countFirstDay++;
        this.countSum++;
    }

    public void addSecondDay(){
        this.countSecondDay++;
        this.countSum++;
    }
    public void addThirdDay(){
        this.countThirdDay++;
        this.countSum++;
    }

    @Override
    public String toString() {
        return "StationOfThreeDay{" +
                "stationName='" + stationName + '\'' +
                ", countFirstDay=" + countFirstDay +
                ", countSecondDay=" + countSecondDay +
                ", countThirdDay=" + countThirdDay +
                ", countSum=" + countSum +
                ", company='" + company + '\'' +
                ", area='" + area + '\'' +
                ", overrideType='" + overrideType + '\'' +
                ", isAdd=" + isAdd +
                ", continueDays=" + continueDays +
                '}';
    }
}
