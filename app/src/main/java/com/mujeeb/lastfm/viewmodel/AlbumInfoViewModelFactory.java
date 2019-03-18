package com.mujeeb.lastfm.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.mujeeb.lastfm.repository.AlbumDataSource;

public class AlbumInfoViewModelFactory implements ViewModelProvider.Factory {
    private final AlbumDataSource albumRepository;

    public AlbumInfoViewModelFactory(AlbumDataSource albumRepository) {
        this.albumRepository = albumRepository;
    }


    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AlbumInfoViewModel.class)) {
            return (T) new AlbumInfoViewModel(albumRepository);
        }
        throw new IllegalArgumentException("The class has to be an instance of: "
                + AlbumInfoViewModel.class.getSimpleName());
    }
}
