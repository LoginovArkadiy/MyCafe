package com.develop.reapps.mycafe.order.hall.tabletime;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import com.develop.reapps.mycafe.R;

import java.util.ArrayList;
import java.util.List;

public class TableTimeView extends View {
    private final int d = 30;
    private final int begin = 10;
    private final int end = 24;
    private final int radius = 10;
    private final int minTime = 60;

    private boolean inTouch1, inTouch2;
    private float length, time, step, h, width, height, yLine;
    private float x1, x2;
    private Paint paint, paint2;
    int colorAccent;
    float textSize, minTextSize;
    List<Point> order;


    public TableTimeView(Context context) {
        super(context);
        length = -1;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        colorAccent = getResources().getColor(R.color.colorAccent);
        paint.setColor(colorAccent);
        textSize = 60f;
        minTextSize = 40f;
        paint.setTextSize(textSize);
        paint.setStrokeWidth(3);

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setColor(Color.BLUE);
        paint2.setTextSize(minTextSize);
        paint2.setStrokeWidth(2);
    }

    private void init() {
        width = getWidth();
        height = getHeight();
        yLine = height / 15;
        time = (end - begin) * 60;
        h = width / 15;
        x1 = h;
        x2 = width - h;

        length = width - 2 * h;
        step = length / time;
        inTouch1 = inTouch2 = false;


        order = new ArrayList<>();
        int i = 0;
        while (order.size() < 3 && i < 50) {
            int x = (int) (Math.random() * length + h);
            int y = (int) (x + minTime * step);
            if (!checkIntersection(x, y))
                order.add(new Point(x, y));

            i++;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (length == -1) init();
        paint.setColor(Color.LTGRAY);
        canvas.drawLine(h, yLine, width - h, yLine, paint);
        paint.setColor(colorAccent);

        canvas.drawLine(x1, yLine, x2, yLine, paint);
        canvas.drawCircle(x1, yLine, (inTouch1) ? 2 * radius : radius, paint);
        canvas.drawCircle(x2, yLine, (inTouch2) ? 2 * radius : radius, paint);
        canvas.drawText(getTime(x1), h, 2 * yLine, paint);
        canvas.drawText(getTime(x2), width - 3 * h, 2 * yLine, paint);

        for (Point p : order) {
            canvas.drawLine(p.x, yLine, p.y, yLine, paint2);
            canvas.drawCircle(p.x, yLine, radius, paint2);
            canvas.drawCircle(p.y, yLine, radius, paint2);
        }


        paint.setColor(Color.RED);
        if (checkIntersection((int) x1, (int) x2))
            canvas.drawText("ЭТО ВРЕМЯ ЗАНЯТО", h, 5 * yLine, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int type = checkTouch(event.getX(), event.getY());
                if (type == 1) inTouch1 = true;
                if (type == 2) inTouch2 = true;
                break;
            case MotionEvent.ACTION_MOVE:
                move(event.getX());
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                inTouch1 = inTouch2 = false;
        }
        return true;
    }


    protected void move(float x) {
        if (x < h || x > width - h) return;
        if (inTouch1) x1 = Math.min(x, x2 - minTime * step);
        else if (inTouch2) x2 = Math.max(x, x1 + minTime * step);

        invalidate();
    }

    protected int checkTouch(float x, float y) {
        if (y > yLine + d || y < yLine - d) return 0;
        return (Math.abs(x - x1) > Math.abs(x - x2) ? 2 : 1);
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private boolean checkIntersection(int x1, int x2) {
        for (Point p : order) {
            if ((x1 < p.y && x2 > p.x) || (x2 > p.x && x2 < p.y)) return true;
        }
        return false;
    }

    private String getTime(float x) {
        int time = (int) ((x - h) / step + begin * 60);
        return "" + (time / 60) + ":" + (time % 60 < 10 ? "0" + (time % 60) : time % 60);
    }


}
