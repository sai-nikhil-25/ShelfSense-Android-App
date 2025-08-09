package com.kunduris1.shelfsenseapp;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class AddItemActivity extends AppCompatActivity {

    EditText nameInput, qtyInput, dateInput;
    Calendar expiryDate = Calendar.getInstance(); // Holds selected expiry date

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // Configure ActionBar to show app logo
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // Initialize input fields
        nameInput = findViewById(R.id.nameInput);
        qtyInput = findViewById(R.id.qtyInput);
        dateInput = findViewById(R.id.dateInput);

        // Show DatePicker dialog when expiry date field is clicked
        dateInput.setOnClickListener(view -> {
            int y = expiryDate.get(Calendar.YEAR);
            int m = expiryDate.get(Calendar.MONTH);
            int d = expiryDate.get(Calendar.DAY_OF_MONTH);

            new DatePickerDialog(this, (DatePicker view1, int year, int month, int dayOfMonth) -> {
                expiryDate.set(year, month, dayOfMonth);
                // Display selected date in dd/MM/yyyy format
                dateInput.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }, y, m, d).show();
        });
// Save new food item on clicking save button
        findViewById(R.id.saveBtn).setOnClickListener(view -> {
            String name = nameInput.getText().toString();
            String qty = qtyInput.getText().toString();

            // Validate all fields are filled
            if (name.isEmpty() || qty.isEmpty() || dateInput.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
// Load existing list, add new item and save updated list
            ArrayList<FoodItem> list = Utils.loadData(this);
            list.add(new FoodItem(name, qty, expiryDate.getTimeInMillis()));
            Utils.saveData(this, list);
            // Close this activity to return to main list
            finish();
        });
    }
}

