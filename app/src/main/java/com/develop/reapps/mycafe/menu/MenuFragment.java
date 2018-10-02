package com.develop.reapps.mycafe.menu;


import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.develop.reapps.mycafe.Product;
import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.menu.productsfragment.MaffinFragment;
import com.develop.reapps.mycafe.menu.productsfragment.PizzaFragment;
import com.develop.reapps.mycafe.menu.productsfragment.ProductFragment;
import com.develop.reapps.mycafe.menu.productsfragment.TabaccoFragment;
import com.develop.reapps.mycafe.server.sections.Section;
import com.develop.reapps.mycafe.server.sections.SectionTask;

import java.util.HashMap;

public class MenuFragment extends Fragment {
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        viewPager = rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = rootView.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        SectionTask sectionTask = new SectionTask(getContext());
        Section[] sections = sectionTask.getSections();
       // adapter.addPage(new PizzaFragment(), "Pizza");
        //adapter.addPage(new MaffinFragment(), "Маффины");
        //adapter.addPage(new TabaccoFragment(), "Табаки");
        for (Section section : sections) {
            Fragment productFragment = new ProductFragment();
            Bundle args = new Bundle();
            args.putString(ProductFragment.SECTION_KEY, section.getSection());
            productFragment.setArguments(args);
            adapter.addPage(productFragment, section.getSection());
        }
        viewPager.setAdapter(adapter);
    }


}
