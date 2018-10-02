package com.develop.reapps.mycafe.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.develop.reapps.mycafe.MainActivity;
import com.develop.reapps.mycafe.OnAddProductListener;
import com.develop.reapps.mycafe.Product;
import com.develop.reapps.mycafe.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductMenuHolder> {
    private List<Product> productList;
    private Context context;
    private LayoutInflater inflater;
    boolean isTabac;

    public ProductAdapter() {
        productList = new ArrayList<>();
        isTabac = false;
    }

    public ProductAdapter(boolean isTabac) {
        productList = new ArrayList<>();
        this.isTabac = isTabac;
    }

    @NonNull
    @Override
    public ProductMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_product, parent, false);

        return new ProductMenuHolder(view);
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ProductMenuHolder holder, int position) {
        Product product = productList.get(position);
        holder.buttonAdd.setOnClickListener(v -> {
            if (isTabac) {
                showDialog(context, product, inflater);
            } else {

                OnAddProductListener listener = (OnAddProductListener) context;
                listener.addProductToBasket(product, 1);
            }
        });
        holder.buttonAdd.setOnTouchListener(MainActivity.onTouchListener);

        holder.layout.setOnClickListener(v -> {

            showDialog(context, product, inflater);
        });

        holder.tvName.setText(product.getName());
        holder.tvDescription.setText(product.getShortDescription());

        holder.image.setImageResource(product.getMyDrawableId());
        product.setImageBitmap(holder.image);

        holder.tvPrice.setText(product.getPrice() + " руб.");
        holder.tvWeight.setText(product.getWeight() + " г");

        if (isTabac) {
            holder.tvWeight.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public void addProducts(Product product) {
        productList.add(product);
        notifyDataSetChanged();
    }

    public void addProducts(List<Product> productList) {
        this.productList.addAll(productList);
        notifyDataSetChanged();
    }

    private void showDialog(Context context, Product product, LayoutInflater inflater) {
        if (isTabac) {
            new TabaccoDialog(context, product, inflater);
        } else {
            new MenuProductDialog(context, product, inflater);
        }
    }


    static class ProductMenuHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tvName;
        TextView tvDescription;
        TextView tvPrice;
        TextView tvWeight;
        Button buttonAdd;
        LinearLayout layout;

        public ProductMenuHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image_product);
            tvName = view.findViewById(R.id.name_product);
            tvDescription = view.findViewById(R.id.direction_product);
            tvPrice = view.findViewById(R.id.tw_price);
            tvWeight = view.findViewById(R.id.weight_product);
            buttonAdd = view.findViewById(R.id.add_button);
            layout = view.findViewById(R.id.product_up_layout);

        }
    }

}
