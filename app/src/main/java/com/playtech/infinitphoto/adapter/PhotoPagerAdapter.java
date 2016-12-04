package com.playtech.infinitphoto.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.playtech.infinitphoto.fragments.allphotos.AllPhotoFragment;
import com.playtech.infinitphoto.fragments.mymatchesfragment.MyMatchesFragment;

import java.util.ArrayList;

public class PhotoPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments;

    public PhotoPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(new MyMatchesFragment());
        fragments.add(new AllPhotoFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Fragment";
    }
}
