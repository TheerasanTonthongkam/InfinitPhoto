package com.playtech.infinitphoto.adapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public final class BindingAdapters {
    private BindingAdapters() {
    }

    @BindingAdapter({"imageUrl"})
    public static void setPhoto(ImageView view, String imageUrl) {
        if (imageUrl.isEmpty()) {
            imageUrl = null;
        }

        Picasso.with(view.getContext())
                .load(imageUrl)
                .into(view);

    }
}
