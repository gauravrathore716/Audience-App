package com.b2c.audience.fragment;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.b2c.audience.FileUtil;
import com.b2c.audience.MyCustomToast;
import com.b2c.audience.NetworkAlertUtility;
import com.b2c.audience.R;
import com.b2c.audience.SessionManager;
import com.b2c.audience.SettingActivity;
import com.b2c.audience.Utility;
import com.b2c.audience.adapter.HomePageAdapter;
import com.b2c.audience.api_presenter.CategoryListPresenter;
import com.b2c.audience.api_presenter.EventListPresenter;
import com.b2c.audience.databinding.FragmentHomeBinding;
import com.b2c.audience.model.CategoryListResponse;
import com.b2c.audience.model.EventListInfo;
import com.b2c.audience.model.EventListResponse;
import com.b2c.audience.model.ModelHomeDetail;
import com.b2c.audience.view.ICategoryView;
import com.b2c.audience.view.IEventListView;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HomeFragment extends BaseFragment implements IEventListView {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;
    private HomePageAdapter adapter;
    private List<EventListInfo> eventListInfos;
    FragmentHomeBinding binding;
    private EventListPresenter presenter;
    private AppCompatActivity mActivity;

    public HomeFragment() {

    }
    public static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = (AppCompatActivity) getActivity();

        presenter = new EventListPresenter();
        presenter.setView(this);

    }

    @Override
    public void onStart() {
        super.onStart();

        if (NetworkAlertUtility.isConnectingToInternet2(mActivity)) {

            HashMap<String, RequestBody> map = new HashMap<>();
            map.put("category_id", RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(5)));
            map.put("keyword", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
            presenter.eventList(mActivity, map);

        }
        else {
            NetworkAlertUtility.showNetworkFailureAlert(mActivity);

        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View rootView = binding.getRoot();
        sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        eventListInfos = new ArrayList<>();
        adapter = new HomePageAdapter(getActivity(), eventListInfos);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setAdapter(adapter);


        return rootView;

    }


    @Override
    public void onSuccess(EventListResponse body) {

        eventListInfos.clear();

        if (body.isStatus()) {
            eventListInfos.addAll(body.getEventListInfos());
        } else if (!body.isActive()) {
            MyCustomToast.showToast(mActivity, body.getMessage());
            UserLogout(mActivity);
        } else {
            MyCustomToast.showToast(mActivity, body.getMessage());
        }
        adapter.notifyDataSetChanged();

    }
}
