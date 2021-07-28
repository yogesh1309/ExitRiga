package com.cw.exitriga.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.cw.exitriga.Fragment.CategoriesDetail1Fragment;
import com.cw.exitriga.Fragment.CategoriesDetails2Fragment;
import com.cw.exitriga.ModelClass.ViewProductData;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.Allurls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MySliderAdapter extends RecyclerView.Adapter<MySliderAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ViewPager2 viewPager1;
    Context context;
    List<ViewProductData> productList;
    public MySliderAdapter(Context context, ViewPager2 viewPager, List<ViewProductData> productList) {
        this.mInflater = LayoutInflater.from(context);
        this.viewPager1 = viewPager;
        this.context=context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.slider_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Picasso.get().load(Allurls.ImageURL + productList.get(position).getImage())
                .error(R.drawable.progress_animation)
                .placeholder(R.drawable.progress_animation)
                .into(holder.img_bg);
        holder.txt_name.setText(productList.get(position).getTitle());

        holder.layout_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CategoriesDetails2Fragment(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout layout_main;
        ImageView img_bg;
        TextView txt_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_main = itemView.findViewById(R.id.layout_main);
            img_bg = itemView.findViewById(R.id.img_bg);
            txt_name = itemView.findViewById(R.id.txt_name);
        }
    }
    public void replaceFragment(Fragment fragment, int position)
    {
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",productList.get(position));
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

