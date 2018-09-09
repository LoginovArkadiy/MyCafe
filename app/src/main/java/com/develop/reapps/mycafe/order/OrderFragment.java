package com.develop.reapps.mycafe.order;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.develop.reapps.mycafe.MainActivity;
import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.order.basket.BasketFragment;
import com.develop.reapps.mycafe.order.hall.HallFragment;
import com.develop.reapps.mycafe.order.hall.tabletime.TableFragment;

public class OrderFragment extends Fragment {

    public OrderFragment() {
        // Required empty public constructor
    }

    LinearLayout layout;
    View rootView;
    Bundle args;
    private LayoutInflater inflater;
    private AlertDialog tableDialog, wallDialog;
    private Context context;
    BasketFragment basketFragment;
    HallFragment hallFragment;
    TableFragment tableFragment;
    CardView cardEdit;

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
        if (MainActivity.ADMIN) {
            cardEdit.setVisibility(View.VISIBLE);
        }
        getChildFragmentManager().beginTransaction().replace(R.id.order_container, hallFragment).commit();
    }

    public void changeFragment(Fragment fragment) {
        getChildFragmentManager().beginTransaction().replace(R.id.order_container, fragment).commit();
    }


    public void clear() {
        basketFragment.clear();
    }

    private void setListeners() {
        Button btTable = rootView.findViewById(R.id.bt_add_table);
        Button btWall = rootView.findViewById(R.id.bt_add_wall);
        Button btEdit = rootView.findViewById(R.id.bt_edit_hall);
        Button btBack = rootView.findViewById(R.id.bt_back_adder);
        cardEdit = rootView.findViewById(R.id.card_edit_hall);
        layout = rootView.findViewById(R.id.hall_buttons_layout);
        btTable.setOnClickListener(v -> tableDialog.show());
        btWall.setOnClickListener(v -> wallDialog.show());
        btEdit.setOnClickListener(v -> {
            cardEdit.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            HallFragment.isEditing = true;
        });
        btBack.setOnClickListener(v -> {
            cardEdit.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
            HallFragment.isEditing = false;

        });
        btBack.setOnTouchListener(MainActivity.onTouchListener);
        btEdit.setOnTouchListener(MainActivity.onTouchListener);
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


    public void setTableFragment(TableFragment tableFragment) {
        this.tableFragment = tableFragment;
        cardEdit.setVisibility(View.GONE);
        layout.setVisibility(View.GONE);
        getChildFragmentManager().beginTransaction().replace(R.id.order_container, tableFragment).commit();
    }
}
