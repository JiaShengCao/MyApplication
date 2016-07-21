package com.example.upproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by cjs on 2016/3/2.
 */
//功能待定
public class Fragment2 extends Fragment implements View.OnClickListener{
    private View view;
    private StatisticsView zhexian;
    private ImageButton model1;//立即开启模式
    private ImageButton model2;//定时开启模式

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.tab02,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        zhexian.setBottomStr(new String[]{"星期一","星期二","星期三","星期四","星期五","星期六","星期天"});
        zhexian.setValues(new float[]{10f,90f,33f,66f,42f,99f,0f});
    }

    private void init() {
        zhexian= (StatisticsView) view.findViewById(R.id.statisticsView);

        model1= (ImageButton) view.findViewById(R.id.model1);
        model2= (ImageButton) view.findViewById(R.id.model2);

        model1.setOnClickListener(this);
        model2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.model1:
                Intent intent1=new Intent(getActivity(),Model1.class);
                startActivity(intent1);
                break;
            case R.id.model2:
                Intent intent2=new Intent(getActivity(),Model2.class);
                startActivity(intent2);
                break;
        }
    }
}
