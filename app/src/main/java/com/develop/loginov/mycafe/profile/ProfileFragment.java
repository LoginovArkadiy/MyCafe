package com.develop.loginov.mycafe.profile;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.develop.loginov.mycafe.MainActivity;
import com.develop.loginov.mycafe.Product;
import com.develop.loginov.mycafe.R;
import com.develop.loginov.mycafe.UserInfo;
import com.develop.loginov.mycafe.news.New;
import com.develop.loginov.mycafe.server.Requests;
import com.develop.loginov.mycafe.server.news.NewPostTask;
import com.develop.loginov.mycafe.server.products.ProductPostTask;
import com.develop.loginov.mycafe.server.user.UserLogOutTask;
import com.develop.loginov.mycafe.server.user.UserSignInTask;
import com.develop.loginov.mycafe.server.user.UserSignUpTask;
import com.develop.loginov.mycafe.server.workers.WorkerPostTask;
import com.develop.loginov.mycafe.workers.Worker;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private static final int GALLERY_REQUEST = 1;
    public static final String APP_PREFERENCES = "profile";
    public static final String APP_PREFERENCES_USERNAME = "username";
    public static final String APP_PREFERENCES_EMAIL = "email";
    public static final String APP_PREFERENCES_PASSWORD = "password";

    CardView userInfoCard, loginCard, signUpCard;
    TextView tvUsername, tvEmail;
    EditText editEmailLogin, editPasswordLogin, editEmailSignUp, editPasswordSignUp, editLoginSignUp;
    ImageView nowImageView;
    Context context;
    View rootView;
    Bitmap bitmap;
    private SharedPreferences myProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        context = getContext();

        assert context != null;
        myProfile = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        initView();
        deserialization();


        if (MainActivity.ADMIN) {
            createAdminAdderView();
        } else {
            View view1 = rootView.findViewById(R.id.likeproducts);
            view1.setVisibility(View.VISIBLE);
        }

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground);
        return rootView;
    }

    private void initView() {
        tvUsername = rootView.findViewById(R.id.login_user);
        tvEmail = rootView.findViewById(R.id.email_user);

        editEmailLogin = rootView.findViewById(R.id.email_login);
        editPasswordLogin = rootView.findViewById(R.id.pass_login);

        editEmailSignUp = rootView.findViewById(R.id.email_sign_up);
        editLoginSignUp = rootView.findViewById(R.id.login_sign_up);
        editPasswordSignUp = rootView.findViewById(R.id.pass_sign_up);

        loginCard = rootView.findViewById(R.id.card_login);
        userInfoCard = rootView.findViewById(R.id.card_user_info);
        signUpCard = rootView.findViewById(R.id.card_sign_up);

        setButtonListeners();
    }

    private void setButtonListeners() {
        rootView.findViewById(R.id.bt_login).setOnClickListener(view -> Requests.makeToastNotification(context, signIn()));
        rootView.findViewById(R.id.bt_exit).setOnClickListener(view -> Requests.makeToastNotification(context, logout()));
        rootView.findViewById(R.id.bt_sign_up).setOnClickListener(view -> Requests.makeToastNotification(context, signUp()));
    }

    private void deserialization() {
        if (myProfile.contains(APP_PREFERENCES_USERNAME) && myProfile.contains(APP_PREFERENCES_EMAIL)) {
            signUpCard.setVisibility(View.GONE);
            loginCard.setVisibility(View.GONE);
            userInfoCard.setVisibility(View.VISIBLE);

            String email = myProfile.getString(APP_PREFERENCES_EMAIL, "");
            String login = myProfile.getString(APP_PREFERENCES_USERNAME, "");
            String password = myProfile.getString(APP_PREFERENCES_PASSWORD, "");

            UserInfo.email = email;
            UserInfo.login = login;
            UserInfo.password = password;

            tvUsername.setText(login);
            tvEmail.setText(email);
        } else {
            signUpCard.setVisibility(View.VISIBLE);
            loginCard.setVisibility(View.VISIBLE);
            userInfoCard.setVisibility(View.GONE);
        }

    }

    private int signIn() {
        String email = editEmailLogin.getText().toString();
        String password = editPasswordLogin.getText().toString();
        UserSignInTask task = new UserSignInTask();
        task.execute(email, password);
        int status = -1;
        try {
            status = task.get(500, TimeUnit.MILLISECONDS).status;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        if (status == 200) safeData(email, password, "ЛОГИН");

        return status;
    }

    private int logout() {
        UserLogOutTask task = new UserLogOutTask();
        task.execute();
        int status = -1;
        try {
            status = task.get(500, TimeUnit.MILLISECONDS).status;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        if (status == 200) removeData();

        return status;
    }

    private int signUp() {
        String login = editLoginSignUp.getText().toString();
        String email = editEmailSignUp.getText().toString();

        String password = editPasswordSignUp.getText().toString();

        UserSignUpTask task = new UserSignUpTask();
        task.execute(login, email, password);
        int status = -1;

        try {
            status = task.get(500, TimeUnit.MILLISECONDS).status;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        if (status == 200) safeData(email, password, login);

        return status;
    }

    private void safeData(String email, String password, String login) {
        loginCard.setVisibility(View.GONE);
        signUpCard.setVisibility(View.GONE);
        userInfoCard.setVisibility(View.VISIBLE);

        tvEmail.setText(email);
        tvUsername.setText(login);
        SharedPreferences.Editor editor = myProfile.edit();
        editor.putString(APP_PREFERENCES_EMAIL, email);
        editor.putString(APP_PREFERENCES_PASSWORD, password);
        editor.putString(APP_PREFERENCES_USERNAME, login);

        UserInfo.email = email;
        UserInfo.login = login;
        UserInfo.password = password;

        editor.apply();
    }

    private void removeData() {
        SharedPreferences.Editor editor = myProfile.edit();
        editor.remove(APP_PREFERENCES_EMAIL);
        editor.remove(APP_PREFERENCES_USERNAME);
        editor.remove(APP_PREFERENCES_PASSWORD);
        editor.apply();
        loginCard.setVisibility(View.VISIBLE);
        signUpCard.setVisibility(View.VISIBLE);
        userInfoCard.setVisibility(View.GONE);

        UserInfo.email = "";
        UserInfo.login = "";
        UserInfo.password = "";
    }


    private void createAdminAdderView() {
        View view = rootView.findViewById(R.id.adminadder);
        view.setVisibility(View.VISIBLE);
        Spinner spinner = view.findViewById(R.id.spinner);
        View productView = view.findViewById(R.id.product_include);
        View workerView = view.findViewById(R.id.addworker);
        View newsView = view.findViewById(R.id.news_include);
        setAdminListeners(productView, workerView, newsView, spinner);
    }

    private void setAdminListeners(View productView, View workerView, View newsView, Spinner spinner) {
        setChangeSrcImageViewClickListener(productView.findViewById(R.id.image_add_product));
        setChangeSrcImageViewClickListener(workerView.findViewById(R.id.image_add_worker));
        setChangeSrcImageViewClickListener(newsView.findViewById(R.id.image_edit));

        productView.findViewById(R.id.bt_post_product).setOnClickListener(v -> {
            String name = getTextEdit(productView, R.id.name_add_product);
            String weight = getTextEdit(productView, R.id.weight_add_product);
            String price = getTextEdit(productView, R.id.price_add_product);
            String description = getTextEdit(productView, R.id.description_add_product);
            String type = String.valueOf(spinner.getSelectedItemPosition());
            assert bitmap != null;
            new ProductPostTask().execute(new Product(name, description, Integer.parseInt(price), Integer.parseInt(weight), Integer.parseInt(type), bitmap));

        });

        workerView.findViewById(R.id.bt_post_worker).setOnClickListener(v -> {
            String email = getTextEdit(workerView, R.id.email_worker);
            String post = getTextEdit(workerView, R.id.post_worker);
            String role = getTextEdit(workerView, R.id.role_worker);
            new WorkerPostTask().execute(new Worker("Егор", post, email, Integer.parseInt(role), bitmap));
        });


        newsView.findViewById(R.id.bt_post_news).setOnClickListener(v -> {
            String name = getTextEdit(newsView, R.id.title_add_news);
            String description = getTextEdit(newsView, R.id.description_add_news);
            new NewPostTask().execute(new New(name, description, "22 августа ", bitmap));
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                    case 1://product
                        productView.setVisibility(View.VISIBLE);
                        workerView.setVisibility(View.GONE);
                        newsView.setVisibility(View.GONE);
                        break;
                    case 2://news
                        newsView.setVisibility(View.VISIBLE);
                        productView.setVisibility(View.GONE);
                        workerView.setVisibility(View.GONE);
                        break;
                    case 3:  //worker
                        workerView.setVisibility(View.VISIBLE);
                        productView.setVisibility(View.GONE);
                        newsView.setVisibility(View.GONE);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private String getTextEdit(View view, int id) {
        return ((EditText) view.findViewById(id)).getText().toString();
    }

    private void setChangeSrcImageViewClickListener(ImageView iv) {
        iv.setOnClickListener(v -> {
            nowImageView = iv;
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        });
    }

    public void setImage(Bitmap bitmap) {

        this.bitmap = Bitmap.createScaledBitmap(bitmap, 144, 144, true);
        nowImageView.setImageBitmap(this.bitmap);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setImage(bitmap);
                }
        }
    }
}
