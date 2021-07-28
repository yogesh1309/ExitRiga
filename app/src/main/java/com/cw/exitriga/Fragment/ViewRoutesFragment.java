package com.cw.exitriga.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cw.exitriga.Adapter.StaggedAdapter;
import com.cw.exitriga.Adapter.ViewNewsAdapter;
import com.cw.exitriga.Adapter.ViewRoutesAdapter;
import com.cw.exitriga.Interface.JsonPlaceHolderApi;
import com.cw.exitriga.ModelClass.ViewCategoriesData;
import com.cw.exitriga.ModelClass.ViewCategoriesResponse;
import com.cw.exitriga.ModelClass.ViewNewsData;
import com.cw.exitriga.ModelClass.ViewRoutesData;
import com.cw.exitriga.ModelClass.ViewRoutesResponse;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.ApiUtils;
import com.cw.exitriga.Utils.CustomAlertdialog;
import com.cw.exitriga.Utils.Customprogress;
import com.cw.exitriga.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewRoutesFragment extends Fragment {

    Activity activity;
    ImageView img_back;
    LinearLayout layout_carroutes;
    LinearLayout layout_search;
    ImageView img_search;
    boolean b = true;
    JsonPlaceHolderApi placeHolderApi;
    RecyclerView recyclerView;
    ViewRoutesAdapter viewMembersAdapter;
    EditText edt_search;
    List<ViewRoutesData> data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_routes, container, false);
        InitView(v);
        if (Utils.isInternetConnected(activity)) {
            SendPostRoutes();
        } else {
            CustomAlertdialog.createDialog(activity, getString(R.string.no_internet));
        }
        Click();

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

    public void SendPostRoutes()
    {
        Customprogress.showPopupProgressSpinner(activity, true);
        placeHolderApi.getroutes().enqueue(new Callback<ViewRoutesResponse>() {
            @Override
            public void onResponse(Call<ViewRoutesResponse> call, Response<ViewRoutesResponse> response) {
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
            public void onFailure(Call<ViewRoutesResponse> call, Throwable t)
            {
                Customprogress.showPopupProgressSpinner(activity, false);
            }
        });
    }

    private void Click() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });

        layout_carroutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CarRoutesFragment());
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
        placeHolderApi = ApiUtils.getAPIService();
        img_back = v.findViewById(R.id.img_back);
        layout_carroutes = v.findViewById(R.id.layout_carroutes);
        layout_search = v.findViewById(R.id.layout_search);
        img_search = v.findViewById(R.id.img_search);
        recyclerView = v.findViewById(R.id.recyclerView);
        edt_search = v.findViewById(R.id.edt_search);
    }

    private void DataSet(List<ViewRoutesData> data)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setHasFixedSize(true);
        viewMembersAdapter = new ViewRoutesAdapter(activity,data);
        recyclerView.setAdapter(viewMembersAdapter);
    }
    public void replaceFragment(Fragment fragment){
        FragmentTransaction ft = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(
                R.anim.slide_from_right,
                R.anim.slide_to_left,
                R.anim.slide_from_left,
                R.anim.slide_to_right  ).addToBackStack("vjhvj");
        ft.replace(R.id.flHomeContainer,fragment);
        ft.commit();
    }
    private void filter(String text) {
        List<ViewRoutesData> filteredList = new ArrayList<>();
        for (ViewRoutesData item : data) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        viewMembersAdapter.filterList(filteredList);
    }

}
