package com.b2c.audience;

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

import com.b2c.audience.adapter.PastAdapter;
import com.b2c.audience.adapter.TransctionAdapter;
import com.b2c.audience.databinding.ActivityMyTranstionBinding;
import com.b2c.audience.model.ModelEventDetail;
import com.b2c.audience.model.ModelTranstionDetail;

import java.util.ArrayList;
import java.util.List;


public class MyTransactionActivity extends AppCompatActivity {

    ActivityMyTranstionBinding binding;
    private TransctionAdapter adapter;
    private List<ModelTranstionDetail> albumList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_transtion);
        binding.setActivity(this);

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
        albumList = new ArrayList<>();
        adapter = new TransctionAdapter(getApplicationContext(), albumList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setAdapter(adapter);


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
}
