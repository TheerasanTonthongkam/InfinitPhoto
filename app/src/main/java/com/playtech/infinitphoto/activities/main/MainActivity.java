package com.playtech.infinitphoto.activities.main;

import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.playtech.infinitphoto.R;
import com.playtech.infinitphoto.adapter.PhotoPagerAdapter;
import com.playtech.infinitphoto.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBindingAndViewModel();
        initToolbar();
        initTab();
    }

    private void initBindingAndViewModel() {
        binding = DataBindingUtil.bind(getRootView());

        viewModel = new MainActivityViewModel();
        initViewModelData(viewModel);
        binding.setViewModel(viewModel);
    }

    private void initViewModelData(MainActivityViewModel viewModel) {
        //TODO: @Theerasan 2016-12-4 should retrieve data from the server
        viewModel.setAlbumName("Beautiful Hipster Family");
    }

    private ViewGroup getRootView() {
        return (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
    }

    private void initTab() {
        TabLayout tabLayout = binding.tabLayout;
        tabLayout.addTab(tabLayout.newTab().setText(R.string.my_matches_label), true);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.all_photos_label));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPager pager = binding.pager;
        PhotoPagerAdapter pagerAdapter = new PhotoPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        initPagerEvent(pager, tabLayout);
    }

    private void initPagerEvent(ViewPager pager, TabLayout tabLayout) {
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
