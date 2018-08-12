package com.develop.loginov.mycafe;


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
import android.widget.Toast;


import com.develop.loginov.mycafe.basket.BasketFragment;
import com.develop.loginov.mycafe.basket.BasketRecycleAdapter;
import com.develop.loginov.mycafe.menu.MenuFragment;
import com.develop.loginov.mycafe.news.NewsFragment;
import com.develop.loginov.mycafe.profile.ProfileFragment;
import com.develop.loginov.mycafe.reviews.ReviewActivity;
import com.develop.loginov.mycafe.workers.WorkersFragment;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnAddProductListener, BasketFragment.OnClearBasketListener, BasketRecycleAdapter.OnChangeCountProductListener {

    HashMap<Product, Integer> mapProducts;
    public static boolean ADMIN;
    private BottomNavigationView navigation;
    BasketFragment basketFragment;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_workers);
        getSupportFragmentManager().beginTransaction().add(R.id.rootfragment, new WorkersFragment()).commit();
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
                startActivity(new Intent(context, ReviewActivity.class));
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
            case R.id.navigation_basket:
                basketFragment = new BasketFragment();
                Bundle args = new Bundle();
                args.putSerializable(BasketFragment.LIST_PRODUCTS, mapProducts);
                basketFragment.setArguments(args);
                changeFragment(basketFragment);
                return true;
            case R.id.navigation_news:
                changeFragment(new NewsFragment());
                return true;
        }
        return false;
    });


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
        if (basketFragment != null) basketFragment.clear();

    }
}
