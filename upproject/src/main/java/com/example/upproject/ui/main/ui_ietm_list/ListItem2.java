package com.example.upproject.ui.main.ui_ietm_list;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.upproject.ConnectServerWithSocket;
import com.example.upproject.R;

/**
 * Created by cjs on 2016/7/24.
 */
public class ListItem2 extends Activity implements View.OnClickListener{

    private Button btn_switch;//开关按钮

    private Button btn_timing;//定时按钮

    private Button btn_priority;//优先级按钮

    private ConnectServerWithSocket conn =new ConnectServerWithSocket();//实例化网络连接对象

    private String flag="0";//电器是否开启标志位，关闭为0，开启为1

    private TextView tv_state1,tv_state2;//2个用电器开关状态


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    btn_switch.setBackgroundResource(R.mipmap.switch_on);
                    tv_state1.setText("开启");
                    tv_state2.setText("已开启");
                    break;
                case 1:
                    btn_switch.setBackgroundResource(R.mipmap.switch_off);
                    tv_state1.setText("关闭");
                    tv_state2.setText("已关闭");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_STATUS);

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.new_item);
        initview();
    }

    private void initview() {
        btn_switch= (Button) findViewById(R.id.btn_switch);
        btn_timing= (Button) findViewById(R.id.btn_timing);
        btn_priority= (Button) findViewById(R.id.btn_priority);
        btn_switch.setOnClickListener(this);
        btn_priority.setOnClickListener(this);
        btn_timing.setOnClickListener(this);
        tv_state1= (TextView) findViewById(R.id.tv_state1);
        tv_state2= (TextView) findViewById(R.id.tv_state2);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_switch://开关电器事件
                if (flag.equals("0")){
                    flag="1";
                    Log.e("test switch ","clickkkkkkkkkkkkkkkkkkkkkkkkkk");
                    String message="4 light 11";
//                    conn.setStr(message);
//                    conn.start();
                    Message msg=handler.obtainMessage();
                    msg.what=0;
                    handler.sendMessage(msg);
                }else{
                    Log.e("test switch ","cliccccccccccccccccccccccccccccc");
                    flag="0";
                    String message="4 light 10";
//                    conn.setStr(message);
//                    conn.start();
                    Message msg=handler.obtainMessage();
                    msg.what=1;
                    handler.sendMessage(msg);
            }
                    break;


        }
    }
}
