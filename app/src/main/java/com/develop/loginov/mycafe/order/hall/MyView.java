package com.develop.loginov.mycafe.order.hall;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface MyView {
    void move(float dx, float dy);
    void changeMas(float x, float y, int angle);
    void stop();
    MyTouch checkTouch(MotionEvent event);

    void drawing(Canvas canvas);
}
