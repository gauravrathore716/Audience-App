package com.b2c.audience.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.b2c.audience.R;
import com.b2c.audience.model.ModelTranstionDetail;
import com.b2c.audience.databinding.RowTranstionBinding;

import java.util.List;


public class TransctionAdapter extends RecyclerView.Adapter<TransctionAdapter.MyViewHolder> {
    private Context mContext;
    private List<ModelTranstionDetail> pastAdapters;


    public TransctionAdapter(Context mContext, List<ModelTranstionDetail> pastAdapters) {
        this.mContext = mContext;
        this.pastAdapters = pastAdapters;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding  binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_transtion, parent, false);
        return new TransctionAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        RowTranstionBinding  binding = (RowTranstionBinding)holder.getBinding();

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
