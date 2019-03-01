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

import com.develop.reapps.mycafe.menu.element.Product;
import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.menu.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

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
        RecyclerView listView = rootView.findViewById(R.id.list_product);
        ProductAdapter adapter = new ProductAdapter();
        listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        listView.setAdapter(adapter);
        adapter.addProducts(createBeginProducts());
        /*for (Product p: createArray()) {
            adapter.addProducts(p);
        }*/
    }

    private List<Product> createBeginProducts() {
        List<Product> products = new ArrayList<>();
        /*for (int i = 0; i < 3; i++) {
            products.add(new Product("Маргарита", "Очень вкусно сытно 700 грамммммммм", R.drawable.pizza, (int) (Math.random() * 500 + 150), (int) (Math.random() * 500 + 150)));
            products.add(new Product("Жаркое солнце", "Сладкий ВКУСНЫЙ AND BEATY 200ГРАММ", R.drawable.pizzathree, (int) (Math.random() * 500 + 150), (int) (Math.random() * 500 + 150)));
            products.add(new Product("Кукусисики", "Покуапаешь один кукусик второй в подарок", R.drawable.pizzatwo, (int) (Math.random() * 500 + 150), (int) (Math.random() * 500 + 150)));
            products.add(new Product("Ащщщ", "Ащщщ горит в рту и не тольуо", R.drawable.pizzafour, (int) (Math.random() * 500 + 150), (int) (Math.random() * 500 + 150)));
            products.add(new Product("Кек", "Будешь орать", R.drawable.pizzafive, (int) (Math.random() * 500 + 150), (int) (Math.random() * 500 + 150)));
        }*/
        return products;
    }

    private Product[] createArray() {/*
        MenuGetTask task = new MenuGetTask();
        task.execute(0);
        Product[] array = new Product[0];
        try {
            array = task.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return array;*/
        return new Product[0];
    }

}
