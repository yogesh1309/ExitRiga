package com.cw.exitriga.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cw.exitriga.Fragment.MeasuredDetailFragment;
import com.cw.exitriga.Fragment.YourListDetailFragment;
import com.cw.exitriga.Fragment.YourListFragment;
import com.cw.exitriga.Interface.GetName;
import com.cw.exitriga.ModelClass.AllRoutesMain;
import com.cw.exitriga.R;
import com.cw.exitriga.Utils.SessionManager;

import java.util.ArrayList;


public class ViewYourListAdapter extends RecyclerView.Adapter<ViewYourListAdapter.ViewHolder> {

    private Context context;
    ArrayList<String> yourList;
    SessionManager sessionManager;
    GetName getName;

    public ViewYourListAdapter(Context context, ArrayList<String> yourList, GetName getName) {
        this.context = context;
        this.yourList = yourList;
        this.getName = getName;
        sessionManager = new SessionManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_yourlist, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txt_name.setText(yourList.get(position));

        holder.img_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getName.getname(yourList.get(position));
            }
        });

        holder.layout_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new YourListFragment(),position);
//                replaceFragment(new YourListDetailFragment());
            }
        });
    }

    @Override
    public int getItemCount() {
        return yourList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_dot;
        LinearLayout layout_main;
        TextView txt_name;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            img_dot = itemLayoutView.findViewById(R.id.img_dot);
            layout_main = itemLayoutView.findViewById(R.id.layout_main);
            txt_name = itemLayoutView.findViewById(R.id.txt_name);
        }
    }
    public void replaceFragment(Fragment fragment, int position) {
        FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",yourList.get(position));
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

