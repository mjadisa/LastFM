package com.mujeeb.lastfm.di.component;

import android.app.Application;

import com.mujeeb.lastfm.LastFMApp;
import com.mujeeb.lastfm.di.module.BuildAlbumDetailsActivityModule;
import com.mujeeb.lastfm.di.module.BuildHomeActivityModule;
import com.mujeeb.lastfm.di.module.NetworkModule;
import com.mujeeb.lastfm.di.module.RepositoryModule;
import com.mujeeb.lastfm.di.scope.ApplicationScope;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Component(modules = {AndroidInjectionModule.class, BuildAlbumDetailsActivityModule.class, BuildHomeActivityModule.class,
        RepositoryModule.class, NetworkModule.class})
@ApplicationScope
public interface AppComponent {
    void inject(LastFMApp lastFMApp);

    @Component.Builder
    interface Builder {
        AppComponent build();

        @BindsInstance
        Builder application(Application application);
    }
}
