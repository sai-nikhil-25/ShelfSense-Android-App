package com.kunduris1.shelfsenseapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView emptyView;
    FoodAdapter adapter;
    ArrayList<FoodItem> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enable edge-to-edge layout
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Handle system insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Configure ActionBar to show app logo
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // Initialize views
        listView = findViewById(R.id.listView);
        emptyView = findViewById(R.id.donateViewText);

        // Load saved food data
        foodList = Utils.loadData(this);

        // Set up the adapter and list
        adapter = new FoodAdapter(this, foodList);
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyView);

        // Navigate to AddItemActivity when "Add Food" button is clicked
        findViewById(R.id.addFoodBtn).setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, AddItemActivity.class))
        );

        // Remove item on long click
        listView.setOnItemLongClickListener((adapterView, view, position, id) -> {
            foodList.remove(position);
            Utils.saveData(this, foodList);
            adapter.notifyDataSetChanged();
            return true;
        });
    }

    // Refresh the list when returning to the activity
    @Override
    protected void onResume() {
        super.onResume();
        foodList.clear();
        foodList.addAll(Utils.loadData(this));
        adapter.notifyDataSetChanged();
    }
}
