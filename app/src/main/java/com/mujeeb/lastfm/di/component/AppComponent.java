package com.mujeeb.lastfm.di.component;

import android.app.Application;

import com.mujeeb.lastfm.LastFMApp;
import com.mujeeb.lastfm.di.module.BuildAlbumInfoActivityModule;
import com.mujeeb.lastfm.di.module.BuildHomeActivityModule;
import com.mujeeb.lastfm.di.module.NetworkModule;
import com.mujeeb.lastfm.di.scope.ApplicationScope;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Component(modules = {AndroidInjectionModule.class, BuildAlbumInfoActivityModule.class,
        BuildHomeActivityModule.class, NetworkModule.class})
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
