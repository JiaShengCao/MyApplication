package com.example.upproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cjs on 2016/3/13.
 */
public class FirstListItem extends Activity {
    private ToggleButton toggleButton;
    private TextView state;
    private EditText newpower;
    private Button change;
    private TextView tv_power;
    private TextView tv_worktime;
    private EditText et_worktime;
    private Button btn_change2;
    private String power=null;
    private String sendmsg;
    private String replay = null;
    private String newtime;
    private Button refresh;
    private Button more;
    private String refreach_power="0";//刷新后的功率
    private String refreach_rongren;//刷新后的容忍度
    private String refreach_state;//刷新后的时间
    private String refreach_time;//刷新后的时间
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstitem);
        change = (Button) findViewById(R.id.change);
        refresh= (Button) findViewById(R.id.refresh);
        init();
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstListItem.this,Tolerate.class);
                startActivity(intent);
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmsg="4 air 20 ";
                ConnectServerWithSocket text1 = new ConnectServerWithSocket();
                text1.setStr(sendmsg);
                text1.start();
                try {
                    text1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                replay = text1.getReplayfromserver();
                if(replay!=null)
                {
                    refreach_power=replay.substring(0,1);//刷新得到的功率
                    refreach_time=replay.substring(3,4);//刷新后的单次运行时间
                    refreach_rongren=replay.substring(6,7);//刷新后的容忍度
                    refreach_time=replay.substring(9,10);//刷新后的时间

                    Toast.makeText(FirstListItem.this, "目前的功率为: " + refreach_power + "/n目前的单次运行时间为： " +
                            refreach_time + "/n目前的容忍度为： " + refreach_rongren, Toast.LENGTH_SHORT).show();

                }


                Message message = handler.obtainMessage();
                message.what = 5;
                handler.sendMessage(message);
            }
        });

        //发送改变功率的事件
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_power = (TextView) findViewById(R.id.gonglv);
                power = newpower.getText().toString();

                sendmsg="4 air 00 "+power;
                ConnectServerWithSocket text1 = new ConnectServerWithSocket();
                text1.setStr(sendmsg);
                text1.start();
                try {
                    text1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                replay = text1.getReplayfromserver();


                Message message = handler.obtainMessage();
                message.what = 3;
                handler.sendMessage(message);
            }
        });
        state = (TextView) findViewById(R.id.state);
        //是否开启了用电器
        toggleButton = (ToggleButton) findViewById(R.id.togglebutton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sendmsg="4 air 11";
                    ConnectServerWithSocket text = new ConnectServerWithSocket();
                    text.setStr(sendmsg);
                    text.start();
                    try {
                        text.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    replay = text.getReplayfromserver();
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    handler.sendMessage(message);
                } else {
                    sendmsg="4 air 10";
                    ConnectServerWithSocket text = new ConnectServerWithSocket();
                    text.setStr(sendmsg);
                    text.start();
                    try {
                        text.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    replay = text.getReplayfromserver();
                    replay= (String) replay.subSequence(0,1);
                    Message message2 = handler.obtainMessage();
                    message2.what = 2;
                    handler.sendMessage(message2);
                }
            }
        });
    }

    private void init() {
        Window window = getWindow();

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_STATUS);

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_NAVIGATION);
        newpower = (EditText) findViewById(R.id.newpower);

        //设置用电器单次工作时间
        //rongrendu= (TextView) findViewById(R.id.rongren);
        tv_worktime= (TextView) findViewById(R.id.tv_worktime);
        et_worktime= (EditText) findViewById(R.id.et_worktime);
        btn_change2= (Button) findViewById(R.id.changge2);
        more= (Button) findViewById(R.id.more);
        btn_change2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newtime=et_worktime.getText().toString();

                sendmsg="4 air 01 "+newtime;
                ConnectServerWithSocket text1 = new ConnectServerWithSocket();
                text1.setStr(sendmsg);
                text1.start();
                try {
                    text1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message message=new Message();
                message.what=4;
                handler.sendMessage(message);
            }
        });
        //改变用电器功率

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            SharedPreferences read=getSharedPreferences("DATA_TABLE",MODE_WORLD_READABLE);
            String value;
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    value=read.getString("STATE1","");
                    if (value.equals("1")){
                    state.setText("运行状态：开启");}
                    break;
                case 2:
                    state.setText("运行状态：关闭");
                    break;
                case 3:
                    value=read.getString("POWER1","");
                    tv_power.setText("功率大小：" + value + "kw");//改动部分
                    break;
                case 4:
                    value=read.getString("TIME1","");
                    tv_worktime.setText("单次运行时间："+value+"小时");
                    break;
                case 5:
                    String value1=read.getString("POWER1","");
                    String value2=read.getString("TIME1","");
                    String value3=read.getString("STATE1","");
                    String value4=read.getString("RONGREN1","");//得到容忍度，还没进行设置
                    tv_power.setText("功率大小：" + value1 + "kw");
                    tv_worktime.setText("单次运行时间："+value2+"小时");
                    break;
            }
        }
    };
}

