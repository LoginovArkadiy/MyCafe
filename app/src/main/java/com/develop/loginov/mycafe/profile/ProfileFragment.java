package com.develop.loginov.mycafe.profile;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.develop.loginov.mycafe.MainActivity;
import com.develop.loginov.mycafe.R;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {


    private static final int GALLERY_REQUEST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ImageView nowImageView;
    Context context;
    View rootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        context = getContext();

        if (MainActivity.ADMIN) {
            createAdminAdderView();
        } else {
            createLikeProductsView();
            View view1 = rootView.findViewById(R.id.likeproducts);
            view1.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    private void createLikeProductsView() {


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
        setChangeSrcImageViewClickListener(productView.findViewById(R.id.image_edit));
        setChangeSrcImageViewClickListener(workerView.findViewById(R.id.image_edit));
        setChangeSrcImageViewClickListener(newsView.findViewById(R.id.image_edit));

        productView.findViewById(R.id.add_edit).setOnClickListener(v -> {
            String name = getTextEdit(productView, R.id.name_edit);
            String weight = getTextEdit(productView, R.id.weight_edit);
            String cast = getTextEdit(productView, R.id.cast_edit);
            String shortD = getTextEdit(productView, R.id.short_edit);
            String longD = getTextEdit(productView, R.id.full_edit);
            Toast.makeText(context, name + "\n" + weight + " г \n" + cast + " руб \n" + shortD + "\n" + longD, Toast.LENGTH_LONG).show();
        });
        workerView.findViewById(R.id.add_edit).setOnClickListener(v -> {
            String name = getTextEdit(workerView, R.id.name_edit);
            String post = getTextEdit(workerView, R.id.work);
            String number = getTextEdit(workerView, R.id.number);
            Toast.makeText(context, name + "\n" + post + "\n" + number, Toast.LENGTH_LONG).show();
        });
        newsView.findViewById(R.id.add_edit).setOnClickListener(v -> {
            String name = getTextEdit(newsView, R.id.name_edit);
            String post = getTextEdit(newsView, R.id.news_description);
            Toast.makeText(context, name + "\n" + post, Toast.LENGTH_LONG).show();
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
        nowImageView.setImageBitmap(bitmap);
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
