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

import com.cw.exitriga.Fragment.YourListDetailFragment;
import com.cw.exitriga.Fragment.YourListFragment;
import com.cw.exitriga.ModelClass.AllRoutesListData;
import com.cw.exitriga.ModelClass.CategoriesList;
import com.cw.exitriga.ModelClass.ViewNewsData;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.Allurls;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ViewYourListSubAdapter extends RecyclerView.Adapter<ViewYourListSubAdapter.ViewHolder> {

    private Context context;
    List<AllRoutesListData> lists;
    public ViewYourListSubAdapter(Context context, List<AllRoutesListData> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_yourlistsub, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        Picasso.get().load(Allurls.ImageURL + lists.get(position).getImage())
                .error(R.drawable.progress_animation)
                .placeholder(R.drawable.progress_animation)
                .into(holder.img_bg);
        holder.txt_name.setText(lists.get(position).getTitle());

        holder.layout_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                replaceFragment(new YourListDetailFragment(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
//        return size;
        return lists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_bg;
        TextView txt_name;
        LinearLayout layout_main;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            img_bg = itemLayoutView.findViewById(R.id.img_bg);
            txt_name = itemLayoutView.findViewById(R.id.txt_name);
            layout_main = itemLayoutView.findViewById(R.id.layout_main);

        }
    }
    public void replaceFragment(Fragment fragment, int position){
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
    public static void createDialog(final Context context)
    {
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_warning);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        TextView txt_cancel = dialog.findViewById(R.id.txt_cancel);
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    // method to access in activity after updating selection
    public List<AllRoutesListData> getStudentist() {
        return lists;
    }
    public void filterList(List<AllRoutesListData> filteredList) {
        lists = filteredList;
        notifyDataSetChanged();
    }
}

