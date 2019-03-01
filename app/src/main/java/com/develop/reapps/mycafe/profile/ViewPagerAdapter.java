package com.develop.reapps.mycafe.profile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Page> pageList;


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        pageList = new ArrayList<>();
    }


    @Override
    public Fragment getItem(int position) {
        return pageList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return pageList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageList.get(position).getTitle();
    }

    public void addPage(Fragment fragment, String title) {
        pageList.add(new Page(fragment, title));
        notifyDataSetChanged();
    }

    public List<Page> getPageList() {
        return pageList;
    }

    public void clearPages() {
        pageList = new ArrayList<>();
        pageList.clear();
        notifyDataSetChanged();
    }

    class Page {
        private Fragment fragment;
        private String title;

        Page(Fragment fragment, String title) {
            this.fragment = fragment;
            this.title = title;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public String getTitle() {
            return title;
        }
    }
}


