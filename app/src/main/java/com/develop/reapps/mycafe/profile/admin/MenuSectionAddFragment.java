package com.develop.reapps.mycafe.profile.admin;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.server.sections.SectionClient;


public class MenuSectionAddFragment extends Fragment {

    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_types_layout, container, false);
        context = getContext();
        initView(view);
        return view;
    }

    private void initView(View view) {
        EditText textSection = view.findViewById(R.id.title_add_types);
        view.findViewById(R.id.bt_post_types).setOnClickListener(v -> {
            String section = textSection.getText().toString();
            new SectionClient(context).loadSection(section);
        });
    }

}
