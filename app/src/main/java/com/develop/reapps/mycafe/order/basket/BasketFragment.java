package com.develop.reapps.mycafe.order.basket;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.develop.reapps.mycafe.MainActivity;
import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.Product;

import java.util.ArrayList;
import java.util.HashMap;


public class BasketFragment extends Fragment {
    public static final String LIST_PRODUCTS = "list_products";
    static TextView twAllCast;
    View rootView;
    BasketAdapter adapter;
    Context context;
    ArrayList<Product> products;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_basket, container, false);
        twAllCast = rootView.findViewById(R.id.basket_all_cast);
        context = getContext();
        Bundle args = getArguments();
        HashMap<Product, Integer> hashMap = (HashMap<Product, Integer>) args.getSerializable(LIST_PRODUCTS);
        products = createList(hashMap);
        setListeners();
        initList();

        return rootView;
    }

    private void setListeners() {
        Button bCreateOrder = rootView.findViewById(R.id.create_order);
        bCreateOrder.setOnClickListener(v -> ((OnCreateOrderListener) context).createOrder());
        bCreateOrder.setOnTouchListener(MainActivity.onTouchListener);
        Button clearBasket = rootView.findViewById(R.id.clear_button);
        clearBasket.setOnTouchListener(MainActivity.onTouchListener);
        clearBasket.setOnClickListener(v -> clear());
    }

    private void initList() {
        RecyclerView listView = rootView.findViewById(R.id.list_product);
        adapter = new BasketAdapter();
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        adapter.addProducts(products);
    }

    ArrayList<Product> createList(HashMap<Product, Integer> hashMap) {
        ArrayList<Product> arrayList = new ArrayList<>();
        for (Product p : hashMap.keySet()) {
            p.setCount(hashMap.get(p));
            arrayList.add(p);
        }

        return arrayList;
    }

    public void clear() {
        adapter.clearBasket();
        OnClearBasketListener listener = (OnClearBasketListener) context;
        listener.clearBasket();
    }


    @SuppressLint("SetTextI18n")
    static void changeBasket(int cast) {
        twAllCast.setText(cast + " руб");
    }


    public interface OnClearBasketListener {
        void clearBasket();
    }

    public interface OnCreateOrderListener {
        void createOrder();
    }
}
