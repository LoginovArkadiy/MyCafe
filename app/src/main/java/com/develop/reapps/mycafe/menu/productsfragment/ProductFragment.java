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

import com.develop.reapps.mycafe.menu.element.Product;
import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.menu.ProductAdapter;
import com.develop.reapps.mycafe.server.products.ProductClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductFragment extends Fragment {
    private View rootView;
    private Context context;
    public static String SECTION_KEY = "SECTION_KEY";
    private String section;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        assert args != null;
        section = args.getString(SECTION_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        rootView = inflater.inflate(R.layout.fragment_simple_menu, container, false);
        initList(section);
        return rootView;
    }

    private void initList(String section) {
        RecyclerView listView = rootView.findViewById(R.id.list_product);
        List<Product> productList = new ArrayList<>();
        ProductAdapter adapter = new ProductAdapter(productList);
        listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        listView.setAdapter(adapter);
        createBeginProducts(section, productList, adapter);
    }

    private void createBeginProducts(String type, List<Product> productList, ProductAdapter adapter) {
        new ProductClient(context).getProductsByType(type, productList, adapter);
    }

}
