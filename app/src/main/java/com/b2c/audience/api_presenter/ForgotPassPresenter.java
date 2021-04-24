package com.b2c.audience.api_presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.b2c.audience.AppController;
import com.b2c.audience.R;
import com.b2c.audience.model.DefaultResponse;
import com.b2c.audience.model.LoginRegisterResponse;
import com.b2c.audience.view.IForgotPassView;
import com.b2c.audience.webservice.RestService;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassPresenter extends BasePresenter<IForgotPassView> {

    public void forgotpassword(final Context context, JsonObject jsonObject) {
        getView().enableLoadingBar(context, true, context.getString(R.string.msg_please_wait));

        AppController.getInstance().getApiInterface().forgotpassword(RestService.requestBodyJsonObject("" + jsonObject))
                .enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<DefaultResponse> call, @NonNull Response<DefaultResponse> response) {
                        getView().enableLoadingBar(context, false, "");

                        if (!handleError(response)) {
                            if (response.isSuccessful() && response.code() == 200) {
                                getView().onSuccess(response.body());
                            }
                        } else {
                            getView().onError(response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<DefaultResponse> call, @NonNull Throwable t) {
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
