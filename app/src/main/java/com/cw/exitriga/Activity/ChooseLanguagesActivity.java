package com.cw.exitriga.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.cw.exitriga.R;
import com.cw.exitriga.Utils.ChangesLang;
import com.cw.exitriga.Utils.SessionManager;

public class ChooseLanguagesActivity extends AppCompatActivity {

    LinearLayout layout_lat,layout_eng;
    Context context;
    Intent intent;
    String lang_name = "en";
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_languages);
        InitView();
        ChangesLang.setLocale(this,sessionManager.getSavedAppLang());
        Click();
    }

    private void Click() {
        layout_eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                lang_name = "en";
                System.out.println("System language >>>>>>>"+lang_name);
                sessionManager.setSavedAppLang(lang_name);
                intent = new Intent(context,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        layout_lat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                lang_name = "lv";
                System.out.println("System language >>>>>>>"+lang_name);
                sessionManager.setSavedAppLang(lang_name);
                intent = new Intent(context,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void InitView()
    {
        context = ChooseLanguagesActivity.this;
        sessionManager = new SessionManager(context);
        layout_lat = findViewById(R.id.layout_lat);
        layout_eng = findViewById(R.id.layout_eng);
    }
}
