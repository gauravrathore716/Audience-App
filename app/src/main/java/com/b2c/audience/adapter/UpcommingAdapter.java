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
import android.widget.PopupMenu;
import android.widget.Toast;

import com.b2c.audience.R;
import com.b2c.audience.databinding.RowEventBinding;
import com.b2c.audience.model.ModelEventDetail;

import java.util.List;

public class UpcommingAdapter extends RecyclerView.Adapter<UpcommingAdapter.MyViewHolder> {
    private Context mContext;
    private List<ModelEventDetail> pastAdapters;


    public UpcommingAdapter(Context mContext, List<ModelEventDetail> pastAdapters) {
        this.mContext = mContext;
        this.pastAdapters = pastAdapters;
    }
    @NonNull
    @Override
    public UpcommingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_event, parent, false);
        return new UpcommingAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final UpcommingAdapter.MyViewHolder holder, int position) {
        final RowEventBinding binding=(RowEventBinding)holder.getBinding();

        if (position==0){
            binding.txtStatus.setVisibility(View.INVISIBLE);
        }
        else {
            binding.txtStatus.setVisibility(View.VISIBLE);
        }
        binding.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(binding.ivMore);
            }
        });

    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_event, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();

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


    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        public MyMenuItemClickListener() {
        }


        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.see_ticket:
                    Toast.makeText(mContext, "Voir le billet", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.goticket:
                    Toast.makeText(mContext, "Se rendre à l’évènement", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.delteticket:
                    Toast.makeText(mContext, "Annuler le billet", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
}
