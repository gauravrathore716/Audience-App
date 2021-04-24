package com.b2c.audience;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.b2c.audience.DatePickerCustom.DatePicker;
import com.b2c.audience.DatePickerCustom.DatePickerDialog;
import com.b2c.audience.DatePickerCustom.SpinnerDatePickerDialogBuilder;

import com.b2c.audience.api_presenter.SignupPresenter;
import com.b2c.audience.databinding.ActivitySignUpTwoBinding;
import com.b2c.audience.model.LoginRegisterResponse;
import com.b2c.audience.view.ILoginView;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SignUpTwoActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, ILoginView {

    ActivitySignUpTwoBinding binding;
    String first_name = "", image = "", phone = "", email = "", social_image_url = "";
    SignupPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up_two);
        binding.setActivity(this);
        presenter = new SignupPresenter();
        presenter.setView(this);

        first_name = getIntent().getStringExtra("first_name");
        image = getIntent().getStringExtra("image");
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
        social_image_url = getIntent().getStringExtra("social_image_url");

        //binding.dob.setText(AppPreferenceManager.getInstance(SignUpTwoActivity.this).getString("DATE"));
        Calendar c2 = Calendar.getInstance();
        c2.add(Calendar.YEAR, -12);
        binding.dob.setHint(ChangeDateFormat.getDateTimeFromMillisecond("dd MMMM yyyy", c2.getTimeInMillis()).toLowerCase());

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNext:
                validtion();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.dob:
                int mYear, mMonth, mDay;
                if (!binding.dob.getText().toString().trim().equalsIgnoreCase("")) {
                    String date1 = ChangeDateFormat.getDateFormatFromOneToOther(binding.dob.getText().toString(), "dd MMMM yyyy", "yyyy-MM-dd");
                    mYear = Integer.parseInt(date1.split("-")[0]);
                    mMonth = Integer.parseInt(date1.split("-")[1]);
                    mMonth = mMonth - 1;
                    mDay = Integer.parseInt(date1.split("-")[2]);
                } else {
                    final Calendar c = Calendar.getInstance();
//                    mYear = 1991;
                    mYear = Integer.parseInt(ChangeDateFormat.getDateTimeFromMillisecond("yyyy", System.currentTimeMillis()));
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                }
                showDate(mYear, mMonth, mDay, R.style.DatePickerSpinner);

//                getDatePicker();

                break;
        }
    }

    private void validtion() {
        String date= binding.dob.getText().toString();
        String password = binding.password.getText().toString();
        String conpassword = binding.confirmPassword.getText().toString();
        if (TextUtils.isEmpty(date)){
            Toast.makeText(this,R.string.dob,Toast.LENGTH_SHORT).show();
            binding.dob.requestFocus();

        }
        else if (TextUtils.isEmpty(password)) {
            binding.password.requestFocus();
            Toast.makeText(this,R.string.please_add_password,Toast.LENGTH_SHORT).show();
        } else if (password.length()<8 ) {
            binding.password.requestFocus();
            Toast.makeText(this,R.string.password_should8,Toast.LENGTH_SHORT).show();

        } else if (!password.equals(conpassword)) {
            binding.confirmPassword.requestFocus();
            Toast.makeText(this,R.string.passwordNotMatch,Toast.LENGTH_SHORT).show();

        }
        else {

            if (NetworkAlertUtility.isConnectingToInternet2(this)){
                SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
                Date date1 = null;
                try {
                    date1 = df.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                df.applyPattern("yyyy-MM-dd");
                String dob = df.format(date1);
                String switchId;

                if (binding.switchbutton.isChecked()) {
                    switchId = "1";
                } else {
                    switchId = "0";
                }


                final HashMap<String, RequestBody> map = new HashMap<>();

                map.put("first_name", RequestBody.create(MediaType.parse("multipart/form-data"), getIntent().getStringExtra("first_name")));
                map.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), getIntent().getStringExtra("name")));
                map.put("email", RequestBody.create(MediaType.parse("multipart/form-data"), getIntent().getStringExtra("email")));
                map.put("password", RequestBody.create(MediaType.parse("multipart/form-data"), password));
                map.put("confirm_password", RequestBody.create(MediaType.parse("multipart/form-data"), conpassword));
                map.put("dob", RequestBody.create(MediaType.parse("multipart/form-data"), dob));
                map.put("event_notify", RequestBody.create(MediaType.parse("multipart/form-data"), switchId));
                map.put("device_token", RequestBody.create(MediaType.parse("multipart/form-data"), SessionManager.getDeviceToken(this)));
                map.put("device_type", RequestBody.create(MediaType.parse("multipart/form-data"), "Android"));
                map.put("device_id", RequestBody.create(MediaType.parse("multipart/form-data"), Utility.getAndroidId(this)));

                File file = null;
                try {
                    file = FileUtil.from(this, SignUpOneActivity.captureMediaFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MultipartBody.Part uploadedFile = MultipartBody.Part.createFormData("profile_pic",
                        Utility.getFileName(this, Uri.fromFile(file)), RequestBody.create(MediaType.parse("image/*"), file));
                presenter.register(this, map, uploadedFile);

            }
            else {
                NetworkAlertUtility.showNetworkFailureAlert(this);
            }
         }
 }
    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(SignUpTwoActivity.this)
                .callback(SignUpTwoActivity.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }

    public void setDate(String date) {
        binding.dob.setText(date);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        date = ChangeDateFormat.getDateFormatFromOneToOther(date, "yyyy-MM-dd", "dd MMMM yyyy");
        binding.dob.setText(date.toLowerCase());
    }

    @Override
    public void onSuccess(LoginRegisterResponse data) {

        MyCustomToast.showToast(this, data.getMessage());

        try {
            if (data.isStatus()) {
                SessionManager.saveUserInfo(getApplicationContext(), data.getUserInfo());
                Intent i = new Intent(this, MainActivity.class);
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