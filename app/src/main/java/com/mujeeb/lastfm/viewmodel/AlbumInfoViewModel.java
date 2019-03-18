package com.mujeeb.lastfm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.mujeeb.lastfm.model.albumInfo.Album;
import com.mujeeb.lastfm.model.albumInfo.AlbumDetails;
import com.mujeeb.lastfm.repository.AlbumDataSource;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AlbumInfoViewModel extends ViewModel {

    private final MutableLiveData<Album> albumDetailsObservable;
    private final CompositeDisposable compositeDisposable;
    private final AlbumDataSource albumRepository;
    private final ObservableBoolean progressObservable;

    public AlbumInfoViewModel(AlbumDataSource albumRepository) {
        this.albumRepository = albumRepository;
        albumDetailsObservable = new MutableLiveData<>();
        compositeDisposable = new CompositeDisposable();
        progressObservable = new ObservableBoolean(false);
    }

    public LiveData<Album> getAlbumInfoObservable() {
        return albumDetailsObservable;
    }

    public ObservableBoolean getProgressObservable() {
        return progressObservable;
    }

    public void getAlbumInfo(String albumArtist, String albumName) {
        compositeDisposable.add(albumRepository.getAlbumDetails(albumName, albumArtist)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> progressObservable.set(true))
                .doOnEvent((success, failure) -> progressObservable.set(false))
                .subscribe(this::handleSuccess, this::handleFailure));
    }

    private void handleSuccess(AlbumDetails albumDetails) {
        albumDetailsObservable.setValue(albumDetails.getAlbum());
    }

    private void handleFailure(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
