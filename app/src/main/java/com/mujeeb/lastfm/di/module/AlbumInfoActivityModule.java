package com.mujeeb.lastfm.di.module;

import com.mujeeb.lastfm.api.LastApiCall;
import com.mujeeb.lastfm.di.scope.AlbumInfoScope;
import com.mujeeb.lastfm.mvp.albumInfo.AlbumInfoContract;
import com.mujeeb.lastfm.mvp.albumInfo.AlbumInfoPresenter;
import com.mujeeb.lastfm.view.albumInfo.AlbumInfoActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AlbumInfoActivityModule {



    @AlbumInfoScope
    @Provides
    static AlbumInfoContract.Presenter provideAuthPresenter(AlbumInfoContract.View view, LastApiCall lastApiCall) {
        return new AlbumInfoPresenter(view, lastApiCall);
    }

    @AlbumInfoScope
    @Binds
    public abstract AlbumInfoContract.View provideView(AlbumInfoActivity albumInfoActivity);
}
