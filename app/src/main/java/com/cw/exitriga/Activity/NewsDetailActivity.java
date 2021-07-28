package com.cw.exitriga.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cw.exitriga.ModelClass.NewsList;
import com.cw.exitriga.ModelClass.ViewNewsData;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.Allurls;
import com.cw.exitriga.Utils.ChangesLang;
import com.cw.exitriga.Utils.SessionManager;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {
    ImageView layout_back, img_bg;
    ViewNewsData newsList;
    TextView txt_name,txt_desc;
    Context context;
    SessionManager sessionManager;
    LinearLayout layout_getdirection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        InitView();
        ChangesLang.setLocale(this, sessionManager.getSavedAppLang());
        Click();
        try {
            Intent intent = getIntent();
            if (intent != null) {
                newsList = (ViewNewsData) intent.getSerializableExtra("data");
                DataSet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DataSet() {
        txt_name.setText(newsList.getTitle());
        txt_desc.setText(Html.fromHtml(newsList.getDescription()));
        Picasso.get().load(Allurls.ImageURL + newsList.getImage())
                .error(R.drawable.progress_animation)
                .placeholder(R.drawable.progress_animation)
                .into(img_bg);
    }

    private void Click() {
        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        layout_getdirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,NewsDirectionActivity.class);
                intent.putExtra("data",newsList);
                startActivity(intent);
            }
        });
    }

    private void InitView() {
        context = NewsDetailActivity.this;
        sessionManager = new SessionManager(context);
        layout_back = findViewById(R.id.layout_back);
        txt_name = findViewById(R.id.txt_name);
        img_bg = findViewById(R.id.img_bg);
        txt_desc = findViewById(R.id.txt_desc);
        layout_getdirection = findViewById(R.id.layout_getdirection);
    }
}
