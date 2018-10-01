package com.develop.reapps.mycafe.profile;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.reapps.mycafe.MainActivity;
import com.develop.reapps.mycafe.MyBitmapConverter;
import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.UserInfo;
import com.develop.reapps.mycafe.server.news.NewsClient;
import com.develop.reapps.mycafe.server.products.ProductClient;
import com.develop.reapps.mycafe.server.retrofit.Requests;
import com.develop.reapps.mycafe.server.sections.Section;
import com.develop.reapps.mycafe.server.sections.SectionTask;
import com.develop.reapps.mycafe.server.user.UserClient;
import com.develop.reapps.mycafe.server.workers.WorkerClient;
import com.develop.reapps.mycafe.workers.Worker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private static final int GALLERY_REQUEST = 1;
    public static final String APP_PREFERENCES = "profile";
    public static final String APP_PREFERENCES_USERNAME = "username";
    public static final String APP_PREFERENCES_EMAIL = "email";
    public static final String APP_PREFERENCES_PASSWORD = "password";

    private CardView userInfoCard, loginCard, signUpCard;
    private TextView tvUsername, tvEmail;
    private EditText editEmailLogin, editPasswordLogin, editEmailSignUp, editPasswordSignUp, editLoginSignUp;
    private ImageView nowImageView;
    private Context context;
    private View rootView;
    private Bitmap bitmap;
    private Uri uri;
    private SharedPreferences myProfile;
    private Section[] menuSections;

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
        int status = new UserClient(context).signIn(email, password);
        if (status == 200) safeData(email, password, "ЛОГИН");
        return status;
    }

    private int logout() {
        int status = new UserClient(context).logOut();
        if (status == 200) removeData();
        return status;
    }

    private int signUp() {
        String login = editLoginSignUp.getText().toString();
        String email = editEmailSignUp.getText().toString();
        String password = editPasswordSignUp.getText().toString();
        int status = new UserClient(context).signUp(email, login, password);
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
        View workerView = view.findViewById(R.id.worker_include);
        View newsView = view.findViewById(R.id.news_include);
        View typesView = view.findViewById(R.id.types_include);
        setAdminListeners(productView, workerView, newsView, typesView, spinner);
    }

    private void setAdminListeners(View productView, View workerView, View newsView, View typesView, Spinner spinner) {
        setChangeSrcImageViewClickListener(productView.findViewById(R.id.image_add_product));
        setChangeSrcImageViewClickListener(workerView.findViewById(R.id.image_add_worker));
        setChangeSrcImageViewClickListener(newsView.findViewById(R.id.image_edit));
        Button productButton = productView.findViewById(R.id.bt_post_product);
        Button workerButton = workerView.findViewById(R.id.bt_post_worker);
        Button newsButton = newsView.findViewById(R.id.bt_post_news);
        Button typesButton = typesView.findViewById(R.id.bt_post_types);
        productButton.setOnClickListener(v -> {
            int index = spinner.getSelectedItemPosition();
            if (index < menuSections.length && index >= 0) {
                String name = getTextEdit(productView, R.id.name_add_product);
                String weight = getTextEdit(productView, R.id.weight_add_product);
                String price = getTextEdit(productView, R.id.price_add_product);
                String description = getTextEdit(productView, R.id.description_add_product);
                String type = menuSections[index].getSection();
                new ProductClient(context).loadProduct(name, description, type, Integer.parseInt(price), Integer.parseInt(weight), getFile(name));
            } else Requests.makeToastNotification(context, "What the Fuck?! Try again!");
        });

        workerButton.setOnClickListener(v -> {
            String email = getTextEdit(workerView, R.id.email_add_worker);
            String post = getTextEdit(workerView, R.id.post_add_worker);
            String role = getTextEdit(workerView, R.id.role_add_worker);
            new WorkerClient(context).loadWorker(new Worker("Егор", post, email, Integer.parseInt(role), getFile(email)));
        });
        newsButton.setOnClickListener(v -> {
            String name = getTextEdit(newsView, R.id.title_add_news);
            String description = getTextEdit(newsView, R.id.description_add_news);
            new NewsClient(context).loadNew(name, description, "30 сентября", getFile(name));
        });
        typesButton.setOnClickListener(v -> {
            String section = getTextEdit(typesView, R.id.title_add_types);
            new SectionTask(context).loadSection(section);
        });
        productButton.setOnTouchListener(MainActivity.onTouchListener);
        workerButton.setOnTouchListener(MainActivity.onTouchListener);
        newsButton.setOnTouchListener(MainActivity.onTouchListener);
        typesButton.setOnTouchListener(MainActivity.onTouchListener);

        initSpinner(productView, workerView, newsView, typesView, spinner);


    }

    private void initSpinner(View productView, View workerView, View newsView, View typesView, Spinner spinner) {
        menuSections = new SectionTask(context).getSections();
        List<String> allSections = new ArrayList<>(menuSections.length);
        for (Section section : menuSections) {
            allSections.add(section.getSection());
        }
        allSections.add("News");
        allSections.add("Workers");
        allSections.add("Section");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, allSections);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < menuSections.length) {
                    productView.setVisibility(View.VISIBLE);
                    workerView.setVisibility(View.GONE);
                    newsView.setVisibility(View.GONE);
                    typesView.setVisibility(View.GONE);
                } else if (position == menuSections.length) {//news
                    newsView.setVisibility(View.VISIBLE);
                    productView.setVisibility(View.GONE);
                    workerView.setVisibility(View.GONE);
                    typesView.setVisibility(View.GONE);
                } else if (position == menuSections.length + 1) {//workers
                    workerView.setVisibility(View.VISIBLE);
                    productView.setVisibility(View.GONE);
                    newsView.setVisibility(View.GONE);
                    typesView.setVisibility(View.GONE);
                } else if (position == menuSections.length + 2) {//sections
                    workerView.setVisibility(View.GONE);
                    productView.setVisibility(View.GONE);
                    newsView.setVisibility(View.GONE);
                    typesView.setVisibility(View.VISIBLE);
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
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int) (750 * ((double) bitmap.getWidth() / (double) bitmap.getHeight())), 750, true);
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
                    this.uri = selectedImage;
                    setImage(bitmap);
                }
        }
    }

    private File getFile(String fileName) {
        File file = new File(context.getCacheDir(), fileName);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file);
            pw.write("");
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {


            file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(MyBitmapConverter.getByteArrayFromBitmap(bitmap));
            fos.flush();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
