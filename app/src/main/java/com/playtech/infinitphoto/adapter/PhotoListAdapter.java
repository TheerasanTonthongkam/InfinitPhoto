package com.playtech.infinitphoto.adapter;

import android.databinding.ObservableArrayList;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.playtech.infinitphoto.databinding.ItemPhotoBinding;
import com.playtech.infinitphoto.model.PhotoModel;
import com.playtech.infinitphoto.viewholder.PhotoViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private ObservableArrayList<PhotoModel> photoModels;
    private LayoutInflater layoutInflater;

    public PhotoListAdapter(ObservableArrayList<PhotoModel> photoModels) {
        this.photoModels = photoModels;
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

        Picasso.with(binding.getRoot().getContext())
                .load(photoModel.getImageUrl())
                .into(binding.image, picassoCallback(photoModel));
    }

    private Callback picassoCallback(PhotoModel photoModel) {
        return new Callback() {
            @Override
            public void onSuccess() {
                photoModel.setLoadFinish(true);
            }

            @Override
            public void onError() {

            }
        };
    }

    @Override
    public int getItemCount() {
        return photoModels.size();
    }
}
