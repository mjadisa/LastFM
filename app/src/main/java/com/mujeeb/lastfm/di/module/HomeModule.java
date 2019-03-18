package com.mujeeb.lastfm.di.module;

import android.arch.lifecycle.ViewModelProviders;

import com.mujeeb.lastfm.di.qualifier.Repository;
import com.mujeeb.lastfm.di.scope.HomeScope;
import com.mujeeb.lastfm.repository.AlbumDataSource;
import com.mujeeb.lastfm.view.home.HomeActivity;
import com.mujeeb.lastfm.viewmodel.HomeViewModel;
import com.mujeeb.lastfm.viewmodel.HomeViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    @HomeScope
    @Provides
    public HomeViewModel provideHomeViewModel(HomeActivity homeActivity,
                                              HomeViewModelFactory homeViewModelFactory) {
        return ViewModelProviders.of(homeActivity, homeViewModelFactory).get(HomeViewModel.class);
    }

    @HomeScope
    @Provides
    public HomeViewModelFactory provideHomeViewModelFactory(
            @Repository AlbumDataSource albumRepository) {
        return new HomeViewModelFactory(albumRepository);
    }

}

