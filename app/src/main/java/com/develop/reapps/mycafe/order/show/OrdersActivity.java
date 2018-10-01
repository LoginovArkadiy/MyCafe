package com.develop.reapps.mycafe.order.show;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.server.order.OrderTask;

import java.util.Arrays;

public class OrdersActivity extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        context = this;
        initList();
    }

    private void initList() {
        RecyclerView listView = findViewById(R.id.list_orders);
        listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        OrdersAdapter adapter = new OrdersAdapter();
        listView.setAdapter(adapter);
        adapter.addAll(Arrays.asList(new OrderTask(context).getOrders()));
    }
}
