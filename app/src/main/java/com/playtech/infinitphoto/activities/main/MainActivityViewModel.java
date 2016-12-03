package com.playtech.infinitphoto.activities.main;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.playtech.infinitphoto.BR;

public class MainActivityViewModel extends BaseObservable {

    private String albumName = "";

    @Bindable
    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;

        notifyPropertyChanged(BR.albumName);
        notifyPropertyChanged(BR.hashTag);
    }

    @Bindable
    public String getHashTag() {
        return "#" + this.albumName.replace(" ", "").toLowerCase();
    }
}
