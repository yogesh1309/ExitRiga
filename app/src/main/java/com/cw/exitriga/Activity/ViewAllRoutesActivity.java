package com.cw.exitriga.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cw.exitriga.Adapter.ViewAllRoutesAdapter;
import com.cw.exitriga.Adapter.ViewNewsAdapter;
import com.cw.exitriga.Interface.AllRoutesList;
import com.cw.exitriga.Interface.JsonPlaceHolderApi;
import com.cw.exitriga.ModelClass.AllRoutesListData;
import com.cw.exitriga.ModelClass.AllRoutesListRespose;
import com.cw.exitriga.ModelClass.AllRoutesMain;
import com.cw.exitriga.ModelClass.ViewNewsData;
import com.cw.exitriga.ModelClass.ViewNewsParameter;
import com.cw.exitriga.ModelClass.ViewNewsResponse;
import com.cw.exitriga.ModelClass.ViewProductData;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.ApiUtils;
import com.cw.exitriga.Utils.CustomAlertdialog;
import com.cw.exitriga.Utils.Customprogress;
import com.cw.exitriga.Utils.SessionManager;
import com.cw.exitriga.Utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllRoutesActivity extends AppCompatActivity {

    ImageView img_back;
    Context context;
    String name ="";
    TextView txt_title;
    LinearLayout layout_submit;
    RecyclerView rec_yourlist;

    private static final int REQUEST_LOCATION = 1;
    protected LocationManager locationManager;
    double lat, lng;
    private JsonPlaceHolderApi mAPIService;
    ViewAllRoutesAdapter viewMembersAdapter;
    SessionManager sessionManager;

    boolean flag = false;
    boolean flag2 = false;
    ArrayList<AllRoutesListData> listMain = new ArrayList<>();
    ArrayList<AllRoutesMain> FavlistMain = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_routes);
        InitView();
        try {
            Intent intent = getIntent();
           name = intent.getStringExtra("name");

            txt_title.setText(name);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        Click();
        CurrentLocation();

    }

    private void Click()
    {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        layout_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (listMain.size()<=0)
                {
                    CustomAlertdialog.createDialog(context,context.getResources().getString(R.string.Please_select_any_one));
                }
                else
                    {
                    FavlistMain.clear();
                    AllRoutesMain allRoutesMain = new AllRoutesMain(name,listMain);
                    FavlistMain.add(allRoutesMain);
                    if (sessionManager.getFavAllRoutesList().size()<=0)
                    {
                        sessionManager.setFavAllRoutesList(FavlistMain);
                    }
                    else {
                        for (int i = 0; i < sessionManager.getFavAllRoutesList().size(); i++) {
                            if (sessionManager.getFavAllRoutesList().get(i).getName().equals(name)) {
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            ArrayList<AllRoutesMain> list = sessionManager.getFavAllRoutesList();
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getName().equals(name)) {
                                    list.remove(i);
                                    flag2 = true;
//                                    break;
                                }
                            }
                            if (flag2)
                            {
                                for (int i=0;i<FavlistMain.size();i++)
                                {
                                    list.add(FavlistMain.get(i));
                                }
                                sessionManager.setFavAllRoutesList(list);
                                finish();
                            }
                        } else {
                            ArrayList<AllRoutesMain> list = sessionManager.getFavAllRoutesList();
                            for (int i=0;i<FavlistMain.size();i++)
                            {
                                list.add(FavlistMain.get(i));
                            }
                            sessionManager.setFavAllRoutesList(list);
                            finish();
                        }
                    }

                }
            }
        });
    }

    private void InitView() {
        context = ViewAllRoutesActivity.this;
        sessionManager = new SessionManager(context);
        mAPIService = ApiUtils.getAPIService();
        img_back = findViewById(R.id.img_back);
        txt_title = findViewById(R.id.txt_title);
        layout_submit = findViewById(R.id.layout_submit);
        rec_yourlist = findViewById(R.id.rec_yourlist);
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

            ActivityCompat.requestPermissions(ViewAllRoutesActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());

                if (Utils.isInternetConnected(context)) {
                    SendPostNews(latti, longi);
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

    public void SendPostNews(Double lat, Double lng)
    {
        Customprogress.showPopupProgressSpinner(context, true);

        ViewNewsParameter viewNewsParameter = new ViewNewsParameter();
        viewNewsParameter.setLatitude(lat);
        viewNewsParameter.setLongitude(lng);

        mAPIService.getallroutes(viewNewsParameter).enqueue(new Callback<AllRoutesListRespose>() {
            @Override
            public void onResponse(Call<AllRoutesListRespose> call, Response<AllRoutesListRespose> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {


                    boolean status = response.body().getStatus();
                    String message = response.body().getMessage();
                    if (status == true) {

                        DataSet(response.body().getData());

                    } else {
                        Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<AllRoutesListRespose> call, Throwable t)
            {
                Customprogress.showPopupProgressSpinner(context, false);
            }
        });
    }

    private void DataSet(List<AllRoutesListData> data)
    {
        rec_yourlist.setLayoutManager(new LinearLayoutManager(context));
        rec_yourlist.setHasFixedSize(true);
        viewMembersAdapter = new ViewAllRoutesAdapter(context,data, new AllRoutesList() {
            @Override
            public void allrouteslist(ArrayList<AllRoutesListData> list) {
                listMain.clear();
                for (int i=0;i<list.size();i++)
                {
                    listMain.add(list.get(i));
                }
            }
        },name);
        rec_yourlist.setAdapter(viewMembersAdapter);
    }

}