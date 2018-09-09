package com.develop.reapps.mycafe.menu;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.develop.reapps.mycafe.MainActivity;
import com.develop.reapps.mycafe.OnAddProductListener;
import com.develop.reapps.mycafe.Product;
import com.develop.reapps.mycafe.R;

import java.util.ArrayList;

public class TabaccoDialog {
    private AlertDialog dialog;
    private Context context;
    private LayoutInflater inflater;
    private Product product;
    private ArrayList<CheckBox> list;

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
        String[] texts = new String[]{"Admiral Acbar Cereal - оригинальный вкус овсяяных хлопьев", "Applecot - зелёное яблоко", "BananaPapa - очень сладкий банан, вкус на любителя", "Exstragon - напиток тархун, насыщенный, не очень сладкий", "Generis Cherry - Вишня", "Gonzo Mint - свежий вкус смеси мят", "Lemonblast - неплохой лимон один из топовых фруктов", "Mary Jane - интересный травяной вкус", "Red Tea - чай каркаде с корицей"};

        for (int i = 0; i < 9; i++) {
            CheckBox checkBox = new CheckBox(context);
            checkBox.setTextSize(15);
            checkBox.setText(texts[i]);
            list.add(checkBox);
        }
    }

    private void createDialog() {
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        View view = inflater.inflate(R.layout.taste_tabacco_dialog, null);
        EditText editText = view.findViewById(R.id.edit_taste);
        LinearLayout group = view.findViewById(R.id.taste_group);
        for (CheckBox checkBox : list) {
            group.addView(checkBox);
        }
        view.findViewById(R.id.bt_post_taste).setOnClickListener(v -> {
            Toast.makeText(context, "Вкус добавлен", Toast.LENGTH_SHORT).show();
            editText.setText("");
        });

        view.findViewById(R.id.bt_add_taste).setOnClickListener(v -> {
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
        view.findViewById(R.id.admin_taste).setVisibility(MainActivity.ADMIN ? View.VISIBLE : View.GONE);
        ad.setTitle("Выберите вкус табака").setView(view).setCancelable(true);
        dialog = ad.create();
    }


    private void showDialog() {
        dialog.show();
    }
}
