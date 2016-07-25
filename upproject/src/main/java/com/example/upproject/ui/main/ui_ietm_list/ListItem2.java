package com.example.upproject.ui.main.ui_ietm_list;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.upproject.ConnectServerWithSocket;
import com.example.upproject.R;

/**
 * Created by cjs on 2016/7/24.
 */
public class ListItem2 extends Activity implements View.OnClickListener{

    private Button btn_fanhui;//返回按钮

    private Button btn_pop;//弹出式菜单

    private Button btn_switch;//开关按钮

    private Button btn_timing;//定时按钮

    private Button btn_priority;//优先级按钮

    private String flag="0";//电器是否开启标志位，关闭为0，开启为1

    private TextView tv_state1,tv_state2;//2个用电器开关状态

    private PopupWindow popupWindow;//弹出式菜单

    private TextView btn_power,btn_time;//弹出式菜单的两个按钮


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
        btn_pop= (Button) findViewById(R.id.btn_init);
        btn_switch= (Button) findViewById(R.id.btn_switch);
        btn_timing= (Button) findViewById(R.id.btn_timing);
        btn_priority= (Button) findViewById(R.id.btn_priority);
        btn_fanhui= (Button) findViewById(R.id.fanhui);
        btn_pop.setOnClickListener(this);
        btn_switch.setOnClickListener(this);
        btn_priority.setOnClickListener(this);
        btn_timing.setOnClickListener(this);
        btn_fanhui.setOnClickListener(this);
        tv_state1= (TextView) findViewById(R.id.tv_state1);
        tv_state2= (TextView) findViewById(R.id.tv_state2);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_switch://开关电器事件
                if (flag.equals("0")){
                    flag="1";
                    String message="4 light 11";
                    ConnectServerWithSocket conn =new ConnectServerWithSocket();//实例化网络连接对象
                    conn.setStr(message);
                    conn.start();
                        try {
                            conn.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    Message msg=handler.obtainMessage();
                    msg.what=0;
                    handler.sendMessage(msg);
                }else{
                    flag="0";
                    String message="4 light 10";
                    ConnectServerWithSocket conn =new ConnectServerWithSocket();//实例化网络连接对象
                    conn.setStr(message);
                    conn.start();
                    try {
                        conn.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Message msg=handler.obtainMessage();
                    msg.what=1;
                    handler.sendMessage(msg);
            }
                    break;
            case R.id.fanhui:
                finish();
                break;
            case R.id.btn_init:
                getPopupWindow();
                popupWindow.showAsDropDown(v);
                break;
        }
    }

    private void getPopupWindow(){
        if (null!=popupWindow){
            popupWindow.dismiss();
            return;
        }else {
            initPopuptWindow();
        }
    }

    private void initPopuptWindow() {
        View popupWindow_view=getLayoutInflater().inflate(R.layout.pop_window,null,false);
        //创建popupowindow实例，200,150为宽度和高度
        popupWindow=new PopupWindow(popupWindow_view,250,250,true);
        //设置动画效果
        popupWindow.setAnimationStyle(R.style.AnimationFade);
        //点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow!=null&&popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow=null;
                }
                return false;
            }
        });

        btn_power= (TextView) popupWindow_view.findViewById(R.id.btn_power);
        btn_time= (TextView) popupWindow_view.findViewById(R.id.btn_time);
        btn_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //创建一个对话框
                AlertDialog.Builder builder=new AlertDialog.Builder(ListItem2.this);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("请输入用电器功率");
                View view= LayoutInflater.from(ListItem2.this).inflate(R.layout.et_power_dialog,null);
                builder.setView(view);
                final EditText et_power= (EditText) view.findViewById(R.id.et_power);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            String power= et_power.getText().toString().trim();
                            Toast.makeText(ListItem2.this,"新设定的功率为"+power,Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();


                Toast.makeText(getApplicationContext(),"点击了更改功率",Toast.LENGTH_SHORT).show();
            }
        });
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //创建一个对话框
                AlertDialog.Builder builder=new AlertDialog.Builder(ListItem2.this);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("请输入单次工作时间");
                View view= LayoutInflater.from(ListItem2.this).inflate(R.layout.et_time_dialog,null);
                builder.setView(view);
                final EditText et_power= (EditText) view.findViewById(R.id.et_time);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String power= et_power.getText().toString().trim();
                        Toast.makeText(ListItem2.this,"新设定的工作时间为"+power,Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();

                Toast.makeText(getApplicationContext(),"点击了更改时间",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
