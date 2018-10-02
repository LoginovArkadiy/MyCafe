package com.develop.reapps.mycafe.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.reapps.mycafe.MainActivity;
import com.develop.reapps.mycafe.OnAddProductListener;
import com.develop.reapps.mycafe.Product;
import com.develop.reapps.mycafe.R;

public class MenuProductDialog {
    private AlertDialog dialog;

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    public MenuProductDialog(Context context, Product product, LayoutInflater inflater) {
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        View view = inflater.inflate(R.layout.alert_layout, null);

        Button bBack = view.findViewById(R.id.button_back);
        Button bAdd = view.findViewById(R.id.button_add);
        TextView tvName = view.findViewById(R.id.alert_name_product);
        TextView tvCount = view.findViewById(R.id.alert_count_product);
        TextView tvCast = view.findViewById(R.id.alert_cast_product);
        TextView tvDescription = view.findViewById(R.id.alert_description);
        SeekBar seekBar = view.findViewById(R.id.alert_seekBar);
        if (MainActivity.ADMIN) {
            view.findViewById(R.id.card_remove).setVisibility(View.VISIBLE);
            Button bRemove = view.findViewById(R.id.button_remove);
            bRemove.setOnClickListener(v -> {
                Toast.makeText(context, "Продукт удалён", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            });
            bRemove.setOnTouchListener(MainActivity.onTouchListener);
        } else view.findViewById(R.id.card_remove).setVisibility(View.GONE);

        ImageView iv = view.findViewById(R.id.alert_imageView);
        ad.setCancelable(true);

        bBack.setOnClickListener(v -> dialog.cancel());
        bBack.setOnTouchListener(MainActivity.onTouchListener);
        bAdd.setOnTouchListener(MainActivity.onTouchListener);

        bAdd.setOnClickListener(v -> {
            OnAddProductListener listener = (OnAddProductListener) context;
            listener.addProductToBasket(product, seekBar.getProgress());
            dialog.cancel();

        });
        iv.setImageResource(product.getMyDrawableId());
        product.setImageBitmap(iv);

        tvCount.setText("1");
        tvCast.setText(product.getPrice() + " руб");
        tvName.setText(product.getName());
        tvDescription.setText(product.getDescription());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                product.setCount(progress);
                tvCount.setText(progress + "");
                tvCast.setText(progress * product.getPrice() + " руб");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() == 0) seekBar.setProgress(1);
            }
        });
        ad.setView(view);
        dialog = ad.create();
        dialog.show();
    }



}
