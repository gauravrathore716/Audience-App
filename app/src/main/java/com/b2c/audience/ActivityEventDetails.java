package com.b2c.audience;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.b2c.audience.adapter.EventTicketListAdapter;
import com.b2c.audience.adapter.PaymentCardAdapter;
import com.b2c.audience.databinding.ActivityEventDeatilsBinding;
import com.b2c.audience.model.ModelCardPayment;
import com.b2c.audience.model.ModelEventTicketList;

import java.util.ArrayList;
import java.util.List;


public class ActivityEventDetails extends AppCompatActivity implements View.OnClickListener {
    ActivityEventDeatilsBinding binding;
    private EventTicketListAdapter adapter;
    private List<ModelEventTicketList> albumList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_event_deatils);
        setfindviewid();

        binding.btnShowMores.setOnClickListener(this);

        albumList = new ArrayList<>();
        adapter = new EventTicketListAdapter(getApplicationContext(), albumList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setAdapter(adapter);
        binding.btnBuyNow.setOnClickListener(this);

    }



    private void setfindviewid() {
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


    public void onClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            case R.id.btnShowMores:
                    binding.txtExtraView.setMaxLines(6);//your TextView
                    binding.btnShowMores.setVisibility(View.GONE);
                    break;
            case R.id.btnBuyNow:
                intent = new Intent(getApplicationContext(),ActivityEventProcess.class);
                startActivity(intent);

        }


    }
}
