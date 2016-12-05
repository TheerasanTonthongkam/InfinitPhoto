package com.playtech.infinitphoto.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.playtech.infinitphoto.R;
import com.playtech.infinitphoto.databinding.ItemPhotoBinding;
import com.playtech.infinitphoto.listener.OnEndList;
import com.playtech.infinitphoto.listener.OnRetryListener;
import com.playtech.infinitphoto.model.PhotoModel;

public class PhotoGirdAdapter extends BaseAdapter {

    private final ObservableArrayList<PhotoModel> photoModels;
    private LayoutInflater layoutInflater;
    private OnRetryListener onRetryListener;
    private OnEndList onEndList;

    public PhotoGirdAdapter(ObservableArrayList<PhotoModel> photoModels) {
        this.photoModels = photoModels;
    }

    @Override
    public int getCount() {
        return photoModels.size();
    }

    @Override
    public PhotoModel getItem(int i) {
        return photoModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).hashCode();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }

        View view;
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.item_photo, viewGroup, false);
        } else {
            view = convertView;
        }

        ItemPhotoBinding binding = DataBindingUtil.bind(view);
        binding.setItem(getItem(i));
        Object tag = binding.image.getTag();
        binding.image.setTag(i);
        binding.retryButton.setOnClickListener(v -> onRetryListener.click(i));
        setImageNotBlinkWhenReuse(binding.image, tag, i);
        onEndList.callback(i, photoModels.size());

        return view;
    }

    private void setImageNotBlinkWhenReuse(ImageView imageView, Object tag, int i) {
        if (tag != null) {
            if ((int) tag != i) {
                imageView.setImageResource(R.color.invisible);
            }
        }
    }

    public void setOnRetryListener(OnRetryListener onRetryListener) {
        this.onRetryListener = onRetryListener;
    }

    public void setOnEndList(OnEndList onEndList) {
        this.onEndList = onEndList;
    }
}
