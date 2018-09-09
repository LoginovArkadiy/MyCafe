package com.develop.reapps.mycafe.order.hall;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;


public class WallView extends View implements MyView {
    private final int thickness = 15;
    private final int d = 30;

    int x1, y1, x2, y2;
    private MyCircle circle;

    Paint paint;

    public WallView(Context context) {
        super(context);
        x1 = x2 = 100;
        y2 = 400;
        y1 = 0;
        circle = new MyCircle(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(thickness);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.DKGRAY);
        canvas.drawLine(x1, y1, x2, y2, paint);
        paint.setColor(Color.LTGRAY);
        circle.draw(canvas);
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
    public void changeMas(float x0, float y0, int angle) {
        int x = (int) x0, y = (int) y0;
        switch (angle) {
            case 1:
                if (y1 == y2) x1 = x;
                if (x1 == x2) y1 = y;
                circle.setPoint(x1, y1);
                break;
            case 2:
                if (y1 == y2) x2 = x;
                if (x1 == x2) y2 = y;
                circle.setPoint(x2, y2);
                break;
        }
        invalidate();
    }

    @Override
    public void stop() {
        circle.setPoint(-10000, -10000);
    }


    public MyTouch checkTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        return (x <= x2 + 30 && x >= x1 - 30 && y >= y1 - 30 && y <= y2 + 30) ? new MyTouch(getAngle(x, y), false) : null;
    }

    @Override
    public boolean isTable() {
        return false;
    }

    @Override
    public void setMyOnClickListener(OnClickListener listener) {
        setOnClickListener(listener);
    }

    private int getAngle(int x, int y) {
        if (x <= x1 + d && x >= x1 - d && y >= y1 - d && y <= y1 + d) return 1;
        if (x <= x2 + d && x >= x2 - d && y >= y2 - d && y <= y2 + d) return 2;
        return 0;
    }

    @Override
    public void drawing(Canvas canvas) {
        draw(canvas);
    }

    @Override
    public Point getCenter() {
        return new Point((x1 + x2) / 2, (y1 + y2) / 2);

    }


    public WallView setType(int type) {
        if (type == 0) {
            x1 = x2 = 100;
            y1 = 0;
            y2 = 400;
        } else {
            y1 = y2 = 100;
            x1 = 0;
            x2 = 400;
        }
        invalidate();
        return this;
    }
}
