package com.tool;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-12-14
 * Time: 19:05
 */
public class AreaStatistics {
    //区域名称
    private String areaName;
    //新增宏站数量
    private  Integer addCountOfOutside;
    //新增室分数量
    private Integer addCountOfInside;
    //新增较昨日
    private Integer addCompareToYesterday;
    //存量宏站数量
    private Integer saveCountOfOutside;
    //存量室分数量
    private Integer saveCountOfInside;
    //存量较昨日
    private Integer saveCompareToYesterday;
    //今日宏站退服总数
    private Integer sumOfOutside;
    //今日室分退服总数
    private Integer sumOfInside;
    //今日退服总数较昨日
    private Integer sumCompareToYesterday;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getAddCountOfOutside() {
        return addCountOfOutside;
    }

    public void setAddCountOfOutside(Integer addCountOfOutside) {
        this.addCountOfOutside = addCountOfOutside;
    }

    public Integer getAddCountOfInside() {
        return addCountOfInside;
    }

    public void setAddCountOfInside(Integer addCountOfInside) {
        this.addCountOfInside = addCountOfInside;
    }

    public Integer getAddCompareToYesterday() {
        return addCompareToYesterday;
    }

    public void setAddCompareToYesterday(Integer addCompareToYesterday) {
        this.addCompareToYesterday = addCompareToYesterday;
    }

    public Integer getSaveCountOfOutside() {
        return saveCountOfOutside;
    }

    public void setSaveCountOfOutside(Integer saveCountOfOutside) {
        this.saveCountOfOutside = saveCountOfOutside;
    }

    public Integer getSaveCountOfInside() {
        return saveCountOfInside;
    }

    public void setSaveCountOfInside(Integer saveCountOfInside) {
        this.saveCountOfInside = saveCountOfInside;
    }

    public Integer getSaveCompareToYesterday() {
        return saveCompareToYesterday;
    }

    public void setSaveCompareToYesterday(Integer saveCompareToYesterday) {
        this.saveCompareToYesterday = saveCompareToYesterday;
    }

    public Integer getSumOfOutside() {
        return sumOfOutside;
    }

    public void setSumOfOutside(Integer sumOfOutside) {
        this.sumOfOutside = sumOfOutside;
    }

    public Integer getSumOfInside() {
        return sumOfInside;
    }

    public void setSumOfInside(Integer sumOfInside) {
        this.sumOfInside = sumOfInside;
    }

    public Integer getSumCompareToYesterday() {
        return sumCompareToYesterday;
    }

    public void setSumCompareToYesterday(Integer sumCompareToYesterday) {
        this.sumCompareToYesterday = sumCompareToYesterday;
    }
}
