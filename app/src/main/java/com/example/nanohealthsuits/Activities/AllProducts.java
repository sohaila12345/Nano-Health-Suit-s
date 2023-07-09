package com.example.nanohealthsuits.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nanohealthsuits.Adapters.AllProductsAdapter;
import com.example.nanohealthsuits.Models.Product;
import com.example.nanohealthsuits.NetworkTransaction.Instantiation;
import com.example.nanohealthsuits.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllProducts extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    List<Product> allProduct;
    RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
        recyclerView = findViewById(R.id.recyclerview);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // Set the initial selected item
        bottomNavigationView.setSelectedItemId(R.id.navigation_item1);

        allProduct = new ArrayList<>();

        Call<List<Product>> responseCall = Instantiation.generateCallToAPI().getAllProducts();
        responseCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                if(response.isSuccessful())
                {
                    List<Product> rs = response.body();
                    for (int i = 0; i < rs.size(); i++) {
                        allProduct.add(rs.get(i));
                    }
                    putDataIntoRecyclerView(allProduct);
                } else {
                    Toast.makeText(AllProducts.this, "Failed to Fetch Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(AllProducts.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void putDataIntoRecyclerView(List<Product> allProduct) {

        AllProductsAdapter ordersAdapter= new AllProductsAdapter(this,allProduct);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(ordersAdapter);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}