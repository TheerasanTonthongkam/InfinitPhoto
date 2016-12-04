package com.playtech.infinitphoto.fragments.mymatchesfragment;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import com.playtech.infinitphoto.model.PhotoModel;
import com.playtech.infinitphoto.BR;
import com.playtech.infinitphoto.sevice.AuthenticationServiceImp;
import com.playtech.infinitphoto.sevice.interfaces.AuthenticationService;

import org.antlr.v4.codegen.model.SrcOp;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyMatchesViewModel extends BaseObservable {
    private ObservableArrayList<PhotoModel> photoModels;
    private boolean loadMore;
    private boolean loading;
    private AuthenticationService authService;

    public MyMatchesViewModel(Context context) {
        authService = AuthenticationServiceImp.getInstance(context);
    }

    public void startRetrieveCookie(String username, String password) {
        setLoading(true);
        setLoadMore(true);
        if (authService.getTokenCookie() == null || authService.getTokenCookie().hasExpired()) {
            authService.login(username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onResponseReady,
                            this::onThrowError);
        } else {
            loadMore();
        }

    }

    @Bindable
    public ObservableArrayList<PhotoModel> getPhotoModels() {
        return photoModels;
    }

    public void setPhotoModels(ObservableArrayList<PhotoModel> photoModels) {
        this.photoModels = photoModels;
        notifyPropertyChanged(BR.photoModels);
    }

    @Bindable
    public boolean isLoadMore() {
        return loadMore;
    }

    private void setLoadMore(boolean loadMore) {
        this.loadMore = loadMore;
        notifyPropertyChanged(BR.loadMore);
    }

    @Bindable
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }

    public void loadMore() {

        for (int i = 0; i < 10; i++){
            photoModels.add(new PhotoModel("https://waldo-thumbs-staging.s3.amazonaws.com/medium/e73f1577-c599-40d0-af28-3784e789b241.jpg"));
            photoModels.add(new PhotoModel("https://waldo-thumbs-staging.s3.amazonaws.com/medium/7c312977-c8ba-490f-ba04-e93a6c6ee5f9.jpg"));
            photoModels.add(new PhotoModel("https://waldo-thumbs-staging.s3.amazonaws.com/medium/7da99626-1227-4d5a-8263-96ae75bb8e0b.jpg"));
            photoModels.add(new PhotoModel("https://waldo-thumbs-staging.s3.amazonaws.com/medium/16157b5b-41a8-47ff-bdc7-48a920d75e31.jpg"));
            photoModels.add(new PhotoModel("https://waldo-thumbs-staging.s3.amazonaws.com/medium/f52bfedb-046d-406d-8612-a0fa15439585.jpg"));
            photoModels.add(new PhotoModel("https://waldo-thumbs-staging.s3.amazonaws.com/medium/068692a7-91dc-42bd-b1c4-c8e463043939.jpg"));
        }
        notifyPropertyChanged(BR.photoModels);
        notifyChange();
        setLoadMore(false);
    }

    private void onResponseReady(Response<ResponseBody> body) {
        if (body.code() == 404) {
            setLoading(false);
            loadMore();
        }
    }

    private void onThrowError(Throwable throwable) {
        throwable.printStackTrace();
    }
}
