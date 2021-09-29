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
    private boolean interrupted;
    private boolean onProgress;
    private Runnable onFinishedRunnable;
    private Runnable onStoppedRunnable;

    public TimerProcessor(Viewer viewer, Handler handler) {
        this.viewer = viewer;
        this.handler = handler;
    }

    public void setOnFinishedRunnable(Runnable onFinishedRunnable) {
        this.onFinishedRunnable = onFinishedRunnable;
    }

    public void setOnStoppedRunnable(Runnable onStoppedRunnable) {
        this.onStoppedRunnable = onStoppedRunnable;
    }

    public void startTimerCountDownProcess() {
        if (onProgress) {
            return;
        }
        onProgress = true;
        interrupted = false;
        remainingTimeInNano = viewer.getInitialTimerValueINNano();
        startTimeInNano = System.nanoTime();
        handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                if (interrupted) {
                    if (onStoppedRunnable != null) {
                        onStoppedRunnable.run();
                    }
                    onProgress = false;
                    return;
                }
                long elapsedTimeInNanoSinceStart = System.nanoTime() - startTimeInNano;
                remainingTimeInNano -= elapsedTimeInNanoSinceStart;
                viewer.setTimeElapsedValue(remainingTimeInNano);
                if (remainingTimeInNano < 0) {
                    viewer.setTimeElapsedValue(0);
                    handler.removeCallbacks(this);
                    if (onFinishedRunnable != null) {
                        onFinishedRunnable.run();
                    }
                    onProgress = false;
                    return;
                }
                startTimeInNano = System.nanoTime();
                handler.postDelayed(this, DELAY_IN_MILLIS);
            }
        });
    }

    public void interrupt() {
        interrupted = true;
    }

    public boolean isInterrupted() {
        return interrupted;
    }
}
