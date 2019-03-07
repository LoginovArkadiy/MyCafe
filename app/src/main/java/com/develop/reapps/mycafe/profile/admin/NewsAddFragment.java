package com.develop.reapps.mycafe.profile.admin;


import android.annotation.SuppressLint;
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
import com.develop.reapps.mycafe.MyTouchListener;
import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.profile.FileUtils;
import com.develop.reapps.mycafe.server.news.NewsClient;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class NewsAddFragment extends Fragment {

    private Context context;
    private Bitmap bitmap;
    private ImageView imageView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_news_layout, container, false);
        context = getContext();
        initView(view);
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView(View newsView) {
        EditText textName = newsView.findViewById(R.id.title_add_news);
        EditText textDescription = newsView.findViewById(R.id.description_add_news);
        Button button = newsView.findViewById(R.id.bt_post_news);
        imageView = newsView.findViewById(R.id.image_edit);
        imageView.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, AdminFragment.GALLERY_REQUEST);
        });
        button.setOnClickListener(v -> {
            String name = textName.getText().toString();
            String description = textDescription.getText().toString();
            new NewsClient(context).loadNew(name, description, FileUtils.getFile(name, context, bitmap));
        });
        button.setOnTouchListener(new MyTouchListener());
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
