package com.cw.exitriga.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cw.exitriga.Activity.ViewAllRoutesActivity;
import com.cw.exitriga.Adapter.ViewYourListAdapter;
import com.cw.exitriga.Adapter.ViewYourListSubAdapter;
import com.cw.exitriga.ModelClass.AllRoutesListData;
import com.cw.exitriga.ModelClass.CategoriesList;
import com.cw.exitriga.ModelClass.ViewNewsData;
import com.cw.exitriga.R;
import com.cw.exitriga.Utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class YourListFragment extends Fragment {

    ImageView img_back;
    Activity activity;
    RecyclerView rec_yourlist;
    ViewYourListSubAdapter cateAdapter;
    LinearLayout layout_search;
    ImageView img_search,img_add;
    boolean b = true;
    List<AllRoutesListData> lists = new ArrayList<>();
    EditText edt_search;
    List<AllRoutesListData> data = new ArrayList<>();
    TextView txt_title,txt_yourlist_notdata;
    String yourlistcatename="";
    SessionManager sessionManager;
    boolean flag2 = false;
    int pos =-1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_your_list, container, false);
        InitView(v);
        Click();
        try
        {
            yourlistcatename = (String) getArguments().getSerializable("data");
            txt_title.setText(yourlistcatename);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        DataSet();

        return v;
    }

    private void filter(String text) {
        try {
            List<AllRoutesListData> filteredList = new ArrayList<>();
            for (AllRoutesListData item : data) {
                if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            cateAdapter.filterList(filteredList);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    @SuppressLint("WrongConstant")
    private void DataSet() {
        if (sessionManager.getFavAllRoutesList().size()<=0)
        {
            txt_yourlist_notdata.setVisibility(View.VISIBLE);
            rec_yourlist.setVisibility(View.GONE);
        }
        else {
            System.out.println("sessionManager.getFavAllRoutesList().size() >>>>>>>>>>"+sessionManager.getFavAllRoutesList().size());
           lists.clear();
            for (int i=0;i<sessionManager.getFavAllRoutesList().size();i++)
            {
                if (sessionManager.getFavAllRoutesList().get(i).getName().equals(yourlistcatename))
                {
                   /* flag2 = true;
                    pos =i;
                    break;*/
                    for (int j=0;j<sessionManager.getFavAllRoutesList().get(i).getData().size();j++)
                    {
                        lists.add(sessionManager.getFavAllRoutesList().get(i).getData().get(j));
                    }
                }
            }

            System.out.println("lists size >>>>>>>>>>"+lists.size());
            if (lists.size()==0)
            {
                txt_yourlist_notdata.setVisibility(View.VISIBLE);
                rec_yourlist.setVisibility(View.GONE);
            }
            else {
                txt_yourlist_notdata.setVisibility(View.GONE);
                rec_yourlist.setVisibility(View.VISIBLE);

                data = lists;

                rec_yourlist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                rec_yourlist.setHasFixedSize(true);
                cateAdapter = new ViewYourListSubAdapter(getActivity(),lists);
                rec_yourlist.setAdapter(cateAdapter);

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
            }
        }
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

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ViewAllRoutesActivity.class);
                intent.putExtra("name",yourlistcatename);
                startActivityForResult(intent,101);
            }
        });
    }
    private void InitView(View v) {
        activity = getActivity();
        sessionManager = new SessionManager(activity);
        img_back = v.findViewById(R.id.img_back);
        rec_yourlist = v.findViewById(R.id.rec_yourlist);
        layout_search = v.findViewById(R.id.layout_search);
        img_search = v.findViewById(R.id.img_search);
        edt_search = v.findViewById(R.id.edt_search);
        txt_title = v.findViewById(R.id.txt_title);
        txt_yourlist_notdata = v.findViewById(R.id.txt_yourlist_notdata);
        img_add = v.findViewById(R.id.img_add);
    }

    @Override
    public void onResume() {
        super.onResume();
        DataSet();
    }
}
