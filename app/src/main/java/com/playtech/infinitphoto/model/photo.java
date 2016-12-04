package com.playtech.infinitphoto.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.android.databinding.library.baseAdapters.BR;

public class Photo extends BaseObservable {
    private int resId;

    public Photo() {}

    public Photo(int resId) {
        this.resId = resId;
    }

    @Bindable
    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
        notifyPropertyChanged(BR.resId);
    }
}
