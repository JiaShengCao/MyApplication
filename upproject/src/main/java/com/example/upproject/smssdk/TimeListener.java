package com.example.upproject.smssdk;

/**
 * Created by myalien on 2016/8/6.
 */
public interface TimeListener {
    void onLastTimeNotify(int lastSecond);
    void onAbleNotify(boolean valuable);
}
