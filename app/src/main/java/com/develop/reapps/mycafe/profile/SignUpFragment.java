package com.develop.reapps.mycafe.profile;


import android.content.Context;
import android.os.Bundle;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    private View rootView;
    private Context context;
    EditText editEmailSignUp, editLoginSignUp, editPasswordSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        context = getContext();
        initView();
        return rootView;
    }

    private void initView() {
        editEmailSignUp = rootView.findViewById(R.id.email_sign_up);
        editLoginSignUp = rootView.findViewById(R.id.login_sign_up);
        editPasswordSignUp = rootView.findViewById(R.id.pass_sign_up);

        Button btSignUp = rootView.findViewById(R.id.bt_sign_up);
        btSignUp.setOnClickListener(view -> Requests.makeToastNotification(context, signUp()));
    }

    private int signUp() {
        String login = editLoginSignUp.getText().toString();
        String email = editEmailSignUp.getText().toString();
        String password = editPasswordSignUp.getText().toString();
        int status = new UserClient(context).signUp(email, login, password);
        if (status == 200) {
            editLoginSignUp.setText("");
            editEmailSignUp.setText("");
            editPasswordSignUp.setText("");
            ((OnProfileDataListener) context).safeData(email);
        }
        return status;
    }

}
