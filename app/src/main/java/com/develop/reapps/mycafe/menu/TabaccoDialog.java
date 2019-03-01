package com.develop.reapps.mycafe.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.develop.reapps.mycafe.MainActivity;
import com.develop.reapps.mycafe.OnAddProductListener;
import com.develop.reapps.mycafe.R;
import com.develop.reapps.mycafe.menu.element.Product;
import com.develop.reapps.mycafe.server.tabaccos.TobaccoClient;
import com.develop.reapps.mycafe.server.tastes.Taste;
import com.develop.reapps.mycafe.server.tastes.TasteClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TabaccoDialog {
    private AlertDialog dialog;
    private Context context;
    private LayoutInflater inflater;
    private Product product;
    private ArrayList<CheckBox> list;
    private LinearLayout group;

    HashMap<String, Integer> hmIds;

    public TabaccoDialog(Context context, Product product, LayoutInflater inflater) {
        this.context = context;
        this.inflater = inflater;
        this.product = product;
        initList();
        createDialog();
        showDialog();
    }

    private void initList() {
        list = new ArrayList<>();
        Taste[] tastes = new TasteClient(context).getTastes(product.getId());
        hmIds = new HashMap<>(tastes.length);
        for (Taste taste : tastes) {
            hmIds.put(taste.getName(), taste.getId());
            list.add(createBox(taste));
        }
    }


    private void createDialog() {
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        View view = inflater.inflate(R.layout.taste_tabacco_dialog, null);
        group = view.findViewById(R.id.taste_group);
        for (CheckBox checkBox : list) {
            group.addView(checkBox);
        }

        EditText editText = view.findViewById(R.id.edit_taste);
        Button btPost = view.findViewById(R.id.bt_post_taste);
        Button btAdd = view.findViewById(R.id.bt_add_taste);
        Button btDelete = view.findViewById(R.id.bt_delete_tobacco);
        setListeners(btPost, btAdd, btDelete, editText);

        view.findViewById(R.id.admin_taste).setVisibility(MainActivity.ADMIN ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.bt_delete_tobacco).setVisibility(MainActivity.ADMIN ? View.VISIBLE : View.GONE);
        ad.setTitle("Выберите вкус табака").setView(view).setCancelable(true);
        dialog = ad.create();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListeners(Button btPost, Button btAdd, Button btDelete, EditText editText) {

        btPost.setOnClickListener(v -> {
            Toast.makeText(context, "Вкус добавлен", Toast.LENGTH_SHORT).show();
            String taste = editText.getText().toString();
            if (taste.length() > 0) {
                new TasteClient(context).loadTaste(taste, product.getId());
                CheckBox checkBox = new CheckBox(context);
                checkBox.setTextSize(15);
                checkBox.setText(taste);
                group.addView(checkBox);
            }
            editText.setText("");
        });

        btAdd.setOnClickListener(v -> {
            StringBuilder edition = new StringBuilder();
            for (CheckBox checkBox : list) {
                if (checkBox.isChecked())
                    edition.append(checkBox.getText().toString()).append("\n \n");
            }
            product.setEdition(edition.toString());
            OnAddProductListener listener = (OnAddProductListener) context;
            listener.addProductToBasket(product, 1);
            dialog.cancel();
        });

        btDelete.setOnClickListener(v -> {
            List<Integer> listChecked = new ArrayList<>();
            for (CheckBox checkBox : list) {
                if (checkBox.isChecked()) {
                    listChecked.add(hmIds.get(checkBox.getText().toString()));
                }
            }

            if (listChecked.size() > 0) {
                TasteClient tasteClient = new TasteClient(context);
                for (int id : listChecked) {
                    tasteClient.delete(id);
                }
            } else {
                new TobaccoClient(context).delete(product.getId());
            }
        });

        btAdd.setOnTouchListener(MainActivity.onTouchListener);
        btPost.setOnTouchListener(MainActivity.onTouchListener);
        btDelete.setOnTouchListener(MainActivity.onTouchListener);
    }

    private void showDialog() {
        dialog.show();
    }

    private CheckBox createBox(Taste taste) {
        CheckBox checkBox = new CheckBox(context);
        checkBox.setTextSize(15);
        checkBox.setText(taste.getName());
        return checkBox;
    }
}
