package com.cw.exitriga.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cw.exitriga.Adapter.MySliderAdapter;
import com.cw.exitriga.Adapter.NewsMySliderAdapter;
import com.cw.exitriga.ModelClass.ViewNewsData;
import com.cw.exitriga.ModelClass.ViewProductData;
import com.cw.exitriga.R;
import com.cw.exitriga.Utils.SessionManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class NewsAllMapFragment extends Fragment {
    ImageView img_back;
    Activity context;
    ViewPager2 viewpager;
    NewsMySliderAdapter adapter;
    LinearLayout layout_back, layout_forward,layout_data;
    private int selectedViewPager = 0;
    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";
    //    ViewProductData viewProductData;
    SessionManager sessionManager;

    SupportMapFragment mMap;
    GoogleMap googleMapMain;

    List<ViewNewsData> viewProductData = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news_all_map, container, false);
        InitView(v);
        try {
            viewProductData = (List<ViewNewsData>) getArguments().getSerializable("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Click();
        ViewpagerDataSet();
        MapDataSet();
        return v;
    }
    private void MapDataSet() {
        mMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMapMain = googleMap;
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                /*googleMap.addMarker(new MarkerOptions().position(new LatLng(37.4233438, -122.0728817)).title("pic pin").snippet("pic pin"));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.4233438, -122.0728817),10));
*/
                for (int i=0;i<viewProductData.size();i++)
                {
                    LatLng latLng1 = new LatLng(Double.parseDouble(viewProductData.get(i).getLatitude()), Double.parseDouble(viewProductData.get(i).getLongitude()));
                    googleMap.addMarker(new MarkerOptions()
                            .position(latLng1)
                            .title(viewProductData.get(i).getTitle())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                }
            }
        });

    }

    private void ViewpagerDataSet() {
        if (viewProductData.size()==0)
        {
            layout_data.setVisibility(View.GONE);
        }
        else {
            layout_data.setVisibility(View.VISIBLE);
            adapter = new NewsMySliderAdapter(getActivity(), viewpager,viewProductData);
            viewpager.setAdapter(adapter);
        }

    }

    private void Click() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.onBackPressed();
            }
        });
        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.setCurrentItem(viewpager.getCurrentItem() - 1, true);
            }
        });

        layout_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.setCurrentItem(viewpager.getCurrentItem() + 1, true);
            }
        });
    }

    private void InitView(View v) {
        context = getActivity();
        sessionManager = new SessionManager(context);
        img_back = v.findViewById(R.id.img_back);
        viewpager = v.findViewById(R.id.viewPager);
        layout_back = v.findViewById(R.id.layout_back);
        layout_forward = v.findViewById(R.id.layout_forward);
        layout_data = v.findViewById(R.id.layout_data);
        mMap = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
    }
}