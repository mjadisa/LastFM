package com.mujeeb.lastfm.di.module;

import com.mujeeb.lastfm.di.scope.HomeScope;
import com.mujeeb.lastfm.view.home.HomeActivity;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class HomeActivityModule {
    @HomeScope
    @Binds
    abstract HomeActivity provideHomeActivity(HomeActivity homeActivity);
}
