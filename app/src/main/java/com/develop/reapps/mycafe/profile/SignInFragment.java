package com.develop.reapps.mycafe.profile;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.server.retrofit.Requests;
import com.develop.reapps.mycafe.server.user.User;
import com.develop.reapps.mycafe.server.user.UserClient;

public class SignInFragment extends Fragment {
    View rootView;
    Context context;
    EditText editEmailLogin, editPasswordLogin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        context = getContext();
        initView();
        return rootView;
    }

    private void initView() {
        editEmailLogin = rootView.findViewById(R.id.email_login);
        editPasswordLogin = rootView.findViewById(R.id.pass_login);

        Button btLogin = rootView.findViewById(R.id.bt_login);
        btLogin.setOnClickListener(view -> Requests.makeToastNotification(context, signIn()));
    }

    private int signIn() {
        String email = editEmailLogin.getText().toString();
        String password = editPasswordLogin.getText().toString();
        User user = new UserClient(context).getUserByEmail(email);
        String login = user == null ? "ЛОГИН" : user.getLogin();
        int status = new UserClient(context).signIn(email, password);
        if (status == 200) {
            editEmailLogin.setText("");
            editPasswordLogin.setText("");
            ((OnProfileDataListener) context).safeData(email);
        }
        return status;
    }

}
