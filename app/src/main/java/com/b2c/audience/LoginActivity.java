package com.b2c.audience;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.b2c.audience.api_presenter.LoginPresenter;
import com.b2c.audience.databinding.ActivityLoginBinding;
import com.b2c.audience.model.LoginRegisterResponse;
import com.b2c.audience.view.ILoginView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements View.OnClickListener, ILoginView {


    private LoginPresenter presenter;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(this,R.layout.activity_login);
        presenter = new LoginPresenter();
        presenter.setView(this);
        binding.close.setOnClickListener(this);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken LoginActivity", newToken);
                SessionManager.setDeviceToken(getApplicationContext(), newToken);
            }
        });

    }

    private void validtion() {
        String emailString = binding.etEmail.getText().toString().trim();
        String passString = binding.etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(emailString)) {
            binding.etEmail.requestFocus();
            Snackbar.make(binding.linearlayout, R.string.enter_email, Snackbar.LENGTH_SHORT).show();
        } else if (!emailString.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            binding.etEmail.requestFocus();
            Snackbar.make(binding.linearlayout, R.string.enter_vaild_email, Snackbar.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(passString)) {
            binding.etPassword.requestFocus();
            Snackbar.make(binding.linearlayout, R.string.please_add_password, Snackbar.LENGTH_SHORT).show();
        } else if (binding.etPassword.length() < 8) {
            binding.etPassword.requestFocus();
            Snackbar.make(binding.linearlayout, R.string.password_should8, Snackbar.LENGTH_SHORT).show();
        } else {

            if (NetworkAlertUtility.isConnectingToInternet2(this)){
                JSONObject jsonObject = null;

                try {

                    jsonObject = new JSONObject();
                    jsonObject.put("email", String.valueOf(emailString));
                    jsonObject.put("password", String.valueOf(passString));
                    jsonObject.put("device_id", Utility.getAndroidId(this));
                    jsonObject.put("device_token", SessionManager.getDeviceToken(this));
                    jsonObject.put("device_type", String.valueOf("Android"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                presenter.login(this, jsonObject);
            }
            else {
                NetworkAlertUtility.showNetworkFailureAlert(this);
            }

        }
    }

    public void onClick(View view) {

        Intent i ;
        switch (view.getId()){
            case R.id.txtForgotPass:
                i = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(i);
                break;
            case R.id.btnLogin:
                validtion();

                break;
            case R.id.txtSignup:
                i = new Intent(LoginActivity.this,SignUpOneActivity.class);
                startActivity(i);
                break;
            case R.id.close:
                i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                finishAffinity();
                break;
        }

    }

    @Override
    public void onSuccess(LoginRegisterResponse body) {

        if (body.isStatus()) {

            SessionManager.saveUserInfo(getApplicationContext(),body.getUserInfo());
            MyCustomToast.showToast(getApplicationContext(), body.getMessage());
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            finishAffinity();

        } else {
            Snackbar.make(binding.linearlayout, body.getMessage(), Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE).show();
        }
    }

    @Override
    public Context getContext()
    {
        return this;
    }
}
