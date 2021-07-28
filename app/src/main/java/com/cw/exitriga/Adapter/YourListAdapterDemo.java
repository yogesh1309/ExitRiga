package com.cw.exitriga.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.exitriga.Fragment.SavedFragment;
import com.cw.exitriga.Fragment.ViewRoutesFragment;
import com.cw.exitriga.ModelClass.ViewCategoriesData;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.Allurls;
import com.cw.exitriga.Utils.RoundRectCornerImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class YourListAdapterDemo extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int LAYOUT_ONE = 0;
    private static final int LAYOUT_TWO = 1;
    Context context;
    List<List<ViewCategoriesData>> data;

    public YourListAdapterDemo(Context context, List<List<ViewCategoriesData>> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0)
            return LAYOUT_ONE;
        else
            return LAYOUT_TWO;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == LAYOUT_ONE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category1, parent, false);
            viewHolder = new ViewHolderOne(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category2, parent, false);
            viewHolder = new ViewHolderTwo(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == LAYOUT_ONE) {
            ViewHolderOne holder1 = (ViewHolderOne) holder;
            for (int i = 0; i < data.get(position).size(); i++) {
                ImageView img = null;
                TextView txt=null;
                switch (i) {
                    case 0:
                       img = holder1.img_bg1;
                       txt = holder1.txt_name1;
                        break;
                    case 1:
                        img = holder1.img_bg2;
                        txt = holder1.txt_name2;
                        break;
                    case 2:
                        img = holder1.img_bg3;
                        txt = holder1.txt_name3;
                        break;
                }
                Picasso.get().load(Allurls.ImageURL + data.get(position).get(i).getImage())
                        .error(R.drawable.progress_animation)
                        .placeholder(R.drawable.progress_animation)
                        .into(img);
                txt.setText(data.get(position).get(i).getName());
            }
            holder1.layout_main1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = data.get(position).get(0).getName();
                    if (name.contains("Routes") | name.contains("routes")) {
                        replaceFragment(new ViewRoutesFragment(), data.get(position).get(0));
                    } else {
                        replaceFragment(new SavedFragment(), data.get(position).get(0));
//                replaceFragment(new CategoriesDetail1Fragment());
                    }
                }
            });

            holder1.layout_main2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = data.get(position).get(1).getName();
                    if (name.contains("Routes") | name.contains("routes")) {
                        replaceFragment(new ViewRoutesFragment(), data.get(position).get(1));
                    } else {
                        replaceFragment(new SavedFragment(), data.get(position).get(1));
//                replaceFragment(new CategoriesDetail1Fragment());
                    }
                }
            });

            holder1.layout_main3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = data.get(position).get(2).getName();
                    if (name.contains("Routes") | name.contains("routes")) {
                        replaceFragment(new ViewRoutesFragment(), data.get(position).get(2));
                    } else {
                        replaceFragment(new SavedFragment(), data.get(position).get(2));
//                replaceFragment(new CategoriesDetail1Fragment());
                    }
                }
            });

        } else {
            ViewHolderTwo holder1 = (ViewHolderTwo) holder;
            for (int i = 0; i < data.get(position).size(); i++) {
                ImageView img = null;
                TextView txt=null;
                switch (i) {
                    case 0:
                        img = holder1.img_bg1;
                        txt = holder1.txt_name1;
                        break;
                    case 1:
                        img = holder1.img_bg2;
                        txt = holder1.txt_name2;
                        break;
                    case 2:
                        img = holder1.img_bg3;
                        txt = holder1.txt_name3;
                        break;
                }

                Picasso.get().load(Allurls.ImageURL + data.get(position).get(i).getImage())
                        .error(R.drawable.progress_animation)
                        .placeholder(R.drawable.progress_animation)
                        .into(img);
                txt.setText(data.get(position).get(i).getName());
            }

            holder1.layout_main1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = data.get(position).get(0).getName();
                    if (name.contains("Routes") | name.contains("routes")) {
                        replaceFragment(new ViewRoutesFragment(), data.get(position).get(0));
                    } else {
                        replaceFragment(new SavedFragment(), data.get(position).get(0));
//                replaceFragment(new CategoriesDetail1Fragment());
                    }
                }
            });

            holder1.layout_main2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = data.get(position).get(1).getName();
                    if (name.contains("Routes") | name.contains("routes")) {
                        replaceFragment(new ViewRoutesFragment(), data.get(position).get(1));
                    } else {
                        replaceFragment(new SavedFragment(), data.get(position).get(1));
//                replaceFragment(new CategoriesDetail1Fragment());
                    }
                }
            });

            holder1.layout_main3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = data.get(position).get(2).getName();
                    if (name.contains("Routes") | name.contains("routes")) {
                        replaceFragment(new ViewRoutesFragment(), data.get(position).get(2));
                    } else {
                        replaceFragment(new SavedFragment(), data.get(position).get(2));
//                replaceFragment(new CategoriesDetail1Fragment());
                    }
                }
            });

        }
    }

    //****************  VIEW HOLDER 1 ******************//

    public void replaceFragment(Fragment fragment, ViewCategoriesData viewCategoriesData) {
        FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", viewCategoriesData);
        fragment.setArguments(bundle);
        ft.setCustomAnimations(
                R.anim.slide_from_right,
                R.anim.slide_to_left,
                R.anim.slide_from_left,
                R.anim.slide_to_right).addToBackStack("vjhvj");
        ft.replace(R.id.flHomeContainer, fragment);
        ft.commit();
    }


    //****************  VIEW HOLDER 2 ******************//

    public class ViewHolderOne extends RecyclerView.ViewHolder {

        RelativeLayout layout_main1, layout_main2, layout_main3;
        RoundRectCornerImageView img_bg1, img_bg2, img_bg3;
        TextView txt_name1, txt_name2, txt_name3;

        public ViewHolderOne(View itemView) {
            super(itemView);
            layout_main1 = itemView.findViewById(R.id.layout_main1);
            layout_main2 = itemView.findViewById(R.id.layout_main2);
            layout_main3 = itemView.findViewById(R.id.layout_main3);
            img_bg1 = itemView.findViewById(R.id.img_bg1);
            img_bg2 = itemView.findViewById(R.id.img_bg2);
            img_bg3 = itemView.findViewById(R.id.img_bg3);
            txt_name1 = itemView.findViewById(R.id.txt_name1);
            txt_name2 = itemView.findViewById(R.id.txt_name2);
            txt_name3 = itemView.findViewById(R.id.txt_name3);
        }
    }

    public class ViewHolderTwo extends RecyclerView.ViewHolder {
        RelativeLayout layout_main1, layout_main2, layout_main3;
        RoundRectCornerImageView img_bg1, img_bg2, img_bg3;
        TextView txt_name1, txt_name2, txt_name3;

        public ViewHolderTwo(View itemView) {
            super(itemView);
            layout_main1 = itemView.findViewById(R.id.layout_main1);
            layout_main2 = itemView.findViewById(R.id.layout_main2);
            layout_main3 = itemView.findViewById(R.id.layout_main3);
            img_bg1 = itemView.findViewById(R.id.img_bg1);
            img_bg2 = itemView.findViewById(R.id.img_bg2);
            img_bg3 = itemView.findViewById(R.id.img_bg3);
            txt_name1 = itemView.findViewById(R.id.txt_name1);
            txt_name2 = itemView.findViewById(R.id.txt_name2);
            txt_name3 = itemView.findViewById(R.id.txt_name3);
        }
    }

}
