package com.b2c.audience;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.b2c.audience.api_presenter.ChangePasswordPresenter;
import com.b2c.audience.api_presenter.LoginPresenter;
import com.b2c.audience.databinding.ActivityChangePasswordBinding;
import com.b2c.audience.model.DefaultResponse;
import com.b2c.audience.view.IChangePassView;
import com.b2c.audience.view.ILoginView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.Map;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener,IChangePassView {

    ActivityChangePasswordBinding binding;
    ChangePasswordPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_change_password);

        presenter = new ChangePasswordPresenter();
        presenter.setView(this);
        binding.setActivity(this);
        binding.changePassword.setOnClickListener(this);

        setSupportActionBar(        binding.toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp);

            actionBar.setHomeAsUpIndicator(upArrow);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.changePassword:
                if (validateAll()) {
                    JSONObject jsonObject;
                    try {
                        JsonObject gsonObject;
                        JSONObject jsonObj_  = new JSONObject();
                        jsonObj_.put("user_id",SessionManager.getInt(getApplicationContext(),SessionManager.ID));
                        jsonObj_ .put("old_password",binding.oldPassword.getText().toString().trim());
                        jsonObj_ .put("new_password", binding.newPassword.getText().toString().trim());
                        jsonObj_ .put("confirm_password",binding.confirmPassword.getText().toString().trim());
                        JsonParser jsonParser = new JsonParser();
                        gsonObject = (JsonObject) jsonParser.parse(jsonObj_ .toString());
                        if(NetworkAlertUtility.isConnectingToInternet(ChangePasswordActivity.this)){
                            presenter.changepass(ChangePasswordActivity.this,gsonObject);
                        }else{
                            NetworkAlertUtility.showNetworkFailureAlert(ChangePasswordActivity.this);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }

    public boolean validateAll() {
        boolean validation = true;
        if(!Utility.validateString(binding.oldPassword.getText().toString().trim())){
            MyCustomToast.showToast(this,getString(R.string.please_enter_pass));
            validation = false;
        }else if(!Utility.validatePassword(binding.oldPassword.getText().toString().trim())){
            MyCustomToast.showToast(this,getString(R.string.please_enter_pass_length));
            validation = false;
        }else  if(!Utility.validateString(binding.newPassword.getText().toString().trim())){
            MyCustomToast.showToast(this,getString(R.string.please_enter_new_pass));
            validation = false;
        }else  if(!Utility.validatePassword(binding.newPassword.getText().toString().trim())){
            MyCustomToast.showToast(this,getString(R.string.please_enter_pass_length));
            validation = false;
        }else  if(!binding.confirmPassword.getText().toString().trim().equals(binding.newPassword.getText().toString().trim())){
            MyCustomToast.showToast(this,getString(R.string.please_enter_both_pass_same));
            validation = false;
        }
        return validation;
    }

    @Override
    public void onSuccess(DefaultResponse data) {
        MyCustomToast.showToast(this, data.getMessage());
        try {
            if (data.isStatus()) {
                Intent i = new Intent(this, SettingActivity.class);
                startActivity(i);
                finishAffinity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public Context getContext() {
        return this;
    }
}
