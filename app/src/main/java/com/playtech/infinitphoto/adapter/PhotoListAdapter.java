package com.playtech.infinitphoto.adapter;

import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.playtech.infinitphoto.databinding.ItemPhotoBinding;
import com.playtech.infinitphoto.model.Photo;
import com.playtech.infinitphoto.viewholder.PhotoViewHolder;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private ObservableArrayList<Photo> photos;
    private LayoutInflater layoutInflater;

    public PhotoListAdapter(ObservableArrayList<Photo> photos) {
        this.photos = photos;
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
        Photo photo = photos.get(position);
        holder.setDataPhotoItem(photo);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }
}
