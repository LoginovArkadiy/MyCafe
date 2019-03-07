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

import com.develop.reapps.mycafe.menu.element.Product;
import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.menu.ProductAdapter;
import com.develop.reapps.mycafe.menu.element.Tobacco;
import com.develop.reapps.mycafe.server.tabaccos.TobaccoClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TobaccoFragment extends Fragment {
    private View rootView;
    private Context context;

    public TobaccoFragment() {
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
        List<Product> list = new ArrayList<>();
        ProductAdapter adapter = new ProductAdapter(list, true);
        listView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        listView.setAdapter(adapter);
        createBeginProducts(list, adapter);
    }


    private void createBeginProducts(List<Product> list, ProductAdapter adapter) {
        new TobaccoClient(context).getTobaccos(list, adapter);
    }

}
