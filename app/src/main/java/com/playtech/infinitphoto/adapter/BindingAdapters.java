package com.playtech.infinitphoto.adapter;

import android.databinding.BindingAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.playtech.infinitphoto.model.PhotoModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public final class BindingAdapters {
    private BindingAdapters() {
    }

    @BindingAdapter({"photoModel", "imageThumbnailUrl"})
    public static void setThumbnailPhoto(ImageView imageView, PhotoModel photoModel, String imageThumbnailUrl) {
        if (photoModel.getImageThumbnailUrl().isEmpty()) {
            photoModel.setImageThumbnailUrl(null);
        }

        Picasso.with(imageView.getContext())
                .load(imageThumbnailUrl)
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
    public static void setGridView(GridView view, PhotoGirdAdapter adapter) {
        view.setAdapter(adapter);
    }
}
