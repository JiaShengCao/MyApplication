package com.example.upproject;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ToggleButton;

/**
 * Created by cjs on 2016/3/13.
 */
public class ListIitem5 extends Activity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listitem5);
        init();
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListIitem5.this,Tolerate5.class);
                startActivity(intent);
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmsg="4 dish 20 ";
                ConnectServerWithSocket text1 = new ConnectServerWithSocket();
                text1.setStr(sendmsg);
                text1.start();
                try {
                    text1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                replay = text1.getReplayfromserver();
                Log.e("refregh5",replay);
            }
        });

        //发送改变功率的事件
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_power = (TextView) findViewById(R.id.gonglv);
                power = newpower.getText().toString();

                sendmsg="4 dish 00 "+power;
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
                    sendmsg="4 dish 11";
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
                    sendmsg="4 dish 10";
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
        tv_worktime= (TextView) findViewById(R.id.tv_worktime);
        et_worktime= (EditText) findViewById(R.id.et_worktime);
        btn_change2= (Button) findViewById(R.id.changge2);
        more= (Button) findViewById(R.id.more);
        btn_change2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newtime=et_worktime.getText().toString();

                sendmsg="4 dish 01 "+newtime;
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
        change = (Button) findViewById(R.id.change);
        refresh= (Button) findViewById(R.id.refresh);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    state.setText("运行状态：开启");
                    break;
                case 2:
                    state.setText("运行状态：关闭");
                    break;
                case 3:
                    tv_power.setText("功率大小：" + power + "kw");
                    break;
                case 4:
                    tv_worktime.setText("单次运行时间："+newtime+"小时");
            }
        }
    };
}
