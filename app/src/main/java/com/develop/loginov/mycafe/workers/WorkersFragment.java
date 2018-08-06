package com.develop.loginov.mycafe.workers;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.develop.loginov.mycafe.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WorkersFragment extends Fragment {

    Context context;

    public WorkersFragment() {
    }

    View rootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_workers, container, false);
        context = getContext();
        initList();

        return rootView;
    }

    private void initList() {

            /*  ListView listView = (ListView) rootView.findViewById(R.id.list_workers);
        assert context != null;
        WorkersAdapter adapter = new WorkersAdapter(context);  */

        RecyclerView listView = rootView.findViewById(R.id.list_workers);
        listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        WorkersRecycleAdapter adapter = new WorkersRecycleAdapter();
        listView.setAdapter(adapter);
        List<Worker> workerList = new ArrayList<>();

        workerList.add(new Worker("Барт", "Бармен", R.drawable.bart, true));
        workerList.add(new Worker("Гомер", "Официант", R.drawable.gomer, true));
        workerList.add(new Worker("Мардж", "Администратор", R.drawable.wife, true));
        workerList.add(new Worker("Лиза", "Бармен", R.drawable.liza, false));
        workerList.add(new Worker("Нед", "Официант", R.drawable.ned, true));
        workerList.add(new Worker("Бёрнс", "Администратор", R.drawable.mrburns, false));
        workerList.add(new Worker("Милхаус", "Кассир", R.drawable.milhaus, true));
        for (Worker w : workerList) {
            adapter.addWorker(w);
        }
    }
}
