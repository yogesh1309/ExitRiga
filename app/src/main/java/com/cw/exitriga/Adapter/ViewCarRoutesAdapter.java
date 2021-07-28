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

import com.cw.exitriga.Fragment.CarRoutesDetailsFragment;
import com.cw.exitriga.ModelClass.CategoriesList;
import com.cw.exitriga.ModelClass.ViewDestinationData;
import com.cw.exitriga.ModelClass.ViewNewsData;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.Allurls;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ViewCarRoutesAdapter extends RecyclerView.Adapter<ViewCarRoutesAdapter.ViewHolder> {

    private Context context;
    List<ViewDestinationData> lists;
    public ViewCarRoutesAdapter(Context context, List<ViewDestinationData> lists) {
        this.context = context;
        this.lists = lists;
    }
    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users, null);
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_carroutes, parent,false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txt_name.setText(lists.get(position).getTitle());
        holder.txt_km.setText(lists.get(position).getDistance());
        holder.txt_time.setText(lists.get(position).getDuration());
        Picasso.get().load(Allurls.ImageURL + lists.get(position).getImage())
                .error(R.drawable.progress_animation)
                .placeholder(R.drawable.progress_animation)
                .into(holder.img_main);
      holder.layout_main.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              replaceFragment(new CarRoutesDetailsFragment(),position);

          }
      });
    }

    @Override
    public int getItemCount() {
//        return size;
        return lists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout_main;
        TextView txt_name,txt_time,txt_km;
        ImageView img_main;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            layout_main = itemLayoutView.findViewById(R.id.layout_main);
            txt_name = itemLayoutView.findViewById(R.id.txt_name);
            img_main = itemLayoutView.findViewById(R.id.img_main);
            txt_time = itemLayoutView.findViewById(R.id.txt_time);
            txt_km = itemLayoutView.findViewById(R.id.txt_km);
        }
    }
    public void replaceFragment(Fragment fragment, int position)
    {
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", lists.get(position));
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
    public List<ViewDestinationData> getStudentist() {
        return lists;
    }
    public void filterList(List<ViewDestinationData> filteredList) {
        lists = filteredList;
        notifyDataSetChanged();
    }
}

