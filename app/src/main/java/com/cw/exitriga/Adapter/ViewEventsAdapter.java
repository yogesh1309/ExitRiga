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
import com.cw.exitriga.Fragment.MeasuredDetailFragment;
import com.cw.exitriga.Fragment.NewsFragment;
import com.cw.exitriga.ModelClass.ViewEventsData;
import com.cw.exitriga.ModelClass.ViewNewsData;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.Allurls;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ViewEventsAdapter extends RecyclerView.Adapter<ViewEventsAdapter.ViewHolder> {

    private Context context;
    List<ViewEventsData> data;
    public ViewEventsAdapter(Context context, List<ViewEventsData> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_events, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {

        Picasso.get().load(Allurls.ImageURL + data.get(position).getImage()).error(R.drawable.progress_animation).placeholder(R.drawable.progress_animation).into(holder.img_bg);
        holder.txt_address.setText(data.get(position).getAddress());
        holder.txt_name.setText(data.get(position).getTitle());
        holder.txt_date.setText(data.get(position).getFromDate()+" To "+data.get(position).getToDate());

        holder.layout_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new MeasuredDetailFragment(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
//        return size;
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout_main;
        ImageView img_bg;
        TextView txt_name,txt_date,txt_address;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            layout_main = itemLayoutView.findViewById(R.id.layout_main);
            img_bg = itemLayoutView.findViewById(R.id.img_bg);
            txt_name = itemLayoutView.findViewById(R.id.txt_name);
            txt_date = itemLayoutView.findViewById(R.id.txt_date);
            txt_address = itemLayoutView.findViewById(R.id.txt_address);
        }
    }
    public void replaceFragment(Fragment fragment, int position){
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data.get(position));
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
    public List<ViewEventsData> getStudentist() {
        return data;
    }
    public void filterList(List<ViewEventsData> filteredList) {
        data = filteredList;
        notifyDataSetChanged();
    }
}

