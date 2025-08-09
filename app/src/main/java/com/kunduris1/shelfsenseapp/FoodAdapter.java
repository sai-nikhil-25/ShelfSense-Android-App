package com.kunduris1.shelfsenseapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;

import java.util.*;



/** @noinspection ALL
 * Custom adapter to display FoodItem objects in a ListView.
 * */
public class FoodAdapter extends ArrayAdapter<FoodItem> {

    public FoodAdapter(Context context, ArrayList<FoodItem> data) {
        super(context, 0, data);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int pos, View convertView, @NonNull ViewGroup parent) {
        FoodItem item = getItem(pos);
        // Inflate list item layout if needed
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_food, parent, false);
        }
// Initialize UI elements in list item
        TextView nameView = convertView.findViewById(R.id.foodName);
        TextView qtyView = convertView.findViewById(R.id.foodQty);
        TextView expView = convertView.findViewById(R.id.expiryStatus);
        Button donateBtn = convertView.findViewById(R.id.donateBtn);

        assert item != null;
        nameView.setText(item.name);
        qtyView.setText(item.quantity);

        long currentTime = System.currentTimeMillis();

        // Calculate how many days left until expiry
        long daysLeft = (item.expiryDate - System.currentTimeMillis()) / (1000 * 60 * 60 * 24);
        // Set expiry status text and color based on days left
        if (daysLeft < 0) {
            expView.setText("Expired "+ Math.abs(daysLeft) + " day(s) ago");
            expView.setTextColor(getContext().getResources().getColor(R.color.expiredRed));
        }else if (daysLeft == 0) {
            expView.setText("Expires today");
            expView.setTextColor(getContext().getResources().getColor(R.color.warningYellow));
        }else if (daysLeft <= 3) {
            expView.setText("Expiring in " + daysLeft + " day(s)");
            expView.setTextColor(getContext().getResources().getColor(R.color.warningYellow));
        } else {
            expView.setText("Fresh - " + daysLeft + " day(s) left");
            expView.setTextColor(getContext().getResources().getColor(R.color.freshGreen));
        }

        // Donate button opens a URL to food donation site in browser
        donateBtn.setOnClickListener(v -> {
            String url = "https://www.feedingamerica.org/take-action/give-food";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            getContext().startActivity(i);
        });

        return convertView;
    }
}
