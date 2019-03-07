package com.develop.reapps.mycafe;

import android.view.MotionEvent;
import android.view.View;

public class MyTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setBackgroundResource(R.color.ltgrey);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                v.setBackgroundResource(R.color.white);
                break;
        }

        return false;
    }
}
