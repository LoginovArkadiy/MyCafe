package com.develop.reapps.mycafe.order.hall;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

public interface MyView {
    void move(float dx, float dy);
    void changeMas(float x, float y, int angle);
    void stop();
    MyTouch checkTouch(MotionEvent event);
    boolean isTable();
    void setMyOnClickListener(View.OnClickListener listener);
    void drawing(Canvas canvas);
    Point getCenter();
}
