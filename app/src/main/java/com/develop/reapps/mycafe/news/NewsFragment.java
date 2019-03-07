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
import com.develop.reapps.mycafe.server.news.NewsClient;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        context = getContext();
        RecyclerView recyclerView = view.findViewById(R.id.list_news);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        List<New> list = new ArrayList<>();
        NewsAdapter adapter = new NewsAdapter(list);
        recyclerView.setAdapter(adapter);
        initList(list, adapter);
        return view;
    }

    private void initList(List<New> list, NewsAdapter adapter) {
        new NewsClient(context).getNews(list, adapter);
    }

}