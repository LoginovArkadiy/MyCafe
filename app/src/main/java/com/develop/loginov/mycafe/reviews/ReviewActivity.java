package com.develop.loginov.mycafe.reviews;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.develop.loginov.mycafe.R;
import com.develop.loginov.mycafe.server.Requests;
import com.develop.loginov.mycafe.server.review.ReviewPostTask;
import com.develop.loginov.mycafe.server.review.ReviewsGetTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class ReviewActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        context = this;
        initView();
        initList();
    }

    private void initView() {
        EditText editTextReview = findViewById(R.id.edit_review);
        findViewById(R.id.bt_post_review).setOnClickListener(view -> {
            String text = editTextReview.getText().toString();
            String date = "22 августа";
            ReviewPostTask task = new ReviewPostTask();
            task.execute(text, date);
            try {
                Requests.makeToastNotification(context, task.get(500, TimeUnit.MILLISECONDS).status);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        });

    }

    private void initList() {
        RecyclerView recyclerView = findViewById(R.id.list_reviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        ReviewsAdapter adapter = new ReviewsAdapter();
        for (Review review : createReviews()) {
            adapter.addReview(review);
        }
        recyclerView.setAdapter(adapter);


    }

    private Review[] createReviews() {
        List<Review> list = new ArrayList<>();
        list.add(new Review("ВОООООББЩЕ КРУТецкое место всем советую 5+", "22 августа 2018", "logArk1"));
        list.add(new Review("ВОООООББЩЕ КРУТецкое место всем советую 5+", "22 августа 2018", "logArk2"));
        list.add(new Review("ВОООООББЩЕ КРУТецкое место всем советую 5+", "22 августа 2018", "logArk3"));
        list.add(new Review("ВОООООББЩЕ КРУТецкое место всем советую 5+", "22 августа 2018", "logArk4"));
        list.add(new Review("ВОООООББЩЕ КРУТецкое место всем советую 5+", "22 августа 2018", "logArk5"));
        list.add(new Review("ВОООООББЩЕ КРУТецкое место всем советую 5+", "22 августа 2018", "logArk6"));
        list.add(new Review("ВОООООББЩЕ КРУТецкое место всем советую 5+", "22 августа 2018", "logArk7"));
        return listToArray(list);

       /* ReviewsGetTask task = new ReviewsGetTask();
        task.execute();
        try {
            Review[] reviews = task.get(500, TimeUnit.MILLISECONDS);
            return reviews;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return new Review[0];*/
    }

    private Review[] listToArray(List<Review> list) {
        Review[] array = new Review[list.size()];
        int i = 0;
        for (Review r : list) {
            array[i] = r;
            i++;
        }
        return array;
    }
}
