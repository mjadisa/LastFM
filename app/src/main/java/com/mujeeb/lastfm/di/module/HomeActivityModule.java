package com.mujeeb.lastfm.di.module;

import com.mujeeb.lastfm.api.LastApiCall;
import com.mujeeb.lastfm.di.scope.HomeScope;
import com.mujeeb.lastfm.mvp.home.HomeContract;
import com.mujeeb.lastfm.mvp.home.HomePresenter;
import com.mujeeb.lastfm.view.home.HomeActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class HomeActivityModule {

    @HomeScope
    @Provides
    static HomeContract.Presenter provideAuthPresenter(HomeContract.View view, LastApiCall lastApiCall) {
        return new HomePresenter(view, lastApiCall);
    }

    @HomeScope
    @Binds
    public abstract HomeContract.View provideView(HomeActivity homeActivity);
}
