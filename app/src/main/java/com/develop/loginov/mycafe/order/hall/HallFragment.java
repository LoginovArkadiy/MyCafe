package com.develop.loginov.mycafe.order.hall;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HallFragment extends Fragment {


    public HallFragment() {
    }


    private Hall hallView;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        hallView = new Hall(context);
        return hallView;
    }

    public void addTable(String number, String count) {
        hallView.addView(new TableView(context).setNumber(number).setCountPeople(count));
    }

    public void addWall(int type) {
        hallView.addView(new WallView(context).setType(type));
    }


}
