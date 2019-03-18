package com.mujeeb.lastfm.di.module;

import android.arch.lifecycle.ViewModelProviders;

import com.mujeeb.lastfm.di.qualifier.Repository;
import com.mujeeb.lastfm.di.scope.AlbumDetailsScope;
import com.mujeeb.lastfm.repository.AlbumDataSource;
import com.mujeeb.lastfm.view.albumInfo.AlbumInfoActivity;
import com.mujeeb.lastfm.viewmodel.AlbumInfoViewModel;
import com.mujeeb.lastfm.viewmodel.AlbumInfoViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class AlbumDetailsModule {
    @AlbumDetailsScope
    @Provides
    public AlbumInfoViewModel provideAlbumInfoViewModel(AlbumInfoViewModelFactory albumInfoViewModelFactory, AlbumInfoActivity albumInfoActivity) {
        return ViewModelProviders.of(albumInfoActivity, albumInfoViewModelFactory)
                .get(AlbumInfoViewModel.class);
    }

    @AlbumDetailsScope
    @Provides
    public AlbumInfoViewModelFactory provideAlbumInfoViewModelFactory(
            @Repository AlbumDataSource albumRepository) {
        return new AlbumInfoViewModelFactory(albumRepository);
    }


}
