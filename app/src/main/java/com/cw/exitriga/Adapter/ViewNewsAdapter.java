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
import com.cw.exitriga.ModelClass.NewsList;
import com.cw.exitriga.ModelClass.ViewNewsData;
import com.cw.exitriga.ModelClass.ViewNewsResponse;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.Allurls;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ViewNewsAdapter extends RecyclerView.Adapter<ViewNewsAdapter.ViewHolder> {

    private Context context;
    List<ViewNewsData> newsLists;

    public ViewNewsAdapter(Context context, List<ViewNewsData> newsLists) {
        this.context = context;
        this.newsLists = newsLists;
    }
    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
//        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users, null);
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_news, parent,false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {

//        holder.img_bg.setImageResource(newsLists.get(position).getImage());
        Picasso.get().load(Allurls.ImageURL + newsLists.get(position).getImage())
                .error(R.drawable.progress_animation)
                .placeholder(R.drawable.progress_animation)
                .into(holder.img_bg);
        holder.txt_name.setText(newsLists.get(position).getTitle());

      holder.layout_main.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(context, NewsDetailActivity.class);
              intent.putExtra("data",newsLists.get(position));
              context.startActivity(intent);
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
        ImageView img_bg;
        TextView txt_name;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            layout_main = itemLayoutView.findViewById(R.id.layout_main);
            img_bg = itemLayoutView.findViewById(R.id.img_bg);
            txt_name = itemLayoutView.findViewById(R.id.txt_name);
        }
    }
    // method to access in activity after updating selection
    public List<ViewNewsData> getStudentist() {
        return newsLists;
    }
    public void filterList(List<ViewNewsData> filteredList) {
        newsLists = filteredList;
        notifyDataSetChanged();
    }

}

