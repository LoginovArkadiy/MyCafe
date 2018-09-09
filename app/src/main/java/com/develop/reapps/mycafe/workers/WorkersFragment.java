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
        WorkersRecycleAdapter adapter = new WorkersRecycleAdapter();
        listView.setAdapter(adapter);

        List<Worker> workers = new ArrayList<>();
/*
        WorkersGetTask workersGetTask = new WorkersGetTask();
        workersGetTask.execute();
        Worker[] workers = new Worker[0];
        try {
            workers = workersGetTask.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        for (Worker w : workers) {
            adapter.addWorker(w);
        }
*/
        workers.add(new Worker("Барт", "Бармен", R.drawable.bart, true));
        workers.add(new Worker("Гомер", "Официант", R.drawable.gomer, true));
        workers.add(new Worker("Мардж", "Администратор", R.drawable.wife, true));
        workers.add(new Worker("Лиза", "Бармен", R.drawable.liza, false));
        workers.add(new Worker("Нед", "Официант", R.drawable.ned, true));
        workers.add(new Worker("Бёрнс", "Администратор", R.drawable.mrburns, false));
        workers.add(new Worker("Милхаус", "Кассир", R.drawable.milhaus, true));
        for (Worker w : workers) {
            adapter.addWorker(w);
        }
    }
}
