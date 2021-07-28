package com.cw.exitriga.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.cw.exitriga.Activity.MainActivity;
import com.cw.exitriga.Adapter.ListViewAdapter;
import com.cw.exitriga.Adapter.StaggedAdapter;
import com.cw.exitriga.Adapter.YourListAdapterDemo;
import com.cw.exitriga.Interface.JsonPlaceHolderApi;
import com.cw.exitriga.ModelClass.CategoriesList;
import com.cw.exitriga.ModelClass.ViewCategoriesData;
import com.cw.exitriga.ModelClass.ViewCategoriesResponse;
import com.cw.exitriga.ModelClass.ViewNewsParameter;
import com.cw.exitriga.ModelClass.ViewNewsResponse;
import com.cw.exitriga.R;
import com.cw.exitriga.Server.Allurls;
import com.cw.exitriga.Server.ApiUtils;
import com.cw.exitriga.Utils.CustomAlertdialog;
import com.cw.exitriga.Utils.Customprogress;
import com.cw.exitriga.Utils.Utils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesFragment extends Fragment {
  RecyclerView recyclerView;
    Context context;
    private JsonPlaceHolderApi mAPIService;
    ShimmerFrameLayout shimmer_view_container;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_categories, container, false);
        InitView(v);
        if (Utils.isInternetConnected(context)) {
            SendPostCategories();
        } else {
            CustomAlertdialog.createDialog(context, getString(R.string.no_internet));
        }
        return v;
    }

    private void DataSet(List<List<ViewCategoriesData>> data)
    {

        LinearLayoutManager staggeredGridLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        YourListAdapterDemo adapter = new YourListAdapterDemo(context, data);
//        ListViewAdapter adapter = new ListViewAdapter(context, data,R.layout.row_category1);
        recyclerView.setAdapter(adapter);
    }

    private void InitView(View v) {
        context = getActivity();
        mAPIService = ApiUtils.getAPIService();
        recyclerView = v.findViewById(R.id.recyclerView);
        shimmer_view_container = v.findViewById(R.id.shimmer_view_container);
    }
    public void SendPostCategories()
    {
//        Customprogress.showPopupProgressSpinner(context, true);
        mAPIService.getcategories().enqueue(new Callback<ViewCategoriesResponse>() {
            @Override
            public void onResponse(Call<ViewCategoriesResponse> call, Response<ViewCategoriesResponse> response) {
//                Customprogress.showPopupProgressSpinner(context, false);
                shimmer_view_container.setVisibility(View.GONE);
                shimmer_view_container.stopShimmerAnimation();
                if (response.isSuccessful()) {


                    boolean status = response.body().getStatus();
                    String message = response.body().getMessage();
                    if (status == true) {

                        recyclerView.setVisibility(View.VISIBLE);
                        DataSet(response.body().getData());

                    } else {
                        Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ViewCategoriesResponse> call, Throwable t)
            {
//                Customprogress.showPopupProgressSpinner(context, false);
                shimmer_view_container.setVisibility(View.GONE);
                shimmer_view_container.stopShimmerAnimation();
            }
        });
    }


}
