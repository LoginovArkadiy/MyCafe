package com.develop.loginov.mycafe.profile;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.develop.loginov.mycafe.server.GetUserByEmailService;
import com.develop.loginov.mycafe.server.ProductPostService;
import com.develop.loginov.mycafe.server.Requests;
import com.develop.loginov.mycafe.server.User;
import com.develop.loginov.mycafe.server.WorkerPostService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    Bitmap bitmap;

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
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground);


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
        setChangeSrcImageViewClickListener(productView.findViewById(R.id.image_add_product));
        setChangeSrcImageViewClickListener(workerView.findViewById(R.id.image_add_worker));
        setChangeSrcImageViewClickListener(newsView.findViewById(R.id.image_edit));

        productView.findViewById(R.id.add_edit).setOnClickListener(v -> {
            String name = getTextEdit(productView, R.id.name_add_product);
            String weight = getTextEdit(productView, R.id.weight_add_product);
            String price = getTextEdit(productView, R.id.price_add_product);
            String description = getTextEdit(productView, R.id.description_add_product);
            String type = String.valueOf(spinner.getSelectedItemPosition());
            assert bitmap != null;
            new ProductAddTask(bitmap).execute(name, description, price, weight, type);

        });

        workerView.findViewById(R.id.add_edit).setOnClickListener(v -> {
            String email = getTextEdit(workerView, R.id.email_worker);
            String post = getTextEdit(workerView, R.id.post_worker);
            String role = getTextEdit(workerView, R.id.role_worker);
            new WorkerAddTask(bitmap).execute(email, role, post);
        });
        newsView.findViewById(R.id.add_edit).setOnClickListener(v -> {
            String name = getTextEdit(newsView, R.id.email_worker);
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


    private byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
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
        this.bitmap = bitmap;
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


    @SuppressLint("StaticFieldLeak")
    public class WorkerAddTask extends AsyncTask<String, Void, String> {
        private Bitmap bitmap;

        public WorkerAddTask(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        protected String doInBackground(String... strings) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Requests.baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            GetUserByEmailService service1 = retrofit.create(GetUserByEmailService.class);
            WorkerPostService service2 = retrofit.create(WorkerPostService.class);
            Call<User> callId = service1.getUser(strings[0]);
            try {
                int id = Objects.requireNonNull(callId.execute().body()).id;
                Call<String> callPostWorker = service2.loadNew(id, Integer.parseInt(strings[1]), strings[2], getByteArrayFromBitmap(bitmap));
                return callPostWorker.execute().body();
            } catch (IOException e) {
                return "ОШИБКА";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ProductAddTask extends AsyncTask<String, Void, String> {
        Bitmap bitmap;

        public ProductAddTask(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        protected String doInBackground(String... strings) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Requests.baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            ProductPostService service = retrofit.create(ProductPostService.class);
            String name = strings[0], description = strings[1];
            int price = Integer.parseInt(strings[2]), weight = Integer.parseInt(strings[3]), type = Integer.parseInt(strings[4]);
            Call<String> call = service.loadProduct(name, description, price, weight, type, getByteArrayFromBitmap(bitmap));
            try {
                Response<String> response = call.execute();
                return response.body();
            } catch (IOException e) {
                return "Exception";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }
    }


}
