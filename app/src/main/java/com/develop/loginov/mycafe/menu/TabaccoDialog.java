package com.develop.loginov.mycafe.menu;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.develop.loginov.mycafe.MainActivity;
import com.develop.loginov.mycafe.OnAddProductListener;
import com.develop.loginov.mycafe.Product;
import com.develop.loginov.mycafe.R;

public class TabaccoDialog {
    private AlertDialog dialog;
    private Context context;
    private LayoutInflater inflater;
    private Product product;

    public TabaccoDialog(Context context, Product product, LayoutInflater inflater) {
        this.context = context;
        this.inflater = inflater;
        this.product = product;
        createDialog();
        showDialog();
    }

    private void createDialog() {
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        View view = inflater.inflate(R.layout.taste_tabacco_dialog, null);
        EditText editText = view.findViewById(R.id.edit_taste);
        RadioGroup group = view.findViewById(R.id.taste_group);

        view.findViewById(R.id.bt_post_taste).setOnClickListener(v -> {
            Toast.makeText(context, "Вкус добавлен", Toast.LENGTH_SHORT).show();
            editText.setText("");
        });

        view.findViewById(R.id.bt_add_taste).setOnClickListener(v -> {
            RadioButton radioButton = view.findViewById(group.getCheckedRadioButtonId());
            product.setEdition(radioButton.getText().toString());
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
