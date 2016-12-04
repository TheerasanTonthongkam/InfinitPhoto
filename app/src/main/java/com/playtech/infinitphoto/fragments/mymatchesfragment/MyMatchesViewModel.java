package com.playtech.infinitphoto.fragments.mymatchesfragment;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import com.playtech.infinitphoto.model.PhotoModel;
import com.playtech.infinitphoto.BR;

public class MyMatchesViewModel extends BaseObservable {
    ObservableArrayList<PhotoModel> photoModels;

    @Bindable
    public ObservableArrayList<PhotoModel> getPhotoModels() {
        return photoModels;
    }

    public void setPhotoModels(ObservableArrayList<PhotoModel> photoModels) {
        this.photoModels = photoModels;
        notifyPropertyChanged(BR.photoModels);
    }
}
