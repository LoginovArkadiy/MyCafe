package com.develop.loginov.mycafe.order.hall;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Hall extends View {
    private MyTouch nowTouch;
    private List<MyView> views;

    public Hall(Context context) {
        super(context);
        views = new ArrayList<>();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        for (MyView view : views) {
            view.drawing(canvas);
        }

    }

    protected void addView(MyView view) {
        views.add(0, view);
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                nowTouch = search(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (nowTouch != null) {
                    if (nowTouch.getAngle() == 0)
                        views.get(nowTouch.getIndex()).move(event.getX(), event.getY());
                    else
                        views.get(nowTouch.getIndex()).changeMas(event.getX(), event.getY(), nowTouch.getAngle());
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (nowTouch != null) {
                    views.get(nowTouch.getIndex()).stop();
                    nowTouch = null;
                }
                break;
        }

        if (nowTouch != null) invalidate();
        return true;
    }

    private MyTouch search(MotionEvent event) {
        int i = 0;
        MyTouch touch;
        for (MyView view : views) {
            touch = view.checkTouch(event);
            if (touch != null) {
                touch.setIndex(i);
                return touch;
            }
            i++;
        }
        return null;
    }
}