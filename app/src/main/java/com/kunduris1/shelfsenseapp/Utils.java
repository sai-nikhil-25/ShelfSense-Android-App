package com.kunduris1.shelfsenseapp;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    private static final String PREF_NAME = "ShelfData"; // SharedPreferences file name
    private static final String KEY = "FoodList";  // Key for storing food list JSON
    /**
     * Save the food list as JSON string in SharedPreferences.
     *
     * @param context Context to access SharedPreferences
     * @param list    ArrayList of FoodItem to save
     */
    public static void saveData(Context context, ArrayList<FoodItem> list) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY, new Gson().toJson(list)); // Convert list to JSON and save
        editor.apply();
    }
    /**
     * Load the food list from SharedPreferences.
     *
     * @param context Context to access SharedPreferences
     * @return ArrayList of FoodItem loaded or empty list if none saved
     */
    public static ArrayList<FoodItem> loadData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY, null);
        Type type = new TypeToken<ArrayList<FoodItem>>() {}.getType();
        // Deserialize JSON to ArrayList or return new empty list
        return json == null ? new ArrayList<>() : new Gson().fromJson(json, type);
    }
}

