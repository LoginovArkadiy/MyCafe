package com.develop.reapps.mycafe.order.hall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.develop.reapps.mycafe.R;


public class TableView extends View implements MyView {
    private final int d = 30;
    Rect dst, src;
    private String number, countPeople;
    private int typeOfTouch = -1;
    Bitmap bitmap;
    float textSize = 100;
    int type, width, height;
    Paint paint;
    long preTime;
    MyCircle circle;

    public TableView(Context context) {
        super(context);
        number = "0";
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.table);

        width = height = 250;
        dst = new Rect(getWidth() / 2, getHeight() / 2, getWidth() / 2 + width, getHeight() / 2 + height);
        src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        circle = new MyCircle(context);
        preTime = -1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, src, dst, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        canvas.drawText(number, dst.centerX() - paint.measureText(number) / 2, dst.centerY() + textSize / 3, paint);
        paint.setColor(Color.LTGRAY);
        circle.draw(canvas);
    }

    @Override
    public void moveD(float x, float y) {
        int dx = (int) x;
        int dy = (int) y;
        dst = new Rect(dx - width / 2, dy - height / 2, dx + width / 2, dy + height / 2);
        invalidate();
    }


    public long getPreTime() {
        return preTime;
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
            case 0:
                moveD(x, y);

        }
        width = dst.width();
        height = dst.height();
        circle.setPoint(x, y);
        invalidate();
    }

    public void changeMas(float x0, float y0) {
        int x = (int) x0, y = (int) y0;
        switch (typeOfTouch) {
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
            case 0: moveD(x0, y0);
        }
        width = dst.width();
        height = dst.height();
        circle.setPoint(x, y);
        invalidate();
    }


    public TableView setNumber(String number) {
        this.number = number;
        return this;
    }

    @Override
    public void stop() {
        circle.setPoint(-10000, -10000);
    }

    protected void changeWidth(double mas) {

    }

    public void setPreTime(long preTime) {
        this.preTime = preTime;
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
    public boolean isTable() {
        return true;
    }

    @Override
    public void drawing(Canvas canvas) {
        draw(canvas);
    }

    @Override
    public Point getCenter() {
        return new Point((dst.left + dst.right) / 2, (dst.bottom + dst.top) / 2);
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


    public TableView setCountPeople(String countPeople) {
        this.countPeople = countPeople;
        return this;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        Log.i("TABLE", "GoooooooooooD");
        int x = (int) event.getX(), y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                typeOfTouch = getAngle(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                changeMas(x, y);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                stop();
        }


        return true;
    }


    public String getNumber() {
        return number;
    }
}
