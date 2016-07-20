package com.example.upproject;

import java.io.Serializable;

/**
 * Created by myalien on 2016/7/17.
 */
public class UserInfo implements Serializable{
    public static final String APPLIANCES="_appliances";
    public static final String TIME = "time";
    public static final String POWER= "power";
    public static final String STATE = "state";
    public static final String TOLERANCE = "tolerance";

    private String appliance;
    private String time; // 用户id
    private String power;
    private String state;
    private String tolerance;

    public String getdbAppliance() {
        return appliance;
    }

    public void setdbAppliance(String appliance) {
        this.appliance = appliance;
    }

    public String getdbTime() {
        return time;
    }

    public void setdbTime(String time) {
        this.time = time;
    }

    public String getdbPower() {
        return power;
    }

    public void setdbPower(String power) {
        this.power = power;
    }

    public String getdbState() {
        return state;
    }

    public void setdbState(String state) {
        this.state = state;
    }

    public String getdbTolerance() {
        return tolerance;
    }

    public void setdbTolerance(String tolerance) {
        this.tolerance = tolerance;
    }
}
