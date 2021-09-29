package com.example.timer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timer.viewerImpl.ViewerImp;

public class MainActivity extends AppCompatActivity {

    private static final String START_TIMER = "Start Timer";
    private static final String STOP_TIMER = "Stop Timer";
    private static final int STOP_STATUS = 0;
    private static final int START_STATUS = 1;
    private TimerProcessor timerProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerProcessor = new TimerProcessor(new ViewerImp(this), new Handler(Looper.getMainLooper()));
        timerProcessor.setOnFinishedRunnable(this::onFinishedTime);
        timerProcessor.setOnStoppedRunnable(this::onStoppedTime);
        Button button = findViewById(R.id.start_timer_button);
        button.setTag(STOP_STATUS);

    }

    public void startTimer(View view) {
        if ((int) view.getTag() == START_STATUS) {
            timerProcessor.interrupt();
            view.setTag(STOP_STATUS);
        } else {
            Button button = (Button) view;
            button.setText(STOP_TIMER);
            timerProcessor.startTimerCountDownProcess();
            view.setTag(START_STATUS);
        }

    }


    private void onFinishedTime() {
        Button button = findViewById(R.id.start_timer_button);
        button.setText(START_TIMER);
        button.setTag(STOP_STATUS);
        MediaPlayer mp = MediaPlayer.create(this, R.raw.ending_sound);
        mp.setOnCompletionListener(MediaPlayer::release);
        mp.start();

    }

    private void onStoppedTime() {
        Button button = findViewById(R.id.start_timer_button);
        button.setText(START_TIMER);
    }
}