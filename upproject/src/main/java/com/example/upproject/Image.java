package com.example.upproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by cjs on 2016/3/8.
 * 进入app时的动画
 */
public class Image extends Activity {
    private Handler handler;
    private Runnable runnable;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_STATUS);

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.image);
        handler = new Handler();
        intent = new Intent(this, ResistActivity.class);
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 2000);
                startActivity(intent);
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
        finish();
    }
}



