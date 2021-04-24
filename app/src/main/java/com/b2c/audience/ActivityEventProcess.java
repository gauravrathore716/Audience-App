package com.b2c.audience;

import android.app.ActionBar;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.b2c.audience.adapter.EventTicketListAdapter;
import com.b2c.audience.adapter.EventTicketSelectListAdapter;
import com.b2c.audience.databinding.ActivityEventProcessBinding;
import com.b2c.audience.model.ModelEventTicketList;
import com.b2c.audience.model.ModelEventTicketSelectList;

import java.util.ArrayList;
import java.util.List;

public class ActivityEventProcess extends AppCompatActivity implements View.OnClickListener {
    ActivityEventProcessBinding  binding;
    private EventTicketSelectListAdapter adapter;
    private List<ModelEventTicketSelectList> albumList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_event_process);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
        albumList = new ArrayList<>();
        adapter = new EventTicketSelectListAdapter(getApplicationContext(), albumList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setAdapter(adapter);
        binding.txtCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtCancel:
                finish();
        }
    }
}
