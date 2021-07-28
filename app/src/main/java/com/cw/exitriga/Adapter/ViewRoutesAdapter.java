package com.cw.exitriga.Adapter;

import android.content.Context;
import android.content.Intent;
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

import com.cw.exitriga.Activity.NewsDetailActivity;
import com.cw.exitriga.Fragment.CarRoutesFragment;
import com.cw.exitriga.ModelClass.ViewNewsData;
import com.cw.exitriga.ModelClass.ViewRoutesData;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.Allurls;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ViewRoutesAdapter extends RecyclerView.Adapter<ViewRoutesAdapter.ViewHolder> {

    private Context context;
    List<ViewRoutesData> newsLists;

    public ViewRoutesAdapter(Context context, List<ViewRoutesData> newsLists) {
        this.context = context;
        this.newsLists = newsLists;
    }
    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users, null);
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_routes, parent,false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.txt_name.setText(newsLists.get(position).getName());

      holder.layout_main.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              replaceFragment(new CarRoutesFragment(),position);
          }
      });
    }

    @Override
    public int getItemCount() {
//        return size;
        return newsLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout_main;
        TextView txt_name;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            layout_main = itemLayoutView.findViewById(R.id.layout_main);
            txt_name = itemLayoutView.findViewById(R.id.txt_name);
        }
    }
    public void replaceFragment(Fragment fragment, int position){
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",newsLists.get(position));
        fragment.setArguments(bundle);
        ft.setCustomAnimations(
                R.anim.slide_from_right,
                R.anim.slide_to_left,
                R.anim.slide_from_left,
                R.anim.slide_to_right  ).addToBackStack("vjhvj");
        ft.replace(R.id.flHomeContainer,fragment);
        ft.commit();
    }
    // method to access in activity after updating selection
    public List<ViewRoutesData> getStudentist() {
        return newsLists;
    }
    public void filterList(List<ViewRoutesData> filteredList) {
        newsLists = filteredList;
        notifyDataSetChanged();
    }
}

