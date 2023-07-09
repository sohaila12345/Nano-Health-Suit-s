package com.example.nanohealthsuits.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.nanohealthsuits.Adapters.AllProductsAdapter;
import com.example.nanohealthsuits.Adapters.SingleProductAdapter;
import com.example.nanohealthsuits.Models.Product;
import com.example.nanohealthsuits.NetworkTransaction.Instantiation;
import com.example.nanohealthsuits.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetail extends AppCompatActivity {
    List<String> singleProduct;
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        recyclerView = findViewById(R.id.recyclerView);

        singleProduct = new ArrayList<>();

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        Call<Product> responseCall = Instantiation.generateCallToAPI().getProduct(id);
        responseCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {

                if(response.isSuccessful())
                {
                    Product product = response.body();

                    // Create a new ArrayList to hold the product(s)
                    List<Product> productList = new ArrayList<>();

                    Log.e("Product", product.getCategory());

                    // Add the retrieved product to the list
                    productList.add(product);
                    putDataIntoRecyclerView(productList);
                } else {
                    Toast.makeText(ProductDetail.this, "Failed to Fetch Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ProductDetail.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void putDataIntoRecyclerView(List<Product> product) {
        SingleProductAdapter ordersAdapter= new SingleProductAdapter(this,product);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(ordersAdapter);
    }
}