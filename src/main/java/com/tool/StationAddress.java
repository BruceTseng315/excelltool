package com.tool;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-12-13
 * Time: 20:38
 */
public class StationAddress {
    private String stationName;
    private String area;
    private String company;
    private String overrideType;

    public String getOverrideType() {
        return overrideType;
    }

    public void setOverrideType(String overrideType) {
        this.overrideType = overrideType;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "StationAddress{" +
                "stationName='" + stationName + '\'' +
                ", area='" + area + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
