package com.playtech.infinitphoto.adapter;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.playtech.infinitphoto.model.PhotoModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public final class BindingAdapters {
    private BindingAdapters() {
    }

    @BindingAdapter({"photoModel", "imageUrl"})
    public static void setPhoto(ImageView imageView, PhotoModel photoModel, String imageUrl) {
        if (photoModel.getImageUrl().isEmpty()) {
            photoModel.setImageUrl(null);
        }

        Picasso.with(imageView.getContext())
                .load(imageUrl)
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

    @BindingAdapter({"photoAdapter"})
    public static void setRecycleView(RecyclerView view, PhotoListAdapter adapter) {
        view.setAdapter(adapter);
    }
}
