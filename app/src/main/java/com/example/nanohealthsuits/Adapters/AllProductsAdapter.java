package com.example.nanohealthsuits.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanohealthsuits.Activities.ProductDetail;
import com.example.nanohealthsuits.Models.Product;
import com.example.nanohealthsuits.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class AllProductsAdapter extends RecyclerView.Adapter<AllProductsAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;

    public AllProductsAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.product_view, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = product.getId();
                Intent intent = new Intent(context, ProductDetail.class);
                intent.putExtra("id", id);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImageView;
        private TextView nameTextView;
        private TextView descriptionTextView;
        private TextView priceTextView;
        private RatingBar ratingBar;
        private ConstraintLayout constraintLayout;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productImg);
            nameTextView = itemView.findViewById(R.id.productName);
            descriptionTextView = itemView.findViewById(R.id.productDetail);
            priceTextView = itemView.findViewById(R.id.price);
            ratingBar = itemView.findViewById(R.id.rating);
            constraintLayout = itemView.findViewById(R.id.product);
        }

        public void bind(Product product) {
            // Bind the product data to the views
            Picasso.get().load(product.getImage()).into(productImageView);
            nameTextView.setText(product.getTitle());
            descriptionTextView.setText(product.getDescription());
            priceTextView.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
            ratingBar.setRating(product.getRating().getRate());
        }
    }
}

