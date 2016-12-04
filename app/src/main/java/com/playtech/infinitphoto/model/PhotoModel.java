package com.playtech.infinitphoto.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.android.databinding.library.baseAdapters.BR;

public class PhotoModel extends BaseObservable {
    private String imageUrl;
    private Bitmap cache;
    private Drawable drawableCache;

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
    public Bitmap getCache() {
        return cache;
    }

    public void setCache(Bitmap cache) {
        this.cache = cache;
        notifyPropertyChanged(BR.cache);
    }

    public Drawable getDrawableCache() {
        return drawableCache;
    }

    public void setDrawableCache(Drawable drawableCache) {
        this.drawableCache = drawableCache;
    }
}
