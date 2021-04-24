package com.b2c.audience.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.b2c.audience.R;
import com.b2c.audience.databinding.RowTicketListBinding;
import com.b2c.audience.databinding.RowTicketSelectListBinding;
import com.b2c.audience.model.ModelEventTicketList;
import com.b2c.audience.model.ModelEventTicketSelectList;

import java.util.List;

public class EventTicketSelectListAdapter extends RecyclerView.Adapter<EventTicketSelectListAdapter.MyViewHolder>  {
    private Context mContext;
    private List<ModelEventTicketSelectList> modelEventTicketSelectLists;
      int minteger = 0;
      int max = 10;

     RowTicketSelectListBinding binding;

    public EventTicketSelectListAdapter(Context mContext, List<ModelEventTicketSelectList> modelEventTicketSelectLists) {
        this.mContext = mContext;
        this.modelEventTicketSelectLists = modelEventTicketSelectLists;
    }
    @NonNull
    @Override
    public EventTicketSelectListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_ticket_select_list, parent, false);
        return new EventTicketSelectListAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventTicketSelectListAdapter.MyViewHolder holder, int position) {
         binding=(RowTicketSelectListBinding)holder.getBinding();
        if(position %2 == 1)
        {
            binding.llBtnView.setBackgroundColor(Color.parseColor("#D3D3D3"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            binding.llBtnView.setBackgroundColor(Color.parseColor("#D3D3D3"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
    }



    @Override
    public int getItemCount() {
        return 5;
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
