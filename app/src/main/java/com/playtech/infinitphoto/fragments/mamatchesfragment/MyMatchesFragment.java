package com.playtech.infinitphoto.fragments.mamatchesfragment;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.playtech.infinitphoto.R;
import com.playtech.infinitphoto.adapter.PhotoListAdapter;
import com.playtech.infinitphoto.databinding.FragmentMyMatchesBinding;
import com.playtech.infinitphoto.model.Photo;

public class MyMatchesFragment extends Fragment {

    FragmentMyMatchesBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_matches, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPhotoGrid();
    }

    private void initPhotoGrid() {
        RecyclerView photoList = binding.photoList;
        GridLayoutManager layout = new GridLayoutManager(getContext(), 3);
        layout.offsetChildrenHorizontal(50);
        layout.offsetChildrenVertical(10);

        photoList.setHasFixedSize(true);
        photoList.setLayoutManager(layout);

        ObservableArrayList<Photo> photos = new ObservableArrayList<>();

        for (int i = 0; i < 50; i++){
            photos.add(new Photo(R.drawable.place_holder));
            photos.add(new Photo(R.drawable.image_place_holder_1));
            photos.add(new Photo(R.drawable.image_place_holder_2));
            photos.add(new Photo(R.drawable.image_place_holder_3));
        }


        PhotoListAdapter adapter = new PhotoListAdapter(photos);

        photoList.setAdapter(adapter);
    }
}
