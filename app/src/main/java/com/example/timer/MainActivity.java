package com.example.timer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timer.viewerImpl.ViewerImp;

public class MainActivity extends AppCompatActivity {


    private TimerProcessor timerProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerProcessor = new TimerProcessor(new ViewerImp(this), new Handler(Looper.getMainLooper()));

    }

    public void startTimer(View view) {
        view.setEnabled(false);
        timerProcessor.startTimerCountDownProcess(this::timerProcessFinishedCalLBack);
    }


    private void timerProcessFinishedCalLBack() {
        findViewById(R.id.start_timer_button).setEnabled(true);
        MediaPlayer mp = MediaPlayer.create(this, R.raw.ending_sound);
        mp.setOnCompletionListener(MediaPlayer::release);
        mp.start();

    }
}