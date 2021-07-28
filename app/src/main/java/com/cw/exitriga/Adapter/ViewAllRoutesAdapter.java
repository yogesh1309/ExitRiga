package com.cw.exitriga.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cw.exitriga.Activity.NewsDetailActivity;
import com.cw.exitriga.Interface.AllRoutesList;
import com.cw.exitriga.ModelClass.AllRoutesListData;
import com.cw.exitriga.ModelClass.ViewNewsData;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.Allurls;
import com.cw.exitriga.Utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ViewAllRoutesAdapter extends RecyclerView.Adapter<ViewAllRoutesAdapter.ViewHolder> {

    private Context context;
    List<AllRoutesListData> newsLists;
    AllRoutesList allRoutesList;
    ArrayList<AllRoutesListData> color_list = new  ArrayList();
    SessionManager sessionManager;
    String name;
    public ViewAllRoutesAdapter(Context context, List<AllRoutesListData> newsLists, AllRoutesList allRoutesList, String name) {
        this.context = context;
        this.newsLists = newsLists;
        this.allRoutesList = allRoutesList;
        this.name = name;
        color_list.clear();

        sessionManager = new SessionManager(context);
    }
    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
//        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users, null);
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_allroutes, parent,false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Picasso.get().load(Allurls.ImageURL + newsLists.get(position).getImage())
                .error(R.drawable.progress_animation)
                .placeholder(R.drawable.progress_animation)
                .into(holder.img_bg);
        holder.txt_name.setText(newsLists.get(position).getTitle());
        holder.txt_desc.setText(Html.fromHtml(newsLists.get(position).getDescription()));

        if (sessionManager.getFavAllRoutesList().size()==0)
        {

        }
        else {
            for (int i=0;i<sessionManager.getFavAllRoutesList().size();i++)
            {
                if (sessionManager.getFavAllRoutesList().get(i).getName().equals(name))
                {
                    for (int j=0;j<sessionManager.getFavAllRoutesList().get(i).getData().size();j++)
                    {
                        if (sessionManager.getFavAllRoutesList().get(i).getData().get(j).getId().equals(newsLists.get(position).getId()))
                        {
                            holder.cb_routes.setChecked(true);
                            color_list.add(newsLists.get(position));
                        }
                    }
                }
            }
            allRoutesList.allrouteslist(color_list);
        }

        holder.cb_routes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    color_list.add(newsLists.get(position));
                    allRoutesList.allrouteslist(color_list);
                }else{
                    color_list.remove(color_list.indexOf(newsLists.get(position)));
                    allRoutesList.allrouteslist(color_list);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return newsLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout layout_main;
        ImageView img_bg;
        TextView txt_name,txt_desc;
        CheckBox cb_routes;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            layout_main = itemLayoutView.findViewById(R.id.layout_main);
            img_bg = itemLayoutView.findViewById(R.id.img_bg);
            txt_name = itemLayoutView.findViewById(R.id.txt_name);
            txt_desc = itemLayoutView.findViewById(R.id.txt_desc);
            cb_routes = itemLayoutView.findViewById(R.id.cb_routes);
        }
    }
    // method to access in activity after updating selection
    public List<AllRoutesListData> getStudentist() {
        return newsLists;
    }
    public void filterList(List<AllRoutesListData> filteredList) {
        newsLists = filteredList;
        notifyDataSetChanged();
    }

}

