package com.example.githubusers.di;

import android.app.Application;

import com.example.githubusers.repository.UserRepository;

import javax.inject.Singleton;

import androidx.paging.PagedList;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Singleton
    @Provides
    PagedList.Config providePageListConfig() {
        return (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(UserRepository.PAGE_SIZE).build();
    }

    @Provides
    Application getApplication() {
        return application;
    }
}
