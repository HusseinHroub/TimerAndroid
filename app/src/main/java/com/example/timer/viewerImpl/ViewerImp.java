package com.example.timer.viewerImpl;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.timer.R;
import com.example.timer.TimerProcessor;
import com.example.timer.Viewer;

public class ViewerImp implements Viewer {
    private final Activity activity;

    public ViewerImp(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void setTimeElapsedValue(long value) {
        TextView textView = activity.findViewById(R.id.displayed_time);
        textView.setText("" + String.format("%.2f", (double) value / (double) TimerProcessor.NANO_IN_SECONDS));
    }

    @Override
    public long getInitialTimerValueINNano() {
        EditText editText = activity.findViewById(R.id.initial_timer);
        long timeInSeconds = Long.parseLong(editText.getText().toString());
        return timeInSeconds * TimerProcessor.NANO_IN_SECONDS;
    }
}
