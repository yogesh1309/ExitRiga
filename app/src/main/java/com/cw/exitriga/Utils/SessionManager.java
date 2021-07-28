package com.cw.exitriga.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.cw.exitriga.ModelClass.AllRoutesListData;
import com.cw.exitriga.ModelClass.AllRoutesListRespose;
import com.cw.exitriga.ModelClass.AllRoutesMain;
import com.cw.exitriga.ModelClass.ViewProductData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SessionManager {
    private SharedPreferences pref;
    private Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "login";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USERID = "userid";
    private static final String KEY_IS_LOGIN = "isLogin";
    private static final String KEY_FCM_TOKEN = "fcmtoken";
    private static final String KEY_DEVICE_ID = "deviceid";
    private static final String KEY_FCM_TOKEN_ID = "fcmtokenid";
    private static final String KEY_APP_LANG = "applang";
    private static final String KEY_PRODUCT_LIST = "productlist";
    private static final String KEY_YOUR_LIST = "yourlist";
    private static final String KEY_FAVALLROUTES_LIST = "favallrouteslist";



    // Constructor
    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }



    public void setSavedAppLang(String userid) {
        editor.putString(KEY_APP_LANG, userid);
        editor.commit();
    }

    public String getSavedAppLang() {
        return pref.getString(KEY_APP_LANG, "");
    }

    public void setSavedFcmTokenId(Integer userid) {
        editor.putInt(KEY_FCM_TOKEN_ID, userid);
        editor.commit();
    }


    public Integer getSavedFcmTokenId() {
        return pref.getInt(KEY_FCM_TOKEN_ID, 0);
    }



    public void setSavedDeviceid(String userid) {
        editor.putString(KEY_DEVICE_ID, userid);
        editor.commit();
    }


    public String getSavedStringDeviceid() {
        return pref.getString(KEY_DEVICE_ID, "");
    }

    public void setSavedFcmtoken(String userid) {
        editor.putString(KEY_FCM_TOKEN, userid);
        editor.commit();
    }


    public String getSavedStringFcmtoken() {
        return pref.getString(KEY_FCM_TOKEN, "");
    }




    //-----------------------------------USERID-------------------------------------------

    //save user name to SharedPref
    public void setSavedUserid(String userid) {
        editor.putString(KEY_USERID, userid);
        editor.commit();
    }

    //retrieve username frome pref
    public String getSavedUserid() {
        return pref.getString(KEY_USERID, "");
    }


    //-----------------------------------TOKEN-------------------------------------------

    //save user name to SharedPref
    public void setSavedToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    //retrieve username frome pref
    public String getSavedToken() {
        return pref.getString(KEY_TOKEN, "");
    }


    //-----------------------------------------------------------------------------------

    //save user name to SharedPref
    public void setSavedUserName(String userName) {
        editor.putString(KEY_USER_NAME, userName);
        editor.commit();
    }

    //retrieve username frome pref
    public String getSavedUserName() {
        return pref.getString(KEY_USER_NAME, "");
    }


    public boolean isUserLogin() {
        return pref.getBoolean(KEY_IS_LOGIN, false);
    }

    public void setUserLoggedIn(boolean isLogin) {
        editor.putBoolean(KEY_IS_LOGIN, isLogin);
        editor.commit();
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }

    public void setProductList(ArrayList<ViewProductData> list)
    {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(KEY_PRODUCT_LIST, json);
        editor.commit();
        editor.apply();
    }

    public ArrayList<ViewProductData> getProductList()
    {
        ArrayList<ViewProductData> arrayItems = new ArrayList<>();
        String serializedObject = pref.getString(KEY_PRODUCT_LIST, null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<ViewProductData>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }
        return arrayItems;
    }


    public void setYourList(ArrayList<String> list)
    {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(KEY_YOUR_LIST, json);
        editor.commit();
        editor.apply();
    }

    public ArrayList<String> getYourList()
    {
        ArrayList<String> arrayItems = new ArrayList<>();
        String serializedObject = pref.getString(KEY_YOUR_LIST, null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }
        return arrayItems;
    }


    public void setFavAllRoutesList(ArrayList<AllRoutesMain> list)
    {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(KEY_FAVALLROUTES_LIST, json);
        editor.commit();
        editor.apply();
    }

    public ArrayList<AllRoutesMain> getFavAllRoutesList()
    {
        ArrayList<AllRoutesMain> arrayItems = new ArrayList<>();
        String serializedObject = pref.getString(KEY_FAVALLROUTES_LIST, null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<AllRoutesMain>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }
        return arrayItems;
    }
}
