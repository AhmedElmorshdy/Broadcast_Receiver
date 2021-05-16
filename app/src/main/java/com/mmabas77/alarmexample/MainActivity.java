package com.mmabas77.alarmexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TimePickerDialog timePicker;
    EditText hour;
    EditText min;
    Button setTime;
    Button shaw;
    Calendar calendar,cal_now;
    int currentHour,currentMin;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hour = findViewById(R.id.hour);
        min = findViewById(R.id.min);
        setTime = findViewById(R.id.timeset);


        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                calendar = Calendar.getInstance();
                Calendar cal_now = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMin=calendar.get(Calendar.MINUTE);

                timePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        hour.setText(String.format("%02d",i));
                        min.setText(String.format("%02d",i1));
                        stetAlerm();

                    }
                },currentHour,currentMin,false);

                timePicker.show();




            }
        });

    }

    public void stetAlerm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Date dat = new Date();
        calendar.setTime(dat);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour.getText().toString()));
        calendar.set(Calendar.MINUTE, Integer.parseInt(min.getText().toString()));
        calendar.set(Calendar.SECOND,0);

        if(calendar.before(cal_now)){
            calendar.add(Calendar.DATE,1);
        }

        Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

        manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), pendingIntent);

    }
}