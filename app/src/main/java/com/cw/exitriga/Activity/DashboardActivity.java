package com.cw.exitriga.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cw.exitriga.Fragment.CategoriesFragment;
import com.cw.exitriga.Fragment.MeasuredFragment;
import com.cw.exitriga.Fragment.NewsFragment;
import com.cw.exitriga.Fragment.SavedCreateFragment;
import com.cw.exitriga.Fragment.SavedFragment;
import com.cw.exitriga.Fragment.SeeMoreFragment;
import com.cw.exitriga.R;
import com.cw.exitriga.Utils.ChangesLang;
import com.cw.exitriga.Utils.SessionManager;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener  {
    MenuItem menuItem1;
    public static ImageView ivToolbarLeftIcon,ivToolbarRightIcon;
    ImageView ivNews,ivCategory,ivSaved,ivMeasures,ivSeeMore;
    String fcmTokenId;
    int mMenuId;
    Fragment fragment;
    LinearLayout layout_news,layout_cate,layout_saved,layout_events,layout_more;
    SessionManager sessionManager;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initviews();
        ChangesLang.setLocale(this,sessionManager.getSavedAppLang());
        initialFunctions();
        onclicks();
    }
    public void initviews(){
        context = DashboardActivity.this;
        sessionManager = new SessionManager(context);
        ivNews = findViewById(R.id.ivNews);
        ivCategory = findViewById(R.id.ivCategory);
        ivSaved = findViewById(R.id.ivSaved);
        ivMeasures = findViewById(R.id.ivMeasures);
        ivSeeMore = findViewById(R.id.ivSeeMore);
        layout_news = findViewById(R.id.layout_news);
        layout_cate = findViewById(R.id.layout_cate);
        layout_saved = findViewById(R.id.layout_saved);
        layout_events = findViewById(R.id.layout_events);
        layout_more = findViewById(R.id.layout_more);
    }
    public void initialFunctions(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(
                R.anim.slide_from_right,
                R.anim.slide_to_left,
                R.anim.slide_from_left,
                R.anim.slide_to_right  );
        ft.replace(R.id.flHomeContainer,new NewsFragment());
        ft.commitAllowingStateLoss();
        ivNews.setImageResource(R.drawable.selected_news_icon);

    }
    public void onclicks(){
        ivNews.setOnClickListener(this);
        layout_more.setOnClickListener(this);
        layout_events.setOnClickListener(this);
        ivCategory.setOnClickListener(this);
        ivSaved.setOnClickListener(this);
        ivMeasures.setOnClickListener(this);
        ivSeeMore.setOnClickListener(this);
        layout_news.setOnClickListener(this);
        layout_cate.setOnClickListener(this);
        layout_saved.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivNews:
                ivNews.setImageResource(R.drawable.selected_news_icon);
                ivCategory.setImageResource(R.drawable.selected_categories__blue);
                ivSaved.setImageResource(R.drawable.selected_saved_icon_blue);
                ivMeasures.setImageResource(R.drawable.selected_measured_icon_blue);
                ivSeeMore.setImageResource(R.drawable.selected_see_more_icon_blue);
                replaceFragment(new NewsFragment());
                break;
            case R.id.layout_news:
                ivNews.setImageResource(R.drawable.selected_news_icon);
                ivCategory.setImageResource(R.drawable.selected_categories__blue);
                ivSaved.setImageResource(R.drawable.selected_saved_icon_blue);
                ivMeasures.setImageResource(R.drawable.selected_measured_icon_blue);
                ivSeeMore.setImageResource(R.drawable.selected_see_more_icon_blue);
                replaceFragment(new NewsFragment());
                break;
            case R.id.ivCategory:
                ivNews.setImageResource(R.drawable.selected_news_icon_blue);
                ivCategory.setImageResource(R.drawable.selected_categories_icon);
                ivSaved.setImageResource(R.drawable.selected_saved_icon_blue);
                ivMeasures.setImageResource(R.drawable.selected_measured_icon_blue);
                ivSeeMore.setImageResource(R.drawable.selected_see_more_icon_blue);
                replaceFragment(new CategoriesFragment());
                break;
            case R.id.layout_cate:
                ivNews.setImageResource(R.drawable.selected_news_icon_blue);
                ivCategory.setImageResource(R.drawable.selected_categories_icon);
                ivSaved.setImageResource(R.drawable.selected_saved_icon_blue);
                ivMeasures.setImageResource(R.drawable.selected_measured_icon_blue);
                ivSeeMore.setImageResource(R.drawable.selected_see_more_icon_blue);
                replaceFragment(new CategoriesFragment());
                break;
            case R.id.ivSaved:
                ivNews.setImageResource(R.drawable.selected_news_icon_blue);
                ivCategory.setImageResource(R.drawable.selected_categories__blue);
                ivSaved.setImageResource(R.drawable.selected_saved_icon);
                ivMeasures.setImageResource(R.drawable.selected_measured_icon_blue);
                ivSeeMore.setImageResource(R.drawable.selected_see_more_icon_blue);
                replaceFragment(new SavedCreateFragment());
                break;
            case R.id.layout_saved:
                ivNews.setImageResource(R.drawable.selected_news_icon_blue);
                ivCategory.setImageResource(R.drawable.selected_categories__blue);
                ivSaved.setImageResource(R.drawable.selected_saved_icon);
                ivMeasures.setImageResource(R.drawable.selected_measured_icon_blue);
                ivSeeMore.setImageResource(R.drawable.selected_see_more_icon_blue);
                replaceFragment(new SavedCreateFragment());
                break;
            case R.id.ivMeasures:
                ivNews.setImageResource(R.drawable.selected_news_icon_blue);
                ivCategory.setImageResource(R.drawable.selected_categories__blue);
                ivSaved.setImageResource(R.drawable.selected_saved_icon_blue);
                ivMeasures.setImageResource(R.drawable.selected_measured_icon);
                ivSeeMore.setImageResource(R.drawable.selected_see_more_icon_blue);
                replaceFragment(new MeasuredFragment());
                break;
            case R.id.layout_events:
                ivNews.setImageResource(R.drawable.selected_news_icon_blue);
                ivCategory.setImageResource(R.drawable.selected_categories__blue);
                ivSaved.setImageResource(R.drawable.selected_saved_icon_blue);
                ivMeasures.setImageResource(R.drawable.selected_measured_icon);
                ivSeeMore.setImageResource(R.drawable.selected_see_more_icon_blue);
                replaceFragment(new MeasuredFragment());
                break;
            case R.id.ivSeeMore:
                ivNews.setImageResource(R.drawable.selected_news_icon_blue);
                ivCategory.setImageResource(R.drawable.selected_categories__blue);
                ivSaved.setImageResource(R.drawable.selected_saved_icon_blue);
                ivMeasures.setImageResource(R.drawable.selected_measured_icon_blue);
                ivSeeMore.setImageResource(R.drawable.selected_see_more_icon);
                replaceFragment(new SeeMoreFragment());
                break;
            case R.id.layout_more:
                ivNews.setImageResource(R.drawable.selected_news_icon_blue);
                ivCategory.setImageResource(R.drawable.selected_categories__blue);
                ivSaved.setImageResource(R.drawable.selected_saved_icon_blue);
                ivMeasures.setImageResource(R.drawable.selected_measured_icon_blue);
                ivSeeMore.setImageResource(R.drawable.selected_see_more_icon);
                replaceFragment(new SeeMoreFragment());
                break;

        }

    }
    public void replaceFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(
                R.anim.slide_from_right,
                R.anim.slide_to_left,
                R.anim.slide_from_left,
                R.anim.slide_to_right  ).addToBackStack(null);
        ft.replace(R.id.flHomeContainer,fragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = (Fragment) fm.findFragmentById(R.id.flHomeContainer);
        if (currentFragment instanceof NewsFragment) {
            //SearchFragment exists in backstack , process accordingly
            finish();

            // super.onBackPressed();
        }
        else if ( currentFragment instanceof CategoriesFragment|| currentFragment instanceof SavedFragment ||currentFragment instanceof MeasuredFragment||currentFragment instanceof SeeMoreFragment) {
            fragment = new NewsFragment();
            ivNews.setImageResource(R.drawable.selected_news_icon);
            ivCategory.setImageResource(R.drawable.ic_categories_white);
            ivSaved.setImageResource(R.drawable.ic_heart_white_outline);
            ivMeasures.setImageResource(R.drawable.ic_calender_white);
            ivSeeMore.setImageResource(R.drawable.ic_see_more_white);
            replaceFragment(fragment);
            // bottomNavigationView.getMenu().findItem(R.id.menu_news).setChecked(true);
        } else {
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

}
