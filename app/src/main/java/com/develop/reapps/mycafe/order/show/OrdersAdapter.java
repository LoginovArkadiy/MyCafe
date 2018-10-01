package com.develop.reapps.mycafe.order.show;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.develop.reapps.mycafe.R;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderHolder> {
    Context context;
    List<String> list;

    OrdersAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.order, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        String s = list.get(position);
        holder.order.setText(s);
    }

    protected void add(String s) {
        list.add(s);
        notifyDataSetChanged();
    }

    protected void addAll(List<String> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class OrderHolder extends RecyclerView.ViewHolder {
        TextView order;

        public OrderHolder(View itemView) {
            super(itemView);
            order = itemView.findViewById(R.id.tv_order);
        }
    }
}
