package com.oliva2.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.oliva2.models.CreateOrderModel;
import com.oliva2.models.UserModel;
import com.oliva2.tags.Tags;

import java.util.Locale;

public class Preferences {

    private static Preferences instance = null;

    private Preferences() {
    }

    public static Preferences getInstance() {
        if (instance == null) {
            instance = new Preferences();
        }
        return instance;
    }

    public void create_update_language(Context context, String lang) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("language", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lang", lang);
        editor.apply();


    }


    public String getLanguage(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("language", Context.MODE_PRIVATE);
        return preferences.getString("lang", Locale.getDefault().getLanguage());

    }

    public void setIsLanguageSelected(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("language_selected", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("selected", true);
        editor.apply();
    }

    public boolean isLanguageSelected(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("language_selected", Context.MODE_PRIVATE);
        return preferences.getBoolean("selected", false);
    }

   public void create_update_userdata(Context context, UserModel userModel) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String user_data = gson.toJson(userModel);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_data", user_data);
        editor.apply();
        create_update_session(context, Tags.session_login);

    }

  public UserModel getUserData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String user_data = preferences.getString("user_data", "");
        UserModel userModel = gson.fromJson(user_data, UserModel.class);
        return userModel;
    }
    public void create_update_session(Context context, String session) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("state", session);
        editor.apply();


    }

    public void create_update_cart_oliva(Context context , CreateOrderModel model)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("cart_oliva", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String cart_oliva_data = gson.toJson(model);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cart_oliva_data", cart_oliva_data);
        editor.apply();

    }

    public CreateOrderModel getcart_olivaData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("cart_oliva", Context.MODE_PRIVATE);
        String json_data = sharedPreferences.getString("cart_oliva_data","");
        Gson gson = new Gson();
        CreateOrderModel model = gson.fromJson(json_data, CreateOrderModel.class);
        return model;
    }

    public void clearcart_oliva(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("cart_oliva", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.apply();
    }






}
