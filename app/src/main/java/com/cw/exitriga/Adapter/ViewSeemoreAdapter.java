package com.cw.exitriga.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cw.exitriga.Activity.NewsDetailActivity;
import com.cw.exitriga.R;

import java.util.List;


public class ViewSeemoreAdapter extends RecyclerView.Adapter<ViewSeemoreAdapter.ViewHolder> {

    private Context context;
    List<String> menunamelist;
    List<Integer> menuiconlist;
    List<Integer> menucolorlist;

    public ViewSeemoreAdapter(Context context, List<String> menunamelist, List<Integer> menuiconlist, List<Integer> menucolorlist) {
        this.context = context;
        this.menucolorlist = menucolorlist;
        this.menuiconlist = menuiconlist;
        this.menunamelist = menunamelist;
    }
    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users, null);
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_seemore, parent,false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.txt_name.setText(menunamelist.get(position));
        holder.layout_color.setBackgroundResource(menucolorlist.get(position));
        holder.img_icon.setImageResource(menuiconlist.get(position));

      holder.layout_main.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

          }
      });
    }

    @Override
    public int getItemCount() {
//        return size;
        return menunamelist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout_main,layout_color;
        ImageView img_icon;
        TextView txt_name;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            layout_main = itemLayoutView.findViewById(R.id.layout_main);
            layout_color = itemLayoutView.findViewById(R.id.layout_color);
            img_icon = itemLayoutView.findViewById(R.id.img_icon);
            txt_name = itemLayoutView.findViewById(R.id.txt_name);
        }
    }
}

