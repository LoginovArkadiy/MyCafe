package com.develop.loginov.mycafe.order.hall;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.develop.loginov.mycafe.R;


public class HallFragment extends Fragment {


    public HallFragment() {
    }


    private Hall hallView;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        hallView = new Hall(context);
        return hallView;
    }

    public void addTable(String number, String count) {
        TableView tableView = new TableView(context);
        tableView.setNumber(Integer.parseInt(number));
        hallView.addView(tableView);
    }

    public void addWall(int type) {
        hallView.addView(new WallView(context).setType(type));

    }


}
