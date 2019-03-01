package com.develop.reapps.mycafe.menu;


import android.content.Context;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.menu.productsfragment.ProductFragment;
import com.develop.reapps.mycafe.menu.productsfragment.TabaccoFragment;
import com.develop.reapps.mycafe.server.sections.Section;
import com.develop.reapps.mycafe.server.sections.SectionClient;

public class MenuFragment extends Fragment {
    ViewPager viewPager;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        viewPager = rootView.findViewById(R.id.viewpager_menu);
        context = getContext();
        TabLayout tabLayout = rootView.findViewById(R.id.tablayout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        setupViewPager(viewPager, tabLayout);
        return rootView;
    }

    private void setupViewPager(ViewPager viewPager, TabLayout tabLayout) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager(), context, viewPager, tabLayout);
        SectionClient sectionClient = new SectionClient(context);
        Section[] sections = sectionClient.getSections();
        // adapter.addPage(new PizzaFragment(), "Pizza");
        //adapter.addPage(new MaffinFragment(), "Маффины");

        adapter.addPage(new TabaccoFragment(), "Табаки", -1);
        for (Section section : sections) {
            Fragment productFragment = new ProductFragment();
            Bundle args = new Bundle();
            args.putString(ProductFragment.SECTION_KEY, section.getSection());
            productFragment.setArguments(args);
            adapter.addPage(productFragment, section.getSection(), section.getId());
        }

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(adapter.getTabView(i));
        }



    }


}
