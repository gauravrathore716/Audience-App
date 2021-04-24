package com.b2c.audience.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.b2c.audience.R;
import com.b2c.audience.adapter.PastAdapter;
import com.b2c.audience.adapter.UpcommingAdapter;
import com.b2c.audience.databinding.FragmentListBinding;
import com.b2c.audience.model.ModelEventDetail;

import java.util.ArrayList;
import java.util.List;
import com.b2c.audience.databinding.FragmentListBinding;



public class UpcommingEventFragment extends Fragment {
    private UpcommingAdapter adapter;
    private List<ModelEventDetail> albumList;
    FragmentListBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);

        albumList = new ArrayList<>();
        adapter = new UpcommingAdapter(getActivity(), albumList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setAdapter(adapter);

        return binding.getRoot();

    }
}
