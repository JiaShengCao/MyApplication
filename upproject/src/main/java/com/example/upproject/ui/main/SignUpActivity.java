package com.example.upproject.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.upproject.ui.main.CleanEditText;
import com.example.upproject.utils.RegexUtils;
import com.example.upproject.utils.ToastUtils;
import com.example.upproject.utils.VerifyCodeManager;

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
    private VerifyCodeManager codeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        initViews();
        codeManager = new VerifyCodeManager(this, phoneEdit, getVerifiCodeButton);

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
                ToastUtils.showShort(this,"注册成功");
                Intent intent =new Intent(this, ResistActivity.class);
                startActivity(intent);
            }
            else
            {
                ToastUtils.showShort(this,"注册失败，请稍后重试");
            }
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_cancel:
                Intent intent =new Intent(this, ResistActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_send_verifi_code:
                // TODO 请求接口发送验证码
                codeManager.getVerifyCode(VerifyCodeManager.REGISTER);
                break;
            case R.id.btn_create_account:
                commit();
                break;
            default:
                break;
        }
    }
}
