package com.example.upproject.smssdk;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by myalien on 2016/8/5.
 */
public class SMSManager {

    private SMSManager() {}
    private static final SMSManager instance = new SMSManager();
    public static SMSManager getInstance(){return instance;}
    public static boolean DEBUG = true;
    private CallbackEventHandler mHandler = new CallbackEventHandler();
    public static final String DEFAULT_APPKEY = "f259d98c6a74";
    public static final String DEFAULT_APPSECRET = "e16b4b62e67ee992f4806ab1c531c68a";
    private boolean inited = false;
    public static int DEFAULT_DELAY = 60;
    private int last = 0;
    private Timer timer;
    public ArrayList<TimeListener> timeList = new ArrayList<>();

    public void registerTimeListener(TimeListener listener){
        timeList.add(listener);
        listener.onLastTimeNotify(last);
        listener.onAbleNotify(last==0);
    }

    public void unRegisterTimeListener(TimeListener listener){
        timeList.remove(listener);
    }

    private void notifyLastTime(){
        for (TimeListener listener:timeList){
            final TimeListener finalListener = listener;
            try {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        finalListener.onLastTimeNotify(last);
                    }
                });
            }catch (Exception e){
                unRegisterTimeListener(listener);
            }
        }
    }

    private void notifyEnable(){
        for (TimeListener listener:timeList){
            final TimeListener finalListener = listener;
            try {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        finalListener.onAbleNotify(last == 0);
                    }
                });
            }catch (Exception e){
                unRegisterTimeListener(listener);
            }
        }
    }

    private void startTimer(){
        timer = new Timer();
        notifyEnable();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                last -= 1;
                notifyLastTime();
                if (last == 0) {
                    notifyEnable();
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }

    private void init(Context ctx){
        ApplicationInfo appInfo = null;
        try {
            appInfo = ctx.getPackageManager()
                    .getApplicationInfo(ctx.getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String appKey ;
        String appSecret ;
        if (appInfo.metaData == null||appInfo.metaData.getString("SMS_MOB_APPKEY") == null||appInfo.metaData.getString("SMS_MOB_APPSECRET") == null){
            appKey = DEFAULT_APPKEY;
            appSecret = DEFAULT_APPSECRET;
        }else{
            appKey = appInfo.metaData.getString("SMS_MOB_APPKEY").trim();
            appSecret = appInfo.metaData.getString("SMS_MOB_APPSECRET").trim();
        }
        log("appkey:"+appKey+"  appsecret:"+appSecret);
        SMSSDK.initSDK(ctx, appKey, appSecret);
        inited = true;
        SMSSDK.registerEventHandler(mHandler);
    }

    public boolean sendMessage(Context ctx,String country,String number){
        if (!inited){
            init(ctx);
        }
        if (last==0) {
            SMSSDK.getVerificationCode(country, number);
            last = DEFAULT_DELAY;
            startTimer();
            return true;
        }else{
            return false;
        }
    }

    public void verifyCode(Context ctx, String country, String number, String code, final CallBack callback){
        if (!inited){
            init(ctx);
        }
        mHandler.setCallback(callback);
        SMSSDK.submitVerificationCode(country, number, code);
    }


    private class CallbackEventHandler extends EventHandler {
        CallBack mCallback;
        Handler handler = new Handler();
        public void setCallback(CallBack mCallback) {
            this.mCallback = mCallback;
        }

        @Override
        public void afterEvent(int event, int result, final Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                log("回调完成");
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    log("提交验证码成功");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mCallback!=null)
                                mCallback.success();
                            mCallback = null;
                        }
                    });
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    log("获取验证码成功");
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    log("返回支持发送验证码的国家列表");
                }
            } else {
                log("Error:" + data.toString());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCallback!=null)
                            mCallback.error((Throwable) data);
                        mCallback = null;
                    }
                });
            }
        }
    }

    private void log(String content){
        if (DEBUG) Log.i("SMSSDK", content);
    }
}
