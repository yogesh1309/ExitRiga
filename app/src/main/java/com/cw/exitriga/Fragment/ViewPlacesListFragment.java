package com.cw.exitriga.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cw.exitriga.Adapter.ViewCarRoutesAdapter;
import com.cw.exitriga.Adapter.ViewPlacesAdapter;
import com.cw.exitriga.Interface.JsonPlaceHolderApi;
import com.cw.exitriga.ModelClass.CategoriesList;
import com.cw.exitriga.ModelClass.ViewDestinationData;
import com.cw.exitriga.ModelClass.ViewDestinationParameter;
import com.cw.exitriga.ModelClass.ViewDestinationResponse;
import com.cw.exitriga.ModelClass.ViewPlaceData;
import com.cw.exitriga.ModelClass.ViewPlaceParameter;
import com.cw.exitriga.ModelClass.ViewPlaceResponse;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.ApiUtils;
import com.cw.exitriga.Utils.CustomAlertdialog;
import com.cw.exitriga.Utils.Customprogress;
import com.cw.exitriga.Utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPlacesListFragment extends Fragment {
   Activity activity;
   ImageView img_back;
   RecyclerView rec_place;
    ViewPlacesAdapter viewMembersAdapter;
    LinearLayout layout_bottom;
    List<CategoriesList> lists = new ArrayList<>();
    ViewDestinationData categoriesList;
    private static final int REQUEST_LOCATION = 1;
    protected LocationManager locationManager;
    double lat, lng;
    private JsonPlaceHolderApi mAPIService;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_places_list, container, false);
       InitView(v);
       Click();
        try {
            categoriesList = (ViewDestinationData) getArguments().getSerializable("data");
            CurrentLocation();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return v;
    }

    private void DataSet(List<ViewPlaceData> data)
    {
        rec_place.setLayoutManager(new LinearLayoutManager(activity));
        rec_place.setHasFixedSize(true);
        viewMembersAdapter = new ViewPlacesAdapter(activity,data);
        rec_place.setAdapter(viewMembersAdapter);
    }

    private void Click() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
        layout_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AddMorePlacesFragment());
            }
        });
    }

    private void InitView(View v)
    {
        activity = getActivity();
        mAPIService = ApiUtils.getAPIService();
        img_back = v.findViewById(R.id.img_back);
        rec_place = v.findViewById(R.id.rec_place);
        layout_bottom = v.findViewById(R.id.layout_bottom);
    }

    public void replaceFragment(Fragment fragment)
    {
        FragmentTransaction ft = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(
                R.anim.slide_from_right,
                R.anim.slide_to_left,
                R.anim.slide_from_left,
                R.anim.slide_to_right  ).addToBackStack("vjhvj");
        ft.replace(R.id.flHomeContainer,fragment);
        ft.commit();
    }
    private void CurrentLocation() {
        //current location
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();

        }
    }
    private void getLocation()
    {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                Geocoder geocoder = new Geocoder(activity, Locale.getDefault());

                if (Utils.isInternetConnected(activity)) {
                    SendPostNews(latti, longi);
                } else {
                    CustomAlertdialog.createDialog(activity, getString(R.string.no_internet));
                }
                System.out.println("lat >>>>>>>>>>" + latti);
                System.out.println("lng >>>>>>>>>>" + longi);

            } else {

                Toast.makeText(activity, "Unable to Trace your location", Toast.LENGTH_SHORT).show();

            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        System.out.println("Request Code >>>>>>>" + requestCode);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();

                } else {
//                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void SendPostNews(Double lat, Double lng)
    {
        Customprogress.showPopupProgressSpinner(activity, true);

        ViewPlaceParameter viewNewsParameter = new ViewPlaceParameter();
        viewNewsParameter.setLatitude(lat);
        viewNewsParameter.setLongitude(lng);
        viewNewsParameter.setDestinationId(categoriesList.getId());

        mAPIService.getplace(viewNewsParameter).enqueue(new Callback<ViewPlaceResponse>() {
            @Override
            public void onResponse(Call<ViewPlaceResponse> call, Response<ViewPlaceResponse> response) {
                Customprogress.showPopupProgressSpinner(activity, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String message = response.body().getMessage();
                    if (status == true) {

                        DataSet(response.body().getData());

                    } else {
                        Toast.makeText(activity, "" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ViewPlaceResponse> call, Throwable t)
            {
                Customprogress.showPopupProgressSpinner(activity, false);
            }
        });
    }
}
