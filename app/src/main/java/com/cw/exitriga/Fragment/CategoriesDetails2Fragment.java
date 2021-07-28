package com.cw.exitriga.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cw.exitriga.ModelClass.ViewProductData;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.Allurls;
import com.squareup.picasso.Picasso;

public class CategoriesDetails2Fragment extends Fragment {

    ImageView img_back,img_bg;
    Activity context;
    ViewProductData viewProductData;
    TextView txt_desc,txt_site,txt_time,txt_address,txt_name;
    LinearLayout layout_direction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_categories_details2, container, false);
        InitView(v);
        Click();
        try {
            viewProductData = (ViewProductData) getArguments().getSerializable("data");
            DataSet(viewProductData);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return v;
    }

    private void DataSet(ViewProductData viewProductData)
    {
        Picasso.get().load(Allurls.ImageURL + viewProductData.getImage()).error(R.drawable.progress_animation).placeholder(R.drawable.progress_animation).into(img_bg);
        txt_name.setText(viewProductData.getTitle());
        txt_address.setText(viewProductData.getAddress());
        txt_desc.setText(Html.fromHtml(viewProductData.getDescription()));
        txt_site.setText(viewProductData.getWebsite());
        txt_time.setText(viewProductData.getDuration());
    }

    private void Click()
    {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                context.onBackPressed();
            }
        });

        layout_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ProductDirectionMapFragment());
            }
        });
    }

    private void InitView(View v)
    {
        context = getActivity();
        img_back = v.findViewById(R.id.img_back);
        txt_desc = v.findViewById(R.id.txt_desc);
        txt_site = v.findViewById(R.id.txt_site);
        txt_time = v.findViewById(R.id.txt_time);
        txt_address = v.findViewById(R.id.txt_address);
        txt_name = v.findViewById(R.id.txt_name);
        img_bg = v.findViewById(R.id.img_bg);
        layout_direction = v.findViewById(R.id.layout_direction);
    }
    public void replaceFragment(Fragment fragment)
    {
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",viewProductData);
        fragment.setArguments(bundle);
        ft.setCustomAnimations(
                R.anim.slide_from_right,
                R.anim.slide_to_left,
                R.anim.slide_from_left,
                R.anim.slide_to_right)
                .addToBackStack("vjhvj");
        ft.replace(R.id.flHomeContainer,fragment);
        ft.commit();
    }

}
