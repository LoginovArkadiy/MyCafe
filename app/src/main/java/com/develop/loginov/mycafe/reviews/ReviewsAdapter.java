package com.develop.loginov.mycafe.reviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.develop.loginov.mycafe.R;

import java.util.ArrayList;
import java.util.List;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder> {
    private List<Review> reviews;
    private Context context;

    protected ReviewsAdapter() {
        reviews = new ArrayList<>();
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);
        return new ReviewsAdapter.ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.name.setText(review.getLogin());
        holder.text.setText(review.getText());
        holder.date.setText(review.getDate());
        holder.iv.setImageResource(R.drawable.ic_launcher_foreground);
    }

    protected void addReview(Review review) {
        reviews.add(review);
        notifyDataSetChanged();
    }

    protected void addReview(List<Review> reviews) {
        this.reviews.addAll(reviews);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    static class ReviewHolder extends RecyclerView.ViewHolder {
        TextView name, text, date;
        ImageView iv;

        ReviewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.login_review);
            text = view.findViewById(R.id.text_review);
            date = view.findViewById(R.id.date_review);
            iv = view.findViewById(R.id.image_review);
        }
    }
}
