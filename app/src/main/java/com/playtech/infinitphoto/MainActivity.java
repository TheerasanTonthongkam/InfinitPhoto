package com.playtech.infinitphoto;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

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
}
