package com.example.upproject.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.upproject.R;

/**
 * Created by cjs on 2016/7/27.
 */
public class TimePickerActivity extends Activity implements View.OnClickListener {

    private TimePicker timePicker_begin;

    private Button btn_7, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6;//七个选择星期按钮

    private boolean state_1, state_2, state_3, state_4, state_5, state_6, state_7;//七个星期的状态

    private String total = "";//总共的日期

    private Button btn_commit_time;//提交按钮

    private Button btn_back;//返回按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_STATUS);

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.timeset);
        initstate();
        initview();
    }


    private void initstate() {
        state_1 = state_2 = state_3 = state_4 = state_5 = state_6 = state_7 = false;
    }

    private void initview() {
        btn_back= (Button) findViewById(R.id.back);
        timePicker_begin = (TimePicker) findViewById(R.id.timepicker_begin);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_commit_time= (Button) findViewById( R.id.btn_commmitTime);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_commit_time.setOnClickListener(this);
        timePicker_begin.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(getApplicationContext(), "选择的时间是" + hourOfDay + "小时" + minute + "分钟", Toast
                        .LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_1:
                if (state_1 == false) {
                   btn_1.setTextColor(getResources().getColor(R.color.my_ZhuTi));
                    state_1 = true;
                    total += "星期一 ";
                } else {
                    btn_1.setTextColor(getResources().getColor(R.color.my_gray));
                    state_1 = false;
                    total = total.replaceAll("星期一 ", "");
                }
                Log.e("state1","total is 》》》》》》》》》》》》》"+total);
                break;
            case R.id.btn_2:
                if (state_2 == false) {
                    btn_2.setTextColor(getResources().getColor(R.color.my_ZhuTi));
                    state_2 = true;
                    total += "星期二 ";
                } else {
                    btn_2.setTextColor(getResources().getColor(R.color.my_gray));
                    state_2 = false;
                    total = total.replaceAll("星期二 ", "");
                }
                Log.e("state1","total is 》》》》》》》》》》》》》"+total);
                break;
            case R.id.btn_3:
                if (state_3 == false) {
                    btn_3.setTextColor(getResources().getColor(R.color.my_ZhuTi));
                    state_3 = true;
                    total += "星期三 ";
                } else {
                    btn_3.setTextColor(getResources().getColor(R.color.my_gray));
                    state_3 = false;
                    total = total.replaceAll("星期三 ", "");
                }
                Log.e("state1","total is 》》》》》》》》》》》》》"+total);
                break;
            case R.id.btn_4:
                if (state_4 == false) {
                    btn_4.setTextColor(getResources().getColor(R.color.my_ZhuTi));
                    state_4 = true;
                    total += "星期四 ";
                } else {
                    btn_4.setTextColor(getResources().getColor(R.color.my_gray));
                    state_4 = false;
                    total = total.replaceAll("星期四 ", "");
                }
                Log.e("state1","total is 》》》》》》》》》》》》》"+total);
                break;
            case R.id.btn_5:
                if (state_5 == false) {
                    btn_5.setTextColor(getResources().getColor(R.color.my_ZhuTi));
                    state_5 = true;
                    total += "星期五 ";
                } else {
                    btn_5.setTextColor(getResources().getColor(R.color.my_gray));
                    state_5 = false;
                    total = total.replaceAll("星期五 ", "");
                }
                Log.e("state1","total is 》》》》》》》》》》》》》"+total);
                break;
            case R.id.btn_6:
                if (state_6 == false) {
                    btn_6.setTextColor(getResources().getColor(R.color.my_ZhuTi));
                    state_6 = true;
                    total += "星期六 ";
                } else {
                    btn_6.setTextColor(getResources().getColor(R.color.my_gray));
                    state_6 = false;
                    total = total.replaceAll("星期六 ", "");
                }
                Log.e("state1","total is 》》》》》》》》》》》》》"+total);
                break;
            case R.id.btn_7:
                if (state_7 == false) {
                    btn_7.setTextColor(getResources().getColor(R.color.my_ZhuTi));
                    state_7 = true;
                    total += "星期日 ";
                } else {
                    btn_7.setTextColor(getResources().getColor(R.color.my_gray));
                    state_7 = false;
                    total = total.replaceAll("星期日 ", "");
                }
                Log.e("state1","total is 》》》》》》》》》》》》》"+total);
                break;
            case R.id.btn_commmitTime:
                Toast.makeText(getApplicationContext(),"每周"+total+"开启",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
