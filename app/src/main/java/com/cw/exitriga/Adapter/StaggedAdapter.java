package com.cw.exitriga.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cw.exitriga.Fragment.CategoriesDetail1Fragment;
import com.cw.exitriga.Fragment.MeasuredDetailFragment;
import com.cw.exitriga.Fragment.SavedFragment;
import com.cw.exitriga.Fragment.ViewRoutesFragment;
import com.cw.exitriga.ModelClass.CategoriesList;
import com.cw.exitriga.ModelClass.ViewCategoriesData;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.Allurls;
import com.cw.exitriga.Utils.RoundRectCornerImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StaggedAdapter extends RecyclerView.Adapter<StaggedAdapter.ViewHolder>  {

    Context context;
    List<List<ViewCategoriesData>> categoriesLists;
    // Constructor for initialization
    public StaggedAdapter(Context context, List<List<ViewCategoriesData>> categoriesLists) {
        this.context = context;
       this.categoriesLists = categoriesLists;
    }

    @NonNull
    @Override
    public StaggedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_list, parent, false);



        // Passing view to ViewHolder
        StaggedAdapter.ViewHolder viewHolder = new StaggedAdapter.ViewHolder(view);
        return viewHolder;
    }

    // Binding data to the into specified position
    @Override
    public void onBindViewHolder(@NonNull StaggedAdapter.ViewHolder holder, int position) {

     /*   Picasso.get().load(Allurls.ImageURL + categoriesLists.get(position).getImage())
                .error(R.drawable.progress_animation)
                .placeholder(R.drawable.progress_animation)
                .into(holder.img_product);
        holder.txt_name.setText(categoriesLists.get(position).getName());

        holder.layout_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String name = categoriesLists.get(position).getName();
                if (name.contains("Routes")|name.contains("routes"))
                {
                    replaceFragment(new ViewRoutesFragment(),position);
                }
                else {
                replaceFragment(new SavedFragment(),position);
//                replaceFragment(new CategoriesDetail1Fragment());
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        // Returns number of items currently available in Adapter
        return categoriesLists.size();
    }

    // Initializing the Views
    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundRectCornerImageView img_product;
        LinearLayout layout_main;
        TextView txt_name;
        public ViewHolder(View view)
        {
            super(view);
            img_product =  view.findViewById(R.id.img_product);
            layout_main =  view.findViewById(R.id.layout_main);
            txt_name =  view.findViewById(R.id.txt_name);
        }
    }
   /* public void replaceFragment(Fragment fragment, int position){
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",categoriesLists.get(position));
       fragment.setArguments(bundle);
        ft.setCustomAnimations(
                R.anim.slide_from_right,
                R.anim.slide_to_left,
                R.anim.slide_from_left,
                R.anim.slide_to_right  ).addToBackStack("vjhvj");
        ft.replace(R.id.flHomeContainer,fragment);
        ft.commit();
    }*/
}
