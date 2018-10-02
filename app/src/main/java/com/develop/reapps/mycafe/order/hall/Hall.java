package com.develop.reapps.mycafe.order.hall;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.develop.reapps.mycafe.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Hall extends View {
    private MyTouch nowTouch;
    private List<MyView> views;
    private AlertDialog dialog;
    private TableView tableEdit;
    int x0, y0;
    private Context context;

    public Hall(Context context) {
        super(context);
        this.context = context;
        views = new ArrayList<>();
        dialog = createEditingDialog(context);
        x0 = y0 = 0;

        for (int i = 0; i < 3; i++) {
            TableView tv = new TableView(context);
            tv.setNumber(String.valueOf(i + 1));
            tv.move(100 + i * 300, 100 + i * 300);
            views.add(tv);
        }

    }


    private AlertDialog createEditingDialog(Context context) {
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_table, null);
        EditText editNumber = view.findViewById(R.id.edit_number_table);
        EditText editCount = view.findViewById(R.id.edit_count_people);
        ad.setTitle("Изменить стол").setNegativeButton("Отмена", (dialogInterface, i) -> {
            dialog.cancel();
        }).setPositiveButton("Ок", (dialogInterface, i) -> {
            String number = editNumber.getText().toString();
            String count = editCount.getText().toString();
            if (number.length() > 0) tableEdit.setNumber(number);
            if (count.length() > 0) tableEdit.setCountPeople(count);
        }).setCancelable(true).setView(view);
        return ad.create();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.LTGRAY);
        for (MyView view : views) {
            view.drawing(canvas);
        }

    }

    protected void addView(MyView view) {
        view.setMyOnClickListener(v -> {

            long time1 = ((TableView) view).getPreTime();
            long time2 = Calendar.getInstance().getTimeInMillis();
            if (time2 - time1 < 1000L) dialog.show();
        });
        views.add(0, view);
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                nowTouch = search(event);
                x0 = (int) event.getX();
                y0 = (int) event.getY();
                if (!HallFragment.isEditing && nowTouch != null) {
                    OnClickTableListener listener = (OnClickTableListener) context;
                    listener.clickTable(Integer.parseInt(((TableView) views.get(nowTouch.getIndex())).getNumber()));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (nowTouch != null) {
                    if (nowTouch.getAngle() == 0)
                        views.get(nowTouch.getIndex()).move(event.getX(), event.getY());
                    else
                        views.get(nowTouch.getIndex()).changeMas(event.getX(), event.getY(), nowTouch.getAngle());
                } else {
                    int x1 = (int) event.getX();
                    int y1 = (int) event.getY();
                    for (MyView view : views) {
                        int dx = x1 - x0, dy = y1 - y0;
                        Point nowPosition = view.getCenter();
                        view.move(nowPosition.x + dx, nowPosition.y + dy);

                    }
                    x0 = x1;
                    y0 = y1;
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

        invalidate();
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

    public interface OnClickTableListener {
        void clickTable(int number);
    }
}