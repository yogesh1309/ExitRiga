package com.cw.exitriga.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cw.exitriga.ModelClass.ViewDestinationData;
import com.cw.exitriga.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.List;



public class CarRoutesMapFragment extends Fragment  {

    ImageView img_back;
    Activity activity;
    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";

    ViewDestinationData categoriesList;

    SupportMapFragment mMap;
    GoogleMap googleMapMain;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_car_routes_map, container, false);
        InitView(v);
        Click();
        try {
            categoriesList = (ViewDestinationData) getArguments().getSerializable("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        MapDataSet();
        return v;
    }

    private void Click() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }

    private void InitView(View v) {
        activity = getActivity();
        img_back = v.findViewById(R.id.img_back);
        mMap = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

    }

    private void MapDataSet() {
        mMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMapMain = googleMap;
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

                LatLng latLng1 = new LatLng(Double.parseDouble(categoriesList.getLatitude()), Double.parseDouble(categoriesList.getLongitude()));
                googleMap.addMarker(new MarkerOptions()
                        .position(latLng1)
                        .title(categoriesList.getTitle())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(11));
            }
        });

    }


}
