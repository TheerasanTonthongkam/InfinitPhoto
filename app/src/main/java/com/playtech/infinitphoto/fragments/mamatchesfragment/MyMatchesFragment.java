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
            photos.add(new Photo("http://www.japan-guide.com/g10/interest_food_top.jpg"));
            photos.add(new Photo("http://muza-chan.net/aj/poze-weblog4/dango.jpg"));
            photos.add(new Photo("https://cdn.cheapoguides.com/wp-content/uploads/sites/2/2015/05/2192225496_76702451ff_b-770x515.jpg"));
            photos.add(new Photo("https://www.tsunagujapan.com/wp-content/uploads/2015/04/6037757090_d41c5e3541_b.jpg"));
            photos.add(new Photo("http://expja.com/wp-content/uploads/2015/12/sashimi-japanese-food-1024x681.jpg"));
        }


        PhotoListAdapter adapter = new PhotoListAdapter(photos);

        photoList.setAdapter(adapter);
    }
}
