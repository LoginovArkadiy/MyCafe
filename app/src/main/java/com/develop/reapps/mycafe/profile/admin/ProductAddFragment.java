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
import com.develop.reapps.mycafe.server.products.ProductClient;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductAddFragment extends Fragment {
    private static final String TYPE_PARAM = "param1";

    private String type;

    private Context context;
    private ImageView imageView;
    private Bitmap bitmap;

    public static ProductAddFragment newInstance(String type) {
        ProductAddFragment fragment = new ProductAddFragment();
        Bundle args = new Bundle();
        args.putString(TYPE_PARAM, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(TYPE_PARAM);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_product_layout, container, false);
        context = getContext();
        initView(view);
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView(View view) {

        EditText textName = view.findViewById(R.id.name_add_product);
        EditText textWeight = view.findViewById(R.id.weight_add_product);
        EditText textPrice = view.findViewById(R.id.price_add_product);
        EditText textDescription = view.findViewById(R.id.description_add_product);
        Button button = view.findViewById(R.id.bt_post_product);

        imageView = view.findViewById(R.id.image_add_product);
        imageView.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, AdminFragment.GALLERY_REQUEST);
        });

        button.setOnClickListener(v -> {
            String name = textName.getText().toString();
            String weight = textWeight.getText().toString();
            String price = textPrice.getText().toString();
            String description = textDescription.getText().toString();
            //     new ProductClient(context).loadProduct(name, description, type, Integer.parseInt(price), Integer.parseInt(weight), FileUtils.getFile(name, context, bitmap));
            new ProductClient(context).testLoadProduct(name,
                    description,
                    type,
                    Integer.parseInt(price),
                    Integer.parseInt(weight),
                    FileUtils.getFile(name, context, bitmap));
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
