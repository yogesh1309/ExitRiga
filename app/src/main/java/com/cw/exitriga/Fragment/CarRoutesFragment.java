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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cw.exitriga.Adapter.ViewCarRoutesAdapter;
import com.cw.exitriga.Adapter.ViewNewsAdapter;
import com.cw.exitriga.Interface.JsonPlaceHolderApi;
import com.cw.exitriga.ModelClass.CategoriesList;
import com.cw.exitriga.ModelClass.ViewDestinationData;
import com.cw.exitriga.ModelClass.ViewDestinationParameter;
import com.cw.exitriga.ModelClass.ViewDestinationResponse;
import com.cw.exitriga.ModelClass.ViewNewsData;
import com.cw.exitriga.ModelClass.ViewNewsParameter;
import com.cw.exitriga.ModelClass.ViewNewsResponse;
import com.cw.exitriga.ModelClass.ViewRoutesData;
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

public class CarRoutesFragment extends Fragment {

    ImageView img_back;
    Activity activity;
    RecyclerView rec_carroutes;
    ViewCarRoutesAdapter viewMembersAdapter;
    LinearLayout layout_search;
    ImageView img_search;
    boolean b = true;
    List<CategoriesList> lists = new ArrayList<>();
    TextView txt_name;
    ViewRoutesData viewRoutesData;
    private static final int REQUEST_LOCATION = 1;
    protected LocationManager locationManager;
    double lat, lng;
    private JsonPlaceHolderApi mAPIService;
    EditText edt_search;
    List<ViewDestinationData> data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_car_routes, container, false);
        InitView(v);
        Click();
        try {
            viewRoutesData = (ViewRoutesData) getArguments().getSerializable("data");

            txt_name.setText(viewRoutesData.getName());

            CurrentLocation();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return v;
    }

    private void DataSet(List<ViewDestinationData> data) {

        rec_carroutes.setLayoutManager(new LinearLayoutManager(activity));
        rec_carroutes.setHasFixedSize(true);
        viewMembersAdapter = new ViewCarRoutesAdapter(activity,data);
        rec_carroutes.setAdapter(viewMembersAdapter);
    }

    private void filter(String text) {
        List<ViewDestinationData> filteredList = new ArrayList<>();
        for (ViewDestinationData item : data) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        viewMembersAdapter.filterList(filteredList);
    }


    private void Click() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b)
                {
                    b=false;
                    layout_search.setVisibility(View.VISIBLE);
                }
                else {

                    b=true;
                    layout_search.setVisibility(View.GONE);
                }
            }
        });

    }

    private void InitView(View v) {
        activity = getActivity();
        mAPIService = ApiUtils.getAPIService();
        img_back = v.findViewById(R.id.img_back);
        rec_carroutes = v.findViewById(R.id.rec_carroutes);
        layout_search = v.findViewById(R.id.layout_search);
        img_search = v.findViewById(R.id.img_search);
        txt_name = v.findViewById(R.id.txt_name);
        edt_search = v.findViewById(R.id.edt_search);
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

        ViewDestinationParameter viewNewsParameter = new ViewDestinationParameter();
        viewNewsParameter.setLatitude(lat);
        viewNewsParameter.setLongitude(lng);
        viewNewsParameter.setRouteId(viewRoutesData.getId());

        mAPIService.getdestination(viewNewsParameter).enqueue(new Callback<ViewDestinationResponse>() {
            @Override
            public void onResponse(Call<ViewDestinationResponse> call, Response<ViewDestinationResponse> response) {
                Customprogress.showPopupProgressSpinner(activity, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String message = response.body().getMessage();
                    if (status == true) {

                        data = response.body().getData();
                        DataSet(response.body().getData());

                    } else {
                        Toast.makeText(activity, "" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ViewDestinationResponse> call, Throwable t)
            {
                Customprogress.showPopupProgressSpinner(activity, false);
            }
        });
    }
}
