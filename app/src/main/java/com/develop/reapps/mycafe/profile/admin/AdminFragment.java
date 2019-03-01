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
public class AdminFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    protected static final int GALLERY_REQUEST = 1;

    private Section[] menuSections;
    private View rootView;
    private Context context;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_admin, container, false);
        context = getContext();
        initView();
        return rootView;
    }

    private void initView() {
        View view = rootView.findViewById(R.id.adminadder);
        Spinner spinner = view.findViewById(R.id.spinner);
        menuSections = new SectionClient(context).getSections();

    }


    private void initSpinner(Spinner spinner) {
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
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
