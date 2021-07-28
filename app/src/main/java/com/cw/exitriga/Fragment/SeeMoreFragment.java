package com.cw.exitriga.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cw.exitriga.Adapter.ViewNewsAdapter;
import com.cw.exitriga.Adapter.ViewSeemoreAdapter;
import com.cw.exitriga.R;

import java.util.ArrayList;
import java.util.List;

public class SeeMoreFragment extends Fragment {

    RecyclerView rec_more;
    Context context;
    ViewSeemoreAdapter ViewSeemoreAdapter;
    List<String> menunamelist = new ArrayList<>();
    List<Integer> menuiconlist = new ArrayList<>();
    List<Integer> menucolorlist = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_see_more, container, false);
        InitView(v);
        DataSet();
        return v;
    }

    private void InitView(View v) {
        context = getActivity();
        rec_more = v.findViewById(R.id.rec_more);
    }
    private void DataSet()
    {
        menunamelist.clear();
        menunamelist.add("Cookie");
        menunamelist.add("About");
        menunamelist.add("Suggestion");
        menunamelist.add("Report");
        menunamelist.add("Language");

        menuiconlist.clear();
        menuiconlist.add(R.drawable.ic_global);
        menuiconlist.add(R.drawable.ic_information);
        menuiconlist.add(R.drawable.ic_thumbs_up);
        menuiconlist.add(R.drawable.ic_report);
        menuiconlist.add(R.drawable.ic_translate);

        menucolorlist.clear();
        menucolorlist.add(R.color.colorcookie);
        menucolorlist.add(R.color.colorabout);
        menucolorlist.add(R.color.colorApp);
        menucolorlist.add(R.color.colorrepost);
        menucolorlist.add(R.color.colorlanguagechanges);

        rec_more.setLayoutManager(new GridLayoutManager(context,2));
        rec_more.setHasFixedSize(true);
        ViewSeemoreAdapter = new ViewSeemoreAdapter(context,menunamelist,menuiconlist,menucolorlist);
        rec_more.setAdapter(ViewSeemoreAdapter);
    }


}
