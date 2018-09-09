package com.develop.reapps.mycafe.news;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.develop.reapps.mycafe.R;

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