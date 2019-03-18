package com.mujeeb.lastfm.di.module;

import com.mujeeb.lastfm.di.scope.AlbumInfoScope;
import com.mujeeb.lastfm.view.albumInfo.AlbumInfoActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildAlbumInfoActivityModule {

    @ContributesAndroidInjector(modules = AlbumInfoActivityModule.class)
    @AlbumInfoScope
    abstract AlbumInfoActivity albumInfoActivity();
}
