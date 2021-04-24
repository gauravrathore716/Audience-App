package com.b2c.audience;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.b2c.audience.api_presenter.CategoryListPresenter;
import com.b2c.audience.databinding.ActivityMainBinding;
import com.b2c.audience.drawerbehavior.AdvanceDrawerLayout;
import com.b2c.audience.fragment.HomeFragment;
import com.b2c.audience.hidekeywboard.HideKeyboard;
import com.b2c.audience.model.CategoryListInfo;
import com.b2c.audience.model.CategoryListResponse;
import com.b2c.audience.view.ICategoryView;
import com.b2c.audience.webservice.ApiInterface;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainActivity extends NavigationItemActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener, ICategoryView {


    public  static ActivityMainBinding binding;
    private AdvanceDrawerLayout drawer;
    CategoryListPresenter presenter;
    private AppCompatActivity mActivity =this;
    ArrayList<CategoryListInfo> arrayList;
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private ViewPagerAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        presenter = new CategoryListPresenter();
        presenter.setView(this);

        HideKeyboard.setupUI(binding.drawer, this);
        setfindviewid();
        drawer = (AdvanceDrawerLayout) findViewById(R.id.drawer);

        binding. drawer.setViewScale(Gravity.START, 0.9f);
        binding. drawer.setRadius(Gravity.START, 35);
        binding.drawer.setViewElevation(Gravity.START, 50);



        binding.ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etSearch.setText("");
            }
        });
        binding.etSearch.setText("");


        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ( binding.etSearch.getText().toString().length()>0){
                    binding.ivClear.setVisibility(View.VISIBLE);
                }
                else {
                    binding.ivClear.setVisibility(View.GONE);

                }
            }
        });
 }




    @Override
    protected void onStart() {
        super.onStart();
        binding.navigationItem.txtUserName.setText(SessionManager.getString(this,SessionManager.FIRST_NAME));
        Log.e("username",""+SessionManager.getString(this,SessionManager.FIRST_NAME));
        binding.navigationItem.txtUserEmail.setText(SessionManager.getString(this,SessionManager.EMAIL));
        Log.e("email",""+SessionManager.getString(this,SessionManager.EMAIL));

        DataBindingAdapter.setProfileSrcUrl(binding.navigationItem.userImage,SessionManager.getUserImage(this));

        if (NetworkAlertUtility.isConnectingToInternet(mActivity)) {

            presenter.categoryList(mActivity);

        }
        else{
            NetworkAlertUtility.showNetworkFailureAlert(mActivity);
        }
    }

    private void setfindviewid() {

        setSupportActionBar(binding.toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.menu_new);

            actionBar.setHomeAsUpIndicator(upArrow);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i ;
        switch (item.getItemId()){
            case R.id.action_menu_scan:
                i = new Intent(this,CameraActivity.class);
                startActivity(i);
                break;

            case android.R.id.home:
                if (binding. drawer.isDrawerOpen(GravityCompat.START)){
                    binding. drawer.closeDrawer(GravityCompat.START);
                }
                else {
                    binding. drawer.openDrawer(GravityCompat.START);

                }
                InputMethodManager inputMethodManager = (InputMethodManager)
                        this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);


                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @SuppressLint("SetTextI18n")
    private void setupViewPager(ViewPager viewPager,List<CategoryListInfo> categoryListInfo) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        for (int i=0; i<categoryListInfo.size(); i++){

            HomeFragment fView = new HomeFragment();
            adapter.addFrag(fView,categoryListInfo.get(i).getName());

        }

        viewPager.setAdapter(adapter);

        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    public void onSuccess(CategoryListResponse body) {
        MyCustomToast.showToast(mActivity,body.getMessage());

        if (body != null){
            setupViewPager(binding.viewPager,body.getCategoryListInfo());


        }
    }

    @Override
    public Context getContext() {
        return this;
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return HomeFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
