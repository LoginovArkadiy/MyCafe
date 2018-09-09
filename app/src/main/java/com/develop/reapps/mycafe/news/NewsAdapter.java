package com.develop.reapps.mycafe.news;

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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private List<New> listNews;

    private Context context;

    public NewsAdapter() {
        listNews = new ArrayList<>();
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        New myNew = listNews.get(position);
        holder.imageView.setImageResource(myNew.getDrawableId());
        holder.title.setText(myNew.getName());
        holder.description.setText(myNew.getDescription());
        holder.time.setText(myNew.getTime());
    }

    @Override
    public int getItemCount() {
        return listNews.size();
    }

    public void addNew(New myNew) {
        listNews.add(myNew);

    }

    static class NewsHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, description, time;
        ImageView review;

        public NewsHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_new);
            title = itemView.findViewById(R.id.name_new);
            time = itemView.findViewById(R.id.time_new);
            description = itemView.findViewById(R.id.description_new);

            review = itemView.findViewById(R.id.bt_review_new);
        }
    }
}
