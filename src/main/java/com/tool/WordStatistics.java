package com.tool;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-12-14
 * Time: 18:45
 */
public class WordStatistics {
    //今日新增
    private Integer newAddToday;
    //今日新增较昨日增加
    private Integer addCompareToYesterday;
    //存量未解决
    private Integer toBeSolved;
    //存量较昨日增加
    private Integer solveCompareToYesterday;
    //宏站
    private Integer countOut;
    //室内
    private Integer countInside;
    //高频站点较昨日新增
    private Integer addStation;
    //高频次数
    private Integer totalCount;
    //平均每站退服
    private Integer average;
    //新增站点最多的区域名单
    private String topAddAreaList;
    //存量站点最多的区域名单
    private String topSaveAreaList;
    //高频基站数目最多的区域名单
    private String topStationAreaList;
    //退服次数最多的区域名单
    private String topOutStationAreaList;

    public Integer getNewAddToday() {
        return newAddToday;
    }

    public void setNewAddToday(Integer newAddToday) {
        this.newAddToday = newAddToday;
    }

    public Integer getAddCompareToYesterday() {
        return addCompareToYesterday;
    }

    public void setAddCompareToYesterday(Integer addCompareToYesterday) {
        this.addCompareToYesterday = addCompareToYesterday;
    }

    public Integer getToBeSolved() {
        return toBeSolved;
    }

    public void setToBeSolved(Integer toBeSolved) {
        this.toBeSolved = toBeSolved;
    }

    public Integer getSolveCompareToYesterday() {
        return solveCompareToYesterday;
    }

    public void setSolveCompareToYesterday(Integer solveCompareToYesterday) {
        this.solveCompareToYesterday = solveCompareToYesterday;
    }

    public Integer getCountOut() {
        return countOut;
    }

    public void setCountOut(Integer countOut) {
        this.countOut = countOut;
    }

    public Integer getCountInside() {
        return countInside;
    }

    public void setCountInside(Integer countInside) {
        this.countInside = countInside;
    }

    public Integer getAddStation() {
        return addStation;
    }

    public void setAddStation(Integer addStation) {
        this.addStation = addStation;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getAverage() {
        return average;
    }

    public void setAverage(Integer average) {
        this.average = average;
    }

    public String getTopAddAreaList() {
        return topAddAreaList;
    }

    public void setTopAddAreaList(String topAddAreaList) {
        this.topAddAreaList = topAddAreaList;
    }

    public String getTopSaveAreaList() {
        return topSaveAreaList;
    }

    public void setTopSaveAreaList(String topSaveAreaList) {
        this.topSaveAreaList = topSaveAreaList;
    }

    public String getTopStationAreaList() {
        return topStationAreaList;
    }

    public void setTopStationAreaList(String topStationAreaList) {
        this.topStationAreaList = topStationAreaList;
    }

    public String getTopOutStationAreaList() {
        return topOutStationAreaList;
    }

    public void setTopOutStationAreaList(String topOutStationAreaList) {
        this.topOutStationAreaList = topOutStationAreaList;
    }
}
