package com.mujeeb.lastfm.di.module;

import com.mujeeb.lastfm.di.scope.HomeScope;
import com.mujeeb.lastfm.view.home.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildHomeActivityModule {
    @ContributesAndroidInjector(modules = {HomeModule.class, AdapterModule.class})
    @HomeScope
    abstract HomeActivity homeActivity();
}
