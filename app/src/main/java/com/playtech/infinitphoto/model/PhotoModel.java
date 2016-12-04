package com.playtech.infinitphoto.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

public class PhotoModel extends BaseObservable {
    private String imageUrl;
    private boolean loadFinish;

    public PhotoModel() {}

    public PhotoModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }

    @Bindable
    public boolean isLoadFinish() {
        return loadFinish;
    }

    public void setLoadFinish(boolean loadFinish) {
        this.loadFinish = loadFinish;
        notifyPropertyChanged(BR.loadFinish);
    }
}
