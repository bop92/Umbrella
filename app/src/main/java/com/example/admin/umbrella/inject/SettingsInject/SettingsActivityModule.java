package com.example.admin.umbrella.inject.SettingsInject;

import com.example.admin.umbrella.view.MainActivity.MainActivityPresenter;
import com.example.admin.umbrella.view.SettingsActivity.SettingsActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsActivityModule {

    @Provides
    SettingsActivityPresenter provideSettingsActivityPresenter(){

        return new SettingsActivityPresenter();
    }

}

