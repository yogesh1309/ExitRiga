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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.exitriga.Activity.NewsDetailActivity;
import com.cw.exitriga.Fragment.CategoriesDetail1Fragment;
import com.cw.exitriga.Fragment.CategoriesDetails2Fragment;
import com.cw.exitriga.Fragment.SavedFragment;
import com.cw.exitriga.ModelClass.CategoriesList;
import com.cw.exitriga.ModelClass.ViewProductData;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.Allurls;
import com.cw.exitriga.Utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ViewSavedAdapter extends RecyclerView.Adapter<ViewSavedAdapter.ViewHolder> {

    private Context context;
    List<ViewProductData> lists;
    SessionManager sessionManager;
    ArrayList<ViewProductData> detailDataArrayList;
    ArrayList<ViewProductData> ClearDataArrayList;
    boolean flag = false;
    boolean flag2 = false;

    public ViewSavedAdapter(Context context, List<ViewProductData> lists) {
        this.context = context;
        this.lists = lists;
        sessionManager = new SessionManager(context);
        detailDataArrayList = new ArrayList<>();
        ClearDataArrayList = new ArrayList<>();
        detailDataArrayList.clear();
        ClearDataArrayList.clear();
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users, null);
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_saved, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

//        holder.img_bg.setImageResource(lists.get(position).getImage());
        Picasso.get().load(Allurls.ImageURL + lists.get(position).getImage())
                .error(R.drawable.progress_animation)
                .placeholder(R.drawable.progress_animation)
                .into(holder.img_bg);
        holder.txt_main.setText(lists.get(position).getTitle());


        if (sessionManager.getProductList().size() == 0) {
            holder.img_fav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart_gray));
        } else {
            System.out.println("session mamanger list size >>>>>"+sessionManager.getProductList().size());
            for (int i = 0; i < sessionManager.getProductList().size(); i++) {
                if (lists.get(position).getId().equals(sessionManager.getProductList().get(i).getId())) {
                    holder.img_fav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart_red));
                }
            }
        }
        holder.layout_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CategoriesDetails2Fragment(), position);
//                replaceFragment(new CategoriesDetail1Fragment(), position);
            }
        });


        holder.layout_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sessionManager.getProductList().size() == 0) {
                    detailDataArrayList.clear();
                    detailDataArrayList.add(lists.get(position));
                    sessionManager.setProductList(detailDataArrayList);
                    notifyDataSetChanged();
                } else {
                    for (int i = 0; i < sessionManager.getProductList().size(); i++) {
                        if (sessionManager.getProductList().get(i).getId().equals(lists.get(position).getId())) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        ArrayList<ViewProductData> list = sessionManager.getProductList();
                         for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getId().equals(lists.get(position).getId())) {
                                list.remove(i);
                               flag2 = true;
                                break;
                            }
                        }
                         if (flag2)
                         {
                             sessionManager.setProductList(list);
                             notifyDataSetChanged();
                             System.out.println("list se remove >>>>>>>>");
                             holder.img_fav.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart_gray));
                         }
                    } else {
                        ArrayList<ViewProductData> list = sessionManager.getProductList();
                        list.add(lists.get(position));
                        sessionManager.setProductList(list);
                        notifyDataSetChanged();
                        System.out.println("list me add >>>>>>>>");
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
//        return size;
        return lists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout_main, layout_fav;
        TextView txt_main;
        ImageView img_bg, img_fav;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            layout_main = itemLayoutView.findViewById(R.id.layout_main);
            img_bg = itemLayoutView.findViewById(R.id.img_bg);
            layout_fav = itemLayoutView.findViewById(R.id.layout_fav);
            txt_main = itemLayoutView.findViewById(R.id.txt_main);
            img_fav = itemLayoutView.findViewById(R.id.img_fav);
        }
    }

    public void replaceFragment(Fragment fragment, int position) {
        FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", lists.get(position));
        fragment.setArguments(bundle);
        ft.setCustomAnimations(
                R.anim.slide_from_right,
                R.anim.slide_to_left,
                R.anim.slide_from_left,
                R.anim.slide_to_right).addToBackStack("vjhvj");
        ft.replace(R.id.flHomeContainer, fragment);
        ft.commit();
    }

}

