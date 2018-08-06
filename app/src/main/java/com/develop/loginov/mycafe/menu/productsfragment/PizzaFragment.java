package com.develop.loginov.mycafe.menu.productsfragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.develop.loginov.mycafe.Product;
import com.develop.loginov.mycafe.R;
import com.develop.loginov.mycafe.menu.ProductRecycleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class PizzaFragment extends Fragment {


    View rootView;
    Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_simple_menu, container, false);
        context = getContext();
        initList();

        return rootView;
    }

    private void initList() {
        /*ListView listView = rootView.findViewById(R.id.list_product);
        ProductAdapter adapter = new ProductAdapter(Objects.requireNonNull(getContext()));
        listView.setAdapter(adapter);*/

        RecyclerView listView = rootView.findViewById(R.id.list_product);
        ProductRecycleAdapter adapter = new ProductRecycleAdapter();
        listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        listView.setAdapter(adapter);
        adapter.addProducts(createBeginProducts());
    }


    private List<Product> createBeginProducts() {
        List<Product> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new Product("Маргарита", "Очень вкусно сытно 700 грамммммммм", R.drawable.pizza, (int) (Math.random() * 500 + 150), (int) (Math.random() * 500 + 150)));
            list.add(new Product("Жаркое солнце", "Сладкий ВКУСНЫЙ AND BEATY 200ГРАММ", R.drawable.pizzathree, (int) (Math.random() * 500 + 150), (int) (Math.random() * 500 + 150)));
            list.add(new Product("Кукусисики", "Покуапаешь один кукусик второй в подарок", R.drawable.pizzatwo, (int) (Math.random() * 500 + 150), (int) (Math.random() * 500 + 150)));
            list.add(new Product("Ащщщ", "Ащщщ горит в рту и не тольуо", R.drawable.pizzafour, (int) (Math.random() * 500 + 150), (int) (Math.random() * 500 + 150)));
            list.add(new Product("Кек", "Будешь орать", R.drawable.pizzafive, (int) (Math.random() * 500 + 150), (int) (Math.random() * 500 + 150)));
        }
        return list;
    }

}
