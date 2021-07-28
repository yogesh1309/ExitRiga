package com.cw.exitriga.Interface;


import com.cw.exitriga.ModelClass.AllRoutesListRespose;
import com.cw.exitriga.ModelClass.ViewCategoriesResponse;
import com.cw.exitriga.ModelClass.ViewDestinationParameter;
import com.cw.exitriga.ModelClass.ViewDestinationResponse;
import com.cw.exitriga.ModelClass.ViewEventsResponse;
import com.cw.exitriga.ModelClass.ViewNewsParameter;
import com.cw.exitriga.ModelClass.ViewNewsResponse;
import com.cw.exitriga.ModelClass.ViewPlaceParameter;
import com.cw.exitriga.ModelClass.ViewPlaceResponse;
import com.cw.exitriga.ModelClass.ViewProductParameter;
import com.cw.exitriga.ModelClass.ViewProductResponse;
import com.cw.exitriga.ModelClass.ViewRoutesResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    // For Demo Purpose
    /*
    @GET("property_list")
    Call<List<PropertListReponse>> getListproperty();

    @FormUrlEncoded
    @POST("signup")
    Call<SignupResponse> signup(@Field("user_id") String user_id, @Field("name") String name,
                                @Field("email") String email, @Field("mobile") String mobile,
                                @Field("property_ids") String property_ids,
                                @Field("block_unit_id") String block_unit_id,
                                @Field("password") String password);

     */

    @POST("getcategory")
    Call<ViewCategoriesResponse> getcategories();

    @POST("route_list")
    Call<ViewRoutesResponse> getroutes();

    @POST("places_list")
    Call<ViewPlaceResponse> getplace(@Body ViewPlaceParameter viewNewsParameter);

    @POST("destination_list")
    Call<ViewDestinationResponse> getdestination(@Body ViewDestinationParameter viewNewsParameter);

    @POST("product_list")
    Call<ViewProductResponse> getproduct(@Body ViewProductParameter viewNewsParameter);

    @POST("news_list")
    Call<ViewNewsResponse> getnews(@Body ViewNewsParameter viewNewsParameter);

    @POST("event_list")
    Call<ViewEventsResponse> getevents(@Body ViewNewsParameter viewNewsParameter);


    @POST("get_allroutesdata")
    Call<AllRoutesListRespose> getallroutes(@Body ViewNewsParameter viewNewsParameter);
}
