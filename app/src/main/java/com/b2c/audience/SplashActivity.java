package com.b2c.audience;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.logging.Handler;

public class SplashActivity extends AppCompatActivity {
    public static int timeout = 3000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
//                Log.e("newToken LoginActivity", newToken);
                SessionManager.setDeviceToken(getApplicationContext(), newToken);
            }
        });


        new android.os.Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent i ;
                if (SessionManager.getInt(getApplicationContext(),SessionManager.ID)==0){
                    i = new Intent(SplashActivity.this, LoginActivity.class);
                }
                else {
                    i = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(i);
                finish();
            }
        },timeout);

    }
}
