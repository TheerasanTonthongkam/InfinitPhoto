package com.playtech.infinitphoto.fragments.mymatchesfragment;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.playtech.infinitphoto.BR;

import com.playtech.infinitphoto.R;
import com.playtech.infinitphoto.adapter.PhotoListAdapter;
import com.playtech.infinitphoto.databinding.FragmentMyMatchesBinding;
import com.playtech.infinitphoto.model.PhotoModel;

public class MyMatchesFragment extends Fragment {

    private FragmentMyMatchesBinding binding;
    private MyMatchesViewModel viewModel;
    private Snackbar snackBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_matches, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModel();
        initPhotoGrid();
        initShackBar();
        initAdapter();
    }

    private void initViewModel() {
        viewModel = new MyMatchesViewModel();
        ObservableArrayList<PhotoModel> photoModels = new ObservableArrayList<>();
        for (int i = 0; i < 10; i++){
            photoModels.add(new PhotoModel("https://waldo-thumbs-staging.s3.amazonaws.com/medium/3b568ff2-f408-4824-996b-0116c06b0a6f.jpg"));
            photoModels.add(new PhotoModel("https://waldo-thumbs-staging.s3.amazonaws.com/medium/373515bd-8f25-400f-a6dc-bff4d95280b7.jpg"));
            photoModels.add(new PhotoModel("https://waldo-thumbs-staging.s3.amazonaws.com/medium/80d7b3f8-6c0b-4178-b639-afd0d01aff92.jpg"));
            photoModels.add(new PhotoModel("https://waldo-thumbs-staging.s3.amazonaws.com/medium/81f461f2-4c73-44d2-b8c1-418969e812f8.jpg"));
            photoModels.add(new PhotoModel("https://waldo-thumbs-staging.s3.amazonaws.com/medium/e2af3d4a-c390-443a-9dba-d57c9a3b7d17.jpg"));
            photoModels.add(new PhotoModel("https://waldo-thumbs-staging.s3.amazonaws.com/medium/275c7a65-68c1-437f-8043-d76b7059ebab.jpg"));
        }

        viewModel.addOnPropertyChangedCallback(onViewModelPropertyChanged());
        viewModel.setPhotoModels(photoModels);
        binding.setViewModel(viewModel);
    }

    private void initPhotoGrid() {
        RecyclerView photoList = binding.photoList;
        GridLayoutManager layout = new GridLayoutManager(getContext(), 3);
        layout.offsetChildrenHorizontal(50);
        layout.offsetChildrenVertical(10);
        photoList.setHasFixedSize(true);
        photoList.setLayoutManager(layout);
    }

    private void initShackBar() {
        snackBar = Snackbar.make(binding.rootLayout, R.string.load_more, Snackbar.LENGTH_INDEFINITE);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackBar.getView();
        ProgressBar progressBar = new ProgressBar(getContext());
        progressBar.setIndeterminate(true);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.load_more_background);
        layout.setBackground(drawable);
        layout.setAlpha(0.9f);
        layout.addView(progressBar);
    }

    private void initAdapter() {
        PhotoListAdapter adapter = new PhotoListAdapter(viewModel.getPhotoModels());
        adapter.setOnRetryListener(position -> {
            PhotoModel photoModel = viewModel.getPhotoModels().get(position);
            photoModel.retry();
        });

        adapter.setOnEndList(() -> {
            if (!snackBar.isShown()) {
                snackBar.show();
                viewModel.setLoadMore(true);
            }
        });
        binding.setAdapter(adapter);
    }

    private Observable.OnPropertyChangedCallback onViewModelPropertyChanged() {
        return new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int id) {
                if (id == BR.loadMore) {
                    triggerLoadMore();
                }
            }
        };
    }

    private void triggerLoadMore() {
        if (!snackBar.isShown() && viewModel.isLoadMore()) {
            snackBar.show();
        }
        if (snackBar.isShown() && !viewModel.isLoadMore()) {
            snackBar.dismiss();
        }
    }

}

