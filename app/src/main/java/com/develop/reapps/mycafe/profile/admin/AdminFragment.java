package com.develop.reapps.mycafe.profile.admin;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.develop.reapps.mycafe.R;

import com.develop.reapps.mycafe.server.sections.Section;
import com.develop.reapps.mycafe.server.sections.SectionClient;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFragment extends Fragment {
    protected static final int GALLERY_REQUEST = 1;
    private Section[] menuSections;
    private List<String> allSections;
    private View rootView;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_admin, container, false);
        context = getContext();
        initView(rootView);
        return rootView;
    }

    private void initView(View view) {
        Spinner spinner = view.findViewById(R.id.spinner);
        allSections = getSections();
        initSpinner(spinner, allSections);

        if (menuSections.length > 0) {
            getChildFragmentManager().beginTransaction().add(R.id.fragment_admin__container, ProductAddFragment.newInstance(allSections.get(0))).commit();
        } else {
            getChildFragmentManager().beginTransaction().add(R.id.fragment_admin__container, new NewsAddFragment()).commit();
        }

    }

    private List<String> getSections() {
        menuSections = new SectionClient(context).getSections();
        List<String> allSections = new ArrayList<>(menuSections.length);
        for (Section section : menuSections) {
            allSections.add(section.getSection());
        }
        allSections.add("Новости");
        allSections.add("Сотрудники");
        allSections.add("Секция меню");
        allSections.add("Табаки");
        return allSections;
    }

    private void initSpinner(Spinner spinner, List<String> sections) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, sections);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < 0 || position >= allSections.size()) {
                    return;
                }
                Fragment fragment = null;
                if (position < menuSections.length) {
                    fragment = ProductAddFragment.newInstance(allSections.get(position));
                } else switch (allSections.get(position)) {
                    case "Новости":
                        fragment = new NewsAddFragment();
                        break;
                    case "Сотрудники":
                        fragment = new WorksAddFragment();
                        break;
                    case "Секция меню":
                        fragment = new MenuSectionAddFragment();
                        break;
                    case "Табаки":
                        fragment = new TobaccoAddFragment();
                        break;
                }
                if (fragment != null) {
                    getChildFragmentManager().beginTransaction().replace(R.id.fragment_admin__container, fragment).commit();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
