package com.b2c.audience.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.b2c.audience.ActivityEventDetails;
import com.b2c.audience.R;
import com.b2c.audience.databinding.RowHomeListBinding;
import com.b2c.audience.model.EventListInfo;
import com.b2c.audience.model.ModelHomeDetail;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;


public class  HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.MyViewHolder> {
    private Context mContext;
    private List<EventListInfo> eventListInfos;


    public HomePageAdapter(Context mContext, List<EventListInfo> eventListInfos) {
        this.mContext = mContext;
        this.eventListInfos = eventListInfos;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding  binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_home_list, parent, false);
        return new HomePageAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        RowHomeListBinding  binding = (RowHomeListBinding)holder.getBinding();

            binding.txtHomeOne.setText(eventListInfos.get(position).getAddress());
            binding.txtHomeTwo.setText(eventListInfos.get(position).getEvent_date());
            Glide.with(mContext).load(eventListInfos.get(position).getEvent_pic())
                .apply(new RequestOptions().placeholder(R.drawable.dummy_event).error(R.drawable.dummy_event))
                .into(holder.binding.ivPro);
            binding.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   movenext();
                }
            });
    }

    private void movenext() {
        Intent  i  = new Intent(mContext,ActivityEventDetails.class);
        mContext.startActivity(i);
    }


    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;

        public MyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.executePendingBindings();
        }
        public ViewDataBinding getBinding() {
            return binding;
        }
    }

}
