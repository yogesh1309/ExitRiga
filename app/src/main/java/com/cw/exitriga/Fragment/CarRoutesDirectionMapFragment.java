package com.cw.exitriga.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.cw.exitriga.Interface.JsonPlaceHolderApi;
import com.cw.exitriga.ModelClass.ViewDestinationData;
import com.cw.exitriga.ModelClass.ViewPlaceData;
import com.cw.exitriga.ModelClass.ViewPlaceParameter;
import com.cw.exitriga.ModelClass.ViewPlaceResponse;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.ApiUtils;
import com.cw.exitriga.Utils.CustomAlertdialog;
import com.cw.exitriga.Utils.Customprogress;
import com.cw.exitriga.Utils.DirectionsJSONParser;
import com.cw.exitriga.Utils.Utils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarRoutesDirectionMapFragment extends Fragment {
    private static final int REQUEST_LOCATION = 1;
    protected LocationManager locationManager;
    ViewDestinationData categoriesList;
    Activity activity;
    ImageView img_back;
    ArrayList<LatLng> mMarkerPoints;
    SupportMapFragment mapFragment;
    double currentlat, currentlng;
    String currentaddress;
    List<ViewPlaceData> viewPlaceDataList = new ArrayList<>();
    private GoogleMap mMap;
    private LatLng mOrigin;
    private LatLng mDestination;
    private Polyline mPolyline;
    private JsonPlaceHolderApi mAPIService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_car_routes_direction_map, container, false);
        InitView(v);
        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                categoriesList = (ViewDestinationData) bundle.getSerializable("data");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        CurrentLocation();
        Click();
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
        mAPIService = ApiUtils.getAPIService();
        img_back = v.findViewById(R.id.img_back);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMarkerPoints = new ArrayList<>();
    }

    private void drawRoute() {

        // Getting URL to the Google Directions API
        String url = getDirectionsUrl(mOrigin, mDestination);
        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Key
        String key = "key=" + getString(R.string.google_maps_key);

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception on download", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private void CurrentLocation() {
        //current location
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();

        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(latti, longi, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                currentaddress = addresses.get(0).getAddressLine(0);
                currentlat = latti;
                currentlng = longi;
                System.out.println("lat >>>>>>>>>>" + currentlat);
                System.out.println("lng >>>>>>>>>>" + currentlng);

                if (Utils.isInternetConnected(activity)) {
                    viewPlaceDataList.clear();
                    SendPostNews(latti, longi);
                } else {
                    CustomAlertdialog.createDialog(activity, getString(R.string.no_internet));
                }

            } else {

                Toast.makeText(getContext(), "Unable to Trace your location", Toast.LENGTH_SHORT).show();

            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                }
                return;
            }
        }
    }

    public void SendPostNews(Double lat, Double lng) {
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
                        viewPlaceDataList = response.body().getData();
                    } else {
//                        Toast.makeText(activity, "" + message, Toast.LENGTH_SHORT).show();
                    }
                }
                MapCalling();
            }

            @Override
            public void onFailure(Call<ViewPlaceResponse> call, Throwable t) {
                MapCalling();
                Customprogress.showPopupProgressSpinner(activity, false);
            }
        });
    }

    private void MapCalling() {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

       /* double latitude = Double.parseDouble("22.8485735");
        double longitude = Double.parseDouble("75.965395");*/
                LatLng location = new LatLng(currentlat, currentlng);
                LatLng location2 = new LatLng(Double.parseDouble(categoriesList.getLatitude()), Double.parseDouble(categoriesList.getLongitude()));
                if (mMarkerPoints.size() > 1) {
                    mMarkerPoints.clear();
                    mMap.clear();
                }
                mMarkerPoints.add(location);
                mMarkerPoints.add(location2);
                MarkerOptions options = new MarkerOptions();
                MarkerOptions options2 = new MarkerOptions();
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).position(location).title(currentaddress);
                options2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).position(location2).title(categoriesList.getAddress());
                mMap.addMarker(options);
                mMap.addMarker(options2);

                if (viewPlaceDataList.size() != 0) {

                    for (int i = 0; i < viewPlaceDataList.size(); i++) {
                        LatLng location1 = new LatLng(Double.parseDouble(viewPlaceDataList.get(i).getLatitude()), Double.parseDouble(viewPlaceDataList.get(i).getLongitude()));
                        mMarkerPoints.add(location1);
                        MarkerOptions options1 = new MarkerOptions();
                        options1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(location1).title(viewPlaceDataList.get(i).getAddress());
                        mMap.addMarker(options1);
                    }
                }
                if (mMarkerPoints.size() >= 2) {
                    mOrigin = mMarkerPoints.get(0);
                    mDestination = mMarkerPoints.get(1);
                    drawRoute();
                }
       /* CustomMarkerInfoWindowView markerWindowView = new CustomMarkerInfoWindowView();
        mMap.setInfoWindowAdapter(markerWindowView);*/
            }
        });
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("DownloadTask", "DownloadTask : " + data);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                if (mPolyline != null) {
                    mPolyline.remove();
                }
                mPolyline = mMap.addPolyline(lineOptions);

            } else
                Toast.makeText(getContext(), "No route is found", Toast.LENGTH_LONG).show();
        }
    }

}