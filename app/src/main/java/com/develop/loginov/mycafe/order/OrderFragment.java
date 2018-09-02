package com.develop.loginov.mycafe.order;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.develop.loginov.mycafe.MainActivity;
import com.develop.loginov.mycafe.R;
import com.develop.loginov.mycafe.order.basket.BasketFragment;
import com.develop.loginov.mycafe.order.hall.HallFragment;

public class OrderFragment extends Fragment {


    public OrderFragment() {
        // Required empty public constructor
    }

    View rootView;
    Bundle args;
    private LayoutInflater inflater;
    private AlertDialog tableDialog, wallDialog;
    private Context context;
    BasketFragment basketFragment;
    HallFragment hallFragment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_order, container, false);
        this.inflater = inflater;
        context = getContext();
        args = getArguments();
        basketFragment = new BasketFragment();
        basketFragment.setArguments(args);
        getChildFragmentManager().beginTransaction().add(R.id.order_container, basketFragment).commit();
        setListeners();
        createTableDialog();
        createWallDialog();
        return rootView;
    }

    public void setHallFragment(HallFragment hallFragment) {
        this.hallFragment = hallFragment;
        hallFragment.setArguments(args);
        if (MainActivity.ADMIN)
            rootView.findViewById(R.id.hall_buttons_layout).setVisibility(View.VISIBLE);
        getChildFragmentManager().beginTransaction().replace(R.id.order_container, hallFragment).commit();
    }

    public void clear() {
        basketFragment.clear();
    }

    private void setListeners() {
        Button btTable = rootView.findViewById(R.id.bt_add_table);
        Button btWall = rootView.findViewById(R.id.bt_add_wall);
        btTable.setOnClickListener(v -> {
            tableDialog.show();
        });
        btWall.setOnClickListener(v -> {
            wallDialog.show();
        });
        btTable.setOnTouchListener(MainActivity.onTouchListener);
        btWall.setOnTouchListener(MainActivity.onTouchListener);
    }


    private void createTableDialog() {
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        View view = inflater.inflate(R.layout.dialog_table, null);
        ad.setTitle("Новый столик").setNegativeButton("Отмена", (dialogInterface, i) -> {
            tableDialog.cancel();
        }).setPositiveButton("Ок", (dialogInterface, i) -> {
            String number = ((EditText) view.findViewById(R.id.edit_number_table)).getText().toString();
            String count = ((EditText) view.findViewById(R.id.edit_count_people)).getText().toString();
            if (number.length() * count.length() > 0) {
                hallFragment.addTable(number, count);
                tableDialog.cancel();
                return;
            }
            Toast.makeText(context, "Заполните все ячейки!", Toast.LENGTH_SHORT).show();
        }).setCancelable(true).setView(view);
        tableDialog = ad.create();
    }

    private void createWallDialog() {
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setTitle("Новая стена").setNegativeButton("Горизонтальная", (dialogInterface, i) -> {
            hallFragment.addWall(1);
            tableDialog.cancel();
        }).setPositiveButton("Вертикальная", (dialogInterface, i) -> {
            hallFragment.addWall(0);
            tableDialog.cancel();
        }).setCancelable(true);
        wallDialog = ad.create();
    }


}
