package com.cw.exitriga.Fragment;

import android.app.Activity;
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

import com.cw.exitriga.ModelClass.AllRoutesListData;
import com.cw.exitriga.ModelClass.CategoriesList;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.Allurls;
import com.squareup.picasso.Picasso;

public class YourListDetailFragment extends Fragment {

    ImageView img_back,img_bg;
    Activity context;
    AllRoutesListData categoriesList;
    TextView txt_title,txt_address,txt_time,txt_site,txt_desc;
    LinearLayout layout_direction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_your_list_detail, container, false);
        InitView(v);
        Click();
        try {
            categoriesList = (AllRoutesListData) getArguments().getSerializable("data");
            DataSet(categoriesList);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return v;
    }

    private void DataSet(AllRoutesListData categoriesList)
    {
        txt_title.setText(categoriesList.getTitle());
        txt_address.setText(categoriesList.getAddress());
        txt_time.setText(categoriesList.getDuration());
        txt_desc.setText(Html.fromHtml(categoriesList.getDescription()));
        Picasso.get().load(Allurls.ImageURL + categoriesList.getImage())
                .error(R.drawable.progress_animation)
                .placeholder(R.drawable.progress_animation)
                .into(img_bg);
    }

    private void Click() {
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
                replaceFragment(new YourListDetailsDirectionMapFragment());
            }
        });
    }
    public void replaceFragment(Fragment fragment){
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", categoriesList);
        fragment.setArguments(bundle);
        ft.setCustomAnimations(
                R.anim.slide_from_right,
                R.anim.slide_to_left,
                R.anim.slide_from_left,
                R.anim.slide_to_right  ).addToBackStack("vjhvj");
        ft.replace(R.id.flHomeContainer,fragment);
        ft.commit();
    }

    private void InitView(View v)
    {
        context = getActivity();
        img_back = v.findViewById(R.id.img_back);
        img_bg = v.findViewById(R.id.img_bg);
        txt_title = v.findViewById(R.id.txt_title);
        txt_address = v.findViewById(R.id.txt_address);
        txt_time = v.findViewById(R.id.txt_time);
        txt_site = v.findViewById(R.id.txt_site);
        txt_desc = v.findViewById(R.id.txt_desc);
        layout_direction = v.findViewById(R.id.layout_direction);
    }
}