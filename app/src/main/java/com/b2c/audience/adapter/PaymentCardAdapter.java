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
import com.b2c.audience.databinding.RowCreditBalBinding;
import com.b2c.audience.model.ModelCardPayment;

import java.util.List;

public class PaymentCardAdapter extends RecyclerView.Adapter<PaymentCardAdapter.MyViewHolder> {
    private Context mContext;
    private List<ModelCardPayment> pastAdapters;


    public PaymentCardAdapter(Context mContext, List<ModelCardPayment> pastAdapters) {
        this.mContext = mContext;
        this.pastAdapters = pastAdapters;
    }
    @NonNull
    @Override
    public PaymentCardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_credit_bal, parent, false);
        return new PaymentCardAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final PaymentCardAdapter.MyViewHolder holder, int position) {
        final RowCreditBalBinding binding=(RowCreditBalBinding)holder.getBinding();

        if (position==0){
            binding.ivCard.setImageResource(R.drawable.visa);
        }
        else {
            //binding.txtPrinciple.setVisibility(View.INVISIBLE);
        }
        if (position==1){
            binding.txtPrinciple.setVisibility(View.VISIBLE);
        }
        else {
            binding.txtPrinciple.setVisibility(View.INVISIBLE);
        }

        if (position==2){
            binding.ivCard.setImageResource(R.drawable.american);
        }
        else {
            //binding.txtPrinciple.setVisibility(View.INVISIBLE);
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
        inflater.inflate(R.menu.menu_payment, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();

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


    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        public MyMenuItemClickListener() {
        }


        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.set_main:
                    Toast.makeText(mContext,R.string.payment_menu_one , Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.delete_cb:
                    Toast.makeText(mContext, R.string.payment_menu_two, Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
}
