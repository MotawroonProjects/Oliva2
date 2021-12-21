package com.oliva2.activities_fragments.activity_splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.oliva2.R;
import com.oliva2.activities_fragments.activity_home.HomeActivity;
import com.oliva2.activities_fragments.activity_login.LoginActivity;
import com.oliva2.databinding.ActivitySplashBinding;
import com.oliva2.language.Language;
import com.oliva2.local_database.AccessDatabase;
import com.oliva2.models.UserModel;
import com.oliva2.preferences.Preferences;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private AccessDatabase accessDatabase;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        new Handler()
                .postDelayed(() -> {

                        Intent intent;

                        if (userModel == null) {
                            intent = new Intent(this, LoginActivity.class);

                        } else {
                            intent = new Intent(this, HomeActivity.class);
                        }
                        startActivity(intent);
                        finish();
                    }
                , 2000);

    }
}
