package com.develop.reapps.mycafe.workers;

import android.content.Context;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.develop.reapps.mycafe.MainActivity;
import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.server.workers.WorkerClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WorkersAdapter extends RecyclerView.Adapter<WorkersAdapter.WorkerHolder> {
    private Context context;
    private ArrayList<Worker> list;


    public WorkersAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public WorkerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_worker, parent, false);
        return new WorkerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerHolder holder, int position) {
        Worker worker = list.get(position);
        holder.imageView.setImageResource(worker.getId_drawable());
        worker.setImageBitmap(holder.imageView);

        holder.twName.setText(worker.getName());
        holder.twOffice.setText(worker.getPost());
        holder.layout.setVisibility(worker.isWorkToday() ? View.VISIBLE : View.INVISIBLE);
        if(MainActivity.ADMIN)
        holder.view.setOnLongClickListener(v -> {
            worker.setWorkToday(!worker.isWorkToday());
            new WorkerClient(context).changeTime(worker.isWorkToday(), worker.getId());
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null && vibrator.hasVibrator()) {
                vibrator.vibrate(100);
            }
            update();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    void addWorker(Worker worker) {
        if (worker.isWorkToday()) {
            list.add(0, worker);
        } else list.add(worker);
        notifyDataSetChanged();
    }

    public void addWorkers(List<Worker> workerList) {
        list.addAll(workerList);
        notifyDataSetChanged();
    }

    private void update() {
        Collections.sort(list, (o1, o2) -> {
            if (o1.isWorkToday() && o2.isWorkToday()) {
                if (o1.getRole() == o2.getRole()) return 0;
                return o1.getRole() > o2.getRole() ? -1 : 1;
            }
            if (o1.isWorkToday()) return -1;
            return 1;
        });

        notifyDataSetChanged();
    }

    static class WorkerHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView twName, twOffice;
        LinearLayout layout;
        View view;

        WorkerHolder(View view) {
            super(view);
            this.view = view;
            imageView = view.findViewById(R.id.face_worker);
            twName = view.findViewById(R.id.name_worker);
            twOffice = view.findViewById(R.id.office);
            layout = view.findViewById(R.id.smena_layout);

        }
    }


}
