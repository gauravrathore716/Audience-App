package com.b2c.audience.api_presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.b2c.audience.AppController;
import com.b2c.audience.R;
import com.b2c.audience.model.LoginRegisterResponse;
import com.b2c.audience.view.IEditProfileView;
import com.b2c.audience.view.IViewProfileView;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;

public class viewProfilePresenter extends BasePresenter<IViewProfileView> {

    public void viewProfile(final Context context, HashMap<String, RequestBody> map) {

        getView().enableLoadingBar(context,true, context.getString(R.string.msg_please_wait));

        AppController.getInstance().getApiInterface().viewProfile(map).enqueue(new Callback<LoginRegisterResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginRegisterResponse> call, @NonNull Response<LoginRegisterResponse> response) {
                getView().enableLoadingBar(context, false, "");

                if (!handleError(response)) {
                    if (response.isSuccessful() && response.code() == 200) {
                        getView().onViewProfile(response.body());
                    }
                }else{
                    getView().onError(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginRegisterResponse> call, @NonNull Throwable t) {
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
