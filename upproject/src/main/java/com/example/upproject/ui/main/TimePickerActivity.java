package com.example.upproject.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.upproject.R;

/**
 * Created by cjs on 2016/7/27.
 */
public class TimePickerActivity extends Activity {
    private TimePicker timePicker_begin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeset);
        initview();
    }

    private void initview() {
        timePicker_begin = (TimePicker) findViewById(R.id.timepicker_begin);
        timePicker_begin.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(getApplicationContext(), "选择的时间是" + hourOfDay + "小时" + minute + "分钟", Toast
                        .LENGTH_SHORT).show();
            }
        });
    }
}
