package com.develop.reapps.mycafe.order.hall.tabletime;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.develop.reapps.mycafe.MainActivity;
import com.develop.reapps.mycafe.OnBackHomeInterface;
import com.develop.reapps.mycafe.R;

public class TableFragment extends Fragment {

    public TableFragment() {
    }

    private View rootView;
    private TableTimeView tableTimeView;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        rootView = inflater.inflate(R.layout.fragment_time_table, container, false);
        init();
        return rootView;
    }

    private void init() {
        tableTimeView = new TableTimeView(context);
        Button back = rootView.findViewById(R.id.bt_back_hall);
        Button ok = rootView.findViewById(R.id.bt_order_ok);
        back.setOnClickListener(v -> {
            goHome();
        });
        ok.setOnClickListener(v -> {
            Toast.makeText(context, "Мы начали готовить ваш заказ", Toast.LENGTH_SHORT).show();
            OnPostOrderListener listener = (OnPostOrderListener) context;
            listener.onPostOrder(tableTimeView.getBeginTime(), tableTimeView.getEndTime());
            goHome();
        });


        back.setOnTouchListener(MainActivity.onTouchListener);
        ok.setOnTouchListener(MainActivity.onTouchListener);

        FrameLayout layout = rootView.findViewById(R.id.time_table_layout);

        layout.addView(tableTimeView);

    }


    private void goHome() {
        ((OnBackHomeInterface) context).backToHome();
    }

    @FunctionalInterface
    public interface OnPostOrderListener {
        void onPostOrder(String t1, String t2);
    }
}
