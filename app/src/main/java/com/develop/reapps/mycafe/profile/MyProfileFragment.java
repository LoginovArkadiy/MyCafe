package com.develop.reapps.mycafe.profile;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.server.retrofit.Requests;
import com.develop.reapps.mycafe.server.user.User;
import com.develop.reapps.mycafe.server.user.UserClient;


public class MyProfileFragment extends Fragment {
    private View rootView;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);
        context = getContext();
        initView();

        return rootView;
    }

    private void initView() {
        TextView tvUsername = rootView.findViewById(R.id.login_user);
        TextView tvEmail = rootView.findViewById(R.id.email_user);
        User user = ((OnProfileDataListener) context).deserialization();
        tvUsername.setText(user.getLogin());
        tvEmail.setText(user.getEmail());
        Button btExit = rootView.findViewById(R.id.bt_exit);
        btExit.setOnClickListener(view -> Requests.makeToastNotification(context, logout()));
    }

    private int logout() {
        int status = new UserClient(context).logOut();
        if (status == 200) ((OnProfileDataListener) context).removeData();
        return status;
    }
}
