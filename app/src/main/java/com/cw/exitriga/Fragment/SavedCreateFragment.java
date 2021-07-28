package com.cw.exitriga.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cw.exitriga.Adapter.ViewYourFollowListAdapter;
import com.cw.exitriga.Adapter.ViewYourListAdapter;
import com.cw.exitriga.Interface.GetName;
import com.cw.exitriga.ModelClass.AllRoutesMain;
import com.cw.exitriga.ModelClass.ViewProductData;
import com.cw.exitriga.R;
import com.cw.exitriga.Utils.SessionManager;

import java.util.ArrayList;

public class SavedCreateFragment extends Fragment {

    RecyclerView rec_yourlist,rec_yourfollowlist;
    ViewYourListAdapter cateAdapter;
    ViewYourFollowListAdapter followListAdapter;
    LinearLayout layout_createnew;
    Context context;
    SessionManager sessionManager;
    ArrayList<String> yourArrayList;
    boolean flag = false;
    boolean flag2 = false;

    boolean Mflag = false;
    boolean Mflag2 = false;
    TextView txt_yourfollowed_notdata,txt_yourlist_notdata;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_saved_create, container, false);
        InitView(v);
        Click();
        YourListDataSet();
        YourFollowListDataSet();
        return v;
    }

    private void Click()
    {
        layout_createnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yourArrayList = new ArrayList<>();
                yourArrayList.clear();
                createDialog(context);
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void YourFollowListDataSet() {
        if (sessionManager.getProductList().size()==0)
        {

            rec_yourfollowlist.setVisibility(View.GONE);
            txt_yourfollowed_notdata.setVisibility(View.VISIBLE);
        }
        else {
            rec_yourfollowlist.setVisibility(View.VISIBLE);
            txt_yourfollowed_notdata.setVisibility(View.GONE);
        }
        rec_yourfollowlist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rec_yourfollowlist.setHasFixedSize(true);
        followListAdapter = new ViewYourFollowListAdapter(getActivity(),sessionManager.getProductList());
        rec_yourfollowlist.setAdapter(followListAdapter);
    }

    @SuppressLint("WrongConstant")
    private void YourListDataSet() {
        if (sessionManager.getYourList().size()==0)
        {
            rec_yourlist.setVisibility(View.GONE);
            txt_yourlist_notdata.setVisibility(View.VISIBLE);
        }
        else {
            rec_yourlist.setVisibility(View.VISIBLE);
            txt_yourlist_notdata.setVisibility(View.GONE);
        }
        rec_yourlist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rec_yourlist.setHasFixedSize(true);
        cateAdapter = new ViewYourListAdapter(getActivity(),sessionManager.getYourList(), new GetName() {
            @Override
            public void getname(String name) {
                yourArrayList = new ArrayList<>();
                yourArrayList.clear();
                createDialog2(context,name);
            }
        });
        rec_yourlist.setAdapter(cateAdapter);
    }

    private void InitView(View v)
    {
        context = getActivity();
        sessionManager = new SessionManager(context);
        rec_yourlist = v.findViewById(R.id.rec_yourlist);
        rec_yourfollowlist = v.findViewById(R.id.rec_yourfollowlist);
        layout_createnew = v.findViewById(R.id.layout_createnew);
        txt_yourfollowed_notdata = v.findViewById(R.id.txt_yourfollowed_notdata);
        txt_yourlist_notdata = v.findViewById(R.id.txt_yourlist_notdata);
    }

    public void createDialog(final Context context)
    {
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_new_create);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LinearLayout layout_ok = dialog.findViewById(R.id.layout_ok);
        EditText edt_name = dialog.findViewById(R.id.edt_name);
        layout_ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String s_name = edt_name.getText().toString().trim();
               if (s_name.isEmpty())
               {
                   edt_name.setError(context.getResources().getString(R.string.Please_enter_name));
               }
               else {
                   dialog.dismiss();
                       if (sessionManager.getYourList().size() == 0) {
                           yourArrayList.clear();
                           yourArrayList.add(s_name);
                           sessionManager.setYourList(yourArrayList);
                           YourListDataSet();
                       } else {
                           for (int i = 0; i < sessionManager.getYourList().size(); i++) {
                               if (sessionManager.getYourList().get(i).equals(s_name)) {
                                   flag = true;
                                   break;
                               }
                           }
                           if (flag) {
                             /*  ArrayList<String> list = sessionManager.getYourList();
                               for (int i = 0; i < list.size(); i++) {
                                   if (list.get(i).getId().equals(lists.get(position).getId())) {
                                       list.remove(i);
                                       flag2 = true;
                                       break;
                                   }*/
                               }
                           else {
                               ArrayList<String> list = sessionManager.getYourList();
                               list.add(s_name);
                               sessionManager.setYourList(list);
                               YourListDataSet();
                           }
                       }
               }
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void createDialog2(final Context context, String name)
    {
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_warning);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        TextView txt_cancel = dialog.findViewById(R.id.txt_cancel);
        TextView txt_edit = dialog.findViewById(R.id.txt_edit);
        TextView txt_delete = dialog.findViewById(R.id.txt_delete);

        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });

        txt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                for (int i = 0; i < sessionManager.getYourList().size(); i++) {
                    if (sessionManager.getYourList().get(i).equals(name)) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    ArrayList<String> list = sessionManager.getYourList();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).equals(name)) {
                            list.remove(i);
                            flag2 = true;
                            break;
                        }
                    }
                    if (flag2)
                    {
                        System.out.println("list >>>>>>>>>>>"+list);
                        sessionManager.setYourList(list);

                        for (int i = 0; i < sessionManager.getFavAllRoutesList().size(); i++) {
                            if (sessionManager.getFavAllRoutesList().get(i).getName().equals(name)) {
                                Mflag = true;
                                break;
                            }
                        }
                        if (Mflag) {
                            ArrayList<AllRoutesMain> Mlist = sessionManager.getFavAllRoutesList();
                            for (int i = 0; i < Mlist.size(); i++) {
                                if (Mlist.get(i).getName().equals(name)) {
                                    Mlist.remove(i);
                                    Mflag2 = true;
//                                    break;
                                }
                            }
                            if (Mflag2) {
                                sessionManager.setFavAllRoutesList(Mlist);
                            }
                        }
                        YourListDataSet();
                    }
                }
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
