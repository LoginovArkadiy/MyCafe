package com.develop.reapps.mycafe;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import com.develop.reapps.mycafe.order.Order;
import com.develop.reapps.mycafe.order.OrderFragment;
import com.develop.reapps.mycafe.order.basket.BasketFragment;
import com.develop.reapps.mycafe.order.basket.BasketAdapter;
import com.develop.reapps.mycafe.menu.MenuFragment;
import com.develop.reapps.mycafe.news.NewsFragment;
import com.develop.reapps.mycafe.order.hall.Hall;
import com.develop.reapps.mycafe.order.hall.HallFragment;
import com.develop.reapps.mycafe.order.hall.tabletime.TableFragment;
import com.develop.reapps.mycafe.order.show.OrdersActivity;
import com.develop.reapps.mycafe.profile.ProfileFragment;
import com.develop.reapps.mycafe.reviews.ReviewActivity;
import com.develop.reapps.mycafe.server.order.OrderTask;
import com.develop.reapps.mycafe.server.retrofit.Requests;
import com.develop.reapps.mycafe.workers.WorkersFragment;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnAddProductListener, BasketFragment.OnClearBasketListener, BasketAdapter.OnChangeCountProductListener, BasketFragment.OnCreateOrderListener, Hall.OnClickTableListener, OnBackHomeInterface, TableFragment.OnPostOrderListener {

    HashMap<Product, Integer> mapProducts;
    public static boolean ADMIN;
    private BottomNavigationView navigation;
    OrderFragment orderFragment;
    static Context context;

    Order nowOrder;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
        getSupportFragmentManager().beginTransaction().add(R.id.rootfragment, new NewsFragment()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.rootfragment, new ProfileFragment()).commit();
        mapProducts = new HashMap<>();
        ADMIN = false;
        context = this;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contact_item:
                break;
            case R.id.about_item:
                break;
            case R.id.reviews_item:
                startActivity(new Intent().setClass(context, ReviewActivity.class));
                break;
            case R.id.orders:
                startActivity(new Intent().setClass(context, OrdersActivity.class));
                break;
            case R.id.admin:
                ADMIN = !ADMIN;

                if (ADMIN) item.setTitle("Простолюдин");
                else item.setTitle("Админ");
                changeFragment(new NewsFragment());
                navigation.setSelectedItemId(R.id.navigation_news);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = (item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                changeFragment(new ProfileFragment());
                return true;
            case R.id.navigation_workers:
                changeFragment(new WorkersFragment());
                return true;
            case R.id.navigation_menu:
                changeFragment(new MenuFragment());
                return true;
            case R.id.navigation_order:
                nowOrder = new Order();
                nowOrder.setHashMap(mapProducts);
                orderFragment = new OrderFragment();

                Bundle args = new Bundle();
                args.putSerializable(BasketFragment.LIST_PRODUCTS, mapProducts);
                orderFragment.setArguments(args);
                changeFragment(orderFragment);
                return true;
            case R.id.navigation_news:
                changeFragment(new NewsFragment());
                return true;
        }
        return false;
    });

    @Override
    public void onBackPressed() {
        nowOrder = null;
        changeFragment(new NewsFragment());
        navigation.setSelectedItemId(R.id.navigation_news);
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
        ft.replace(R.id.rootfragment, fragment);
        ft.commit();
    }

    @Override
    public void addProductToBasket(Product product, int count) {
        Toast.makeText(getApplicationContext(), product.getName(), Toast.LENGTH_SHORT).show();
        Integer value = mapProducts.containsKey(product) ? mapProducts.get(product) + count : count;
        mapProducts.put(product, value);
    }


    @Override
    public void clearBasket() {
        mapProducts.clear();
    }

    @Override
    public void changeCount(Product product) {
        if (product.getCount() == 0) mapProducts.remove(product);
        else mapProducts.put(product, product.getCount());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (orderFragment != null) orderFragment.clear();
    }


    @SuppressLint("ClickableViewAccessibility")
    public static View.OnTouchListener onTouchListener = (v, event) -> {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setBackgroundResource(R.color.ltgrey);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                v.setBackgroundResource(R.color.white);
                break;
        }

        return false;
    };

    @Override
    public void createOrder() {
        orderFragment.setHallFragment(new HallFragment());
    }

    @Override
    public void clickTable(int numberTable) {
        nowOrder.setNumberTable(numberTable);
        if (orderFragment != null) orderFragment.setTableFragment(new TableFragment());
    }

    @Override
    public void backToHome() {
        navigation.setSelectedItemId(R.id.navigation_home);
        changeFragment(new ProfileFragment());
    }


    @Override
    public void onPostOrder(String t1, String t2) {
        nowOrder.setBeginTime(t1);
        nowOrder.setEndTime(t2);
        if (nowOrder.isGood()) {
            OrderTask task = new OrderTask(context);
            task.loadOrder(nowOrder.getOrder());
        } else {
            Requests.makeToastNotification(context, "Заказ сформирован неправильно");
        }
    }

    public static Context getAppContext() {
        return context;
    }
}
