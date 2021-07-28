package com.cw.exitriga.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cw.exitriga.Adapter.ViewNewsAdapter;
import com.cw.exitriga.Interface.JsonPlaceHolderApi;
import com.cw.exitriga.ModelClass.NewsList;
import com.cw.exitriga.ModelClass.ViewNewsData;
import com.cw.exitriga.ModelClass.ViewNewsParameter;
import com.cw.exitriga.ModelClass.ViewNewsResponse;
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

public class NewsFragment extends Fragment {
   RecyclerView rec_news;
    ViewNewsAdapter viewMembersAdapter;
    Context context;
    LinearLayout layout_search;
    ImageView img_search,img_map;
    boolean b = true;
    private static final int REQUEST_LOCATION = 1;
    protected LocationManager locationManager;
    double lat, lng;
    private JsonPlaceHolderApi mAPIService;
    EditText edt_search;
    List<ViewNewsData> data;
    List<ViewNewsData> viewNewsDataList;
    private SwipeRefreshLayout swiperefresh_items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        InitView(v);
        Click();
        CurrentLocation();
        LayoutRefershData();

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

    private void filter(String text) {
        List<ViewNewsData> filteredList = new ArrayList<>();
        for (ViewNewsData item : data) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        viewMembersAdapter.filterList(filteredList);
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

    private void Click() {
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

        img_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new NewsAllMapFragment());
            }
        });
    }

    private void DataSet(List<ViewNewsData> data)
    {
        rec_news.setLayoutManager(new LinearLayoutManager(context));
        rec_news.setHasFixedSize(true);
        viewMembersAdapter = new ViewNewsAdapter(context,data);
        rec_news.setAdapter(viewMembersAdapter);
    }

    private void InitView(View v) {
        context = getActivity();
        mAPIService = ApiUtils.getAPIService();
        rec_news = v.findViewById(R.id.rec_news);
        layout_search = v.findViewById(R.id.layout_search);
        img_search = v.findViewById(R.id.img_search);
        edt_search = v.findViewById(R.id.edt_search);
        swiperefresh_items = v.findViewById(R.id.swiperefresh_items);
        img_map = v.findViewById(R.id.img_map);
    }

    private void getLocation()
    {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                if (Utils.isInternetConnected(context)) {
                    viewNewsDataList = new ArrayList<>();
                    viewNewsDataList.clear();
                    SendPostNews(latti, longi);
                } else {
                    CustomAlertdialog.createDialog(context, getString(R.string.no_internet));
                }
            } else {

                Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location1 != null) {
                    double latti = location1.getLatitude();
                    double longi = location1.getLongitude();
                    if (Utils.isInternetConnected(context)) {
                        viewNewsDataList = new ArrayList<>();
                        viewNewsDataList.clear();
                        SendPostNews(latti, longi);
                    } else {
                        CustomAlertdialog.createDialog(context, getString(R.string.no_internet));
                    }
//                Toast.makeText(context, "Unable to Trace your location", Toast.LENGTH_SHORT).show();
                }
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

    public void SendPostNews(Double lat, Double lng)
    {
        Customprogress.showPopupProgressSpinner(context, true);

        ViewNewsParameter viewNewsParameter = new ViewNewsParameter();
        viewNewsParameter.setLatitude(lat);
        viewNewsParameter.setLongitude(lng);

        mAPIService.getnews(viewNewsParameter).enqueue(new Callback<ViewNewsResponse>() {
            @Override
            public void onResponse(Call<ViewNewsResponse> call, Response<ViewNewsResponse> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {


                    boolean status = response.body().getStatus();
                    String message = response.body().getMessage();
                    if (status == true) {

                        data = response.body().getData();
                        if (response.body().getData().size()<=3)
                        {
                            for (int i=0;i<response.body().getData().size();i++)
                            {
                                viewNewsDataList.add(response.body().getData().get(i));
                            }
                        }
                        else {
                            for (int i=0;i<3;i++)
                            {
                                viewNewsDataList.add(response.body().getData().get(i));

                            }
                        }
                        DataSet(viewNewsDataList);

                    } else {
                        Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ViewNewsResponse> call, Throwable t)
            {
                Customprogress.showPopupProgressSpinner(context, false);
            }
        });
    }

    private void LayoutRefershData() {
        swiperefresh_items.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                if (Utils.isInternetConnected(context)) {
//                    sendPostViewDashboard();
//                } else {
//                    CustomAlertdialog.createDialog(context, getString(R.string.no_internet));
//                }
                CurrentLocation();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(swiperefresh_items.isRefreshing()) {
                            swiperefresh_items.setRefreshing(false);

                        }
                    }
                }, 1000);

            }
        });
    }

    public void replaceFragment(Fragment fragment)
    {
        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) viewNewsDataList);
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
