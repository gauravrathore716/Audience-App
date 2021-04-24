package com.b2c.audience;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.b2c.audience.api_presenter.ForgotPassPresenter;
import com.b2c.audience.databinding.ActivityForgetPasswordBinding;
import com.b2c.audience.model.DefaultResponse;
import com.b2c.audience.view.IForgotPassView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

public class ForgotPasswordActivity extends BaseActivity implements IForgotPassView {

    ActivityForgetPasswordBinding binding;
     ForgotPassPresenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forget_password);

        presenter=new ForgotPassPresenter();
        presenter.setView(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btnFindPassword:
                try {
                    if(Utility.validateEmail(binding.email.getText().toString().trim())){
                        JsonObject gsonObject;
                        JSONObject jsonObj_  = new JSONObject();
                        jsonObj_ .put("email",binding.email.getText().toString().trim());
                        JsonParser jsonParser = new JsonParser();
                        gsonObject = (JsonObject) jsonParser.parse(jsonObj_ .toString());
                        if(NetworkAlertUtility.isConnectingToInternet(ForgotPasswordActivity.this)){
                            presenter.forgotpassword(ForgotPasswordActivity.this,gsonObject);
                        }else{
                            NetworkAlertUtility.showNetworkFailureAlert(ForgotPasswordActivity.this);
                        }
                    } else
                    Toast.makeText(getApplicationContext(),R.string.enter_vaild_email,Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.back_to_login:
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(DefaultResponse body) {
        if (body.isStatus()) {
            //Snackbar.make(binding.layoutMain, body.getMessage(), Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE).show();
            MyCustomToast.showToast(getApplicationContext(),body.getMessage());
            finish();
        } else if (!body.isActive()) {
            //Snackbar.make(binding.layoutMain, body.getMessage(), Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE).show();
            MyCustomToast.showToast(getApplicationContext(),body.getMessage());
        } else {
            //Snackbar.make(binding.layoutMain, body.getMessage(), Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE).show();
            MyCustomToast.showToast(getApplicationContext(),body.getMessage());
        }
    }

    @Override
    public Context getContext()
    {
        return this;
    }



}
