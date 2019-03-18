package com.mujeeb.lastfm.di.module;

import com.mujeeb.lastfm.di.qualifier.Remote;
import com.mujeeb.lastfm.di.qualifier.Repository;
import com.mujeeb.lastfm.di.scope.ApplicationScope;
import com.mujeeb.lastfm.repository.AlbumDataSource;
import com.mujeeb.lastfm.repository.AlbumRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Repository
    @ApplicationScope
    AlbumDataSource provideAlbumRepository(@Remote AlbumDataSource dataSource) {
        return new AlbumRepository(dataSource);
    }
}
