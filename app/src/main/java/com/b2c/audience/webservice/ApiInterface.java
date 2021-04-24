package com.b2c.audience.webservice;



import com.b2c.audience.model.CategoryListResponse;
import com.b2c.audience.model.DefaultResponse;
import com.b2c.audience.model.EventListResponse;
import com.b2c.audience.model.LoginRegisterResponse;

import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiInterface {

    String BASE_URL = "http://139.162.242.206/clients/audience/webservices/";
        //String BASE_URL = "http://app.audience.events/webservices/";


    String LOGIN = "login";
    String FORGETPASSWORD = "forgotpassword";
    String REGISTER = "register";
    String LOGOUT = "logout";
    String CHANGEPASSWORD = "changePassword";
    String EDITPROFILE = "editSetting";
    String VIEWPROFILE = "viewProfile";
    String CATEGORYLIST = "categoryList";
    String EVENTLIST = "eventList";

    @POST(LOGIN)
    Call<LoginRegisterResponse> login(@Body RequestBody params);

    @POST(FORGETPASSWORD)
    Call<DefaultResponse> forgotpassword(@Body RequestBody params);

    @Multipart
    @POST(REGISTER)
    Call<LoginRegisterResponse> register(@PartMap() Map<String, RequestBody> partMa, @Part MultipartBody.Part imageFile);

    @POST(LOGOUT)
    Call<LoginRegisterResponse> logout(@Body RequestBody params);


    @POST(CHANGEPASSWORD)
    Call<DefaultResponse> changePassword(@Body RequestBody params);


    @Multipart
    @POST(EDITPROFILE)
    Call<LoginRegisterResponse> editSetting(@PartMap() Map<String, RequestBody> partMa, @Part MultipartBody.Part imageFile);

    @Multipart
    @POST(VIEWPROFILE)
    Call<LoginRegisterResponse> viewProfile(@PartMap() Map<String, RequestBody> partMa);


    @GET(CATEGORYLIST)
    Call<CategoryListResponse> categoryList();

    @Multipart
    @POST(EVENTLIST)
    Call<EventListResponse> eventList(@PartMap() Map<String, RequestBody> partMa);

}


