package com.cw.exitriga.Adapter;

import android.content.Context;
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

import com.cw.exitriga.Fragment.ViewPlaesListDetailsFragment;
import com.cw.exitriga.ModelClass.CategoriesList;
import com.cw.exitriga.R;

import java.util.List;


public class AddPlacesAdapter extends RecyclerView.Adapter<AddPlacesAdapter.ViewHolder> {

    private Context context;
    List<CategoriesList> lists;
    public AddPlacesAdapter(Context context, List<CategoriesList> lists) {
        this.context = context;
        this.lists = lists;
    }
    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users, null);
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_places, parent,false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.img_bg.setImageResource(lists.get(position).getImage());
        holder.txt_name.setText(lists.get(position).getName());

      holder.layout_main.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
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
        TextView txt_name;
        ImageView img_bg;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            layout_main = itemLayoutView.findViewById(R.id.layout_main);
            img_bg = itemLayoutView.findViewById(R.id.img_bg);
            txt_name = itemLayoutView.findViewById(R.id.txt_name);
        }
    }
    public void replaceFragment(Fragment fragment){
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(
                R.anim.slide_from_right,
                R.anim.slide_to_left,
                R.anim.slide_from_left,
                R.anim.slide_to_right  ).addToBackStack("vjhvj");
        ft.replace(R.id.flHomeContainer,fragment);
        ft.commit();
    }

}

