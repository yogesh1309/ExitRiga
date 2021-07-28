package com.cw.exitriga.Fragment;

import android.Manifest;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.cw.exitriga.Adapter.StaggedAdapter;
import com.cw.exitriga.Adapter.ViewSavedAdapter;
import com.cw.exitriga.Interface.JsonPlaceHolderApi;
import com.cw.exitriga.ModelClass.CategoriesList;
import com.cw.exitriga.ModelClass.ViewCategoriesData;
import com.cw.exitriga.ModelClass.ViewCategoriesResponse;
import com.cw.exitriga.ModelClass.ViewProductData;
import com.cw.exitriga.ModelClass.ViewProductParameter;
import com.cw.exitriga.ModelClass.ViewProductResponse;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.ApiUtils;
import com.cw.exitriga.Utils.CustomAlertdialog;
import com.cw.exitriga.Utils.Customprogress;
import com.cw.exitriga.Utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SavedFragment extends Fragment {
    RecyclerView recyclerView;
    Context context;
    private ViewSavedAdapter thingAdapter;
    ViewCategoriesData viewCategoriesData;
    private JsonPlaceHolderApi placeHolderApi;
    private static final int REQUEST_LOCATION = 1;
    protected LocationManager locationManager;
    double lat, lng;
    ImageView img_map;
    List<ViewProductData> viewProductData = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_saved, container, false);
        InitView(v);
        Click();
        try {
            viewCategoriesData = (ViewCategoriesData) getArguments().getSerializable("data");
            CurrentLocation();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return v;
    }

    private void Click() {
        img_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CategoriesDetail1Fragment());
            }
        });
    }

    private void DataSet(List<ViewProductData> data) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        thingAdapter = new ViewSavedAdapter(getActivity(),data);
        recyclerView.setAdapter(thingAdapter);
    }

    private void CurrentLocation() {
        //current location
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();

        }
    }

    private void getLocation()
    {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());

                if (Utils.isInternetConnected(context)) {
                    viewProductData.clear();
                    SendPostProduct(viewCategoriesData.getId(),latti, longi);
                } else {
                    CustomAlertdialog.createDialog(context, getString(R.string.no_internet));
                }

                System.out.println("lat >>>>>>>>>>" + latti);
                System.out.println("lng >>>>>>>>>>" + longi);

            } else {

                Toast.makeText(context, "Unable to Trace your location", Toast.LENGTH_SHORT).show();

            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

    private void InitView(View v) {
        context = getActivity();
        placeHolderApi = ApiUtils.getAPIService();
        recyclerView = v.findViewById(R.id.recyclerView);
        img_map = v.findViewById(R.id.img_map);
    }

    public void SendPostProduct(String id, double latti, double longi)
    {
        Customprogress.showPopupProgressSpinner(context, true);

        ViewProductParameter viewProductParameter = new ViewProductParameter();
        viewProductParameter.setCategoryId(id);
        viewProductParameter.setLatitude(latti);
        viewProductParameter.setLongitude(longi);

        placeHolderApi.getproduct(viewProductParameter).enqueue(new Callback<ViewProductResponse>() {
            @Override
            public void onResponse(Call<ViewProductResponse> call, Response<ViewProductResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {


                    boolean status = response.body().getStatus();
                    String message = response.body().getMessage();
                    if (status == true)
                    {
                        viewProductData = response.body().getData();
                        DataSet(response.body().getData());
                    } else {
                        Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ViewProductResponse> call, Throwable t)
            {
                Customprogress.showPopupProgressSpinner(context, false);
            }
        });
    }
    public void replaceFragment(Fragment fragment)
    {
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) viewProductData);
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
