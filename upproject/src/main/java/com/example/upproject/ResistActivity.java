package com.example.upproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SyncStatusObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.upproject.ui.main.ForgetPasswordActivity;
import com.example.upproject.ui.main.MainActivity;
import com.example.upproject.ui.main.SignUpActivity;
import com.example.upproject.utils.RegexUtils;
import com.example.upproject.utils.ShareUtils;

/**
 * Created by cjs on 2016/3/14.
 */
public class ResistActivity extends Activity implements View.OnClickListener{
    private Intent intent1;
    private EditText getusername;
    private EditText getpassword;
    private String UserName;
    private String replay;
    private String sendmsg;
    private String PassWord;
    private Button cannel;
    private static final int REQUEST_CODE_TO_REGISTER = 0x001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Window window = getWindow();

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_STATUS);

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.resist);
        ShareUtils.configPlatforms(this);
        getusername = (EditText) findViewById(R.id.username);
        getpassword = (EditText) findViewById(R.id.password);
    }






    /**可用于优化登陆界面，enterMainActivity()
     * 检查输入
     *
     * @param
     * @param
     * @return
     * private void clickLogin() {
    String account = accountEdit.getText().toString();
    String password = passwordEdit.getText().toString();
    if (checkInput(account, password)) {
    // TODO: 请求服务器登录账号
    }
    }
    public boolean checkInput(String account, String password) {
        // 账号为空时提示
        if (account == null || account.trim().equals("")) {
            Toast.makeText(this, R.string.tip_account_empty, Toast.LENGTH_LONG)
                    .show();
        } else {
            // 账号不匹配手机号格式（11位数字且以1开头）
            if ( !RegexUtils.checkMobile(account)) {
                Toast.makeText(this, R.string.tip_account_regex_not_right,
                        Toast.LENGTH_LONG).show();
            } else if (password == null || password.trim().equals("")) {
                Toast.makeText(this, R.string.tip_password_can_not_be_empty,
                        Toast.LENGTH_LONG).show();
            } else {
                return true;
            }
        }

        return false;
    }
*/
    /**
     * 功能:主页面转到不同的其他界面
     * @param v
     */
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.resist:
                enterMainActivity();
                break;
            case R.id.tv_create_account:
                enterRegister();
                break;
            case R.id.tv_forget_password:
                enterForgetPwd();
                break;
            default:
                break;
        }
    }

    /**
     * 跳转到主页面
     */
    public void enterMainActivity()
    {
        intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);
    }
    /**
     * 跳转到忘记密码
     */
    private void enterForgetPwd() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到注册页面
     */
    private void enterRegister() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivityForResult(intent, REQUEST_CODE_TO_REGISTER);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
