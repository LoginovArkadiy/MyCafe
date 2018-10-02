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
import com.develop.reapps.mycafe.menu.ProductAdapter;
import com.develop.reapps.mycafe.server.products.ProductClient;

import java.util.Arrays;

public class ProductFragment extends Fragment {
    private View rootView;
    private Context context;
    public static String SECTION_KEY = "SECTION_KEY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        Bundle args = getArguments();
        assert args != null;
        String section = args.getString(SECTION_KEY);
        rootView = inflater.inflate(R.layout.fragment_simple_menu, container, false);
        initList(section);
        return rootView;
    }

    private void initList(String section) {
        RecyclerView listView = rootView.findViewById(R.id.list_product);
        ProductAdapter adapter = new ProductAdapter();
        listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        listView.setAdapter(adapter);
        adapter.addProducts(Arrays.asList(createBeginProducts(section)));
    }


    private Product[] createBeginProducts(String section) {
        return new ProductClient(context).getProductsByType(section);
    }


}
