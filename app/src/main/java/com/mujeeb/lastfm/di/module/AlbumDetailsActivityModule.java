package com.mujeeb.lastfm.di.module;

import com.mujeeb.lastfm.di.scope.AlbumDetailsScope;
import com.mujeeb.lastfm.view.albumInfo.AlbumInfoActivity;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AlbumDetailsActivityModule {
    @AlbumDetailsScope
    @Binds
    abstract AlbumInfoActivity provideAlbumInfoActivity(AlbumInfoActivity albumInfoActivity);
}
