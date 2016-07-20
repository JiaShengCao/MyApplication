package com.example.upproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by cjs on 2016/3/2.
 */
public class Fragment3 extends Fragment {
    private View view;
    private EditText key;
    private View layout_name;
    private View layout_pass;
    private View layout_name2;
    private View layout_pass2;
    private Button btn_access;
    private String keyword;
    private Button btn_add;
    private TextView tishi1;
    private TextView tishi2;
    private Button btn_delete;
    private EditText add_user,add_key,delete_user,delete_key;
    private String add_user_str,add_key_str,delete_user_str,delete_key_str,sendmsg,replay;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.tab03,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

    }

    private void init() {
        key= (EditText) view.findViewById(R.id.key);
        tishi1= (TextView) view.findViewById(R.id.tishi1);
        tishi2= (TextView) view.findViewById(R.id.tishi2);
        btn_access= (Button) view.findViewById(R.id.btn_access);
        btn_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword=key.getText().toString();
                if (keyword.equals("cjs")){
                    layout_name=view.findViewById(R.id.layout_name);
                    layout_pass=view.findViewById(R.id.layout_pass);
                    layout_name2=view.findViewById(R.id.layout_name2);
                    layout_pass2=view.findViewById(R.id.layout_pass2);
                    btn_delete= (Button) view.findViewById(R.id.btn_delete);
                    btn_add= (Button) view.findViewById(R.id.btn_add);
                    add_user= (EditText) view.findViewById(R.id.add_user);
                    add_key= (EditText) view.findViewById(R.id.add_key);
                    delete_user= (EditText) view.findViewById(R.id.delete_user);
                    delete_key= (EditText) view.findViewById(R.id.delete_key);

                    Toast.makeText(view.getContext(),"密码输入正确",Toast.LENGTH_SHORT).show();
                    tishi1.setVisibility(View.VISIBLE);
                    layout_name.setVisibility(View.VISIBLE);
                    layout_pass.setVisibility(View.VISIBLE);
                    btn_add.setVisibility(View.VISIBLE);
                    tishi2.setVisibility(View.VISIBLE);
                    layout_name2.setVisibility(View.VISIBLE);
                    layout_pass2.setVisibility(View.VISIBLE);
                    btn_delete.setVisibility(View.VISIBLE);
                    btn_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(view.getContext(),"添加服务账户",Toast.LENGTH_SHORT).show();
                            add_user_str=add_user.getText().toString();
                            add_key_str=add_key.getText().toString();

                            sendmsg="2 "+add_user_str+" "+add_key_str+" 1"+" 2";
                            ConnectServerWithSocket text = new ConnectServerWithSocket();
                            text.setStr(sendmsg);
                            text.start();
                            try {
                                text.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            replay = text.getReplayfromserver();
                            Log.e("add user=====",replay);

                        }
                    });
                    btn_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(view.getContext(),"删除服务账户",Toast.LENGTH_SHORT).show();
                            delete_user_str=delete_user.getText().toString();
                            delete_key_str=delete_key.getText().toString();

                            sendmsg="3 "+delete_user_str+" "+delete_key_str+" 1"+" 2";
                            ConnectServerWithSocket text = new ConnectServerWithSocket();
                            text.setStr(sendmsg);
                            text.start();
                            try {
                                text.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            replay = text.getReplayfromserver();
                            Log.e("delete user=====",replay);
                        }
                    });

                }else
                    Toast.makeText(view.getContext(),"密码输入错误",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
