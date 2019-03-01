package com.develop.reapps.mycafe.order.basket;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.develop.reapps.mycafe.menu.element.Product;
import com.develop.reapps.mycafe.R;

import java.util.ArrayList;
import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.BasketHolder> {

    private ArrayList<Product> list;
    private Context context;

    BasketAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public BasketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_basket_product, parent, false);
        return new BasketHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BasketHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = list.get(position);
        int count = product.getCount();
        product.setCount(count);
        holder.tvCast.setText(count * product.getPrice() + " руб");
        holder.tvCount.setText(count + "");
        String name = product.getEdition().length() > 0 ? product.getName() + "\n" + product.getEdition() : product.getName();
        holder.tvName.setText(name);

        holder.imageView.setImageResource(product.getMyDrawableId());
        product.setImageBitmap(holder.imageView);

        holder.seekBar.setProgress(product.getCount());
        BasketFragment.changeBasket(getAllCast());
        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                product.setCount(progress);
                holder.tvCast.setText(progress * product.getPrice() + " руб");
                holder.tvCount.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() == 0) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(context);
                    ad.setCancelable(false).setTitle("Вы уверены что хотите удалить этот продукт?").setPositiveButton("Да", (dialog, which) -> {
                        removeProduct(position);
                    }).setNegativeButton("Отмена", (dialog, which) -> {
                        seekBar.setProgress(1);
                        product.setCount(1);
                    });
                    ad.create().show();
                }

                OnChangeCountProductListener listener = (OnChangeCountProductListener) context;
                listener.changeCount(product);
                localChangeBasket();
            }
        });


    }

    public int getAllCast() {
        int cast = 0;
        for (Product p : list) {
            cast += p.getPrice() * p.getCount();
        }
        return cast;
    }

    protected void clearBasket() {
        list.clear();
        localChangeBasket();
    }

    private void removeProduct(int position) {
        Product product = list.remove(position);
        notifyItemChanged(position, list.size());
        OnChangeCountProductListener listener = (OnChangeCountProductListener) context;
        listener.changeCount(product);
        localChangeBasket();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addProducts(Product product) {
        list.add(product);
        notifyDataSetChanged();
    }

    public void addProducts(List<Product> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    private void localChangeBasket() {
        BasketFragment.changeBasket(getAllCast());
        notifyDataSetChanged();
    }

    public interface OnChangeCountProductListener {
        void changeCount(Product product);
    }

    static class BasketHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName, tvCast, tvCount;
        SeekBar seekBar;

        public BasketHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image_product);
            tvName = view.findViewById(R.id.name_product);
            tvCast = view.findViewById(R.id.cast_product);
            tvCount = view.findViewById(R.id.count_product);
            seekBar = view.findViewById(R.id.seekBar_count_basket);
        }
    }
}
