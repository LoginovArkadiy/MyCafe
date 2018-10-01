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
        New[] myNews = new NewsClient(context).getNews();
        for(int i = myNews.length - 1; i > -1; i--){
            adapter.addNew(myNews[i]);
        }
        adapter.notifyDataSetChanged();
    }


}