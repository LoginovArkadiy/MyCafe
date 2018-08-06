package com.develop.loginov.mycafe.menu;


import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.develop.loginov.mycafe.Product;
import com.develop.loginov.mycafe.R;
import com.develop.loginov.mycafe.menu.productsfragment.MaffinFragment;
import com.develop.loginov.mycafe.menu.productsfragment.PizzaFragment;
import com.develop.loginov.mycafe.menu.productsfragment.TabacFragment;

import java.util.HashMap;

public class MenuFragment extends Fragment {
    ViewPager viewPager;
    ViewPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addPage(new PizzaFragment(), "Пиццы");
        adapter.addPage(new MaffinFragment(), "Маффины");
        adapter.addPage(new TabacFragment(), "Табаки");
       /* adapter.addPage(new PizzaFragment(), "Напитки");
        adapter.addPage(new PizzaFragment(), "Десерты");
        adapter.addPage(new PizzaFragment(), "Табаки");
        adapter.addPage(new PizzaFragment(), "Прочее");*/
        viewPager.setAdapter(adapter);
    }

    public HashMap<Product, Integer> getBasketProducts() {
        HashMap<Product, Integer> hm = new HashMap<>();
        for (Page p : adapter.getPageList()) {

        }


        return hm;
    }


}
