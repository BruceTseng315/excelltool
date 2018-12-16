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
    private  int addCountOfOutside;
    //新增室分数量
    private int addCountOfInside;
    //新增较昨日
    private int addCompareToYesterday;
    //存量宏站数量
    private int saveCountOfOutside;
    //存量室分数量
    private int saveCountOfInside;
    //存量较昨日
    private int saveCompareToYesterday;
    //今日宏站退服总数
    private int sumOfExitOutside;
    //今日室分退服总数
    private int sumOfEixtInside;
    //今日退服总数较昨日
    private int sumCompareToYesterday;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getAddCountOfOutside() {
        return addCountOfOutside;
    }

    public void setAddCountOfOutside(int addCountOfOutside) {
        this.addCountOfOutside = addCountOfOutside;
    }

    public int getAddCountOfInside() {
        return addCountOfInside;
    }

    public void setAddCountOfInside(int addCountOfInside) {
        this.addCountOfInside = addCountOfInside;
    }

    public int getAddCompareToYesterday() {
        return addCompareToYesterday;
    }

    public void setAddCompareToYesterday(int addCompareToYesterday) {
        this.addCompareToYesterday = addCompareToYesterday;
    }

    public int getSaveCountOfOutside() {
        return saveCountOfOutside;
    }

    public void setSaveCountOfOutside(int saveCountOfOutside) {
        this.saveCountOfOutside = saveCountOfOutside;
    }

    public int getSaveCountOfInside() {
        return saveCountOfInside;
    }

    public void setSaveCountOfInside(int saveCountOfInside) {
        this.saveCountOfInside = saveCountOfInside;
    }

    public int getSaveCompareToYesterday() {
        return saveCompareToYesterday;
    }

    public void setSaveCompareToYesterday(int saveCompareToYesterday) {
        this.saveCompareToYesterday = saveCompareToYesterday;
    }

    public int getSumOfExitOutside() {
        return sumOfExitOutside;
    }

    public void setSumOfExitOutside(int sumOfExitOutside) {
        this.sumOfExitOutside = sumOfExitOutside;
    }

    public int getSumOfEixtInside() {
        return sumOfEixtInside;
    }

    public void setSumOfEixtInside(int sumOfEixtInside) {
        this.sumOfEixtInside = sumOfEixtInside;
    }

    public int getSumCompareToYesterday() {
        return sumCompareToYesterday;
    }

    public void setSumCompareToYesterday(int sumCompareToYesterday) {
        this.sumCompareToYesterday = sumCompareToYesterday;
    }

    public void incAddCountOfOutside(){
        this.addCountOfOutside++;
    }
    public void incAddCountOfInside(){
        this.addCountOfInside++;
    }
    public void incSaveCountOfOutside(){
        this.saveCountOfOutside++;
    }
    public void incSaveCountOfInside(){
        this.saveCountOfInside++;
    }
}
