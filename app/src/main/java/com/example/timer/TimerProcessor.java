package com.example.timer;

import android.os.Handler;
import android.os.Looper;

public class TimerProcessor {

    private static final long DELAY_IN_MILLIS = 100;
    public static final long NANO_IN_SECONDS = 1000000000L;

    private Handler handler;
    private long startTimeInNano;
    private long remainingTimeInNano;
    private Viewer viewer;

    public TimerProcessor(Viewer viewer, Handler handler) {
        this.viewer = viewer;
        this.handler = handler;
    }

    public void startTimerCountDownProcess(Runnable timerFinishRunnable) {
        remainingTimeInNano = viewer.getInitialTimerValueINNano();
        startTimeInNano = System.nanoTime();
        handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                long elapsedTimeInNanoSinceStart = System.nanoTime() - startTimeInNano;
                remainingTimeInNano -= elapsedTimeInNanoSinceStart;
                viewer.setTimeElapsedValue(remainingTimeInNano);
                if (remainingTimeInNano < 0) {
                    viewer.setTimeElapsedValue(0);
                    handler.removeCallbacks(this);
                    timerFinishRunnable.run();
                    return;
                }
                startTimeInNano = System.nanoTime();
                handler.postDelayed(this, DELAY_IN_MILLIS);
            }
        });
    }
}
