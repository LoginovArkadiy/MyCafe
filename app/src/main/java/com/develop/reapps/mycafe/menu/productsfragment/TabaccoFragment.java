package com.develop.reapps.mycafe.menu.productsfragment;


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

import com.develop.reapps.mycafe.Product;
import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.menu.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabaccoFragment extends Fragment {
    private View rootView;
    private Context context;

    public TabaccoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_simple_menu, container, false);
        context = getContext();
        initList();
        return rootView;
    }

    private void initList() {
        RecyclerView listView = rootView.findViewById(R.id.list_product);
        ProductAdapter adapter = new ProductAdapter(true);
        listView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        listView.setAdapter(adapter);
        adapter.addProducts(createBeginProducts());
    }


    private List<Product> createBeginProducts() {
        List<Product> list = new ArrayList<>();
        String s = "Какое то длинное описание чтоб было покрасивше напишу побольше ы - ЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫЫ";
        for (int i = 0; i < 3; i++) {
            list.add(new Product("DarkSide", s, R.drawable.darkside, (int) (Math.random() * 500 + 150), 0));
            list.add(new Product("ВТО", s, R.drawable.tabac, (int) (Math.random() * 500 + 150), 0));
            list.add(new Product("Argelini", s, R.drawable.argelini, (int) (Math.random() * 500 + 150), 0));
            list.add(new Product("Serbetli", s, R.drawable.serbetli, (int) (Math.random() * 500 + 150), 0));
            list.add(new Product("Daily Hookah", s, R.drawable.hookah, (int) (Math.random() * 500 + 150),0));
        }
        return list;
    }

}
