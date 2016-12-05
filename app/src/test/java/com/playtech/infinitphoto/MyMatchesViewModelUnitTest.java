package com.playtech.infinitphoto;

import android.content.Context;
import android.databinding.ObservableArrayList;

import com.playtech.infinitphoto.cookie.PersistentCookieStore;
import com.playtech.infinitphoto.model.Album;
import com.playtech.infinitphoto.model.AlbumsData;
import com.playtech.infinitphoto.model.Photo;
import com.playtech.infinitphoto.model.PhotoModel;
import com.playtech.infinitphoto.model.RawAlbumData;
import com.playtech.infinitphoto.schedulers.ThreadScheduler;
import com.playtech.infinitphoto.service.interfaces.AlbumService;
import com.playtech.infinitphoto.service.interfaces.AuthenticationService;
import com.playtech.infinitphoto.view.fragments.mymatchesfragment.MyMatchesViewModel;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.HttpCookie;
import java.util.ArrayList;

import rx.Observable;
import rx.Single;
import rx.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class MyMatchesViewModelUnitTest {

    private MyMatchesViewModel viewModel;
    @Mock private Context context;
    @Mock private PersistentCookieStore store;
    @Mock private ObservableArrayList<PhotoModel> photoModels;

    @Mock private AuthenticationService authService;
    @Mock private AlbumService albumService;

    @Mock private Throwable throwable;

    private RawAlbumData res;

    HttpCookie cookie;

    @Before
    public void before() {
        viewModel = new MyMatchesViewModel();
        ThreadScheduler scheduler = new ThreadScheduler(Schedulers.io(), Schedulers.immediate());
        viewModel.setThreadScheduler(scheduler);
        viewModel.setAlbumService(albumService);
        viewModel.setAuthService(authService);
        cookie = new HttpCookie("token", "name");
        authService.setPersistentCookieStore(store);

        res = new RawAlbumData();
        res.data = new AlbumsData();
        res.data.album = new Album();
        res.data.album.photos = new Photo();

        res.data.album.photos.total = 0;
        res.data.album.photos.records = new ArrayList<>();
    }

    @Test
    public void getPhotos_whenNoPhotos_returnNull() {
        ObservableArrayList<PhotoModel> photoModels = viewModel.getPhotoModels();
        assertThat(photoModels, is(nullValue()));
    }

    @Test
    public void getPhotos_whenHasPhotos_returnPhotos() {
        viewModel.setPhotoModels(photoModels);
        ObservableArrayList<PhotoModel> photos = viewModel.getPhotoModels();
        assertThat(photoModels, equalTo(photos));
    }

    @Test
    public void doLogin_whenNoCookies_login() {
        String username = "username";
        String password = "password";

        when(store.getTokenCookie()).thenReturn(null);
        when(authService.getTokenCookie()).thenReturn(null);
        when(authService.getLoginResponseCode(username, password))
                .thenReturn(Single.just(404));

        viewModel.startRetrieveCookie(username, password);

        boolean loading = viewModel.isLoading();
        assertThat(loading, is(false));
    }

    @Test
    public void doLogin_whenNoCookiesAndInvalidResponse_error() {
        String username = "username";
        String password = "password";

        when(store.getTokenCookie()).thenReturn(null);
        when(authService.getTokenCookie()).thenReturn(null);
        when(authService.getLoginResponseCode(username, password))
                .thenReturn(Single.just(401));

        viewModel.setPhotoModels(new ObservableArrayList<>());
        viewModel.startRetrieveCookie(username, password);

        boolean error = viewModel.isError();
        assertThat(error, is(true));
    }

    @Test
    public void sendResponse_404_hideLoad() {
        String username = "username";
        String password = "password";

        when(store.getTokenCookie()).thenReturn(null);
        when(authService.getTokenCookie()).thenReturn(null);
        when(authService.getLoginResponseCode(username, password))
                .thenReturn(Single.just(404));
        when(albumService.getAlbums(anyInt())).thenReturn(Observable.just(res));
        viewModel.setPhotoModels(new ObservableArrayList<>());
        viewModel.startRetrieveCookie(username, password);

        boolean loading = viewModel.isLoading();
        assertThat(loading, is(false));

    }

    @Test
    public void doLogin_loginAlready_loadData() {
        String username = "username";
        String password = "password";

        when(store.getTokenCookie()).thenReturn(cookie);
        when(authService.getTokenCookie()).thenReturn(cookie);
        when(authService.isTokenExpire()).thenReturn(false);
        when(albumService.getAlbums(anyInt())).thenReturn(Observable.just(res));

        viewModel.setPhotoModels(new ObservableArrayList<>());
        viewModel.startRetrieveCookie(username, password);

        verify(albumService).getAlbums(anyInt());
    }

    @Test
    public void createViewModel_withConstructor_servicesWereInited() {
        viewModel = new MyMatchesViewModel(store);
        AlbumService albumService = viewModel.getAlbumService();
        assertThat(albumService, is(notNullValue()));

        AuthenticationService authService = viewModel.getAuthService();
        assertThat(authService, is(notNullValue()));
    }

    @Test
    public void doLogin_whenError_throwError() {
        String username = "username";
        String password = "password";

        when(store.getTokenCookie()).thenReturn(null);
        when(authService.getTokenCookie()).thenReturn(null);
        Exception someException = new Exception();
        doReturn(Single.error(someException)).when(authService).getLoginResponseCode(username, password);

        viewModel.setPhotoModels(new ObservableArrayList<>());
        viewModel.startRetrieveCookie(username, password);

        boolean error = viewModel.isError();
        assertThat(error, is(true));

    }

    @Test
    public void getPhotos_whenError_trowError() {
        String username = "username";
        String password = "password";

        when(store.getTokenCookie()).thenReturn(cookie);
        when(authService.getTokenCookie()).thenReturn(cookie);
        when(authService.isTokenExpire()).thenReturn(false);
        Exception someException = new Exception();
        doReturn(Observable.just(someException)).when(albumService).getAlbums(anyInt());

        viewModel.setPhotoModels(new ObservableArrayList<>());
        viewModel.startRetrieveCookie(username, password);

        boolean error = viewModel.isError();
        assertThat(error, is(true));
    }
}
