package com.b2c.audience.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.b2c.audience.R;
import com.b2c.audience.databinding.RowEventBinding;
import com.b2c.audience.databinding.RowTicketListBinding;
import com.b2c.audience.model.ModelEventDetail;
import com.b2c.audience.model.ModelEventTicketList;

import java.util.List;

public class EventTicketListAdapter extends RecyclerView.Adapter<EventTicketListAdapter.MyViewHolder> {
    private Context mContext;
    private List<ModelEventTicketList> modelEventTicketLists;


    public EventTicketListAdapter(Context mContext, List<ModelEventTicketList> modelEventTicketLists) {
        this.mContext = mContext;
        this.modelEventTicketLists = modelEventTicketLists;
    }
    @NonNull
    @Override
    public EventTicketListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_ticket_list, parent, false);
        return new EventTicketListAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventTicketListAdapter.MyViewHolder holder, int position) {
        final RowTicketListBinding binding=(RowTicketListBinding)holder.getBinding();
        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#F3F3F3"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }

    }


    @Override
    public int getItemCount() {
        return 3;
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
