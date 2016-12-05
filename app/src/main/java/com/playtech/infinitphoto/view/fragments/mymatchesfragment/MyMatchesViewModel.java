package com.playtech.infinitphoto.view.fragments.mymatchesfragment;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import com.playtech.infinitphoto.cookie.PersistentCookieStore;
import com.playtech.infinitphoto.model.PhotoModel;
import com.playtech.infinitphoto.BR;
import com.playtech.infinitphoto.model.PhotoSet;
import com.playtech.infinitphoto.model.RawAlbumData;
import com.playtech.infinitphoto.schedulers.ThreadScheduler;
import com.playtech.infinitphoto.service.ServiceProvider;
import com.playtech.infinitphoto.service.interfaces.AlbumService;
import com.playtech.infinitphoto.service.interfaces.AuthenticationService;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyMatchesViewModel extends BaseObservable {
    private ObservableArrayList<PhotoModel> photoModels;
    private boolean loadMore;
    private boolean loading;
    private boolean error;
    private AuthenticationService authService;
    private AlbumService albumService;
    private int photoModelsSize = 0;
    private int maxSize = 0;

    private ThreadScheduler threadScheduler;

    public MyMatchesViewModel() {}

    public MyMatchesViewModel(PersistentCookieStore store) {
        authService = ServiceProvider.getAuthenticationServiceInstance(store);
        albumService = ServiceProvider.getAlumServiceInstance(authService.getTokenCookie());
    }

    public void startRetrieveCookie(String username, String password) {
        setLoading(true);
        if (authService.getTokenCookie() == null || authService.isTokenExpire()) {
            authService.getLoginResponseCode(username, password)
                    .subscribeOn(threadScheduler.getSubscribeScheduler())
                    .observeOn(threadScheduler.getAndroidMainTread())
                    .subscribe(this::onResponseReady,
                            this::onThrowError);
        } else {
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
        albumService.getAlbums(photoModels.size())
                .subscribeOn(threadScheduler.getSubscribeScheduler())
                .observeOn(threadScheduler.getAndroidMainTread())
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

    private void onResponseReady(int code) {
        if (code == 404) {
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

    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }

    public void setAlbumService(AlbumService albumService) {
        this.albumService = albumService;
    }

    public AuthenticationService getAuthService() {
        return authService;
    }

    public AlbumService getAlbumService() {
        return albumService;
    }

    public ThreadScheduler getThreadScheduler() {
        return threadScheduler;
    }

    public void setThreadScheduler(ThreadScheduler threadScheduler) {
        this.threadScheduler = threadScheduler;
    }
}
