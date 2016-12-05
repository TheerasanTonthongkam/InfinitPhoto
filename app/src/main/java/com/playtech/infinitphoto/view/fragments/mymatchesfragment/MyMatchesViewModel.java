package com.playtech.infinitphoto.view.fragments.mymatchesfragment;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import com.playtech.infinitphoto.model.PhotoModel;
import com.playtech.infinitphoto.BR;
import com.playtech.infinitphoto.model.PhotoSet;
import com.playtech.infinitphoto.model.RawAlbumData;
import com.playtech.infinitphoto.service.AlumServiceImp;
import com.playtech.infinitphoto.service.AuthenticationServiceImp;
import com.playtech.infinitphoto.service.interfaces.AlumService;
import com.playtech.infinitphoto.service.interfaces.AuthenticationService;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyMatchesViewModel extends BaseObservable {
    private ObservableArrayList<PhotoModel> photoModels;
    private boolean loadMore;
    private boolean loading;
    private boolean error;
    final private AuthenticationService authService;
    private AlumService alumService;
    private int photoModelsSize = 0;
    private int maxSize = 0;

    public MyMatchesViewModel(Context context) {
        authService = AuthenticationServiceImp.getInstance(context);
    }

    public void startRetrieveCookie(String username, String password) {
        setLoading(true);
        if (authService.getTokenCookie() == null || authService.getTokenCookie().hasExpired()) {
            authService.login(username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onResponseReady,
                            this::onThrowError);
        } else {
            alumService = AlumServiceImp.getInstance(authService.getTokenCookie());
            setLoading(true);
            loadData();
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

    private void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }

    @Bindable
    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
        notifyPropertyChanged(BR.error);
    }

    private void loadData() {
        alumService.getAlbums(photoModels.size())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadDataSuccess, this::onThrowError);
    }

    private void loadDataSuccess(RawAlbumData res) {
        setLoading(false);
        setLoadMore(false);
        maxSize = res.data.album.photos.total;
        ArrayList<PhotoSet> records = res.data.album.photos.records;
        for(PhotoSet ps : records) {
            photoModels.add(new PhotoModel(ps.urls.get(2).url));
        }
        if (photoModelsSize != photoModels.size()) {
            notifyPropertyChanged(BR.photoModels);
            photoModelsSize = photoModels.size();
        }
    }

    public void loadMore() {
        if (photoModelsSize < maxSize) {
            setLoadMore(true);
            setError(false);
            loadData();
        }
    }

    private void onResponseReady(Response<ResponseBody> body) {
        if (body.code() == 404) {
            setLoading(false);
            loadMore();
        } else {
            setError(true);
        }
    }

    private void onThrowError(Throwable throwable) {
        setError(true);
        setLoading(false);
        throwable.printStackTrace();
    }

    public int getMaxSize() {
        return maxSize;
    }
}
