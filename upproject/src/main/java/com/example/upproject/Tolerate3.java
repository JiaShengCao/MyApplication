package com.example.upproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by cjs on 2016/5/18.
 */
public class Tolerate3 extends Activity{
    private RadioGroup group;
    private String sendmsg;
    private String replay = null;
    private RadioButton tolerent1,tolerent2,tolerent3,tolerent4,tolerent5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        group.setOnCheckedChangeListener(radiogpchange);

    }
    private RadioGroup.OnCheckedChangeListener radiogpchange=new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.tolerate1:
                    sendmsg="4 washer 02 1";
                    ConnectServerWithSocket text = new ConnectServerWithSocket();
                    text.setStr(sendmsg);
                    text.start();
                    try {
                        text.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    replay = text.getReplayfromserver();
                    break;
                case R.id.tolerate2:
                    sendmsg="4 washer 02 2";
                    ConnectServerWithSocket text2 = new ConnectServerWithSocket();
                    text2.setStr(sendmsg);
                    text2.start();
                    try {
                        text2.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    replay = text2.getReplayfromserver();
                    break;
                case R.id.tolerate3:
                    sendmsg="4 washer 02 3";
                    ConnectServerWithSocket text3 = new ConnectServerWithSocket();
                    text3.setStr(sendmsg);
                    text3.start();
                    try {
                        text3.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    replay = text3.getReplayfromserver();
                    break;
                case R.id.tolerate4:
                    sendmsg="4 washer 02 4";
                    ConnectServerWithSocket text4 = new ConnectServerWithSocket();
                    text4.setStr(sendmsg);
                    text4.start();
                    try {
                        text4.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    replay = text4.getReplayfromserver();
                    break;
                case R.id.tolerate5:
                    sendmsg="4 washer 02 5";
                    ConnectServerWithSocket text5 = new ConnectServerWithSocket();
                    text5.setStr(sendmsg);
                    text5.start();
                    try {
                        text5.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    replay = text5.getReplayfromserver();
                    break;
            }
        }
    };


    private void init() {
        Window window = getWindow();

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_STATUS);

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.tolerate);
        group= (RadioGroup) findViewById(R.id.group);
        tolerent1= (RadioButton) findViewById(R.id.tolerate1);
        tolerent2= (RadioButton) findViewById(R.id.tolerate2);
        tolerent3= (RadioButton) findViewById(R.id.tolerate3);
        tolerent4= (RadioButton) findViewById(R.id.tolerate4);
        tolerent5= (RadioButton) findViewById(R.id.tolerate5);
    }
}
