package com.mujeeb.lastfm.di.module;


import com.mujeeb.lastfm.di.scope.HomeScope;
import com.mujeeb.lastfm.view.home.AlbumListRecyclerViewAdapter;
import com.mujeeb.lastfm.view.home.AlbumSelectedInterface;
import com.mujeeb.lastfm.view.home.HomeActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class AdapterModule {
    @Provides
    @HomeScope
    public AlbumListRecyclerViewAdapter provideAlbumListRecyclerViewAdapter(AlbumSelectedInterface albumSelectedInterface) {
        return new AlbumListRecyclerViewAdapter(albumSelectedInterface);
    }


    @Provides
    @HomeScope
    public AlbumSelectedInterface provideAlbumSelectedInterface(HomeActivity homeActivity) {
        return homeActivity;
    }

}