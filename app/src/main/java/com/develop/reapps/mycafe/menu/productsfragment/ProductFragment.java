package com.develop.reapps.mycafe.menu.productsfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.develop.reapps.mycafe.Product;
import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.menu.ProductRecycleAdapter;
import com.develop.reapps.mycafe.server.products.ProductClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProductFragment extends Fragment {
    private View rootView;
    private Context context;
    String section;
    public static String SECTION_KEY = "SECTION_KEY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        Bundle args = getArguments();
        assert args != null;
        section = args.getString(SECTION_KEY);
        rootView = inflater.inflate(R.layout.fragment_simple_menu, container, false);
        initList();
        return rootView;
    }

    private void initList() {
        RecyclerView listView = rootView.findViewById(R.id.list_product);
        ProductRecycleAdapter adapter = new ProductRecycleAdapter();
        listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        listView.setAdapter(adapter);
        adapter.addProducts(Arrays.asList(createBeginProducts()));
    }


    private Product[] createBeginProducts() {
        return new ProductClient(context).getProductsByType(section);
    }


}
