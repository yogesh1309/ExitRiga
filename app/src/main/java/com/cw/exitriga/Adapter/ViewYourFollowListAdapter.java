package com.cw.exitriga.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.exitriga.Fragment.CategoriesDetails2Fragment;
import com.cw.exitriga.ModelClass.ViewProductData;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.Allurls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ViewYourFollowListAdapter extends RecyclerView.Adapter<ViewYourFollowListAdapter.ViewHolder> {

    private Context context;
    ArrayList<ViewProductData> productList;
    public ViewYourFollowListAdapter(Context context, ArrayList<ViewProductData> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_yourfolloelist, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

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
    public int getItemCount()
    {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout_main;
        TextView txt_name;
        ImageView img_bg;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            layout_main = itemLayoutView.findViewById(R.id.layout_main);
            txt_name = itemLayoutView.findViewById(R.id.txt_name);
            img_bg = itemLayoutView.findViewById(R.id.img_bg);

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

