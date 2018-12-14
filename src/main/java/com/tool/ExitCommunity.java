package com.tool;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2018-12-13
 * Time: 9:34
 */
public class ExitCommunity {
    //小区名称
    private String communityName;
    //退服日期
    private Date date;

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
