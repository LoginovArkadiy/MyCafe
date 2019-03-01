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
import com.develop.reapps.mycafe.server.tabaccos.TobaccoClient;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaffinFragment extends Fragment {
    private View rootView;
    private Context context;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        rootView = inflater.inflate(R.layout.fragment_simple_menu, container, false);
        initList();
        return rootView;
    }

    private void initList() {
        RecyclerView listView = rootView.findViewById(R.id.list_product);
        ProductAdapter adapter = new ProductAdapter();
        listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        listView.setAdapter(adapter);
        adapter.addProducts(createBeginProducts());
    }


    private List<Product> createBeginProducts() {
       /* for (int i = 0; i < 3; i++) {
            products.add(new Product("РРРРРР", "Порычим на 200 ррррублей?", R.drawable.maffin, (int) (Math.random() * 500 + 150), (int) (Math.random() * 500 + 150)));
            products.add(new Product("Милашка", "За твоим столиком будет милый хотя бы кекс", R.drawable.maffintwo, (int) (Math.random() * 500 + 150), (int) (Math.random() * 500 + 150)));
            products.add(new Product("Хнык", "Не плачь - не ты одна жирная", R.drawable.maffinthree, (int) (Math.random() * 500 + 150), (int) (Math.random() * 500 + 150)));
            products.add(new Product("Angryer", "Не злись, в следующий раз победим)", R.drawable.maffinfour, (int) (Math.random() * 500 + 150), (int) (Math.random() * 500 + 150)));
        }*/

        Product[] tabacs = new TobaccoClient(context).getTobaccos();
        return Arrays.asList(tabacs);
    }


}
