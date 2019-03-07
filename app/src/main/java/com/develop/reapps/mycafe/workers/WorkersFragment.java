package com.develop.reapps.mycafe.workers;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.server.workers.WorkerClient;

import java.util.ArrayList;
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
        RecyclerView listView = rootView.findViewById(R.id.list_workers);
        listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        List<Worker> list = new ArrayList<>();
        WorkersAdapter adapter = new WorkersAdapter(list);
        listView.setAdapter(adapter);

        new WorkerClient(context).getWorkers(list, adapter);
    }
}
