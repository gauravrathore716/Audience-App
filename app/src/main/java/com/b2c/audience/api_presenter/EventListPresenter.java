package com.b2c.audience.api_presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.b2c.audience.AppController;
import com.b2c.audience.R;
import com.b2c.audience.model.CategoryListResponse;
import com.b2c.audience.model.DefaultResponse;
import com.b2c.audience.model.EventListInfo;
import com.b2c.audience.model.EventListResponse;
import com.b2c.audience.model.LoginRegisterResponse;
import com.b2c.audience.view.ICategoryView;
import com.b2c.audience.view.IEventListPassView;
import com.b2c.audience.view.IEventListView;
import com.b2c.audience.webservice.RestService;
import com.google.gson.JsonObject;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;

public class EventListPresenter extends BasePresenter<IEventListView> {

    public void eventList(final Context context, HashMap<String, RequestBody> map) {

        getView().enableLoadingBar(context,true, context.getString(R.string.msg_please_wait));

        AppController.getInstance().getApiInterface().eventList(map).enqueue(new Callback<EventListResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventListResponse> call, @NonNull Response<EventListResponse> response) {
                getView().enableLoadingBar(context, false, "");

                if (!handleError(response)) {
                    if (response.isSuccessful() && response.code() == 200) {
                        getView().onSuccess(response.body());
                    }
                }else{
                    getView().onError(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventListResponse> call, @NonNull Throwable t) {
                getView().enableLoadingBar(context, false, "");
                try {
                    t.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getView().onError(t.getMessage());
            }
        });
    }

}
