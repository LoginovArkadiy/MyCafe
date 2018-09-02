package com.develop.loginov.mycafe.order.hall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.develop.loginov.mycafe.R;


public class TableView extends View implements MyView {
    private final int d = 20;
    int xCircle, yCircle;
    Rect dst, src;
    String number;
    Bitmap bitmap;
    float textSize = 100;
    int type, width, height;
    Paint paint;

    public TableView(Context context) {
        super(context);
        number = "0";
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.table);

        width = height = 200;
        dst = new Rect(getWidth() / 2, getHeight() / 2, getWidth() / 2 + width, getHeight() / 2 + height);
        src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        xCircle = -100;
        yCircle = -100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, src, dst, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        canvas.drawText(number, dst.centerX() - paint.measureText(number) / 2, dst.centerY() + textSize / 3, paint);
        paint.setColor(Color.GRAY);
        canvas.drawCircle(xCircle, yCircle, 20, paint);
    }

    @Override
    public void move(float x, float y) {
        int dx = (int) x;
        int dy = (int) y;
        dst = new Rect(dx - width / 2, dy - height / 2, dx + width / 2, dy + height / 2);
        invalidate();
    }


    @Override
    public void changeMas(float x0, float y0, int angle) {
        int x = (int) x0, y = (int) y0;
        switch (angle) {
            case 1:

                dst.left = x;
                dst.top = y;
                break;
            case 2:
                dst.right = x;
                dst.top = y;
                break;
            case 3:
                dst.right = x;
                dst.bottom = y;
                break;
            case 4:
                dst.left = x;
                dst.bottom = y;
                break;

        }
        width = dst.width();
        height = dst.height();
        xCircle = x;
        yCircle = y;
        invalidate();
    }

    public void setNumber(int number) {
        this.number = Integer.toString(number);
    }

    @Override
    public void stop() {
        xCircle = -100;
        yCircle = -100;
    }

    protected void changeWidth(double mas) {

    }

    protected void changeHeight(double mas) {
    }

    @Override
    public MyTouch checkTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        return (x >= dst.left - d && x <= dst.right + d && y >= dst.top - d && y <= dst.bottom + d) ? new MyTouch(getAngle(x, y), true) : null;
    }

    @Override
    public void drawing(Canvas canvas) {
        draw(canvas);
    }

    private int getAngle(int x, int y) {

        if (x >= dst.left - d && x <= dst.left + d && y >= dst.top - d && y <= dst.top + d)
            return 1;
        if (x >= dst.right - d && x <= dst.right + d && y >= dst.top - d && y <= dst.top + d)
            return 2;
        if (x >= dst.right - d && x <= dst.right + d && y >= dst.bottom - d && y <= dst.bottom + d)
            return 3;
        if (x >= dst.left - d && x <= dst.left + d && y >= dst.bottom - d && y <= dst.bottom + d)
            return 4;
        return 0;
    }




}
