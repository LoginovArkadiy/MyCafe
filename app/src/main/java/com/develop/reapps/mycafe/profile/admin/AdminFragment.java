package com.develop.reapps.mycafe.profile.admin;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.develop.reapps.mycafe.MainActivity;
import com.develop.reapps.mycafe.MyBitmapConverter;
import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.profile.FileUtils;
import com.develop.reapps.mycafe.server.news.NewsClient;
import com.develop.reapps.mycafe.server.products.ProductClient;
import com.develop.reapps.mycafe.server.retrofit.Requests;
import com.develop.reapps.mycafe.server.sections.Section;
import com.develop.reapps.mycafe.server.sections.SectionClient;
import com.develop.reapps.mycafe.server.tabaccos.TobaccoClient;
import com.develop.reapps.mycafe.server.workers.WorkerClient;
import com.develop.reapps.mycafe.workers.Worker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFragment extends Fragment {
    protected static final int GALLERY_REQUEST = 1;

    private Bitmap bitmap;
    private Section[] menuSections;
    private ImageView nowImageView;
    private View rootView;
    private Context context;
    private File file;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_admin, container, false);
        context = getContext();
        initView();
        return rootView;
    }

    private void initView() {
        View view = rootView.findViewById(R.id.adminadder);
        view.setVisibility(View.VISIBLE);
        Spinner spinner = view.findViewById(R.id.spinner);
        View productView = view.findViewById(R.id.product_include);
        View workerView = view.findViewById(R.id.worker_include);
        View newsView = view.findViewById(R.id.news_include);
        View typesView = view.findViewById(R.id.types_include);
        View tabaccoView = view.findViewById(R.id.tabacco_include);
        setAdminListeners(productView, workerView, newsView, typesView, tabaccoView, spinner);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setAdminListeners(View productView, View workerView, View newsView, View typesView, View tabaccoView, Spinner spinner) {
        setChangeSrcImageViewClickListener(productView.findViewById(R.id.image_add_product));
        setChangeSrcImageViewClickListener(workerView.findViewById(R.id.image_add_worker));
        setChangeSrcImageViewClickListener(newsView.findViewById(R.id.image_edit));
        setChangeSrcImageViewClickListener(tabaccoView.findViewById(R.id.image_add_tabacco));

        Button productButton = productView.findViewById(R.id.bt_post_product);
        Button workerButton = workerView.findViewById(R.id.bt_post_worker);
        Button newsButton = newsView.findViewById(R.id.bt_post_news);
        Button typesButton = typesView.findViewById(R.id.bt_post_types);
        Button tobaccoButton = tabaccoView.findViewById(R.id.bt_post_tabacco);

        productButton.setOnClickListener(v -> {
            int index = spinner.getSelectedItemPosition();
            if (index < menuSections.length && index >= 0) {
                String name = getTextEdit(productView, R.id.name_add_product);
                String weight = getTextEdit(productView, R.id.weight_add_product);
                String price = getTextEdit(productView, R.id.price_add_product);
                String description = getTextEdit(productView, R.id.description_add_product);
                String type = menuSections[index].getSection();
                new ProductClient(context).loadProduct(name, description, type, Integer.parseInt(price), Integer.parseInt(weight), FileUtils.getFile(name, context, bitmap));
            } else Requests.makeToastNotification(context, "What the Fuck?! Try again!");
        });

        workerButton.setOnClickListener(v -> {
            String email = getTextEdit(workerView, R.id.email_add_worker);
            String post = getTextEdit(workerView, R.id.post_add_worker);
            String role = getTextEdit(workerView, R.id.role_add_worker);
            new WorkerClient(context).loadWorker(new Worker("Егор", post, email, Integer.parseInt(role), FileUtils.getFile(email, context, bitmap)));
        });

        newsButton.setOnClickListener(v -> {
            String name = getTextEdit(newsView, R.id.title_add_news);
            String description = getTextEdit(newsView, R.id.description_add_news);
            new NewsClient(context).loadNew(name, description, FileUtils.getFile(name, context, bitmap));
        });

        typesButton.setOnClickListener(v -> {
            String section = getTextEdit(typesView, R.id.title_add_types);
            new SectionClient(context).loadSection(section);
        });


        tobaccoButton.setOnClickListener(v -> {
            Requests.makeToastNotification(context, "ТАБАКИ");
            String name = getTextEdit(tabaccoView, R.id.name_add_tabacco);
            String price = getTextEdit(tabaccoView, R.id.price_add_tabacco);
            String description = getTextEdit(tabaccoView, R.id.description_add_tabacco);
            if (price.length() == 0 || name.length() == 0 || description.length() == 0) {
                Requests.makeToastNotification(context, "Введите все данные");
                return;
            }
            new TobaccoClient(context).loadTobacco(name, Integer.parseInt(price), description, FileUtils.getFile(name, context, bitmap));
        });

        productButton.setOnTouchListener(MainActivity.onTouchListener);
        workerButton.setOnTouchListener(MainActivity.onTouchListener);
        newsButton.setOnTouchListener(MainActivity.onTouchListener);
        typesButton.setOnTouchListener(MainActivity.onTouchListener);

        initSpinner(productView, workerView, newsView, typesView, tabaccoView, spinner);


    }

    private void initSpinner(View productView, View workerView, View newsView, View typesView, View tabaccoView, Spinner spinner) {
        menuSections = new SectionClient(context).getSections();
        List<String> allSections = new ArrayList<>(menuSections.length);
        for (Section section : menuSections) {
            allSections.add(section.getSection());
        }
        allSections.add("Новости");
        allSections.add("Сотрудники");
        allSections.add("Секция меню");
        allSections.add("Табаки");
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
                    tabaccoView.setVisibility(View.GONE);

                } else if (position == menuSections.length) {//news
                    newsView.setVisibility(View.VISIBLE);
                    productView.setVisibility(View.GONE);
                    workerView.setVisibility(View.GONE);
                    typesView.setVisibility(View.GONE);
                    tabaccoView.setVisibility(View.GONE);

                } else if (position == menuSections.length + 1) {//workers
                    workerView.setVisibility(View.VISIBLE);
                    productView.setVisibility(View.GONE);
                    newsView.setVisibility(View.GONE);
                    typesView.setVisibility(View.GONE);
                    tabaccoView.setVisibility(View.GONE);

                } else if (position == menuSections.length + 2) {//sections
                    workerView.setVisibility(View.GONE);
                    productView.setVisibility(View.GONE);
                    newsView.setVisibility(View.GONE);
                    typesView.setVisibility(View.VISIBLE);
                    tabaccoView.setVisibility(View.GONE);
                } else if (position == menuSections.length + 3) {
                    tabaccoView.setVisibility(View.VISIBLE);
                    workerView.setVisibility(View.GONE);
                    productView.setVisibility(View.GONE);
                    newsView.setVisibility(View.GONE);
                    typesView.setVisibility(View.GONE);
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
        Bitmap bitmap;
        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImage);
                        if (selectedImage != null && selectedImage.getPath() != null) {
                            file = new File(FileUtils.getPath(selectedImage, context));
                            setImage(bitmap);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }



}
