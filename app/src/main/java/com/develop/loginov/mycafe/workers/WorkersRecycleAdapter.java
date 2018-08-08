package com.develop.loginov.mycafe.workers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.develop.loginov.mycafe.R;

import java.util.ArrayList;
import java.util.List;

public class WorkersRecycleAdapter extends RecyclerView.Adapter<WorkersRecycleAdapter.WorkerHolder> {

    private ArrayList<Worker> list;


    public WorkersRecycleAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public WorkerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_worker, parent, false);
        return new WorkerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerHolder holder, int position) {
        Worker worker = list.get(position);
        holder.imageView.setImageResource(worker.getId_drawable());
        holder.twName.setText(worker.getName());
        holder.twOffice.setText(worker.getWorkname());
        holder.layout.setVisibility(worker.isWorkNow() ? View.VISIBLE : View.INVISIBLE);
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addWorker(Worker worker) {
        if (worker.isWorkNow()) {
            list.add(0, worker);
        } else list.add(worker);
        notifyDataSetChanged();
    }

    public void addWorkers(List<Worker> workerList) {
        list.addAll(workerList);
        notifyDataSetChanged();
    }

    static class WorkerHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView twName, twOffice;
        LinearLayout layout;
        View view;

        public WorkerHolder(View view) {
            super(view);
            this.view = view;
            imageView = view.findViewById(R.id.face_worker);
            twName = view.findViewById(R.id.name_worker);
            twOffice = view.findViewById(R.id.office);
            layout = view.findViewById(R.id.smena_layout);

        }
    }
}
