package com.cw.exitriga.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.cw.exitriga.ModelClass.VIewDestinationMultipleImage;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.Allurls;
import com.squareup.picasso.Picasso;


import java.util.List;


public class MainPackage_Sliding_adapter extends PagerAdapter {

    private final LayoutInflater inflater;
    Context context;
    private List<VIewDestinationMultipleImage> images;

    public MainPackage_Sliding_adapter(Context context, @NonNull List<VIewDestinationMultipleImage> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return images == null ? 0 : images.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position)
    {
        View imageLayout = inflater.inflate(R.layout.item_banner_home, container, false);

        if (imageLayout != null) {
            final AppCompatImageView imageView = imageLayout.findViewById(R.id.iv_slideimg);
            Picasso.get().load(Allurls.ImageURL + images.get(position).getPath())
                    .error(R.drawable.progress_animation)
                    .placeholder(R.drawable.progress_animation)
                    .into(imageView);

            container.addView(imageLayout, 0);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

        return imageLayout;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}
