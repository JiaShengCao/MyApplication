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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by cjs on 2016/3/14.
 */
public class ResistActivity extends Activity {
    private Intent intent1;
    private EditText getusername;
    private EditText getpassword;
    private String UserName;
    private String replay;
    private String sendmsg;
    private String PassWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_STATUS);

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.resist);
        getusername = (EditText) findViewById(R.id.username);
        getpassword = (EditText) findViewById(R.id.password);
        Button resist = (Button) findViewById(R.id.resist);
        resist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UserName = getusername.getText().toString();
//                PassWord = getpassword.getText().toString();
//                sendmsg="1"+" "+UserName+" "+PassWord;
//                ConnectServerWithSocket text = new ConnectServerWithSocket();
//                text.setStr(sendmsg);
//                text.start();
//                try {
//                    text.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                replay = text.getReplayfromserver();
//                replay= (String) replay.subSequence(0,1);
                Message message = handler.obtainMessage();
                message.what = 1;
                handler.sendMessage(message);
            }
        });

    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
//                   if (replay.equals("1")) {
                       intent1 = new Intent(ResistActivity.this, MainActivity.class);
                       startActivity(intent1);
//                   }else {
//                       Toast.makeText(ResistActivity.this, "输入账号密码不匹配，请重新输入", Toast.LENGTH_LONG).show();
//                    }
                  break;
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
