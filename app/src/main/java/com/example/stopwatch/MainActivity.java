package com.example.stopwatch;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button start, pause, reset, lap;
    long millisecondTime, startTime, timeBuff, updateTime = 0L;

    Handler handler;

    int seconds, minutes, milliseconds;

    ListView listView;

    String[] ListElements = new String[] {};

    List<String> ListElementsArrayList;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        start = (Button)findViewById(R.id.button);
        pause = (Button)findViewById(R.id.button2);
        reset = (Button)findViewById(R.id.button3);
        lap = (Button)findViewById(R.id.button4);
        listView = (ListView)findViewById(R.id.listview1);

        handler = new Handler();

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));
        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList);

        listView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

                reset.setEnabled(false);
                start.setEnabled(false);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeBuff += millisecondTime;

                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
                start.setEnabled(true);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                millisecondTime = 0L;
                startTime = 0L;
                timeBuff = 0L;
                updateTime = 0L;
                seconds = 0;
                minutes = 0;
                milliseconds = 0;

                textView.setText("00:00:00");

                ListElementsArrayList.clear();

                adapter.notifyDataSetChanged();
            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListElementsArrayList.add(textView.getText().toString());

                adapter.notifyDataSetChanged();
            }
        });
    }

    public Runnable runnable = new Runnable() {
        public void run() {
            millisecondTime = SystemClock.uptimeMillis() - startTime;

            updateTime = timeBuff + millisecondTime;

            seconds = (int) (updateTime / 1000);

            minutes = seconds / 60;

            seconds = seconds % 60;

            milliseconds = (int) (updateTime % 1000);

            textView.setText("" + minutes + ":"
                    + String.format("%02d", seconds) + ":"
                    + String.format("%03d", milliseconds));

            handler.postDelayed(this, 0);
        }
    };
}
