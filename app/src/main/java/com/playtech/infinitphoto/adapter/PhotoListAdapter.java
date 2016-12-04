package com.playtech.infinitphoto.adapter;

import android.databinding.ObservableArrayList;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.playtech.infinitphoto.databinding.ItemPhotoBinding;
import com.playtech.infinitphoto.model.PhotoModel;
import com.playtech.infinitphoto.viewholder.PhotoViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private ObservableArrayList<PhotoModel> photoModels;
    private LayoutInflater layoutInflater;
    private int imageCacheSize;

    public PhotoListAdapter(ObservableArrayList<PhotoModel> photoModels, int imageCacheSize) {
        this.photoModels = photoModels;
        this.imageCacheSize = imageCacheSize;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ItemPhotoBinding binding = ItemPhotoBinding.inflate(layoutInflater, parent, false);

        return new PhotoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        PhotoModel photoModel = photoModels.get(position);
        holder.setDataPhotoItem(photoModel);
        ItemPhotoBinding binding = holder.getBinding();

        removeDrawableCache(position);
        if (photoModel.getDrawableCache() != null) {
            binding.image.setImageDrawable(photoModel.getDrawableCache());
            return;
        }

        Picasso.with(binding.getRoot().getContext())
                .load(photoModel.getImageUrl())
                .into(binding.image, picassoCallback(photoModel, binding.image));
    }

    private void removeDrawableCache(int position) {
        int lastIndex = position - imageCacheSize;
        int maxIndex = position + imageCacheSize;

        if (lastIndex >= 0)
            photoModels.get(lastIndex).setDrawableCache(null);

        if (maxIndex < photoModels.size() - 1)
            photoModels.get(maxIndex).setDrawableCache(null);
    }

    private Callback picassoCallback(PhotoModel photoModel, ImageView imageView) {
        return new Callback() {
            @Override
            public void onSuccess() {
                Drawable drawable = imageView.getDrawable();
                photoModel.setDrawableCache(drawable);
            }

            @Override
            public void onError() {
                Toast.makeText(imageView.getContext(), "Not load", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public int getItemCount() {
        return photoModels.size();
    }
}
