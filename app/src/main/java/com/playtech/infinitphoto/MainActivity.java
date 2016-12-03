package com.playtech.infinitphoto;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.playtech.infinitphoto.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBinding();
        initToolbar();
    }

    private void initBinding() {
        binding = DataBindingUtil.bind(getRootView());
    }

    private ViewGroup getRootView() {
        return (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
    }
}
