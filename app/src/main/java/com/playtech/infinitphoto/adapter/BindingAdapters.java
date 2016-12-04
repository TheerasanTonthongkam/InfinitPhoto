package com.playtech.infinitphoto.adapter;

import android.databinding.BindingAdapter;
import android.view.ViewParent;
import android.widget.ImageView;

import com.playtech.infinitphoto.model.PhotoModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public final class BindingAdapters {
    private BindingAdapters() {
    }

    @BindingAdapter({"photoModel"})
    public static void setPhoto(ImageView imageView, PhotoModel photoModel) {
        Picasso.with(imageView.getContext())
                .load(photoModel.getImageUrl())
                .into(imageView, picassoCallback(photoModel));
    }

    private static Callback picassoCallback(PhotoModel photoModel) {
        return new Callback() {
            @Override
            public void onSuccess() {
                photoModel.setLoadFinish(true);
            }

            @Override
            public void onError() {
                photoModel.setLoadError(true);
                photoModel.setLoadFinish(true);
            }
        };
    }
}
