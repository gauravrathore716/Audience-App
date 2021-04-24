package com.b2c.audience;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.b2c.audience.databinding.ChoosePhotoDialogBinding;

import static android.app.Activity.RESULT_OK;

public class BottomSheetExample extends BottomSheetDialogFragment implements View.OnClickListener {

    private BottmsheetListener listener;
    ChoosePhotoDialogBinding binding;


    SignUpOneActivity signUpOneActivity;
    SettingActivity settingActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.choose_photo_dialog, container, false);
        binding.textCamera.setOnClickListener(this);
        binding.textGallery.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textCamera:
                listener.onButtonClicked(view);
                dismiss();
                break;
            case R.id.textGallery:
                listener.onButtonClicked(view);
                dismiss();
                break;
        }

    }


    public interface  BottmsheetListener{
        void onButtonClicked(View view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (BottmsheetListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must impliment bottom listnener");
        }
    }

}
