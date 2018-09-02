package com.develop.loginov.mycafe.order.hall;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;


public class WallView extends View implements MyView {
    private final int thickness = 15;

    int x1, y1, x2, y2;

    Paint paint;

    public WallView(Context context) {
        super(context);
        x1 = x2 = 100;
        y2 = 400;
        y1 = 0;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(thickness);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.DKGRAY);
        canvas.drawLine(x1, y1, x2, y2, paint);
    }

    @Override
    public void move(float x, float y) {
        float dx = x - (x1 + x2) / 2;
        float dy = y - (y1 + y2) / 2;
        x1 += dx;
        x2 += dx;
        y1 += dy;
        y2 += dy;
        invalidate();
    }

    @Override
    public void changeMas(float x, float y, int angle) {

    }

    @Override
    public void stop() {

    }


    public MyTouch checkTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        return (x <= x2 + 30 && x >= x1 - 30 && y >= y1 - 30 && y <= y2 +30) ? new MyTouch(0, false) : null;
    }

    @Override
    public void drawing(Canvas canvas) {
        draw(canvas);
    }


    public WallView setType(int type) {
        if (type == 0) {
            x1 = x2 = 100;
            y1 = 0;y2 = 400;
        } else {
            y1 = y2 = 100;
            x1 = 0;x2 = 400;
        }
        invalidate();
        return this;
    }
}
