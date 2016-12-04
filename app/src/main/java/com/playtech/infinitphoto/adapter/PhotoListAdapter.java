package com.playtech.infinitphoto.adapter;

import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.playtech.infinitphoto.databinding.ItemPhotoBinding;
import com.playtech.infinitphoto.listener.OnEndList;
import com.playtech.infinitphoto.listener.OnRetryListener;
import com.playtech.infinitphoto.model.PhotoModel;
import com.playtech.infinitphoto.viewholder.PhotoViewHolder;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private final ObservableArrayList<PhotoModel> photoModels;
    private LayoutInflater layoutInflater;
    private OnRetryListener onRetryListener;
    private OnEndList onEndList;

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
        binding.retryButton.setOnClickListener(v -> onRetryListener.click(position));
        if (position == getItemCount() - 1 ) {
            onEndList.callback();
        }
    }

    @Override
    public int getItemCount() {
        return photoModels.size();
    }

    public void setOnRetryListener(OnRetryListener onRetryListener) {
        this.onRetryListener = onRetryListener;
    }

    public void setOnEndList(OnEndList onEndList) {
        this.onEndList = onEndList;
    }

//    public ObservableArrayList<PhotoModel> getPhotoModels() {
//        return photoModels;
//    }
//
//    public void setPhotoModels(ObservableArrayList<PhotoModel> photoModels) {
//        this.photoModels = photoModels;
//    }
}
