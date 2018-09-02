package com.develop.loginov.mycafe.order.hall;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyCircle extends View {
    private int x, y ;
    private final int radius = 30;
    private Paint paint;

    public MyCircle(Context context) {
        super(context);
        x = -10000;
        y = -10000;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, radius, paint);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(x, y, radius, paint);
    }

    public void setPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
