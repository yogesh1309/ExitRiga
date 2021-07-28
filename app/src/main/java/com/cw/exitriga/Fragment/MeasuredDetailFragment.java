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

import com.cw.exitriga.ModelClass.ViewEventsData;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.Allurls;
import com.squareup.picasso.Picasso;

public class MeasuredDetailFragment extends Fragment {
    Activity activity;
    ImageView layout_back;
    ViewEventsData viewEventsData;
    ImageView img_bg;
    TextView txt_name,txt_date,txt_address,txt_fees,txt_desc;
    LinearLayout layout_direction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_measured_detail, container, false);
        InitVIew(v);
        Click();
        try {
            viewEventsData  = (ViewEventsData) getArguments().getSerializable("data");
            dataSet(viewEventsData);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return  v;
    }

    private void dataSet(ViewEventsData data)
    {
        Picasso.get().load(Allurls.ImageURL + data.getImage()).error(R.drawable.progress_animation).placeholder(R.drawable.progress_animation).into(img_bg);
        txt_address.setText(data.getAddress());
        txt_name.setText(data.getTitle());
        txt_date.setText(data.getFromDate()+" To "+data.getToDate());
        txt_fees.setText(data.getRate()+" EUR");
        txt_desc.setText(Html.fromHtml(data.getDescription()));
    }

    private void Click()
    {
        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });

        layout_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new MeasuredDirectionMapFragment());
            }
        });
    }

    private void InitVIew(View v) {
        activity = getActivity();
        layout_back = v.findViewById(R.id.layout_back);
        img_bg = v.findViewById(R.id.img_bg);
        txt_name = v.findViewById(R.id.txt_name);
        txt_date = v.findViewById(R.id.txt_date);
        txt_address = v.findViewById(R.id.txt_address);
        txt_fees = v.findViewById(R.id.txt_fees);
        txt_desc = v.findViewById(R.id.txt_desc);
        layout_direction = v.findViewById(R.id.layout_direction);
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction ft = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", viewEventsData);
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
