package com.cw.exitriga.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cw.exitriga.Adapter.AddPlacesAdapter;
import com.cw.exitriga.Adapter.ViewPlacesAdapter;
import com.cw.exitriga.ModelClass.CategoriesList;
import com.cw.exitriga.R;

import java.util.ArrayList;
import java.util.List;

public class AddMorePlacesFragment extends Fragment {

    ImageView img_back;
    Activity activity;
    RecyclerView rec_place;
    AddPlacesAdapter viewMembersAdapter;
    List<CategoriesList> lists = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_more_places, container, false);
        InitView(v);
        Click();
        DataSet();
        return v;
    }
    private void DataSet()
    {
        lists.clear();
        CategoriesList categoriesList = new CategoriesList("Durbe manor",R.drawable.img_carratues_demo);
        CategoriesList categoriesList1 = new CategoriesList("Eastern gold museum",R.drawable.img_carratues_demo2);
        CategoriesList categoriesList2 = new CategoriesList("Slokenbeka manor",R.drawable.img_carratues_demo);
        CategoriesList categoriesList3 = new CategoriesList("Latvian road museum",R.drawable.img_carratues_demo2);
        lists.add(categoriesList);
        lists.add(categoriesList1);
        lists.add(categoriesList2);
        lists.add(categoriesList3);
        rec_place.setLayoutManager(new LinearLayoutManager(activity));
        rec_place.setHasFixedSize(true);
        viewMembersAdapter = new AddPlacesAdapter(activity,lists);
        rec_place.setAdapter(viewMembersAdapter);
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
        rec_place = v.findViewById(R.id.rec_place);
    }

}
