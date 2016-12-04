package com.playtech.infinitphoto.viewholder;

import android.support.v7.widget.RecyclerView;

import com.playtech.infinitphoto.databinding.ItemPhotoBinding;
import com.playtech.infinitphoto.model.PhotoModel;

public class PhotoViewHolder extends RecyclerView.ViewHolder{

    private ItemPhotoBinding binding;

    public PhotoViewHolder(ItemPhotoBinding itemView) {
        super(itemView.getRoot());
        this.binding = itemView;
    }

    public void setDataPhotoItem(PhotoModel photoModel)  {
        binding.setItem(photoModel);
    }

    public ItemPhotoBinding getBinding() {
        return binding;
    }
}
