package com.cw.exitriga.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cw.exitriga.Adapter.MainPackage_Sliding_adapter;
import com.cw.exitriga.ModelClass.CategoriesList;
import com.cw.exitriga.ModelClass.ViewDestinationData;
import com.cw.exitriga.R;

public class CarRoutesDetailsFragment extends Fragment {
   ImageView img_back,img_bg;
   Activity activity;
   LinearLayout layout_places,layout_showmap,layout_direction;
    ViewDestinationData categoriesList;
    TextView txt_name,txt_desc,txt_title;
    ViewPager pager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_car_routes_details, container, false);
        InitView(v);
        Click();
        try {
            categoriesList = (ViewDestinationData) getArguments().getSerializable("data");
            DataSet(categoriesList);
            setSlider();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return v;
    }

    private void DataSet(ViewDestinationData categoriesList) {

        txt_name.setText(categoriesList.getTitle());
        txt_title.setText(categoriesList.getTitle());
        txt_desc.setText(Html.fromHtml(categoriesList.getDescription()));
    }
    private void setSlider()
    {
        pager.setAdapter(new MainPackage_Sliding_adapter(activity, categoriesList.getMultipleImage()));
    }

    private void Click() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
        layout_places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ViewPlacesListFragment());
            }
        });

        layout_showmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CarRoutesMapFragment());
            }
        });
        layout_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CarRoutesDirectionMapFragment());
            }
        });
    }
    private void InitView(View v) {
        activity = getActivity();
        img_back = v.findViewById(R.id.img_back);
        layout_places = v.findViewById(R.id.layout_places);
        layout_showmap = v.findViewById(R.id.layout_showmap);
        txt_name = v.findViewById(R.id.txt_name);
        img_bg = v.findViewById(R.id.img_bg);
        txt_desc = v.findViewById(R.id.txt_desc);
        txt_title = v.findViewById(R.id.txt_title);
        pager = v.findViewById(R.id.pager);
        layout_direction = v.findViewById(R.id.layout_direction);
    }
    public void replaceFragment(Fragment fragment){
        FragmentTransaction ft = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",categoriesList);
        fragment.setArguments(bundle);
        ft.setCustomAnimations(
                R.anim.slide_from_right,
                R.anim.slide_to_left,
                R.anim.slide_from_left,
                R.anim.slide_to_right  ).addToBackStack("vjhvj");
        ft.replace(R.id.flHomeContainer,fragment);
        ft.commit();
    }

}
