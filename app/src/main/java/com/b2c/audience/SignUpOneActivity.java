package com.b2c.audience;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.b2c.audience.databinding.ActivitySignUpOneBinding;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SignUpOneActivity extends AppCompatActivity implements View.OnClickListener, BottomSheetExample.BottmsheetListener {


    public static final int REQUEST_CAMERA = 1001;
    public static final int REQUEST_GALLERY = 1002;
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final int BITMAP_SAMPLE_SIZE = 8;
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final String IMAGE_EXTENSION = "jpg";
    public static AlertDialog alertAppUpdate;
    private static String imageStoragePath;
    private static Toast toast;
    public static  Uri captureMediaFile = null;
    ActivitySignUpOneBinding binding;
    private AppCompatActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_one);
        binding.setActivity(this);
        binding.ivUserImage.setOnClickListener(this);

    }

    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btnNext:
                validtion();

                break;
            case R.id.ivUserImage:
                BottomSheetExample bottomSheetExample = new BottomSheetExample();
                bottomSheetExample.show(getSupportFragmentManager(), "examplebottomsheet");
                break;

        }
    }

    private void validtion() {

        String firstName = binding.etFirstName.getText().toString().trim();
        String Name = binding.etName.getText().toString().trim();
        String emailString = binding.etEmail.getText().toString().trim();
        if (captureMediaFile == null) {
            Toast.makeText(this, R.string.please_select_profile_image, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(firstName)) {
            binding.etFirstName.requestFocus();
            Toast.makeText(this, R.string.firstname, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Name)) {
            binding.etName.requestFocus();
            Toast.makeText(this, R.string.name_last, Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(emailString)) {
            binding.etEmail.requestFocus();
            Toast.makeText(this, R.string.enter_email, Toast.LENGTH_SHORT).show();
        } else if (!emailString.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            binding.etEmail.requestFocus();
            Toast.makeText(this, R.string.enter_vaild_email, Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(SignUpOneActivity.this, SignUpTwoActivity.class);
            i.putExtra("first_name", binding.etFirstName.getText().toString().trim());
            i.putExtra("name", binding.etName.getText().toString().trim());
            i.putExtra("email", binding.etEmail.getText().toString().trim());
            startActivity(i);
        }
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
                    binding.ivUserImage.setImageBitmap(bitmap);

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
                binding.ivUserImage.setImageURI(captureMediaFile);

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isGranted = true;
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
                break;
            }
        }

        if (isGranted) {
            if (requestCode == REQUEST_CAMERA) {
                openCamera();
            } else if (requestCode == REQUEST_GALLERY) {
                openGallery();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
