package com.dnb.Parser.dto;


import java.io.Serializable;

public class LogResponse implements Serializable {

    //default serialVersion id
    private static final long serialVersionUID = 1L;
    private String user;
    private String website;
    private String activityTypeDescription;
    private String signedInTime;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getActivityTypeDescription() {
        return activityTypeDescription;
    }

    public void setActivityTypeDescription(String activityTypeDescription) {
        this.activityTypeDescription = activityTypeDescription;
    }

    public String getSignedInTime() {
        return signedInTime;
    }

    public void setSignedInTime(String signedInTime) {
        this.signedInTime = signedInTime;
    }

    @Override
    public String toString() {
        return "{" +
                "user:" + user +
                ", website:" + website +
                ", activityTypeDescription:" + activityTypeDescription +
                ", signedInTime:" + signedInTime +
                '}';
    }
}
