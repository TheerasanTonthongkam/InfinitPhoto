package com.playtech.infinitphoto.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

public class PhotoModel extends BaseObservable {
    private String imageThumbnailUrl;
    private boolean loadFinish;
    private boolean loadError;

    public PhotoModel(String imageThumbnailUrl) {
        this.imageThumbnailUrl = imageThumbnailUrl;
    }

    @Bindable
    public String getImageThumbnailUrl() {
        return imageThumbnailUrl;
    }

    public void setImageThumbnailUrl(String imageThumbnailUrl) {
        this.imageThumbnailUrl = imageThumbnailUrl;
        notifyPropertyChanged(BR.imageThumbnailUrl);
    }

    @Bindable
    public boolean isLoadFinish() {
        return loadFinish;
    }

    public void setLoadFinish(boolean loadFinish) {
        this.loadFinish = loadFinish;
        notifyPropertyChanged(BR.loadFinish);
    }

    @Bindable
    public boolean isLoadError() {
        return loadError;
    }

    public void setLoadError(boolean loadError) {
        this.loadError = loadError;
        notifyPropertyChanged(BR.loadError);
    }

    public void retry() {
        retryWithUrl(this.imageThumbnailUrl);
    }

    private void retryWithUrl(String url) {
        setLoadError(false);
        setLoadFinish(false);
        setImageThumbnailUrl(url);
    }
}
