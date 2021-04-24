package com.b2c.audience;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Network;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.b2c.audience.DatePickerCustom.DatePicker;
import com.b2c.audience.DatePickerCustom.DatePickerDialog;
import com.b2c.audience.DatePickerCustom.SpinnerDatePickerDialogBuilder;
import com.b2c.audience.api_presenter.EditProfilePresenter;
import com.b2c.audience.api_presenter.SettingPresenter;
import com.b2c.audience.api_presenter.viewProfilePresenter;
import com.b2c.audience.databinding.ActivitySettingBinding;
import com.b2c.audience.model.LoginRegisterResponse;
import com.b2c.audience.view.IEditProfileView;
import com.b2c.audience.view.ILoginView;
import com.b2c.audience.view.ILogoutView;
import com.b2c.audience.view.IViewProfileView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SettingActivity extends BaseActivity implements View.OnClickListener,ILogoutView, ILoginView ,BottomSheetExample.BottmsheetListener, DatePickerDialog.OnDateSetListener, IEditProfileView, IViewProfileView {

    ActivitySettingBinding binding;
    private AppCompatActivity mActivity = this;
    SettingPresenter settingPresenter;
    EditProfilePresenter editProfilePresenter;
    viewProfilePresenter viewProfilePresenter;
    public static final int REQUEST_CAMERA = 1001;
    public static final int REQUEST_GALLERY = 1002;
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final int BITMAP_SAMPLE_SIZE = 8;
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final String IMAGE_EXTENSION = "jpg";
    private static String imageStoragePath;
    private static Toast toast;
    public static Uri captureMediaFile = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        binding.changePass.setOnClickListener(this);
        settingPresenter = new SettingPresenter();
        settingPresenter.setView(this);

        editProfilePresenter = new EditProfilePresenter();
        editProfilePresenter.setView(this);

        viewProfilePresenter = new viewProfilePresenter();
        viewProfilePresenter.setView(this);

        DataBindingAdapter.setProfileSrcUrl(binding.userImage,SessionManager.getUserImage(mActivity));
        binding.etName.setText(SessionManager.getString(mActivity,SessionManager.FIRST_NAME));
        binding.etEmail.setText(SessionManager.getString(mActivity,SessionManager.EMAIL));
        binding.etNikName.setText(SessionManager.getString(mActivity,SessionManager.NAME));

        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
            Date date = df.parse(SessionManager.getString(mActivity,SessionManager.DOB));
            df.applyPattern("dd MMMM yyyy");
            String dob = df.format(date);
            binding.txtDob.setText(dob);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Calendar c2 = Calendar.getInstance();
        c2.add(Calendar.YEAR, -12);


        binding.txtDob.setHint(ChangeDateFormat.getDateTimeFromMillisecond("dd MMMM yyyy", c2.getTimeInMillis()).toLowerCase());

        if (NetworkAlertUtility.isConnectingToInternet(mActivity)){
            HashMap<String, RequestBody> map = new HashMap<>();
            map.put("user_id", RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(SessionManager.getInt(mActivity, SessionManager.ID))));
            viewProfilePresenter.viewProfile(mActivity,map);

        }
        else{
            NetworkAlertUtility.showNetworkFailureAlert(mActivity);
        }

        DataBindingAdapter.setProfileSrcUrl(binding.userImage,SessionManager.getUserImage(mActivity));
        binding.etName.setText(SessionManager.getString(mActivity,SessionManager.FIRST_NAME));
        binding.etEmail.setText(SessionManager.getString(mActivity,SessionManager.EMAIL));
        binding.etNikName.setText(SessionManager.getString(mActivity,SessionManager.NAME));
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
            Date date = df.parse(SessionManager.getString(mActivity,SessionManager.DOB));
            df.applyPattern("dd MMMM yyyy");
            String dob = df.format(date);
            binding.txtDob.setText(dob);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Calendar c3 = Calendar.getInstance();
        c3.add(Calendar.YEAR, -12);


        binding.txtDob.setHint(ChangeDateFormat.getDateTimeFromMillisecond("dd MMMM yyyy", c3.getTimeInMillis()).toLowerCase());


        setfindviewid();


    }

    private void setfindviewid() {

        setSupportActionBar(binding.toolbar);
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
    public void onClick(View view) {
        Intent i ;
        switch (view.getId()){

            case R.id.changePass:
                 i = new Intent(getApplicationContext(),ChangePasswordActivity.class);
                startActivity(i);
                break;
            case R.id.txtDob:
                int mYear, mMonth, mDay;
                if (!binding.txtDob.getText().toString().trim().equalsIgnoreCase("")) {
                    String date1 = ChangeDateFormat.getDateFormatFromOneToOther(binding.txtDob.getText().toString(), "dd MMMM yyyy", "yyyy-MM-dd");
                    mYear = Integer.parseInt(date1.split("-")[0]);
                    mMonth = Integer.parseInt(date1.split("-")[1]);
                    mMonth = mMonth - 1;
                    mDay = Integer.parseInt(date1.split("-")[2]);
                }
                else
                    {
                    final Calendar c = Calendar.getInstance();
//                    mYear = 1991;
                    mYear = Integer.parseInt(ChangeDateFormat.getDateTimeFromMillisecond("yyyy", System.currentTimeMillis()));
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                }
                showDate(mYear, mMonth, mDay, R.style.DatePickerSpinner);
                break;
            case R.id.saveProfile:
                editProfile();
                break;
            case R.id.txtSignOut:
                dialogLogout();
                break;

            case R.id.userImage:
                BottomSheetExample bottomSheetExample = new BottomSheetExample();
                bottomSheetExample.show(getSupportFragmentManager(), "examplebottomsheet");
                break;
        }

    }

    private void showDate(int mYear, int mMonth, int mDay, int datePickerSpinner) {
        new SpinnerDatePickerDialogBuilder()
                .context(SettingActivity.this)
                .callback(SettingActivity.this)
                .spinnerTheme(datePickerSpinner)
                .defaultDate(mYear, mMonth, mDay)
                .build()
                .show();

    }


    @Override
    public void onButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.textCamera:
                openCamera();
                break;
            case R.id.textGallery:
                openGallery();
                break;
        }
    }

    private void openGallery() {
        Intent takePictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(takePictureIntent, REQUEST_GALLERY);

    }

    private void openCamera() {
        if (!PermissionCaller.getInstance(this).isListOfPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA}, REQUEST_CAMERA))
            return;
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            File tempFile = File.createTempFile("image", ".png", new File(Utility.getTempMediaDirectory(this)));
            captureMediaFile = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", tempFile);
            Log.e("capturemedia file url", "" + captureMediaFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);

            List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                grantUriPermission(packageName, captureMediaFile, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == REQUEST_CAMERA) {

            try {
                getContentResolver().notifyChange(captureMediaFile, null);
                ContentResolver cr = getContentResolver();

                Bitmap bitmap = null;

                try {
                    bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, captureMediaFile);
                    Log.e("width", "" + bitmap.getWidth());
                    Log.e("Height", "" + bitmap.getHeight());
                    if (bitmap.getWidth() > bitmap.getHeight()) {
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (bitmap != null)
                    binding.userImage.setImageBitmap(bitmap);

//                GlideApp.with(getApplicationContext())
//                        .load(captureMediaFile)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
////                        .placeholder(R.drawable.loader_small)
//                        .into(binding.userImage);

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (requestCode == REQUEST_GALLERY) {

            if (resultCode == RESULT_OK) {
                captureMediaFile = resultData.getData();
                binding.userImage.setImageURI(captureMediaFile);

            }
        }

    }

    private void editProfile() {

        String switchId;

        if (binding.switcher.isChecked()) {
            switchId = "1";
        } else {
            switchId = "0";
        }

        String firstName = binding.etName.getText().toString().trim();
        String name = binding.etNikName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String dob = binding.txtDob.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            binding.etName.requestFocus();
            Toast.makeText(this, R.string.firstname, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(name)) {
            binding.etNikName.requestFocus();
            Toast.makeText(this, R.string.name, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email)) {
            binding.etEmail.requestFocus();
            Toast.makeText(this, R.string.enter_email, Toast.LENGTH_SHORT).show();
        } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            binding.etEmail.requestFocus();
            Toast.makeText(this, R.string.enter_vaild_email, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(dob)) {
            binding.txtDob.requestFocus();
            Toast.makeText(this, R.string.dob, Toast.LENGTH_SHORT).show();
        } else if (NetworkAlertUtility.isConnectingToInternet2(this)) {
                SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
                Date date1 = null;
                try {
                    date1 = df.parse(dob);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                df.applyPattern("yyyy-MM-dd");
                String dob2 = df.format(date1);

                HashMap<String, RequestBody> map = new HashMap<>();
                map.put("user_id", RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(SessionManager.getInt(mActivity, SessionManager.ID))));
                map.put("first_name", RequestBody.create(MediaType.parse("multipart/form-data"), firstName));
                map.put("name", RequestBody.create(MediaType.parse("multipart/form-data"), name));
                map.put("email", RequestBody.create(MediaType.parse("multipart/form-data"), email));
                map.put("dob", RequestBody.create(MediaType.parse("multipart/form-data"), dob2));
                map.put("event_notify", RequestBody.create(MediaType.parse("multipart/form-data"), switchId));


                File file = null;
                MultipartBody.Part uploadedFile = null;
                if (SettingActivity.captureMediaFile != null) {
                    try {
                        file = FileUtil.from(this, SettingActivity.captureMediaFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    uploadedFile = MultipartBody.Part.createFormData("profile_pic", Utility.getFileName(this, Uri.fromFile(file)), RequestBody.create(MediaType.parse("image/*"), file));
                }

                editProfilePresenter.editprofile(mActivity, map, uploadedFile);

            } else {
                NetworkAlertUtility.showNetworkFailureAlert(mActivity);
            }
    }


    private void dialogLogout() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle(getString(R.string.txt_attention));
        builder.setMessage(getString(R.string.alert_want_to_logout));
        builder.setCancelable(true);
        builder.setNegativeButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.setNegativeButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton(getString(R.string.btn_confirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                try {
                    JsonObject gsonObject;
                    JSONObject jsonObj_ = new JSONObject();
                    jsonObj_.put("user_id",SessionManager.getInt(mActivity,SessionManager.ID));
                    jsonObj_.put("device_id",Utility.getAndroidId(mActivity));
                    jsonObj_.put("device_token",SessionManager.getDeviceToken(mActivity));
                    jsonObj_.put("device_type","Android");

                    JsonParser jsonParser = new JsonParser();
                    gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                    if (NetworkAlertUtility.isConnectingToInternet(SettingActivity.this)) {
                        settingPresenter.logout(SettingActivity.this, jsonObj_);
                    } else {
                        NetworkAlertUtility.showNetworkFailureAlert(SettingActivity.this);
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
        });

        final AlertDialog alert = builder.create();
        //2. now setup to change color of the button
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.alertDialogButton));
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
            }
        });
        alert.show();


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
    public void onSuccess(LoginRegisterResponse body) {

        MyCustomToast.showToast(this, body.getMessage());
        try {
            if (body.isStatus()) {
                UserLogout(mActivity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public Context getContext() {
        return this;
    }


    public void setDate(String date) {
        binding.txtDob.setText(date);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        date = ChangeDateFormat.getDateFormatFromOneToOther(date, "yyyy-MM-dd", "dd MMMM yyyy");
        binding.txtDob.setText(date.toLowerCase());
    }

    @Override
    public void onEditProfile(LoginRegisterResponse body) {

        MyCustomToast.showToast(getApplicationContext(), body.getMessage());

        if (body.isStatus()) {

            SessionManager.saveUserInfo(getApplicationContext(),body.getUserInfo());
            finish();
        }

    }

    @Override
    public void onViewProfile(LoginRegisterResponse body) {

        if (body.isStatus()) {

            SessionManager.saveUserInfo(getApplicationContext(),body.getUserInfo());
        }
    }
}
