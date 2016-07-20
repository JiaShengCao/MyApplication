package com.example.upproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by cjs on 2016/3/2.
 */
//存放第一个listview，显示电器清单信息
public class Fragment1 extends Fragment {
    private View view;
    private ListView mlistview;
    private SimpleAdapter adapter;
    private List<HashMap<String, Object>> mHashMap;
    private HashMap<String, Object> map;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            adapter = new SimpleAdapter(getActivity(), getData(),
                    R.layout.item, new String[]{"image", "title", "content"}, new int[]
                    {R.id.item, R.id.tv_title, R.id.tv_content});
            mlistview.setAdapter(adapter);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab01, container, false);
        return view;
    }

    //   实现页面一清单的点击事件
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mlistview = (ListView) view.findViewById(R.id.list);
        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent1 = new Intent(getActivity(), FirstListItem.class);
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent(getActivity(), SecondListItem.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(getActivity(), ThirdListItem.class);
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 = new Intent(getActivity(), Listitem4.class);
                        startActivity(intent4);
                        break;
                    case 4:
                        Intent intent5 = new Intent(getActivity(), ListIitem5.class);
                        startActivity(intent5);
                        break;
                    default:
                        Toast.makeText(getActivity(), "default", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        new MyThread().start();

    }

    //adapter的数据源
    public List<HashMap<String, Object>> getData() {
        mHashMap = new ArrayList<HashMap<String, Object>>();
        map = new HashMap<String, Object>();
        map.put("image", R.mipmap.kongtiao);
        map.put("title", "空调");
        map.put("content", "air condition");
        mHashMap.add(map);

        map = new HashMap<String, Object>();
        map.put("image", R.mipmap.light);
        map.put("title", "电灯");
        map.put("content", "light");
        mHashMap.add(map);

        map = new HashMap<String, Object>();
        map.put("image", R.mipmap.wash);
        map.put("title", "洗衣机");
        map.put("content", "washer");
        mHashMap.add(map);

        map = new HashMap<String, Object>();
        map.put("image",  R.mipmap.elecar);
        map.put("title", "充电汽车");
        map.put("content", "ele car");
        mHashMap.add(map);

        map = new HashMap<String, Object>();
        map.put("image", R.mipmap.dishwash);
        map.put("title", "洗碗机");
        map.put("content", "dish_washing");
        mHashMap.add(map);


        return mHashMap;
    }


    //新开一个线程避免阻塞UI
    class MyThread extends Thread {
        @Override
        public void run() {
            handler.sendEmptyMessage(0x123);
        }
    }
}

