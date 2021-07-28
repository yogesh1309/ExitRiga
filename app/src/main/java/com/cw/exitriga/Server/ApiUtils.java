package com.cw.exitriga.Server;


import com.cw.exitriga.Interface.JsonPlaceHolderApi;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = Allurls.MainURL;

    public static JsonPlaceHolderApi getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(JsonPlaceHolderApi.class);
    }
}
