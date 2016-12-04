package com.playtech.infinitphoto.adapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;


public final class BindingAdapters {
    private BindingAdapters() {
    }

    @BindingAdapter({"imageRes"})
    public static void setPhoto(ImageView view, int imageRes) {
        view.setImageResource(imageRes);
    }
}
