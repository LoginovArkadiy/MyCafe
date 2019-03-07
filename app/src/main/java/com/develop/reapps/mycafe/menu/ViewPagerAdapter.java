package com.develop.reapps.mycafe.menu;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Vibrator;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.server.retrofit.Requests;
import com.develop.reapps.mycafe.server.sections.SectionClient;
import com.develop.reapps.mycafe.server.tastes.TasteClient;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Page> pageList;
    private Context context;
    private Vibrator vibrator;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public ViewPagerAdapter(FragmentManager fm, Context context, ViewPager viewPager, TabLayout tabLayout) {
        super(fm);
        this.context = context;
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
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

    public void addPage(Fragment fragment, String title, int id) {

        pageList.add(new Page(fragment, title, createView(title, id)));
    }

    public List<Page> getPageList() {
        return pageList;
    }

    private View createView(String title, int id) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_tab_layout, null);
        ((TextView) view.findViewById(R.id.tabtext)).setText(title);
        int index = pageList.size();
        view.setOnClickListener(v -> selectPage(index));
        view.setOnLongClickListener(v -> {
            long mills = 200L;
            if (vibrator != null && vibrator.hasVibrator()) {
                vibrator.vibrate(mills);
            }
            if (id == -1) {
                Requests.makeToastNotification(context, "Чтобы удалить табаки следует связаться с разработчиками!");
                return true;
            }
            AlertDialog.Builder ad = new AlertDialog.Builder(context);
            ad.setCancelable(true).setTitle("Вы уверены что хотите удалить эту секцию?").setPositiveButton("Да", (dialog, which) -> new SectionClient(context).delete(id));
            ad.create().show();
            Requests.makeToastNotification(context, "Вы нажали на " + title);
            return true;
        });

        return view;
    }


    public View getTabView(int position) {
        return pageList.get(position).getTabView();
    }

    void selectPage(int position) {
        tabLayout.setScrollPosition(position, 0f, true);
        viewPager.setCurrentItem(position);
    }
    class Page {
        private Fragment fragment;
        private String title;
        private View view;

        Page(Fragment fragment, String title, View view) {
            this.fragment = fragment;
            this.title = title;
            this.view = view;
        }

        public View getTabView() {
            return view;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public String getTitle() {
            return title;
        }
    }
}



