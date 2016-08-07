package com.example.upproject.ui.main;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.upproject.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mDatas;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private int mScreen1_3;
    private ImageView mTabline;
    private int mCurrentPageIndex;
    private ImageView iv_bottom1;//第一个底部图片
    private ImageView iv_bottom2;//第二个底部图片
    private ImageView iv_bottom3;//第三个底部图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_STATUS);

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_main);
        initTableLine();
        initView();
    }


    //初始化下划线
    private void initTableLine() {
        //用display获取当前屏幕宽
        mTabline= (ImageView) findViewById(R.id.tabline);
        Display display= getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics=new DisplayMetrics();
        display.getMetrics(outMetrics);
        mScreen1_3=outMetrics.widthPixels/3;
        ViewGroup.LayoutParams lp=  mTabline.getLayoutParams();
        lp.width=mScreen1_3;
        mTabline.setLayoutParams(lp);
    }

    //初始化控件
    public void initView(){

        //初始化数据：单次运行时间/功率/开关/容忍度

        mViewPager= (ViewPager) findViewById(R.id.viewpager);
        textView1= (TextView) findViewById(R.id.textview1);
        textView2= (TextView) findViewById(R.id.textview2);
        textView3= (TextView) findViewById(R.id.textview3);

        iv_bottom1= (ImageView) findViewById(R.id.iv_bottom1);
        iv_bottom2= (ImageView) findViewById(R.id.iv_bottom2);
        iv_bottom3= (ImageView) findViewById(R.id.iv_bottom3);

        iv_bottom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        iv_bottom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });
        iv_bottom3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
            }
        });

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
            }
        });

        mDatas=new ArrayList<Fragment>();
        Fragment1 fragment1=new Fragment1();
        Fragment2 fragment2=new Fragment2();
        Fragment3 fragment3=new Fragment3();
        mDatas.add(fragment1);
        mDatas.add(fragment2);
        mDatas.add(fragment3);
        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mDatas.get(position);
            }

            @Override
            public int getCount() {
                return mDatas.size();
            }
        };
        mViewPager.setAdapter(mAdapter);


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动页面时调用
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) mTabline.getLayoutParams();
                //从0到1滑动
                if (mCurrentPageIndex==0&&position==0){
                    lp.leftMargin= (int) (positionOffset*mScreen1_3+mCurrentPageIndex*mScreen1_3);
                }
                //从0到1滑动
                else if (mCurrentPageIndex==1&&position==0)
                {
                    lp.leftMargin= (int) (mCurrentPageIndex*mScreen1_3+(positionOffset-1)*mScreen1_3);
                }
                //从1到2滑动
                else if ((mCurrentPageIndex==1&&position==1))
                {
                    lp.leftMargin= (int) (mCurrentPageIndex*mScreen1_3+positionOffset*mScreen1_3);
                }
                //从2到1滑动
                else if (mCurrentPageIndex==2&&position==1)
                {
                    lp.leftMargin= (int) (mCurrentPageIndex*mScreen1_3+(positionOffset-1)*mScreen1_3);
                }
                mTabline.setLayoutParams(lp);
            }
            //切换fragment停留的页面调用
            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position){
                    case 0:
                        textView1.setTextColor(Color.parseColor("#1ec7ae"));
                        iv_bottom1.setImageResource(R.mipmap.myhome_selector);
                        break;
                    case 1:
                        textView2.setTextColor(Color.parseColor("#1ec7ae"));
                        iv_bottom2.setImageResource(R.mipmap.smart_selector);
                        break;
                    case 2:
                        textView3.setTextColor(Color.parseColor("#1ec7ae"));
                        iv_bottom3.setImageResource(R.mipmap.my_selector);
                        break;
                }
                mCurrentPageIndex=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void resetTextView() {
        iv_bottom1.setImageResource(R.mipmap.myhome_unselector);
        iv_bottom2.setImageResource(R.mipmap.smart_unselector);
        iv_bottom3.setImageResource(R.mipmap.my_unselector);
        textView1.setTextColor(Color.GRAY);
        textView2.setTextColor(Color.GRAY);
        textView3.setTextColor(Color.GRAY);
    }
}
