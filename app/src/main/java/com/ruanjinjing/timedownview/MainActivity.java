package com.ruanjinjing.timedownview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ruanjinjing.library.TimeDownView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimeDownView timeDownView = (TimeDownView) findViewById(R.id.time_down_view);
        long timeDistance = 24 * 60 * 60 * 1000;
        timeDownView.startTimeDown(timeDistance, "剩余时间:");
    }
}
