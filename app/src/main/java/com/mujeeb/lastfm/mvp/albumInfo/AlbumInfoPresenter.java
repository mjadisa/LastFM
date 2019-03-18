package com.mujeeb.lastfm.mvp.albumInfo;

import android.support.annotation.Nullable;

import com.mujeeb.lastfm.api.LastApiCall;
import com.mujeeb.lastfm.model.albumInfo.Album;
import com.mujeeb.lastfm.model.albumInfo.AlbumDetails;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.mujeeb.lastfm.ConstantString.ALBUM_GET_INFO;
import static com.mujeeb.lastfm.ConstantString.API_KEY;
import static com.mujeeb.lastfm.ConstantString.FORMAT;

public class AlbumInfoPresenter implements AlbumInfoContract.Presenter {

    private final AlbumInfoContract.View view;
    private final LastApiCall lastApiCall;
    private final CompositeDisposable compositeDisposable;


    public AlbumInfoPresenter(AlbumInfoContract.View view, LastApiCall lastApiCall) {
        this.view = view;
        this.lastApiCall = lastApiCall;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getData(String album, String artist) {
        if (album != null && artist != null) {
            compositeDisposable.add(lastApiCall.getAlbumDetails(ALBUM_GET_INFO,
                    album, artist, API_KEY, FORMAT)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> view.showProgress())
                    .doOnTerminate(view::hideProgress)
                    .subscribe(this::handleResult, this::handleError));
        }
    }

    private void handleResult(@Nullable AlbumDetails albumDetailsResponse) {
        Album album = null;
        if (albumDetailsResponse != null && albumDetailsResponse.getAlbum() != null) {
            album = albumDetailsResponse.getAlbum();
        }

        if (album != null) {
            view.showAlbumName(album.getName());
            view.showArtistName(album.getArtist());
            view.showWiki(album.getWiki().getContent());

        }
    }


    private void handleError(Throwable throwable) {
        if (throwable.getMessage() != null) {
            view.showError(throwable.getMessage());
        }
    }

    @Override
    public void stop() {
        compositeDisposable.clear();
    }
}
