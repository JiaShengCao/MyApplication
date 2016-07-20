package com.example.upproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by cjs on 2016/3/17.
 */
public class Model2 extends Activity {
    private CheckBox device1,device2,device3,device4,device5;
    private int addnum=0;
    private String addname;
    private Button sendmessage;
    private String sendmsg;
    private String replay;
    private EditText time_begin;
    private EditText time_end;
    private String begin;
    private String end;
    private String new_addnum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        new_addnum=String.valueOf(addnum);
        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                begin=time_begin.getText().toString();
                end=time_end.getText().toString();
                sendmsg="5 2 "+begin+" "+end+" "+addnum+" "+addname.substring(4);
                Log.e("newaddnum",new_addnum);
                Log.e("model_send_test",sendmsg);
                ConnectServerWithSocket text = new ConnectServerWithSocket();
                text.setStr(sendmsg);
                text.start();
                try {
                    text.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                replay = text.getReplayfromserver();
                Log.e("model_test",replay);
                Toast.makeText(getApplicationContext(),"用电器为:  "+addname.substring(4),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(){
        Window window = getWindow();

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_STATUS);

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.model1);
        device1= (CheckBox) findViewById(R.id.use1);
        device2= (CheckBox) findViewById(R.id.use2);
        device3= (CheckBox) findViewById(R.id.use3);
        device4= (CheckBox) findViewById(R.id.use4);
        device5= (CheckBox) findViewById(R.id.use5);
        time_begin= (EditText) findViewById(R.id.time_begin);
        time_end= (EditText) findViewById(R.id.time_end);
        sendmessage= (Button) findViewById(R.id.sendmessage);

        device1.setOnClickListener(checklistener);
        device2.setOnClickListener(checklistener);
        device3.setOnClickListener(checklistener);
        device4.setOnClickListener(checklistener);
        device5.setOnClickListener(checklistener);
    }
    //选择模式种需要使用的用电器
    private View.OnClickListener checklistener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView txt= (TextView) v;
            switch (txt.getId()){
                case R.id.use1:
                    if (device1.isChecked()){
                        addname+=" air";
                        addnum++;
                    }else {
                        addname=addname.replaceAll(" air","");
                        addnum--;
                    }
                    break;
                case R.id.use2:
                    if (device2.isChecked()){
                        addname+=" light";
                        addnum++;
                    }else {
                        addname=addname.replaceAll(" light","");
                        addnum--;
                    }
                    break;
                case R.id.use3:
                    if (device3.isChecked()){
                        addname+=" wash";
                        addnum++;
                    }else {
                        addname=addname.replaceAll(" wash","");
                        addnum--;
                    }
                    break;
                case R.id.use4:
                    if (device4.isChecked()){
                        addname+=" car";
                        addnum++;
                    }else {
                        addname=addname.replaceAll(" car","");
                        addnum--;
                    }
                    break;
                case R.id.use5:
                    if (device5.isChecked()){
                        addname+=" dish";
                        addnum++;
                    }else {
                        addname=addname.replaceAll(" dish","");
                        addnum--;
                    }
                    break;
            }
        }
    };

}
