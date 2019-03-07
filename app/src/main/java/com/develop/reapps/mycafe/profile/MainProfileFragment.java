package com.develop.reapps.mycafe.profile;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.profile.admin.AdminFragment;
import com.develop.reapps.mycafe.server.user.User;

import java.util.Objects;

public class MainProfileFragment extends Fragment {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private View rootView;
    private Context context;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_profile, container, false);
        context = getContext();
        initView();
        setData(deserialization());
        verifyStoragePermissions(getActivity());
        return rootView;
    }

    private User deserialization() {
        return ((OnProfileDataListener) Objects.requireNonNull(context)).deserialization();
    }

    private void initView() {
        viewPager = rootView.findViewById(R.id.viewpager_profile);
        tabLayout = rootView.findViewById(R.id.tablayout_profile);
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setData(User user) {
        adapter.clearPages();
        if (user != null) {
            adapter.addPage(new MyProfileFragment(), "Профиль");
            if (user.getRole() > 1) {
                adapter.addPage(new AdminFragment(), "Админ");
            } else {
                //adapter.addPage(new SignUpFragment(), "Заказы");
            }
        } else {
            adapter.addPage(new SignInFragment(), "Вход");
            adapter.addPage(new SignUpFragment(), "Регистрация");
        }
        adapter.notifyDataSetChanged();
    }


    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }
}
