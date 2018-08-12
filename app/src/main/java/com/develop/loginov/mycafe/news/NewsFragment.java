package com.develop.loginov.mycafe.news;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.develop.loginov.mycafe.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsFragment extends Fragment {

    NewsAdapter adapter;

    public NewsFragment() {
    }

    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        context = getContext();

        RecyclerView recyclerView = view.findViewById(R.id.list_news);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        adapter = new NewsAdapter();
        recyclerView.setAdapter(adapter);
        initList();
        return view;
    }

    private void initList() {
        adapter.addNew(new New("Новый сотрудник", "Знакомьтесь - наш бармен Барт!)", R.drawable.bart, "5 августа 14:02"));
        adapter.addNew(new New("АКЦИЯ", "Если Вы Егор и ваш друг Никита, то вам хорошо!", R.drawable.friends, "7 августа 14:02"));
        adapter.addNew(new New("У нас новый табак", "Смотрите какая красивая лэйбл", R.drawable.tabac, "10 августа 14:02"));
        adapter.addNew(new New("Новый сотрудник", "Знакомьтесь - наш менджер Мардж!)", R.drawable.wife, "12 августа 14:02"));
        adapter.addNew(new New("АКЦИЯ", "1+1 = 2, покупаете наш Бургер Arifmetic и наш официант поможет вам разобраться с домашним заданием", R.drawable.stock, "12 августа 14:02"));

    }


}