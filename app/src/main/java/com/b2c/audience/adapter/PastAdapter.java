package com.b2c.audience.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import com.b2c.audience.R;
import com.b2c.audience.model.ModelEventDetail;
import com.b2c.audience.databinding.RowEventBinding;
import java.util.List;


public class PastAdapter extends RecyclerView.Adapter<PastAdapter.MyViewHolder> {
    private Context mContext;
    private List<ModelEventDetail> pastAdapters;


    public PastAdapter(Context mContext, List<ModelEventDetail> pastAdapters) {
        this.mContext = mContext;
        this.pastAdapters = pastAdapters;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding  binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_event, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        RowEventBinding binding=(RowEventBinding)holder.getBinding();
        binding.ivMore.setVisibility(View.GONE);
        binding.llBtnView.setVisibility(View.GONE);
        binding.txtMail.setVisibility(View.GONE);
        binding.txtStatus.setVisibility(View.INVISIBLE);


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
