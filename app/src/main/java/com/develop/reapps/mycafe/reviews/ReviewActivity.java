package com.develop.reapps.mycafe.reviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.develop.reapps.mycafe.MainActivity;
import com.develop.reapps.mycafe.MyDateConverter;
import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.server.review.ReviewClient;
import com.develop.reapps.mycafe.server.user.UserClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ReviewActivity extends AppCompatActivity {

    private Context context;
    private ReviewClient task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        context = this;
        task = new ReviewClient(context);
        initView();
        initList();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        EditText editTextReview = findViewById(R.id.edit_review);
        Button btPostReview = findViewById(R.id.bt_post_review);
        btPostReview.setOnClickListener(view -> {
            String text = editTextReview.getText().toString();
            String strDate = MyDateConverter.getMoment();
            Toast toast = Toast.makeText(context, strDate, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();

            task.loadReview(text, strDate);
        });
        btPostReview.setOnTouchListener(MainActivity.onTouchListener);

    }

    private void initList() {
        RecyclerView recyclerView = findViewById(R.id.list_reviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        ReviewsAdapter adapter = new ReviewsAdapter();
        for (Review review : createReviews()) {
            String login;
            try {
                login = new UserClient(context).getUserById(review.getAuthorId()).getLogin();
            } catch (NullPointerException e) {
                login = "Anonim";
            }

            review.setLogin(login);
            adapter.addReview(review);
        }
        recyclerView.setAdapter(adapter);
    }

    private Review[] createReviews() {
        return task.getReviews();
    }

}
