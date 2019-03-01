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
import android.widget.EditText;
import android.widget.ImageView;

import com.develop.reapps.mycafe.MyBitmapConverter;
import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.profile.FileUtils;
import com.develop.reapps.mycafe.server.workers.WorkerClient;
import com.develop.reapps.mycafe.workers.Worker;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class WorksAddFragment extends Fragment {

    private Context context;
    private ImageView imageView;
    private Bitmap bitmap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_worker_layout, container, false);
        context = getContext();
        initView(view);

        return view;
    }

    private void initView(View view) {
        EditText textEmail = view.findViewById(R.id.email_add_worker);
        EditText textPost = view.findViewById(R.id.post_add_worker);
        EditText textRole = view.findViewById(R.id.role_add_worker);

        imageView = view.findViewById(R.id.image_add_worker);
        imageView.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, AdminFragment.GALLERY_REQUEST);
        });

        view.findViewById(R.id.bt_post_worker).setOnClickListener(v -> {
            String email = textEmail.getText().toString();
            String post = textPost.getText().toString();
            String role = textRole.getText().toString();
            new WorkerClient(context).loadWorker(new Worker("Егор", post, email, Integer.parseInt(role), FileUtils.getFile(email, context, bitmap)));
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
