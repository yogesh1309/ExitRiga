package com.cw.exitriga.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cw.exitriga.Fragment.SavedFragment;
import com.cw.exitriga.Fragment.ViewRoutesFragment;
import com.cw.exitriga.ModelClass.ViewCategoriesData;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.Allurls;
import com.cw.exitriga.Utils.RoundRectCornerImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter {

    private Context context;
    List<List<ViewCategoriesData>> data;
    public ListViewAdapter(Context context, List<List<ViewCategoriesData>> data, int row_category1) {
        super(context, row_category1,data);
        this.context = context;
        this.data=data;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        // inflate layout from xml
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        int layoutResource = 0; // determined by view type
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 0:
                layoutResource = R.layout.row_category1;
                break;

            case 1:
                layoutResource = R.layout.row_category2;
                break;
        }

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        // set data to views

        if (position % 2==0)
        {
            for (int i=0;i<data.get(position).size();i++)
            {
                if (i==0)
                {
                    Picasso.get().load(Allurls.ImageURL + data.get(position).get(i).getImage())
                            .error(R.drawable.progress_animation)
                            .placeholder(R.drawable.progress_animation)
                            .into(holder.img_bg1);
                    holder.txt_name1.setText(data.get(position).get(i).getName());
                }
                else if (i==1)
                {
                    Picasso.get().load(Allurls.ImageURL + data.get(position).get(i).getImage())
                            .error(R.drawable.progress_animation)
                            .placeholder(R.drawable.progress_animation)
                            .into(holder.img_bg2);
                    holder.txt_name2.setText(data.get(position).get(i).getName());
                }
                else if (i==2)
                {
                    Picasso.get().load(Allurls.ImageURL + data.get(position).get(i).getImage())
                            .error(R.drawable.progress_animation)
                            .placeholder(R.drawable.progress_animation)
                            .into(holder.img_bg3);
                    holder.txt_name3.setText(data.get(position).get(i).getName());
                }
            }
        }
        else {
            for (int i=0;i<data.get(position).size();i++)
            {
                if (i==0)
                {
                    Picasso.get().load(Allurls.ImageURL + data.get(position).get(i).getImage())
                            .error(R.drawable.progress_animation)
                            .placeholder(R.drawable.progress_animation)
                            .into(holder.img_bg1);
                    holder.txt_name1.setText(data.get(position).get(i).getName());
                }
                else if (i==1)
                {
                    Picasso.get().load(Allurls.ImageURL + data.get(position).get(i).getImage())
                            .error(R.drawable.progress_animation)
                            .placeholder(R.drawable.progress_animation)
                            .into(holder.img_bg2);
                    holder.txt_name2.setText(data.get(position).get(i).getName());
                }
                else if (i==2)
                {
                    Picasso.get().load(Allurls.ImageURL + data.get(position).get(i).getImage())
                            .error(R.drawable.progress_animation)
                            .placeholder(R.drawable.progress_animation)
                            .into(holder.img_bg3);
                    holder.txt_name3.setText(data.get(position).get(i).getName());
                }
            }
        }

        holder.layout_main1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = data.get(position).get(0).getName();
                if (name.contains("Routes")|name.contains("routes"))
                {
                    replaceFragment(new ViewRoutesFragment(),data.get(position).get(0));
                }
                else {
                    replaceFragment(new SavedFragment(),data.get(position).get(0));
//                replaceFragment(new CategoriesDetail1Fragment());
                }
            }
        });

        holder.layout_main2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = data.get(position).get(1).getName();
                if (name.contains("Routes")|name.contains("routes"))
                {
                    replaceFragment(new ViewRoutesFragment(),data.get(position).get(1));
                }
                else {
                    replaceFragment(new SavedFragment(),data.get(position).get(1));
//                replaceFragment(new CategoriesDetail1Fragment());
                }
            }
        });

        holder.layout_main3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = data.get(position).get(2).getName();
                if (name.contains("Routes")|name.contains("routes"))
                {
                    replaceFragment(new ViewRoutesFragment(),data.get(position).get(2));
                }
                else {
                    replaceFragment(new SavedFragment(),data.get(position).get(2));
//                replaceFragment(new CategoriesDetail1Fragment());
                }
            }
        });

        return convertView;
    }

    private class ViewHolder {
        RoundRectCornerImageView img_bg1,img_bg2,img_bg3;
        TextView txt_name1,txt_name2,txt_name3;
        RelativeLayout layout_main3,layout_main2,layout_main1;
        public ViewHolder(View v) {
            img_bg1 = v.findViewById(R.id.img_bg1);
            img_bg2 = v.findViewById(R.id.img_bg2);
            img_bg3 = v.findViewById(R.id.img_bg3);
            txt_name1 = v.findViewById(R.id.txt_name1);
            txt_name2 = v.findViewById(R.id.txt_name2);
            txt_name3 = v.findViewById(R.id.txt_name3);
            layout_main3 = v.findViewById(R.id.layout_main3);
            layout_main2 = v.findViewById(R.id.layout_main2);
            layout_main1 = v.findViewById(R.id.layout_main1);

        }
    }
  public void replaceFragment(Fragment fragment, ViewCategoriesData viewCategoriesData){
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",viewCategoriesData);
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
