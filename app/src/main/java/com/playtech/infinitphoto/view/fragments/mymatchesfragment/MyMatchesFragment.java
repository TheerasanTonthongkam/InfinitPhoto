package com.playtech.infinitphoto.view.fragments.mymatchesfragment;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.playtech.infinitphoto.BR;

import com.playtech.infinitphoto.R;
import com.playtech.infinitphoto.adapter.PhotoGirdAdapter;
import com.playtech.infinitphoto.cookie.PersistentCookieStore;
import com.playtech.infinitphoto.databinding.FragmentMyMatchesBinding;
import com.playtech.infinitphoto.model.PhotoModel;
import com.playtech.infinitphoto.schedulers.ThreadScheduler;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyMatchesFragment extends Fragment {

    private FragmentMyMatchesBinding binding;
    private MyMatchesViewModel viewModel;
    private Snackbar snackBar;
    private PhotoGirdAdapter adapter;

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
        initShackBar();
        initErrorBar();
        initAdapter();
        login();
    }

    private void initViewModel() {
        viewModel = new MyMatchesViewModel(new PersistentCookieStore(getContext()));
        ThreadScheduler scheduler = new ThreadScheduler(Schedulers.io(), AndroidSchedulers.mainThread());
        viewModel.setThreadScheduler(scheduler);
        ObservableArrayList<PhotoModel> photoModels = new ObservableArrayList<>();
        viewModel.addOnPropertyChangedCallback(onViewModelPropertyChanged());
        viewModel.setPhotoModels(photoModels);
        binding.setViewModel(viewModel);
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

    private void initErrorBar() {
        binding.retryButton.setOnClickListener(v -> {
            if (viewModel.getPhotoModels().size() > 0) {
                viewModel.loadMore();
            } else {
                login();
            }
        });
    }

    private void initAdapter() {
        adapter = new PhotoGirdAdapter(viewModel.getPhotoModels());
        adapter.setOnRetryListener(position -> {
            PhotoModel photoModel = viewModel.getPhotoModels().get(position);
            photoModel.retry();
        });

        adapter.setOnEndList(this::onLastItem);
        binding.setAdapter(adapter);
    }

    private void login() {
        viewModel.startRetrieveCookie("andy", "1234");
    }

    private Observable.OnPropertyChangedCallback onViewModelPropertyChanged() {
        return new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int id) {
                if (id == BR.loadMore) {
                    triggerLoadMore();
                }
                if (id == BR.photoModels) {
                    changeDataSet();
                }
            }
        };
    }

    private void changeDataSet() {
        if (viewModel.getPhotoModels() != null && adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void triggerLoadMore() {
        if (!snackBar.isShown() && viewModel.isLoadMore()) {
            snackBar.show();
        }
        if (snackBar.isShown() && !viewModel.isLoadMore()) {
            snackBar.dismiss();
        }
    }

    private void onLastItem(int position, int size) {
        if (isLastItem(position, size)) {
            Snackbar.make(binding.rootLayout, R.string.lastItem, Snackbar.LENGTH_SHORT).show();
        }

        if (isScrollToEnd(position, size)) {
            viewModel.loadMore();
        }
    }

    private boolean isLastItem(int position, int size) {
        return isScrollToEnd(position, size) && size == viewModel.getMaxSize();
    }

    private boolean isScrollToEnd(int position, int size) {
        return position == (size-1);
    }
}

