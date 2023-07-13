package com.example.nanohealthsuits.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

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

    private List<Product> allProduct;
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;
    private UserSessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        sessionManager = UserSessionManager.getInstance(this);

        recyclerView = findViewById(R.id.recyclerview);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // Set the initial selected item
        bottomNavigationView.setSelectedItemId(R.id.navigation_item1);
        if (!sessionManager.isLoggedIn()) {
            // User is not logged in, redirect to login activity
            Intent intent = new Intent(AllProducts.this, UserLogin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            allProduct = new ArrayList<>();

            Call<List<Product>> responseCall = Instantiation.generateCallToAPI().getAllProducts();
            responseCall.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                    if (response.isSuccessful()) {
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
                    Toast.makeText(AllProducts.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void putDataIntoRecyclerView(List<Product> allProduct) {
        AllProductsAdapter ordersAdapter = new AllProductsAdapter(this, allProduct);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(ordersAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            // Add cases for other menu items if needed
        }
        return false;
    }

     @Override
     public void onBackPressed() {
     finishAffinity(); // Close all activities in the task
}

    @NonNull
    @Override
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        return super.getOnBackInvokedDispatcher();
    }
}
