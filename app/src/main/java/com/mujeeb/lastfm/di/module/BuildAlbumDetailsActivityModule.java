package com.mujeeb.lastfm.di.module;

import com.mujeeb.lastfm.di.scope.AlbumDetailsScope;
import com.mujeeb.lastfm.view.albumInfo.AlbumInfoActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildAlbumDetailsActivityModule {

    @ContributesAndroidInjector(modules = AlbumDetailsModule.class)
    @AlbumDetailsScope
    abstract AlbumInfoActivity albumInfoActivity();
}
