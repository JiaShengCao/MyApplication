package com.example.upproject.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.example.upproject.ConnectServerWithSocket;
import com.example.upproject.R;
import com.example.upproject.ResistActivity;
import com.example.upproject.smssdk.CallBack;
import com.example.upproject.smssdk.SMSManager;
import com.example.upproject.utils.RegexUtils;
import com.example.upproject.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @desc 注册界面
 * 功能描述：一般会使用手机登录，通过获取手机验证码，跟服务器交互完成注册
 * Created by devilwwj on 16/1/24.
 */
public class SignUpActivity extends Activity implements OnClickListener {
    private static final String TAG = "SignupActivity";
    // 界面控件
    private CleanEditText phoneEdit;
    private CleanEditText passwordEdit;
    private CleanEditText verifyCodeEdit;
    private Button getVerifiCodeButton;
    private ImageView getcannel;
    public static final int EXTERNAL_STORAGE_REQ_CODE = 10 ;
    private Timer timer = new Timer();
    private Handler mHandler = new Handler();
    private int recLen = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        initViews();
    }

    /**
     * 通用findViewById,减少重复的类型转换
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <E extends View> E getView(int id) {
        try {
            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            Log.e(TAG, "Could not cast View to concrete class.", ex);
            throw ex;
        }
    }


    private void initViews() {
        getcannel=getView(R.id.iv_cancel);
        getVerifiCodeButton = getView(R.id.btn_send_verifi_code);
        getVerifiCodeButton.setOnClickListener(this);
        phoneEdit = getView(R.id.et_phone);
        phoneEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);// 下一步
        verifyCodeEdit = getView(R.id.et_verifiCode);
        verifyCodeEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);// 下一步
        passwordEdit = getView(R.id.et_password);
        passwordEdit.setImeOptions(EditorInfo.IME_ACTION_DONE);
        passwordEdit.setImeOptions(EditorInfo.IME_ACTION_GO);
        passwordEdit.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // 点击虚拟键盘的done
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO) {
                    commit();
                }
                return false;
            }
        });
    }


    private void commit() {


        SMSManager.getInstance().verifyCode(this, "86",phoneEdit.getText().toString().trim(),verifyCodeEdit.getText().toString().trim(), new CallBack() {
            @Override
            public void success() {
                String phone = phoneEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();
                String code = verifyCodeEdit.getText().toString().trim();
                if (checkInput(phone, password, code)) {
                    String sendmsg="2 "+phone+" "+phone+" 1"+" 2";
                    ConnectServerWithSocket text = new ConnectServerWithSocket();
                    text.setStr(sendmsg);
                    text.start();
                    try {
                        text.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String replay = text.getReplayfromserver();
                    if(replay.equals("1"))
                    {
                        ToastUtils.showShort(SignUpActivity.this,"注册成功");
                        Intent intent =new Intent(SignUpActivity.this, ResistActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        ToastUtils.showShort(SignUpActivity.this,"注册失败，请稍后重试");
                    }
                }
            }

            @Override
            public void error(Throwable error) {
                Toast.makeText(SignUpActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private boolean checkInput(String phone, String password, String code) {
        if (TextUtils.isEmpty(phone)) { // 电话号码为空
            ToastUtils.showShort(this, R.string.tip_phone_can_not_be_empty);
        } else {
            if (!RegexUtils.checkMobile(phone)) { // 电话号码格式有误
                ToastUtils.showShort(this, R.string.tip_phone_regex_not_right);
            } else if (TextUtils.isEmpty(code)) { // 验证码不正确
                ToastUtils.showShort(this, R.string.tip_please_input_code);
            } else if (password.length() < 6 || password.length() > 32
                    || TextUtils.isEmpty(password)) { // 密码格式
                ToastUtils.showShort(this,
                        R.string.tip_please_input_6_32_password);
            } else {
                return true;
            }
        }

        return false;
    }

//    public boolean requestPermission(){
//        //判断当前Activity是否已经获得了该权限
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
//                ||ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//
//            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                0    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                T0oast.makeText(this, "please give me the permission", Toast.LENGTH_SHORT).show();
//            } else {
//                //进行权限请求
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.READ_PHONE_STATE},
//                        EXTERNAL_STORAGE_REQ_CODE);
//                return false;
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case EXTERNAL_STORAGE_REQ_CODE: {
//                // 如果请求被拒绝，那么通常grantResults数组为空
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                    //申请成功，进行相应操作
//                    sendCode();
//                } else {
//                    //申请失败，可以继续向用户解释。
//                }
//                return;
//            }
//        }
//    }


    public void sendCode() {
        String phone = phoneEdit.getText().toString().trim();
//        if (!requestPermission()){
//            return;
//        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(this, R.string.tip_please_input_phone);
            return;
        } else if (phone.length() < 11) {
            ToastUtils.showShort(this, R.string.tip_phone_regex_not_right);
            return;
        } else if (!RegexUtils.checkMobile(phone)) {
            ToastUtils.showShort(this, R.string.tip_phone_regex_not_right);
            return;
        }
//测试服务器有没有这个手机号
//        if (LocalAccountManager.getInstance(this).exist(tilNumber.getEditText().getText().toString())){
//            Toast.makeText(this,"手机号已经注册",Toast.LENGTH_SHORT).show();
//            return;
//        }
        SMSManager.getInstance().sendMessage(this, "86",phoneEdit.getText().toString().trim());
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        setButtonStatusOff();
                        if (recLen < 1) {
                            setButtonStatusOn();
                        }
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(task, 0, 1000);
    }

    private void setButtonStatusOff() {
        getVerifiCodeButton.setText(String.format(
                this.getResources().getString(R.string.count_down), recLen--));
        getVerifiCodeButton.setClickable(false);
        getVerifiCodeButton.setTextColor(Color.parseColor("#f3f4f8"));
        getVerifiCodeButton.setBackgroundColor(Color.parseColor("#b1b1b3"));
    }

    private void setButtonStatusOn() {
        timer.cancel();
        getVerifiCodeButton.setText("重新发送");
        getVerifiCodeButton.setTextColor(Color.parseColor("#b1b1b3"));
        getVerifiCodeButton.setBackgroundColor(Color.parseColor("#f3f4f8"));
        recLen = 60;
        getVerifiCodeButton.setClickable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_cancel:
                Intent intent =new Intent(this, ResistActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_send_verifi_code:
                // TODO 请求接口发送验证码
                sendCode();
                break;
            case R.id.btn_create_account:
                commit();
                break;
            default:
                break;
        }
    }
}
