package com.b2c.audience;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.b2c.audience.databinding.NavigationItemBinding;
import com.mikhaellopez.circularimageview.CircularImageView;

@SuppressLint("Registered")
public class NavigationItemActivity extends BaseActivity implements View.OnClickListener {

    NavigationItemBinding binding;
    private AppCompatActivity mActivity = this;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        binding = DataBindingUtil.setContentView(this, R.layout.navigation_item);

        binding.txtYourBill.setOnClickListener(this);
        binding.txtTransction.setOnClickListener(this);
        binding.txtPayment.setOnClickListener(this);
        binding.txtCamera.setOnClickListener(this);
        binding.txtSetting.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.txtYourBill:
                i = new Intent(this,MyTicketsActivity.class);
                startActivity(i);

                break;
            case R.id.txtPayment:
                i = new Intent(this,PaymentActivity.class);
                startActivity(i);
                break;

            case R.id.txtTransction:
                i = new Intent(this,MyTransactionActivity.class);
                startActivity(i);
                break;

            case R.id.txtCamera:
                i = new Intent(this,CameraActivity.class);
                startActivity(i);
                break;

            case R.id.txtSetting:
                i = new Intent(this,SettingActivity.class);
                startActivity(i);
                break;

                default:
                    break;

        }

    }
}
