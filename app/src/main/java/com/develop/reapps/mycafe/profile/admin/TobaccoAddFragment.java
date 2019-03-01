package com.develop.reapps.mycafe.profile.admin;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.develop.reapps.mycafe.MyBitmapConverter;
import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.profile.FileUtils;
import com.develop.reapps.mycafe.server.retrofit.Requests;
import com.develop.reapps.mycafe.server.tabaccos.TobaccoClient;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class TobaccoAddFragment extends Fragment {

    private Context context;
    private Bitmap bitmap;
    private ImageView imageView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_tobacco_layout, container, false);
        context = getContext();
        initView(view);
        return view;
    }

    private void initView(View view) {
        Button tobaccoButton = view.findViewById(R.id.bt_post_tabacco);
        EditText textName = view.findViewById(R.id.name_add_tabacco);
        EditText textPrice = view.findViewById(R.id.price_add_tabacco);
        EditText textDescription = view.findViewById(R.id.description_add_tabacco);
        imageView = view.findViewById(R.id.image_add_tabacco);
        imageView.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, AdminFragment.GALLERY_REQUEST);
        });

        tobaccoButton.setOnClickListener(v -> {
            Requests.makeToastNotification(context, "ТАБАКИ");
            String name = textName.getText().toString();
            String price = textPrice.getText().toString();
            String description = textDescription.getText().toString();

            if (price.length() == 0 || name.length() == 0 || description.length() == 0) {
                Requests.makeToastNotification(context, "Введите все данные");
                return;
            }
            new TobaccoClient(context).loadTobacco(name, Integer.parseInt(price), description, FileUtils.getFile(name, context, bitmap));
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AdminFragment.GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImage);
                        if (bitmap.getWidth() > 1000) {
                            bitmap = MyBitmapConverter.resizeBItmap(bitmap, bitmap.getWidth() / 500);
                        }
                        imageView.setImageURI(selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}
